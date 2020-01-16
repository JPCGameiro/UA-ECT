library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity OutPort is
	port(clk		:	in  std_logic;
		  ce   	:	in  std_logic;
		  wr		:	in  std_logic;
		  dataIn	:	in  std_logic_vector(31 downto 0);
		  dataOut:	out std_logic_vector(31 downto 0));
end OutPort;

architecture behav of OutPort is
begin
	process(clk)
	begin
		if(rising_edge(clk)) then
			if(ce = '1' and wr ='1') then
				dataOut <= dataIn;
			end if;
		end if;
	end process;
end behav;