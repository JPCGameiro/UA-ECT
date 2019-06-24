library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity DrinksFSM is
	port(reset : in  std_logic;
		  clk   : in  std_logic;
		  V     : in  std_logic;
		  C     : in  std_logic;
		  drink : out std_logic);
end DrinksFSM;

architecture Behavioral of DrinksFSM is
	type TState is (E0, E1, E2, E3);
	signal s_currentState, s_nextState : TState;
	signal cntV, cntC : std_logic_vector(1 downto 0);
begin
	proc_0 : process(clk)
	begin
		if(rising_edge(clk))then
			if(reset = '0')then
				s_currentState <= E0;
			else
				s_currentState <= s_nextState;
			end if;
		end if;
	end process;
	
	proc_1 : process(s_currentState, V, C)
	begin
		case(s_currentState) is
		
		when E0 => 
			cntV  <= "00";
			cntC  <= "00";
			drink <= '0';
			if(V = '1')then
				s_nextState <= E1;
			elsif(C = '1')then
				s_nextState <= E2;
			else
				s_nextState <= E0;
			end if;
		
		when E1 =>
			cntV  <= "01";
			cntC  <= "00";
			drink <= '0';
			if(V = '1')then
				s_nextState <= E2;
			elsif(c = '1')then
				s_nextState <= E3;
			else 
				s_nextState <= E1;
			end if;
		
		when E2 =>
			cntV  <= "10";
			cntC  <= "01";
			drink <= '0';
			if(V = '1')then
				s_nextState <= E3;
			elsif(c = '1')then
				s_nextState <= E3;
			else
				s_nextState <= E2;
			end if;
			
		when E3 =>
			cntV  <= "00";
			cntC  <= "00";
			drink <= '1';
			s_nextState <= E0;
			
		end case;
	end process;
end Behavioral;
			
		
		  