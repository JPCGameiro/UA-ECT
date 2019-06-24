library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity ShiftRegister is
	generic(N          :  positive := 8);
		port(clk        : in  std_logic;
			  reset      : in  std_logic;
		     load       : in  std_logic;
			  shiftLeft  : in  std_logic;
			  shiftRight : in  std_logic;
			  shLeftin   : in  std_logic;
			  shRightin  : in  std_logic;
			  dataIn     : in  std_logic_vector((N-1) downto 0);
			  dataOut    : out std_logic_vector((N-1) downto 0));
end ShiftRegister;

architecture Behavioral of ShiftRegister is
	signal s_regData : std_logic_vector((N-1) downto 0);
begin
	process(clk)
	begin
		if(rising_edge(clk))then
			if(reset = '1')then
				s_regData <= (others => '0');
			else
				if(load = '1')then
					s_regData <= dataIn;
				elsif(shiftLeft = '1')then
					s_regData <= s_regData((N-2) downto 0) & shLeftin; 
				elsif(shiftRight = '1')then
					s_regData <= shRightin & s_regData((N-1) downto 1);
				end if;
			end if;
		end if;
	end process;
	dataOut <= s_regData;
end Behavioral;