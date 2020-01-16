
library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity SignExtend is
	port(dataIn	:	in  std_logic_vector(15 downto 0);
		  dataOut:	out std_logic_vector(31 downto 0));
end SignExtend;

architecture Behavioral of SignExtend is
begin
	dataOut(31 downto 16) <= (others => dataIn(15));
	dataOut(15 downto 0)	 <= dataIn;
end Behavioral;