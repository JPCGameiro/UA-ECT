---------------------------------------------------------------------------
-- University of Aveiro - DETI
-- "Computer Architecture I" course (Practical classes)
-- 
-- Frequency Divider
---------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity divFreq is
	generic(KDIV	: positive);
	port( clkIn 	: in  std_logic;
		   clkOut	: out std_logic);
end divFreq;

architecture Behavioral of divFreq is
	signal s_count : natural := 0;
begin
	assert KDIV > 1 report "KDIV must be greater than 1" severity error;

	process(clkIn)
	begin
		if(rising_edge(clkIn)) then
			if(s_count = KDIV - 1) then
				s_count <= 0;
				clkOut <= '1';
			else
				s_count <= s_count + 1;
				clkOut <= '0';
			end if;
		end if;
	end process;	
end Behavioral;
