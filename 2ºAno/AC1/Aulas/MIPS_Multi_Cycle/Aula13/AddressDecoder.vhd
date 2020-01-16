library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity AddressDecoder is
	port(bitIn	: in  std_logic;
		  out0	: out std_logic;
		  out1	: out std_logic);
end AddressDecoder;

architecture Behavioral of AddressDecoder is
begin
	process(bitIn)
	begin
		if(bitIn = '1') then
			out0 <= '1';
			out1 <= '0';
		else 
			out0 <= '0';
			out1 <= '1';
		end if;
	end process;
end Behavioral;