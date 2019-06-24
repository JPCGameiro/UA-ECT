library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity FlipFlopD is
	port(clk  : in  std_logic;
		  d    : in  std_logic;
		  set  : in  std_logic;
		  reset: in  std_logic;
		  q    : out std_logic);
end FlipFlopD;

--architecture Behavioral of FlipFlopD is
--begin
--	process(clk)          -- set e reset funcionam de forma síncrona
--	begin
--		if (rising_edge(clk)) then
--			if (reset = '1') then
--				q <= '0';
--			elsif (set = '1') then
--				q <= '1';
--			else
--				q <= d;
--			end if;
--		end if;
--	end process;
--end Behavioral;

architecture Behavioral of FlipFlopD is
begin
	process(clk, reset)   --set e reset funcionam de forma assíncrona
	begin
		if (reset = '1') then
			q <= '0';
		elsif (rising_edge(clk)) then
			if(set <= '1') then
				q <= '1';
			else
				q <= d;
			end if;
		end if;
	end process;
end Behavioral;
		
		
	

