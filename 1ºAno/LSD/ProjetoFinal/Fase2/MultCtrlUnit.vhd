library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity MultCtrlUnit is
	generic(numBits        : positive := 8);
		port(clk            : in  std_logic;
			  reset          : in  std_logic;
			  start          : in  std_logic;
			  multiplierLsb  : in  std_logic;
			  done           : out std_logic;
			  regsShift      : out std_logic;
			  regsInit       : out std_logic;
			  accEnable      : out std_logic);
end MultCtrlUnit;

architecture Behavioral of MultCtrlUnit is
	type TState is (ST_IDLE, ST_INIT, ST_BIT_TEST, ST_RES_ACC, ST_SHIFT);
	signal s_state: TState;
	
	subtype TCounter is natural range 0 to numBits;
   signal iterCnt : TCounter;
begin
	process(clk)
	begin
		if(rising_edge(clk))then
			if(reset = '1')then
				s_state   <= ST_IDLE;
				done      <= '1';
				regsShift <= '0';
				regsInit  <= '0';
				accEnable <= '0';
				iterCnt   <= 0;
			else
				case s_state is
					
					when ST_IDLE =>
						if(start = '1')then
							s_state   <= ST_INIT;
							done      <= '0';
							regsShift <= '0';
							regsInit  <= '1';
							accEnable <= '0';
						end if;
						iterCnt <= 0;
					
					when ST_INIT =>
						s_state   <= ST_BIT_TEST;
						done      <= '0';
						regsInit  <= '0';
						
					when ST_BIT_TEST =>
						if(multiplierLsb = '1')then
							s_state   <= ST_RES_ACC;
							accEnable <= '1';
						else
							s_state   <= ST_SHIFT;
						   regsShift <= '1';
						end if;
						iterCnt <= iterCnt + 1;
						
					when ST_RES_ACC =>
						s_state   <= ST_SHIFT;
						accEnable <= '0';
						regsShift <= '1';
						
					when ST_SHIFT =>
						if(iterCnt < numbits)then
							s_state <= ST_BIT_TEST;
						else
							s_state <= ST_IDLE;
							done      <= '1';
						end if;
						regsShift <= '0';
					end case;
				end if;
			end if;
		end process;
end Behavioral;
							
						
			