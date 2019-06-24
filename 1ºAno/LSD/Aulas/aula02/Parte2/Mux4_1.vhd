library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity Mux4_1 is
	port(sel    : in  std_logic_vector(1 downto 0);
		  dataIn : in  std_logic_vector(3 downto 0);
		  dataOut: out std_logic);
end Mux4_1;

architecture Mux4_1Behav of Mux4_1 is
begin
	process(dataIn, sel)
	begin
		if (sel = "00") then
			dataOut <= dataIn(0);
		else 
			if(sel = "01") then
				dataOut <= dataIn(1);
			elsif (sel = "10") then
				dataOut <= dataIn(2);
			elsif (sel = "11") then
				dataOut <= dataIn(3);
			else
				dataOut <= '0';
			end if;
		end if;
	end process;
end Mux4_1Behav;
		