---------------------------------------------------------------------------
-- University of Aveiro - DETI
-- "Computer Architecture I" course (Practical classes)
-- 
-- Package which defines ROM and RAM address sizes
---------------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;

package MIPS_pkg is

	constant	ROM_ADDR_SIZE	: positive := 6;  -- 2^6 => 64, 32-bit words
	constant	RAM_ADDR_SIZE	: positive := 6;  -- 2^6 => 64, 32-bit words

end package MIPS_pkg;

package body MIPS_pkg is

end package body MIPS_pkg;
