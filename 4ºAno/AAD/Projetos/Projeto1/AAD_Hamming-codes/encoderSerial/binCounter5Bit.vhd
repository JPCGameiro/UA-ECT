LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY flipFlopDPET IS
  PORT (clk, D			: IN STD_LOGIC;
        nSet, nRst	: IN STD_LOGIC;
        Q, nQ			: OUT STD_LOGIC);
END flipFlopDPET;

ARCHITECTURE behavior OF flipFlopDPET IS
BEGIN
  PROCESS (clk, nSet, nRst)
  BEGIN
    IF (nRst = '0')
	    THEN Q <= '0';
		      nQ <= '1';
		 ELSIF (nSet = '0')
		       THEN Q <= '1';
		            nQ <= '0';
	          ELSIF (clk = '1') AND (clk'EVENT)
	                THEN Q <= D;
		                  nQ <= NOT D;

	 END IF;
  END PROCESS;
END behavior;


----------------------

LIBRARY ieee;
USE ieee.std_logic_1164.all;

LIBRARY gates;
USE gates.all;

-- 3 and gates + 4 xor gates + 5 D-type flip flop
ENTITY binCounter5Bit IS
  PORT (nRst		: IN STD_LOGIC;
        clk			: IN STD_LOGIC;
        c			: OUT STD_LOGIC_VECTOR (4 DOWNTO 0));
END binCounter5Bit;

ARCHITECTURE structure OF binCounter5bit IS
  SIGNAL pD1, pD2, pD3, pD4		: STD_LOGIC;

  SIGNAL iD1, iD2, iD3, iD4		: STD_LOGIC;
  
  SIGNAL iQ0, iQ1, iQ2, iQ3, iQ4	: STD_LOGIC;
  
  SIGNAL inQ0							: STD_LOGIC;
  
  COMPONENT gateAnd2
  PORT(x0, x1			: IN STD_LOGIC;
       y					: OUT STD_LOGIC);
  END COMPONENT;
  
  COMPONENT gateXor2
  PORT(x0, x1			: IN STD_LOGIC;
		 y					: OUT STD_LOGIC);
  END COMPONENT;
  
  COMPONENT flipFlopDPET
  PORT(clk, D			: IN STD_LOGIC;
		 nSet, nRst		: IN STD_LOGIC;
		 Q, nQ			: OUT STD_LOGIC);
  END COMPONENT;
  
BEGIN
  ad1: gateAnd2 PORT MAP (iQ0, iQ1, pD1);
  ad2: gateAnd2 PORT MAP (pD1, iQ2, pD2);
  ad3: gateAnd2 PORT MAP (pD2, iQ3, pD3);
  
  xr1: gateXor2 PORT MAP (iQ0, iQ1, iD1);
  xr2: gateXor2 PORT MAP (pD1, iQ2, iD2);
  xr3: gateXor2 PORT MAP (pD2, iQ3, iD3);
  xr4: gateXor2 PORT MAP (pD3, iQ4, iD4);
  
  ff0: flipFlopDPET PORT MAP (clk, inQ0, '1', nRst, iQ0, inQ0);
  ff1: flipFlopDPET PORT MAP (clk, iD1,  '1', nRst, iQ1);
  ff2: flipFlopDPET PORT MAP (clk, iD2,  '1', nRst, iQ2);
  ff3: flipFlopDPET PORT MAP (clk, iD3,  '1', nRst, iQ3);
  ff4: flipFlopDPET PORT MAP (clk, iD4,  '1', nRst, iQ4);
  
  c(0) <= iQ0;
  c(1) <= iQ1;
  c(2) <= iQ2;
  c(3) <= iQ3;
  c(4) <= iQ4;
END structure;