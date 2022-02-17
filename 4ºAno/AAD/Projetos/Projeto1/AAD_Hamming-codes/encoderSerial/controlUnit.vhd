LIBRARY ieee;
USE ieee.std_logic_1164.all;
USE ieee.numeric_std.all;
USE ieee.std_logic_arith.all;
USE ieee.std_logic_unsigned.all;

library gates;
use gates.all;

ENTITY contMem IS
  PORT (addr:  IN STD_LOGIC_VECTOR (3 DOWNTO 0);
        dOut: OUT STD_LOGIC_VECTOR (6 DOWNTO 0));
END contMem;

ARCHITECTURE behavior OF contMem IS
BEGIN
  PROCESS (addr)
  TYPE CMem IS ARRAY(0 TO 14) OF STD_LOGIC_VECTOR (6 DOWNTO 0);
    CONSTANT prog: CMem := ("1100000", --setup  -> a=1,b=1,c=0,d=0,SEL=0,SELP=00 
									 "1010000", --E1  -> a=1,b=0,c=1,d=0,SEL=0,SELP=00 
									 "1001000", --E2  -> a=1,b=0,c=0,d=1,SEL=0,SELP=00 
									 "0110000", --E3  -> a=0,b=1,c=1,d=0,SEL=0,SELP=00
									 "0101000", --E4  -> a=0,b=1,c=0,d=1,SEL=0,SELP=00
									 "0011000", --E5  -> a=0,b=0,c=1,d=1,SEL=0,SELP=00 
									 "1110000", --E6  -> a=1,b=1,c=1,d=0,SEL=0,SELP=00
									 "1101000", --E7  -> a=1,b=1,c=0,d=1,SEL=0,SELP=00
									 "1011000", --E8  -> a=1,b=0,c=1,d=1,SEL=0,SELP=00
									 "0111000", --E9  -> a=0,b=1,c=1,d=1,SEL=0,SELP=00
									 "1111000", --E10 -> a=1,b=1,c=1,d=1,SEL=0,SELP=00
									 "0000100", --E11 -> a=0,b=0,c=0,d=0,SEL=1,SELP=00
									 "0000101", --E12 -> a=0,b=0,c=0,d=0,SEL=1,SELP=01
									 "0000110", --E13 -> a=0,b=0,c=0,d=0,SEL=1,SELP=10
									 "0000111"); --E14 -> a=0,b=0,c=0,d=0,SEL=1,SELP=11
    VARIABLE pos: INTEGER;
  BEGIN
    pos := CONV_INTEGER (addr);
    dOut <= prog(pos);
  END PROCESS;
END behavior;


----------------------


LIBRARY ieee;
USE ieee.std_logic_1164.all;

-- 1 not gate + 1 and gate
ENTITY controlUnit IS
  PORT (nGRst: 	IN STD_LOGIC;
		  binRst:	In std_logic;
		  addr:  	IN STD_LOGIC_VECTOR (3 DOWNTO 0);
        dOut: 		OUT STD_LOGIC_VECTOR (6 DOWNTO 0);
		  nRst:		out STD_LOGIC);
END ControlUnit;

ARCHITECTURE behavior OF ControlUnit IS
	signal sig_binRst : STD_LOGIC;
	
	COMPONENT contMem
   PORT (addr		: IN STD_LOGIC_VECTOR (3 DOWNTO 0);
         dOut		: OUT STD_LOGIC_VECTOR (6 DOWNTO 0));
	END COMPONENT;
	
	COMPONENT gateNot
	PORT(x0		 	: IN STD_LOGIC;
		  y			: OUT STD_LOGIC);
	END COMPONENT;
	
	COMPONENT gateAnd2
	PORT(x0, x1		: IN  STD_LOGIC;
		  y			: OUT STD_LOGIC);
	END COMPONENT;
	
BEGIN
	cMem: contMem PORT MAP (addr, dOut);
	
	not1: gateNot PORT MAP(binRst, sig_binRst); 
	
	and5 : gateAnd2 PORT MAP(nGRst, sig_binRst, nRst);
	
END behavior;