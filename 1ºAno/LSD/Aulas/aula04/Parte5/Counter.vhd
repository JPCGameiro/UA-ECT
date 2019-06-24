library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity Counter is
	port(upDown : in  std_logic;
		  reset  : in  std_logic;
		  clk    : in  std_logic;
		  count  : out std_logic_vector(3 downto 0));
end Counter;

architecture Behavioral1 of Counter is
	signal s_count : unsigned(3 downto 0);
begin
	process(clk)              --reset funciona de forma sícrona
	begin
		if(rising_edge(clk))then
			if(reset = '1')then
				s_count <= "0000";
			else
				if(upDown = '1')then
					s_count <= s_count+1;
				elsif(upDown = '0')then
					s_count <= s_count-1;
				end if;
			end if;
		end if;
	end process;
	count <= std_logic_vector(s_count);
end Behavioral1;