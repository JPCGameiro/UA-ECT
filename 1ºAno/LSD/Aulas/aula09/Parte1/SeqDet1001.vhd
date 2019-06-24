library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity SeqDet1001 is
	port(CLOCK_50 : in  std_logic;
		  SW       : in  std_logic_vector(1 downto 0);
		  LEDR     : out std_logic_vector(0 downto 0);
		  LEDG     : out std_logic_vector(0 downto 0));
end SeqDet1001;

architecture Shell of SeqDet1001 is
	signal clk_out : std_logic;
begin
	clk_core : entity work.ClkDividerN(RTL)
						generic map(k      => 250000000)
							port map(clkIn  => CLOCK_50,
										clkOut => clk_out);
	LEDR(0) <= clk_out;
	
	fsm_core : entity work.SeqDetFSM(MooreArch)
							port map(clk   => clk_out,
										reset => SW(0),
										xin   => SW(1),
										yout  => LEDG(0));
end Shell;