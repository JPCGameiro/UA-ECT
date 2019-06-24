library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity Latch is
	port(enable : in  std_logic;
		  d      : in  std_logic;
		  q      : out std_logic);
end Latch;

architecture Behavioral of Latch is
begin
	process(enable, d)
	begin
		if(enable = '1')then
			q <= d;
		end if;
	end process;
end Behavioral;