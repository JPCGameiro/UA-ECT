library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity IterMultCore is
	generic(numBits : positive := 8);
		port(clk     : in  std_logic;
			  reset   : in  std_logic;
			  start   : in  std_logic;
			  done    : out std_logic;
			  operand0: in  std_logic_vector((numbits-1) downto 0);
			  operand1: in  std_logic_vector((numbits-1) downto 0);
			  ovf     : out std_logic;
			  result  : out std_logic_vector(((2*numbits)-1) downto 0));
end IterMultCore;

architecture Shell of IterMultCore is
	signal s_regsShift, s_regsInit, s_accEnable : std_logic;
	signal s_extOperand0          : std_logic_vector((2*numBits)-1 downto 0);
	signal s_multiplicand        : std_logic_vector((2*numBits)-1 downto 0);
	signal s_multiplier          : std_logic_vector((numBits-1) downto 0);
	signal s_check               : std_logic;
	signal s_result              : std_logic_vector((2*numBits)-1 downto 0);
	signal s_operand0, s_operand1: std_logic_vector((numBits-1) downto 0);
begin	


	proc_transformation0 : process(operand0)
	begin
		if(operand0(numBits-1) = '1')then
			s_operand0 <= std_logic_vector(unsigned(operand0 xor "11111111") + 1);
		else
			s_operand0 <= operand0;
		end if;
	end process;
	
	proc_transformation1 : process(operand1)
	begin
		if(operand1(numBits-1) = '1')then
			s_operand1 <= std_logic_vector(unsigned(operand1 xor "11111111") + 1);
		else
			s_operand1 <= operand1;
		end if;
	end process;
	
	
	
	control_core : entity work.MultCtrlUnit(Behavioral)
		generic map(numBits       => numBits)
			port map(clk           => clk,
						start         => start,
						reset         => reset,
						done          => done,
						regsShift     => s_regsShift,
						regsInit      => s_regsInit,
						accEnable     => s_accEnable,
						multiplierLsb => s_multiplier(0));
	
	s_extOperand0((2 * numBits) - 1 downto numBits) <= (others => '0');
   s_extOperand0((numBits - 1) downto 0) <= s_operand0;
	
	multiplicand_reg : entity work.ShiftRegister(Behavioral)
		generic map(N          => 2*numBits)
			port map(clk        => clk,
						reset      => reset,
						load       => s_regsInit,
						shiftLeft  => s_regsShift,
						shiftRight => '0',
						shLeftin   => '0',
						shRightin  => '0',
						dataIn     => s_extOperand0,
						dataOut    => s_multiplicand);
						
	multiplier_reg : entity work.ShiftRegister(Behavioral)
		generic map(N          => numBits)
			port map(clk        => clk,
						reset      => reset,
						load       => s_regsInit,
						shiftLeft  => '0',
						shiftRight => s_regsShift,
						shLeftin   => '0',
						shRightin  => '0',
						dataIn     => s_operand1,
						dataOut    => s_multiplier);
	 
	 addSub_core : entity work.AddSubRegN(Behavioral)
		generic map(N        => 2*numBits)
			port map(clk      => clk,
						reset    => reset,
						enable   => s_accEnable,
						addSub   => '1',
						loadData => (others => '0'),
						load     => s_regsInit,
						dataIn   => s_multiplicand,
						dataOut  => s_result);
	


		--verificação do sinal que o resultado vai apresentar e do da ocorrência de overflow
	proc_checK : process(operand0, operand1)
	begin
		if(((operand0(numBits-1)='1' and operand1(numBits-1)='1')) or ((operand0(numBits-1)='0' and operand1(numBits-1)='0')))then
			s_check <= '1';
		else
			s_check <= '0';
		end if;
	end process;
	
	proc_signal : process(s_check, s_result)
	begin
		if(s_check = '0')then
				result <= std_logic_vector(unsigned(s_result xor "1111111111111111") + 1);
				if(s_result((2*numBits)-1) = '1')then
					ovf <= '1';
				else
					ovf <= '0';
				end if;
		else
				result <= std_logic_vector(s_result);
				if(s_result((2*numBits)-1) = '1')then
					ovf <= '1';
				else
					ovf <= '0';
				end if;
		end if;
	end process;
						
end Shell;
						
	
	