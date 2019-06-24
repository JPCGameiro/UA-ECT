library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity Bin7SegDecoder is
	port(binInput0: in  std_logic_vector(3 downto 0);
		  binInput1: in  std_logic_vector(3 downto 0);
	     decOut_n0: out std_logic_vector(6 downto 0);
		  decOut_n1: out std_logic_vector(6 downto 0);
		  Out_sign : out std_logic_vector(6 downto 0));
end Bin7SegDecoder;

architecture Behavioral of Bin7SegDecoder is
begin
	decOut_n0 <="1111001" when (binInput0 = "0001") else --1
					"0100100" when (binInput0 = "0010") else --2
					"0110000" when (binInput0 = "0011") else --3
					"0011001" when (binInput0 = "0100") else --4
					"0010010" when (binInput0 = "0101") else --5
					"0000010" when (binInput0 = "0110") else --6
					"1111000" when (binInput0 = "0111") else --7
					"0000000" when (binInput0 = "1000") else --8
					"0010000" when (binInput0 = "1001") else --9
					"0001000" when (binInput0 = "1010") else --A
					"0000011" when (binInput0 = "1011") else --b
					"1000110" when (binInput0 = "1100") else --C
					"0100001" when (binInput0 = "1101") else --d
					"0000110" when (binInput0 = "1110") else --E
					"0001110" when (binInput0 = "1111") else --F
					"1000000" when (binInput0 = "0000");     --0
	decOut_n1 <="1111001" when (binInput1 = "0001") else --1
					"0100100" when (binInput1 = "0010") else --2
					"0110000" when (binInput1 = "0011") else --3
					"0011001" when (binInput1 = "0100") else --4
					"0010010" when (binInput1 = "0101") else --5
					"0000010" when (binInput1 = "0110") else --6
					"1111000" when (binInput1 = "0111") else --7
					"0000000" when (binInput1 = "1000") else --8
					"0010000" when (binInput1 = "1001") else --9
					"0001000" when (binInput1 = "1010") else --A
					"0000011" when (binInput1 = "1011") else --b
					"1000110" when (binInput1 = "1100") else --C
					"0100001" when (binInput1 = "1101") else --d
					"0000110" when (binInput1 = "1110") else --E
					"0001110" when (binInput1 = "1111") else --F
					"1000000" when (binInput1 = "0000");     --0
	process(binInput0, binInput1)
	begin
		if(binInput1 = "0000")then
			if(binInput0(3) = '1') then
				Out_sign <= "0111111";
			else
				Out_sign <= "1111111";
			end if;
		else
			if(binInput1(3) = '1') then
				Out_sign <= "0111111";
			else
				Out_sign <= "1111111";
			end if;
		end if;
	end process;			
end Behavioral;