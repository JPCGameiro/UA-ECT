library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity FreqDivider_Counter is
	port(SW       : in  std_logic_vector(0 downto 0);
		  CLOCK_50 : in  std_logic;
		  LEDR     : out std_logic_vector(3 downto 0);
		  LEDG     : out std_logic_vector(0 downto 0);
		  HEX0     : out std_logic_vector(6 downto 0));
end FreqDivider_Counter;

architecture Shell of FreqDivider_Counter is
	signal s_clkout : std_logic;
	signal s_count  : std_logic_vector(3 downto 0);
begin
	FreqDivider   : entity work.FreqDivider(Behavioral)
							  port map(clkIn     => CLOCK_50,
										  k         => "00000010111110101111000010000000",
										  clkOut    => s_clkout);
										  LEDG(0)   <= s_clkOut;
	CounterUpDown : entity work.Counter(Behavioral1)
								port map(reset    => '0',
											upDown   => SW(0),
											clk      => s_clkout,
											count    => s_count);
											LEDR     <= s_count;
	Bin7SegDecoder: entity work.Bin7SegDecoder(Behavioral2)
								port map(binInput => s_count,
											decOut_n => HEX0);
end Shell;
											