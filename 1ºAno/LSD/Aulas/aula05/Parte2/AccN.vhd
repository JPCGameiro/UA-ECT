library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity AccN is
	generic(N : positive := 4);
		port(reset   : in  std_logic;
			  clk     : in  std_logic;
			  enable  : in  std_logic;
			  dataIn  : in  std_logic_vector(N downto 0);
			  dataOut : out std_logic_vector(N downto 0));
end AccN;

architecture Shell of AccN is
	signal s_adderOut : std_logic_vector(N downto 0);
	signal s_regOut   : std_logic_vector(N downto 0);
begin
	adder_core : entity work.AdderN(Behavioral)
							generic map(N => N)
								port map(operand0 => dataIn,
											operand1 => s_regOut,
											result   => s_adderOut);
											
	reg_core   : entity work.RegN(Behavioral1)
							generic map(N => N)
								port map(reset   => reset,
											clk     => clk,
											enable  => enable,
											dataIn  => s_adderOut,
											dataOut => s_regOut);
	dataOut <= s_regOut;
end Shell;