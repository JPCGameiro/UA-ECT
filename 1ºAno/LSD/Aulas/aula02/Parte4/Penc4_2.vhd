library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity Penc4_2 is
	port(decodeIn   : in  std_logic_vector(3 downto 0);
	     encodedOut : out std_logic_vector(1 downto 0);
		  validOut   : out std_logic);
end Penc4_2;

architecture Behavioral of Penc4_2 is
begin
	process(decodeIn)
		begin
			if(decodeIn(3) = '1') then
				validOut   <= '1';
				encodedOut <= "11";
			else
				if(decodeIn(2) = '1') then
					validOut   <= '1';
					encodedOut <= "10";
				elsif(decodeIn(1) = '1') then
							validOut   <= '1';
							encodedOut <= "01";
				elsif(decodeIn(0) = '1') then
							validOut   <= '1';
							encodedOut <= "00";
				else
							validOut   <= '0';
							encodedOut <= "--";
				end if;
	      end if;
		end process;
end Behavioral;