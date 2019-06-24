library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity SqrCtrlUnit is
		generic(numBits   : positive := 16);
			port(clk       : in  std_logic;
				  reset     : in  std_logic;
				  start     : in  std_logic;
			     s         : in  std_logic_vector((numBits-1) downto 0);
				  operand   : in  std_logic_vector((numBits-1) downto 0);
				  regsShift : out std_logic;
				  regsInit  : out std_logic;
				  loadShift : out std_logic;
				  enables   : out std_logic;
				  enabled   : out std_logic;
				  done      : out std_logic;
				  result    : out std_logic_vector((numBits-1) downto 0));
end SqrCtrlUnit;
	

architecture Behavioral of SqrCtrlUnit is
	type TState is (ST_IDLE, ST_INIT, ST_D_SUM, ST_D_SHIFTIN, ST_SHIFT_D, ST_S_SUM, ST_S_TEST);
	signal s_state : TState;
	
	subtype TCounter is natural range 0 to numBits;
	signal cnt : TCounter;                      ---serve para a implementação alternativa
	
	signal s_result : unsigned((numBits-1) downto 0);

begin
	process(clk)
	begin
		if(rising_edge(clk))then
			if(reset = '1')then
				s_state   <= ST_IDLE;
				done      <= '1';
				enabled   <= '0';
				enables   <= '0';
				loadShift <= '0';
				regsInit  <= '0';
				regsShift <= '0';
			else
				
				case s_state is
					
					when ST_IDLE =>
						if(start = '1')then
							s_state   <= ST_INIT;
							done      <= '0';
							enabled   <= '0';
							enables   <= '0';
							loadShift <= '0';
							regsInit  <= '1';
							regsShift <= '0';
							cnt       <= 0;
						else
							s_state   <= ST_IDLE;
							done      <= '1';
							enabled   <= '0';
							enables   <= '0';
							loadShift <= '0';
							regsInit  <= '0';
							regsShift <= '0';
						end if;
						
					
					when ST_INIT =>
						s_state   <= ST_D_SUM;
						done      <= '0';
						enabled   <= '1';
						enables   <= '0';
						loadShift <= '0';
						regsInit  <= '0';
						regsShift <= '0';
						
							
					
					when ST_D_SUM =>
						s_state   <= ST_D_SHIFTIN;
						done      <= '0';
						enabled   <= '0';
						enables   <= '0';
						loadShift <= '1';
						regsInit  <= '0';
						regsShift <= '0';
						cnt       <= cnt+1;
					
					when ST_D_SHIFTIN =>
						s_state   <= ST_SHIFT_D;
						done      <= '0';
						enabled   <= '0';
						enables   <= '0';
						loadShift <= '0';
						regsInit  <= '0';
						regsShift <= '1';
						
					when ST_SHIFT_D =>
						s_state   <= ST_S_SUM;
						done      <= '0';
						enabled   <= '0';
						enables   <= '1';
						loadShift <= '0';
						regsInit  <= '0';
						regsShift <= '0';
					
					when ST_S_SUM =>
						s_state   <= ST_S_TEST;
						done      <= '0';
						enabled   <= '0';
						enables   <= '0';
						loadShift <= '0';
						regsInit  <= '0';
						regsShift <= '0';
					
					when ST_S_TEST =>
						if(s <= operand)then
							s_state <= ST_INIT;
							enabled <= '1';
						else
							s_state <= ST_IDLE;
							done    <= '1';
						end if;
					end case;
				end if;
			end if;
		end process;
		
		proc_result :process(operand, cnt, s_result)
		begin
			if(unsigned(operand) < "1100100")then
				s_result <= to_unsigned(cnt+2, (numBits));
				result   <= std_logic_vector(s_result);
			else
				s_result <= to_unsigned(cnt+3, (numBits));
				result   <= std_logic_vector(s_result);
			end if;
		end process;
			
end Behavioral;
						
						