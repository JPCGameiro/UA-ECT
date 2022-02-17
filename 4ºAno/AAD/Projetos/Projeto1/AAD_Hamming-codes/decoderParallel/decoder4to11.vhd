library ieee;
use ieee.std_logic_1164.all;

library logic;
use logic.all;

entity decoder4to11 is
	port(p		: in  std_logic_vector(3 downto 0);
		  a		: out std_logic_vector(10 downto 0));
end decoder4to11;

architecture structural of decoder4to11 is
	signal np : std_logic_vector(3 downto 0);
	signal s0 : std_logic_vector(7 downto 0);

	component gateAnd2
		port(x0, x1		: in  std_logic;
			  y			: out std_logic);
	end component;
	component gateNot
		port(x   		: in  std_logic;
			  y			: out std_logic);
	end component;
	
begin
	n0 : gateNot port map (p(0), np(0));	
	n1 : gateNot port map (p(1), np(1));
	n2 : gateNot port map (p(2), np(2));
	n3 : gateNot port map (p(3), np(3));
	
	a00 : gateAnd2 port map (np(0), np(1), s0(0));
	a01 : gateAnd2 port map (np(0), p(1), s0(1));
	a02 : gateAnd2 port map (p(0), np(1), s0(2));
	a03 : gateAnd2 port map (p(0), p(1), s0(3));
	a04 : gateAnd2 port map (np(2), np(3), s0(4));
	a05 : gateAnd2 port map (p(2), np(3), s0(5));
	a06 : gateAnd2 port map (np(2), p(3), s0(6));
	a07 : gateAnd2 port map (p(2), p(3), s0(7));
	
	and00 : gateAnd2 port map (s0(0), s0(7), a(5));
	and01 : gateAnd2 port map (s0(1), s0(5), a(3));
	and02 : gateAnd2 port map (s0(1), s0(6), a(4));
	and03 : gateAnd2 port map (s0(1), s0(7), a(9));
	and04 : gateAnd2 port map (s0(2), s0(5), a(1));
	and05 : gateAnd2 port map (s0(2), s0(6), a(2));
	and06 : gateAnd2 port map (s0(2), s0(7), a(8));
	and07 : gateAnd2 port map (s0(3), s0(4), a(0));
	and08 : gateAnd2 port map (s0(3), s0(5), a(6)); 
	and09 : gateAnd2 port map (s0(3), s0(6), a(7));
	and10 : gateAnd2 port map (s0(3), s0(7), a(10));

end structural;

