library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity DivCtrlUnit is
	generic(numBits   :   positive := 8);
		port(clk       : in  std_logic;
			  reset     : in  std_logic;
			  start     : in  std_logic;
			  Msbit     : in  std_logic;
			  enable    : out std_logic;
			  regsInit  : out std_logic;
			  op        : out std_logic;
			  done      : out std_logic;
			  shiftd    : out std_logic;
			  shiftq    : out std_logic;
			  bitq      : out std_logic);
end DivCtrlUnit;

architecture Behavioral of DivCtrlUnit is
	type TState is (ST_IDLE, ST_INIT, ST_DIV_SHIFT, ST_REST_SUB, ST_REST_TEST, ST_Q0, ST_Q1);
	signal s_state : TState;
	
	subtype TCounter is natural range 0 to numBits;
	signal cnt : TCounter;

begin
	process(clk)
	begin
		if(rising_edge(clk))then
			if(reset = '1')then
				s_state  <= ST_IDLE;
				done     <= '1';
				op       <= '0';
				enable   <= '0';
				regsInit <= '0';
				shiftq   <= '0';
				shiftd   <= '0';
				bitq     <= '0';
				cnt      <= 0;
			else
				
				case s_state is
					
					when ST_IDLE =>
						if(start = '1')then
							s_state  <= ST_INIT;
							regsInit <= '1';
							shiftd   <= '0';
							shiftq   <= '0';
							op       <= '0';
							bitq     <= '0';
							done     <= '0';
							enable   <= '0';
						else
							s_state <= ST_IDLE;
							done     <= '1';
							op       <= '0';
							enable   <= '0';
							regsInit <= '0';
							shiftq   <= '0';
							shiftd   <= '0';
							bitq     <= '0';
							cnt      <= 0;
						end if;
						cnt <= 0;
					
					
					when ST_INIT =>
						s_state  <= ST_DIV_SHIFT;
						regsInit <= '0';
						shiftd   <= '1';
						shiftq   <= '0';
						bitq     <= '0';
						op       <= '0';
						enable   <= '0';
						
					
					when ST_DIV_SHIFT =>
						s_state  <= ST_REST_SUB;
						enable   <= '1';
						regsInit <= '0';
						op       <= '0';
						bitq     <= '0';
						shiftd   <= '0';
						shiftq   <= '0';
						
					
					when ST_REST_SUB =>
						s_state <= ST_REST_TEST;
						enable  <= '0';
						op      <= '0';
						
					
					when ST_REST_TEST => 
						if(Msbit = '1')then
							s_state  <= ST_Q0;
							regsInit <= '0';
							enable   <= '1';
							op       <= '1';
							shiftq   <= '1';
							shiftd   <= '0';
							bitq     <= '0';
							cnt      <= cnt+1;
						else
							s_state  <= ST_Q1;
							enable   <= '0';
							op       <= '0';
							regsInit <= '0';
							shiftq   <= '1';
							shiftd   <= '0';
							bitq     <= '1';
							cnt      <= cnt+1;
						end if;
					
					
					when ST_Q0 =>
						if(cnt>=numBits)then
							s_state  <= ST_IDLE;
							enable   <= '0';
							op       <= '0';
							shiftd   <= '0';
							shiftq   <= '0';
							regsInit <= '0';
							bitq     <= '0';
							done     <= '1';
							cnt      <= 0;
						else
							s_state  <= ST_INIT; 
							enable   <= '0';
							shiftq   <= '0';
						end if;
					
					
					when ST_Q1 =>
						if(cnt>=numBits)then
							s_state  <= ST_IDLE;
							enable   <= '0';
							op       <= '0';
							shiftd   <= '0';
							shiftq   <= '0';
							regsInit <= '0';
							bitq     <= '0';
							done     <= '1';
							cnt      <= 0;
						else
							s_state  <= ST_INIT;
							enable   <= '0';
							shiftq   <= '0';
						end if;
				
					end case;
				end if;
			end if;
		end process;
end Behavioral;
							
							
				
				