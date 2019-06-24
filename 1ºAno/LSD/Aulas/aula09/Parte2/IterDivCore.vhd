library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity IterDivCore is
   generic(numBits : positive := 8);
   port(clk        : in  std_logic;
        reset      : in  std_logic;
        start      : in  std_logic;
        busy       : out std_logic;
        done       : out std_logic;
        operand0   : in  std_logic_vector((numBits - 1) downto 0);
        operand1   : in  std_logic_vector((numBits - 1) downto 0);
        quotient   : out std_logic_vector((numBits - 1) downto 0);
        remainder  : out std_logic_vector((numBits - 1) downto 0));
end IterDivCore;

architecture Structural of IterDivCore is

begin
	busy		 <= '0';
	done		 <= '1';
	quotient	 <= (others => '0');
	remainder <= (others => '0');
end Structural;
