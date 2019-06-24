library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity CounterUpDown4 is
	port(enable  : in  std_logic;
		  clk     : in  std_logic;
		  reset   : in  std_logic;
		  upDown  : in  std_logic;
		  dataOut : out std_logic_vector(3 downto 0));
end CounterUpDown4;

architecture Behavioral of CounterUpDown4 is
	signal count : unsigned(3 downto 0);
begin
	process(clk)
	begin
		if(rising_edge(clk))then
			if(reset = '1')then
				count <= (others => '0');
			elsif(enable = '1')then
				if(upDown = '0')then
					count <= count - 1;
				else
					count <= count + 1;
				end if;
			end if;
		end if;
	end process;
	dataOut <= std_logic_vector(count);
end Behavioral;
					
					