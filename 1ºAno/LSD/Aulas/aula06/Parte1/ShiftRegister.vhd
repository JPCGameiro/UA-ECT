library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERiC_STD.all;

entity ShiftRegister is
	port(clk    : in  std_logic;
		  sin     : in  std_logic;
		  dataOut : out std_logic_vector(4 downto 0));
end ShiftRegister;

architecture Behavioral of ShiftRegister is
	signal s_shiftReg : std_logic_vector(4 downto 0);
begin
		process(clk)
		begin
			if(rising_edge(clk))then
				s_shiftReg <= s_ShiftReg(3 downto 0) & sin;
			end if;
		end process;
		dataOut <= s_shiftReg;
end Behavioral;
					