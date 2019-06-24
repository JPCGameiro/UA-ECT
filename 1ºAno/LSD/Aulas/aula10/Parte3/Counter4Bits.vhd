library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity Counter4Bits is
	port(clk   : in  std_logic;
		  count : out std_logic_vector(3 downto 0));
end Counter4Bits;

architecture Behavioral of Counter4Bits is
	signal s_count : unsigned(3 downto 0);
begin
	process(clk)
	begin
		if(rising_edge(clk))then
			S_count <= s_count + 1;
		end if;
	end process;
	count <= std_logic_vector(s_count);
end Behavioral;