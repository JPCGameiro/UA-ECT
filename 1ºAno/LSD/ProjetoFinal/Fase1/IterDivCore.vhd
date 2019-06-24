library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity IterDivCore is
	generic(numBits   : positive := 8);
		port(clk       : in  std_logic;
			  reset     : in  std_logic;
			  start     : in  std_logic;
			  operand0  : in  std_logic_vector((numBits-1) downto 0);
			  operand1  : in  std_logic_vector((numBits-1) downto 0);
			  done      : out std_logic;
			  result    : out std_logic_vector((numbits-1) downto 0);
			  ovf       : out std_logic);
end IterDivCore;

architecture Shell of IterDivCore is
	signal s_dividend, s_divisor : std_logic_vector(((2*numBits)-1) downto 0);
	signal s_parDataOut          : std_logic_vector(((2*numBits)-1) downto 0);
	signal s_addSub              : std_logic_vector(((2*numBits)-1) downto 0);
	signal s_result              : std_logic_vector((numBits-1) downto 0);
	signal s_enable, s_regsInit, s_op, s_shiftd, s_shiftq, s_bitq: std_logic;
	signal s_operand0, s_operand1: std_logic_vector((numBits-1) downto 0);
	signal s_check               : std_logic;       
begin
	
	
	--transformação dos números nos seus módulos para a conta
	--o sinal é adicionado no fim da operação
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
	
	

	--dividendo alinhado à esquerda	
	s_dividend(((2*numBits)-1) downto numBits) <= (others => '0');
	s_dividend((numBits-1) downto 0) <= s_operand0;
	
	--divisor alinhado à direita
	s_divisor(((2*numBits)-1) downto numBits) <= s_operand1;
	s_divisor((numBits-1) downto 0) <= (others => '0');
	
	--Unidade de Controlo
	control_unit : entity work.DivCtrlUnit(Behavioral)
							generic map(numBits   => numBits)
								port map(clk       => clk,
										   reset     => reset,
										   start     => start,
										   Msbit     => s_addSub((2*numBits)-1),
										   enable    => s_enable,
										   regsInit  => s_regsInit,
										   op        => s_op,
										   done      => done,
										   shiftd    => s_shiftd,
										   shiftq    => s_shiftq,
										   bitq      => s_bitq);
	
	--ShiftRegister Divisor
	shift_divisor : entity work.ShiftRegister(Behavioral)
							generic map(N          => 2*numBits)
								port map(clk        => clk,
										   reset      => reset,
										   load       => s_regsInit,
										   shiftLeft  => '0',
										   shiftRight => s_shiftd,
										   shLeftin   => '0',
										   shRightin  => '0',
										   dataIn     => s_divisor,
										   dataOut    => s_parDataOut);
											
	--ShiftRegister Quociente
	shift_quocie : entity work.ShiftRegister(Behavioral)
							generic map(N          => numBits)
								port map(clk        => clk,
										   reset      => reset,
										   load       => s_regsInit,
										   shiftLeft  => s_shiftq,
										   shiftRight => '0',
										   shLeftin   => s_bitq,
										   shRightin  => '0',
										   dataIn     => (others => '0'),
										   dataOut    => s_result);
	
	--entidade AddSubRegN
	addSub_core : entity work.AddSubRegN(Behavioral)
							generic map(N          => 2*numbits)
								port map(clk        => clk,
										   reset      => reset,
										   load       => s_regsInit,
										   enable     => s_enable,
										   addSub     => s_op,
										   loadData   => s_dividend,
										   dataIn     => s_parDataOut,
										   dataOut    => s_addSub);
											
	
	
	--verificação do sinal que o resultado vai apresentar e da ocorrência de overflow
	proc_checK : process(operand0, operand1)
	begin
		if(((operand0(numBits-1)='1' and operand1(numBits-1)='1')) or ((operand0(numBits-1)='0' and operand1(numBits-1)='0')))then
			s_check <= '1';
		else
			s_check <= '0';
		end if;
		if(operand1 = "00000000")then
			ovf <= '1';
		else
			ovf <= '0';
		end if;
	end process;
	
	proc_signal : process(s_check, s_result)
	begin
		if(s_check = '0')then
				result <= std_logic_vector(unsigned(s_result xor "11111111") + 1);
		else
				result <= std_logic_vector(s_result);
		end if;
	end process;
	
			
	
	
end Shell;