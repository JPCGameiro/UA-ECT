library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity Mux21_N is
	generic(N		: positive 	:=	32);
		port(Sel		:	in  std_logic;
			  In0		:	in  std_logic_vector(N-1 downto 0);
			  In1		:	in  std_logic_vector(N-1 downto 0);
			  MuxOut	:	out std_logic_vector(N-1 downto 0));
end Mux21_N;

architecture Behavioral of Mux21_N is
begin
	process(Sel, In0, In1)
	begin
		case Sel is
			when '0' =>
				MuxOut <= In0;
			when '1' =>
				MuxOut <= In1;
		end case;
	end process;
end Behavioral;