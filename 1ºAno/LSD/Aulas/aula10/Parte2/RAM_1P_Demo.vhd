library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity RAM_1P_Demo is

	port(	SW	:	in std_logic_vector(12 downto 0);
			KEY:	in	std_logic_vector(0 downto 0);
			LEDG:	out std_logic_vector(7 downto 0));

end RAM_1P_Demo;


architecture Behavioral of RAM_1P_Demo is

begin

	ram_16_8:	entity work.Ram_1P_16_8(Behavioral)
						port map(clk => KEY(0),
									writeEnable => SW(0),
									address => SW(4 downto 1),
									writeData => SW(12 downto 5),
									readData => LEDG(7 downto 0));

end Behavioral;