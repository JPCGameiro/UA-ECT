library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity DrinksMachine is
	port(CLOCK_50 : in  std_logic;
		  KEY      : in  std_logic_vector(0 downto 0);
		  SW       : in  std_logic_vector(1 downto 0);
		  LEDG     : out std_logic_vector(0 downto 0));
end DrinksMachine;

architecture Shell of DrinksMachine is
	signal clk_out, dbc_0, dbc_1 : std_logic;
begin 
	dbc0_core : entity work.DebounceUnit(Behavioral)     
						generic map(kHzClkFreq     => 50000,
										mSecMinInWidth => 100,
										inPolarity     => '1',
										outPolarity    => '1')
							port map(refclk    => CLOCK_50,
										dirtyIn   => SW(0),
										pulsedOut => dbc_0);
										
	dbc1_core : entity work.DebounceUnit(Behavioral)
						generic map(kHzClkFreq     => 50000,
										mSecMinInWidth => 100,
										inPolarity     => '1',
										outPolarity    => '1')
							port map(refclk    => CLOCK_50,
										dirtyIn   => SW(1),
										pulsedOut => dbc_1);
										
	fsm_core  : entity work.DrinksFSM(Behavioral)
							port map(clk   => CLOCK_50,
										reset => KEY(0),
										V     => dbc_0,
										C     => dbc_1,
										Drink => LEDG(0));
end Shell;
		  