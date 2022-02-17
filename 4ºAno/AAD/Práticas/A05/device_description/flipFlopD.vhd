LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY gateInv IS
  PORT (x: IN STD_LOGIC;
        y: OUT STD_LOGIC);
END gateInv;

ARCHITECTURE logicFunction OF gateInv IS
BEGIN
  y <= NOT x;
END logicFunction;

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY gateNand2 IS
  PORT (x0, x1: IN STD_LOGIC;
        y: OUT STD_LOGIC);
END gateNand2;

ARCHITECTURE logicFunction OF gateNand2 IS
BEGIN
  y <= NOT (x0 AND x1);
END logicFunction;

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY gateNand3 IS
  PORT (x0, x1, x2: IN STD_LOGIC;
        y: OUT STD_LOGIC);
END gateNand3;

ARCHITECTURE logicFunction OF gateNand3 IS
BEGIN
  y <= NOT (x0 AND x1 AND x2);
END logicFunction;

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY gateNand4 IS
  PORT (x0, x1, x2, x3: IN STD_LOGIC;
        y: OUT STD_LOGIC);
END gateNand4;

ARCHITECTURE logicFunction OF gateNand4 IS
BEGIN
  y <= NOT (x0 AND x1 AND x2 AND x3);
END logicFunction;

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY latchSR2 IS
  PORT (nSet, nRst: IN STD_LOGIC;
        Q, nQ: OUT STD_LOGIC);
END latchSR2;

ARCHITECTURE structure OF latchSR2 IS
  SIGNAL iNSet, iNRst: STD_LOGIC;
  SIGNAL iQ, iNQ: STD_LOGIC;
  COMPONENT gateNand2
    PORT (x0, x1: IN STD_LOGIC;
          y: OUT STD_LOGIC);
  END COMPONENT;
BEGIN
  iNSet <= nSet;
  iNRst <= nRst;
  nand0: gateNand2 PORT MAP (iNSet, iNQ, iQ);
  nand1: gateNand2 PORT MAP (iNRst,  iQ, iNQ);
  Q <= iQ;
  nQ <= iNQ;
END structure;

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY latchSR3 IS
  PORT (nSet0, nSet1, nRst0, nRst1: IN STD_LOGIC;
        Q, nQ: OUT STD_LOGIC);
END latchSR3;

ARCHITECTURE structure OF latchSR3 IS
  SIGNAL iNSet0, iNSet1, iNRst0, iNRst1: STD_LOGIC;
  SIGNAL iQ, iNQ: STD_LOGIC;
  COMPONENT gateNand3
    PORT (x0, x1, x2: IN STD_LOGIC;
          y: OUT STD_LOGIC);
  END COMPONENT;
BEGIN
  iNSet0 <= nSet0;
  iNSet1 <= nSet1;
  iNRst0 <= nRst0;
  iNRst1 <= nRst1;
  nand0: gateNand3 PORT MAP (iNSet0, iNSet1, iNQ, iQ);
  nand1: gateNand3 PORT MAP (iNRst0, iNRst1,  iQ, iNQ);
  Q <= iQ;
  nQ <= iNQ;
END structure;

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY flipFlopD IS
  PORT (clk, D: IN STD_LOGIC;
        nSet, nRst: IN STD_LOGIC;
        Q, nQ: OUT STD_LOGIC);
END flipFlopD;

ARCHITECTURE structure OF flipFlopD IS
  SIGNAL lckP, setP, ckQ, nckQ: STD_LOGIC;
  SIGNAL nTrigSet, nTrigRst, stChg, nStChg: STD_LOGIC;
  SIGNAL lock, nLock: STD_LOGIC;
  SIGNAL nD, iQ, iNQ: STD_LOGIC;
  COMPONENT gateInv
    PORT (x: IN STD_LOGIC;
          y: OUT STD_LOGIC);
  END COMPONENT;
  COMPONENT gateNand2
    PORT (x0, x1: IN STD_LOGIC;
          y: OUT STD_LOGIC);
  END COMPONENT;
  COMPONENT gateNand3
    PORT (x0, x1, x2: IN STD_LOGIC;
          y: OUT STD_LOGIC);
  END COMPONENT;
  COMPONENT gateNand4
    PORT (x0, x1, x2, x3: IN STD_LOGIC;
          y: OUT STD_LOGIC);
  END COMPONENT;
  COMPONENT latchSR2
    PORT (nSet, nRst: IN STD_LOGIC;
          Q, nQ: OUT STD_LOGIC);
  END COMPONENT;
  COMPONENT latchSR3
    PORT (nSet0, nSet1, nRst0, nRst1: IN STD_LOGIC;
          Q, nQ: OUT STD_LOGIC);
  END COMPONENT;
BEGIN
  nand20: gateNand2 PORT MAP (clk, stChg, lckP);
  sr20:   latchSR2  PORT MAP (clk, lckP, lock, nLock);
  nand40: gateNand4 PORT MAP (nSet, nRst, nTrigSet, nTrigRst, stChg);
  inv0:   gateInv   PORT MAP (stChg, nStChg);
  nand30: gateNand3 PORT MAP (clk, lock, nStChg, setP);
  sr30:   latchSR3  PORT MAP (setP, setP, clk, nStChg, ckQ, nckQ);
  inv1:   gateInv   PORT MAP (D, nD);
  nand31: gateNand3 PORT MAP (nRst, D, ckQ, nTrigSet);
  nand32: gateNand3 PORT MAP (nSet, nD, ckQ, nTrigRst);
  sr31:   latchSR3  PORT MAP (nSet, nTrigSet, nRst, nTrigRst, iQ, iNQ);
  Q <= iQ;
  NQ <= iNQ;
END structure;
