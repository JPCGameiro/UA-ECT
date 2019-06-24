library IEEE;
use IEEE.STD_LOGIC_1164.all;
use ieee.numeric_std.all;

entity AddSub4 is
port(	a	:	in std_logic_vector(3 downto 0);
		b	:	in std_logic_vector(3 downto 0);
		sub:	in std_logic;
		s	:	out std_logic_vector(3 downto 0);
		cout:	out std_logic);

end AddSub4;

--architecture Structural2 of AddSub4 is
--
--	signal b2 : std_logic_vector(3 downto 0);
--	signal cin : std_logic;
	
--begin	
--	process(b2)
--	begin
--		if (sub='0') then
--			b2 <= b;
--			cin <= '0';
--		else
--			b2 <= (std_logic_vector( unsigned( not b) + 1 ));
--			cin <= '1';
--		end if;
--	end process;
--	
--	system_core: entity work.Adder4(Structural)
--						port map(a    => a(3 downto 0),
--									b    => b2(3 downto 0),
--									cin  => cin,
--									s    => s(3 downto 0),
--									cout => cout);
--						
--end Structural2;

architecture Behavioral1 of AddSub4 is
		signal S_a, s_b, s_s : unsigned (4 downto 0);
begin
		s_a  <= '0' & unsigned(a);  --'0's para capturar o cout do bit mais significativo
		s_b  <= '0' & unsigned(b);
    s_s  <= (s_a + S_b) when (sub = '0') else
            (s_a - s_b);
    s    <= std_logic_vector(s_s(3 downto 0));
    cout <= s_s(4);
end Behavioral1;