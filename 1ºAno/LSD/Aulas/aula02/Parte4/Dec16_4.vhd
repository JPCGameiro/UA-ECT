library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity Dec16_4 is
	port(decodeIn   : in  std_logic_vector(15 downto 0);
		  encodedOut : out std_logic_vector(3 downto 0);
		  validOut   : out std_logic);
end Dec16_4;

architecture Behavior of Dec16_4 is
begin
	process(decodeIn)
	begin
		if (decodeIn(15) = '1') then
			validOut   <= '1';
			encodedOut <= "1111";
		else
			if (decodeIn(14) = '1') then
				validOut   <= '1';
				encodedOut <= "1110";
			elsif (decodeIn(13) = '1') then
					validOut   <= '1';
					encodedOut <= "1101";
			elsif (decodeIn(12) = '1') then
					validOut   <= '1';
					encodedOut <= "1100";
			elsif (decodeIn(11) = '1') then
					validOut   <= '1';
					encodedOut <= "1011";
			elsif (decodeIn(10) = '1') then
					validOut   <= '1';
					encodedOut <= "1010";
			elsif (decodeIn(9) = '1') then
					validOut   <= '1';
					encodedOut <= "1001";
			elsif (decodeIn(8) = '1') then
					validOut   <= '1';
					encodedOut <= "1000";
			elsif (decodeIn(7) = '1') then
					validOut   <= '1';
					encodedOut <= "0111";
			elsif (decodeIn(6) = '1') then
					validOut   <= '1';
					encodedOut <= "0110";
			elsif (decodeIn(5) = '1') then
					validOut   <= '1';
					encodedOut <= "0101";
			elsif (decodeIn(4) = '1') then
					validOut   <= '1';
					encodedOut <= "0100";
			elsif (decodeIn(3) = '1') then
					validOut   <= '1';
					encodedOut <= "0011";
			elsif (decodeIn(2) = '1') then
					validOut   <= '1';
					encodedOut <= "0010";
			elsif (decodeIn(1) = '1') then
					validOut   <= '1';
					encodedOut <= "0001";
			elsif (decodeIn(0) = '1') then
					validOut   <= '1';
					encodedOut <= "0000";
			else
					validOut   <= '0';
					encodedOut <= "----";
			end if;
	    end if;
	end process;
end Behavior;

			
					