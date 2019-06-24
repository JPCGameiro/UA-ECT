library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity  SeqDetFSM is
	port(clk   : in  std_logic;
		  reset : in  std_logic;
		  xin   : in  std_logic;
		  yout  : out std_logic);
end SeqDetFSM;

--arquitetura segundo o modelo de Mealy
architecture MealyArch of SeqDetFSM is
	type state is (S0, S1, S10, S100);
	signal currentState, nextState : state;
begin
	
	clk_proc : process(clk)
	begin
		if(rising_edge(clk))then
			if(reset = '1')then
				currentState <= S0;
			else
				currentState <= nextState;
			end if;
		end if;
	end process;
	
	fsm_proc : process(currentState, xin)
	begin
		yout <= '0';
		case(currentState) is
		
		when S0 =>
			if(xin='1')then
				nextState <= S1;
			else
				nextState <= S0;
			end if;
		
		when S1 =>
			if(xin='0')then
				nextState <= S10;
			else
				nextState <= S1;
			end if;
		
		when S10 =>
			if(xin='0')then
				nextState <= S100;
			else
				nextState <= S1;
			end if;
			
		when S100 =>
			if(xin='1')then
				yout <= '1';
				nextState <= S1;
			else
				nextState <= S0;
			end if;
			
		end case;
	end process;
end MealyArch;

--arquitetura segundo o modelo de Moore
architecture MooreArch of SeqDetFSM is
	type state is (S0, S1, S10, S100, S1001);
	signal currentState, nextState : state;
begin
	
	clk_proc : process(clk)
	begin
		if(rising_edge(clk))then
			if(reset = '1')then
				currentState <= S0;
			else
				currentState <= nextState;
			end if;
		end if;
	end process;
	
	fsm_proc : process(currentState, xin)
	begin
		case(currentState)is
		
		when s0 =>
			yout <= '0';
			if(xin='1')then
				nextState <= S1;
			else
				nextState <= S0;
			end if;
			
		when S1 =>
			yout <= '0';
			if(xin='1')then
				nextState <= S1;
			else
				nextState <= S10;
			end if;
			
		when S10 =>
			yout <= '0';
			if(xin='1')then
				nextState <= S1;
			else
				nextState <= S100;
			end if;
			
		when S100 => 
			yout <= '0';
			if(xin='1')then
				nextState <= S1001;
			else
				nextState <= S0;
			end if;
		
		when S1001 =>
			yout <= '1';
			if(xin='1')then
				nextState <= S1;
			else
				nextState <= S10;
			end if;
			
		end case;
	end process;
end MooreArch;
			
			
			