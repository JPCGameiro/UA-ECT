library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity MainCtrlUnit is
	port(clk      : in  std_logic;
		  reset    : in  std_logic;
		  start    : in  std_logic;
		  sum      : in  std_logic;
		  sub      : in  std_logic;
		  mult     : in  std_logic;
		  div      : in  std_logic;
		  multDone : in  std_logic;
		  divDone  : in  std_logic;
		  multStart: out std_logic;
		  divStart : out std_logic;
		  multSel  : out std_logic_vector(1 downto 0));
end MainCtrlUnit;

architecture Behavioral of MainCtrlUnit is
	type TState is (ST_IDLE, ST_SUM, ST_SUB, ST_MULT_START, ST_MULT_WAIT, ST_DIV_START, ST_DIV_WAIT);
	signal s_state : TState;
begin
	process(clk)
	begin
		if(rising_edge(clk))then
			if(reset = '1')then
				s_state   <= ST_IDLE;
				multSel   <= "00";
				multStart <= '0';
				divStart  <= '0';
			
			else
				case s_state is
				
				when ST_IDLE =>
					if(start  = '1')then
						if(sum = '1')then        --soma
							s_state   <= ST_SUM;
							multSel   <= "00";
							multStart <= '0';
							divStart  <= '0';
						elsif(sub = '1')then     --subtracção
							s_state   <= ST_SUB;
							multSel   <= "01";
							multStart <= '0';
							divStart  <= '0';
						elsif(mult = '1')then    --multiplicação
							s_state   <= ST_MULT_START;
							multSel   <= "10";
							multStart <= '1';
							divStart  <= '0';
						elsif(div = '1')then      --divisão
							s_state   <= ST_DIV_START;
							multSel   <= "11";
							multStart <= '0';
							divStart  <= '1';
						end if;
					end if;
				
				
				when ST_SUM =>
					s_state <= ST_IDLE;
					multSel   <= "00";
					multStart <= '0';
					divStart  <= '0';
					
				when ST_SUB =>
					s_state <= ST_IDLE;
					multSel   <= "01";
					multStart <= '0';
					divStart  <= '0';
					
					
				
				when ST_MULT_START =>
					s_state   <= ST_MULT_WAIT;
               multSel   <= "10";
					multStart <= '0';
               divStart  <= '0';
					
				
            when ST_MULT_WAIT  =>
               if (multDone = '1') then
                  s_state <= ST_IDLE;
                  multSel   <= "10";
						multStart <= '0';
						divStart  <= '0';
               end if;
					
					
				when ST_DIV_START =>
					s_state   <= ST_DIV_WAIT;
               multSel   <= "11";
					multStart <= '0';
               divStart  <= '0';
					
				 when ST_DIV_WAIT   =>
               if (divDone = '1') then
                  s_state <= ST_IDLE;
                  multSel   <= "11";
						multStart <= '0';
						divStart  <= '0';
               end if;
				end case;
			end if;
		end if;
	end process;
end Behavioral;

							
							
							
							