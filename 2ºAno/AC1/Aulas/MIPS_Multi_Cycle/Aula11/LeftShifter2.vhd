library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity LeftShifter2 is
	port(dataIn	 :	in  std_logic_vector(31 downto 0);
		  dataOut : out std_logic_vector(31 downto 0));
end LeftShifter2;

architecture Behavioral of LeftShifter2 is
begin
	dataOut <= dataIn(29 downto 0) & "00";
end Behavioral;