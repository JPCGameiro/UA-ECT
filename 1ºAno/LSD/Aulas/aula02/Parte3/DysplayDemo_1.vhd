library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity Bin7Seg is 
	port(KEY   : in std_logic;
		  SW    : in  std_logic_vector(3 downto 0);
		  HEX0  : out std_logic_vector(6 downto 0);
		  LEDR  : out std_logic_vector(6 downto 0);
		  LEDG  : out std_logic_vector(3 downto 0));
end Bin7Seg;

architecture Structural of Bin7Seg is
	signal out_1 : std_logic_vector(6 downto 0);
	signal out_2 : std_logic_vector(3 downto 0);
	
begin
	system_core : entity work.Bin7SegDecoder(Behavioral)
							port map(enable   => KEY,
							         binInput => SW,
										decOut_n => out_1);
					HEX0 <= out_1;
					LEDR <= out_1;
					LEDG <= SW;
end Structural;