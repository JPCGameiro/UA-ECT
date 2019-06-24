library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity ShiftRegister_Demo is
	generic(size : positive := 4);
	port(CLOCK_50 : in  std_logic;
		  SW       : in  std_logic_vector(7 downto 0);
		  LEDR     : out std_logic_vector(size downto 0));
end ShiftRegister_Demo;

architecture Shell of ShiftRegister_Demo is
	signal clk_out : std_logic;

begin
	freq_core  : entity work.ClkDividerN(Behavioral)
						generic map(divFactor => 50000000)
						port map(clkIn  => CLOCK_50,
									clkout => clk_out);
									
	shift_core : entity work.ShiftRegisterLoad(Behavioral)
						generic map(size  => size)
						port map(sin     => SW(0),
									en      => SW(1),
									load    => SW(2),
									reset   => SW(3), 
									clk     => clk_out,
									dataIn  => SW(7 downto 4),
									dataOut => LEDR(size-1 downto 0));
									end Shell;