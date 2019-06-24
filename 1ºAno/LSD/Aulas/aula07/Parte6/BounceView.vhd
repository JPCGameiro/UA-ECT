library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity BounceView is
	port(clk  : in  std_logic;
		  in0  : in  std_logic;
		  out0 : out std_logic);
end BounceView;

architecture Behavioral of BounceView is
	signal s : std_logic;
begin
	process(clk)
	begin
		if(rising_edge(clk))then
			if(s = '0')then
				if(in0 = '1')then
					out0 <= '1';
					s    <= '1';
				else
					out0 <= '0';
					s    <= '0';
				end if;
			else
				if(in0 = '1')then
					out0 <= '0';
					s    <= '0';
				else
					out0 <= '1';
					s    <= '1';
				end if;
			end if;
		end if;
	end process;
end Behavioral;
	