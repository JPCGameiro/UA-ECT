library IEEE;
use IEEE.STD_LOGIC_1164.all;



entity Mux2N is		
	generic(N 		: positive := 32);
		port(l0		: in  std_logic_vector(N-1 downto 0);
			  l1		: in  std_logic_vector(N-1 downto 0);
			  sel 	: in  std_logic;
			  outMux	: out std_logic_vector(N-1 downto 0));
end Mux2N;

architecture Behavioral of Mux2N is
begin
	process(l0, l1, sel)
	begin
		case sel is
			when '0' =>
				outMux <= l0;
			when '1' =>
				outMux <= l1;
		end case;
	end process;
end Behavioral;