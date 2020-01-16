library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity Mux41_N is
	generic(N		: positive 	:=	32);
		port(Sel		:	in  std_logic_vector(1 downto 0);
			  In0		:	in  std_logic_vector(N-1 downto 0);
			  In1		:	in  std_logic_vector(N-1 downto 0);
			  In2		:	in  std_logic_vector(N-1 downto 0);
			  In3		:	in  std_logic_vector(N-1 downto 0);
			  MuxOut	:	out std_logic_vector(N-1 downto 0));
end Mux41_N;

architecture Behavioral of Mux41_N is
begin
	process(Sel, In0, In1, In2, In3)
	begin
		case Sel is
			when "00" =>
				MuxOut <= In0;
			when "01" =>
				MuxOut <= In1;
			when "10" =>
				MuxOut <= In2;
			when "11" =>
				MuxOut <= In3;
		end case;
	end process;
end Behavioral;