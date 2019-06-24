library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERiC_STD.all;

entity ShiftRegisterN is
   generic(size : positive := 4);
	port(clk     : in  std_logic;
		  sin     : in  std_logic;
		  dataOut : out std_logic_vector(size downto 0));
end ShiftRegisterN;

architecture Behavioral of ShiftRegisterN is
	signal s_shiftReg : std_logic_vector(size downto 0);
begin
		process(clk)
		begin
			if(rising_edge(clk))then
				s_shiftReg <= s_ShiftReg((size-1) downto 0) & sin;
			end if;
		end process;
		dataOut <= s_shiftReg;
end Behavioral;