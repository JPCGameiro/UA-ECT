---------------------------------------------------------------------------
-- University of Aveiro - DETI
-- "Computer Architecture I" course (Practical classes)
-- 
-- Package which defines global signals (used by the DisplayUnit module)
---------------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;

library work;
use work.MIPS_pkg.all;

package DisplayUnit_pkg is
	constant	SINGLE_CYCLE_DP : std_logic := '0';
	constant	MULTI_CYCLE_DP  : std_logic := '1';
	
	signal 	DU_PC		: std_logic_vector(31 downto 0);

	signal	DU_IMdata	: std_logic_vector(31 downto 0);
	signal 	DU_IMaddr	: std_logic_vector(ROM_ADDR_SIZE-1 downto 0);
	
	signal 	DU_RFdata	: std_logic_vector(31 downto 0);
	signal 	DU_RFaddr	: std_logic_vector( 4 downto 0);

	signal 	DU_DMdata	: std_logic_vector(31 downto 0);
	signal 	DU_DMaddr	: std_logic_vector(RAM_ADDR_SIZE-1 downto 0);

	signal	DU_CState		: std_logic_vector(4 downto 0);
end package DisplayUnit_pkg;

package body DisplayUnit_pkg is

end package body DisplayUnit_pkg;
