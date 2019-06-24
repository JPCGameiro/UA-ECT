library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERiC_STD.all;

entity ShiftRegisterLoad is
   generic(size : positive := 4);
	port(clk     : in  std_logic;
		  en      : in  std_logic;
		  load    : in  std_logic;
		  reset   : in  std_logic;
		  sin     : in  std_logic;
		  dataIn  : in  std_logic_vector(size-1 downto 0);
		  dataOut : out std_logic_vector(size-1 downto 0));
end ShiftRegisterLoad;

architecture Behavioral of ShiftRegisterLoad is
	signal s_shiftReg : std_logic_vector(size-1 downto 0);
begin
		process(clk, en)
		begin
			if(en = '1')then
				if(rising_edge(clk))then
					if(reset = '1')then
						s_shiftReg <= (others => '0');
					else
						if(load = '1')then
							s_shiftReg <= dataIn;
						else
							s_shiftReg <= s_ShiftReg((size-2) downto 0) & sin;
						end if;
					end if;
				end if;
			end if;
		end process;
		dataOut <= s_shiftReg;
end Behavioral;