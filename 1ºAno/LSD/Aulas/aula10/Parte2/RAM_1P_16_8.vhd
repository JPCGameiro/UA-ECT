library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity RAM_1P_16_8 is
	port(clk         : in  std_logic;
		  address     : in  std_logic_vector(3 downto 0);
		  writeEnable : in  std_logic;
		  writeData   : in  std_logic_vector(7 downto 0);
		  readData    : out std_logic_vector(7 downto 0));
end RAM_1P_16_8;

architecture Behavioral of RAM_1P_16_8 is
	subtype memory is std_logic_vector(7 downto 0);
	type Ram is array(15 downto 0) of memory;
	signal ram0 : Ram;
begin
	process(clk)
	begin
		if(rising_edge(clk))then
			if(writeEnable = '1')then
				ram0(to_integer(unsigned(address))) <= writeData;
			end if;
		end if;
	end process;
	readData <= ram0(to_integer(unsigned(address)));
end Behavioral;