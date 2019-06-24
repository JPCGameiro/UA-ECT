library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity ALU4_5 is
	port(a,b : in  std_logic_vector(3 downto 0);
		  op  : in  std_logic_vector(2 downto 0);
		  r,m : out std_logic_vector(3 downto 0));
end ALU4_5;

architecture Behavior of ALU4_5 is
	signal s_a, s_b, s_r   : signed(3 downto 0);  
	signal s_m             : signed(7 downto 0);
begin
	s_a <= signed(a);
	s_b <= signed(b);
	s_m <= s_a*s_b;
	
	with op select
		s_r <= s_a + s_b       when "000",
				 s_a - s_b       when "001",
				 s_m(3 downto 0) when "010",
				 s_a / s_b       when "011",
				 s_a rem s_b     when "100",
				 s_a and s_b     when "101",
				 s_a  or s_b     when "110",
				 s_a xor s_b     when "111";
		r <= std_logic_vector(s_r);
		m <= std_logic_vector(s_m(7 downto 4)) when (op = "010") else (others => '0');
end Behavior;