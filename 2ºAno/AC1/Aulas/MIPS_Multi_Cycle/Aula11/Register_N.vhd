library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity Register_N is
	generic(N	: positive := 32);
		port(clk				:	in  std_logic;
			  enable			:	in  std_logic;
			  dataIn			:	in  std_logic_vector(N-1 downto 0);
			  dataOut		:	out std_logic_vector(N-1 downto 0));
end Register_N;

architecture Behavioral of Register_N is
begin
	process(clk)
	begin
		if(rising_edge(clk))then
			if(enable='1')then
				dataOut <= dataIn;
			end if;
		end if;
	end process;
end Behavioral;
	