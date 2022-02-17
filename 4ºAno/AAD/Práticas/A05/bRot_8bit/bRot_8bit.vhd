LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY gateXOr2 IS
  PORT (x0, x1: IN STD_LOGIC;
        y: OUT STD_LOGIC);
END gateXOr2;

ARCHITECTURE logicFunction OF gateXOr2 IS
BEGIN
  y <= x0 XOR x1;
END logicFunction;

LIBRARY ieee;
USE ieee.std_logic_1164.all;

LIBRARY lRot_8bit;
USE lRot_8bit.all;

ENTITY halfAdder_1bit IS
  PORT (a:  IN STD_LOGIC;
        cI: IN STD_LOGIC;
        s:  OUT STD_LOGIC;
        cO: OUT STD_LOGIC);
END halfAdder_1bit;

ARCHITECTURE structure OF halfAdder_1bit IS
  COMPONENT gateAnd2
    PORT (x0, x1: IN STD_LOGIC;
          y: OUT STD_LOGIC);
  END COMPONENT;
  COMPONENT gateXOr2
    PORT (x0, x1: IN STD_LOGIC;
          y: OUT STD_LOGIC);
  END COMPONENT;
BEGIN
  xor20: gateXOr2 PORT MAP (cI, a, s);
  and20: gateAnd2 PORT MAP (cI, a, cO);
END structure;

LIBRARY ieee;
USE ieee.std_logic_1164.all;

LIBRARY lRot_8bit;
USE lRot_8bit.all;

ENTITY comp2Of3bit IS
  PORT (a:    IN STD_LOGIC_VECTOR (2 DOWNTO 0);
        comp: IN STD_LOGIC;
        b:    OUT STD_LOGIC_VECTOR (2 DOWNTO 0));
END comp2Of3bit;

ARCHITECTURE structure OF comp2Of3bit IS
  SIGNAL na0, a1, a2: STD_LOGIC;
  SIGNAL cInx1, cOutx1: STD_LOGIC;
  COMPONENT gateInv
    PORT (x: IN STD_LOGIC;
          y: OUT STD_LOGIC);
  END COMPONENT;
  COMPONENT gateAnd2
    PORT (x0, x1: IN STD_LOGIC;
          y: OUT STD_LOGIC);
  END COMPONENT;
  COMPONENT gateXOr2
    PORT (x0, x1: IN STD_LOGIC;
          y: OUT STD_LOGIC);
  END COMPONENT;
  COMPONENT halfAdder_1bit
    PORT (a:  IN STD_LOGIC;
          cI: IN STD_LOGIC;
          s:  OUT STD_LOGIC;
          cO: OUT STD_LOGIC);
  END COMPONENT;
BEGIN
  inv0:   gateInv PORT MAP (a(0), na0);
  xor20:  gateXOr2 PORT MAP (a(1), comp, a1);
  xor21:  gateXOr2 PORT MAP (a(2), comp, a2);
  and20:  gateAnd2 PORT MAP (na0, comp, cInx1);
  b(0) <= a(0);
  hadd20: halfAdder_1bit PORT MAP (a1, cInx1,  b(1), cOutx1);
  hadd21: halfAdder_1bit PORT MAP (a2, cOutx1, b(2));
END structure;

LIBRARY ieee;
USE ieee.std_logic_1164.all;

LIBRARY lRot_8bit;
USE lRot_8bit.all;

ENTITY bRot_8bit IS
  PORT (dIn: IN STD_LOGIC_VECTOR (7 DOWNTO 0);
        sel: IN STD_LOGIC_VECTOR (2 DOWNTO 0);
        dir: IN STD_LOGIC;
        dOut: OUT STD_LOGIC_VECTOR (7 DOWNTO 0));
END bRot_8bit;

ARCHITECTURE structure OF bRot_8bit IS
  SIGNAL iSel: STD_LOGIC_VECTOR (2 DOWNTO 0);
  COMPONENT lRot_8bit
    PORT (dIn: IN STD_LOGIC_VECTOR (7 DOWNTO 0);
          sel: IN STD_LOGIC_VECTOR (2 DOWNTO 0);
          dOut: OUT STD_LOGIC_VECTOR (7 DOWNTO 0));
  END COMPONENT;
  COMPONENT comp2Of3bit
    PORT (a:    IN STD_LOGIC_VECTOR (2 DOWNTO 0);
          comp: IN STD_LOGIC;
          b:    OUT STD_LOGIC_VECTOR (2 DOWNTO 0));
  END COMPONENT;
BEGIN
  comp20: comp2Of3bit PORT MAP (sel, dir, iSel);
  lrot0:  lRot_8bit PORT MAP (dIn, iSel, dOut);
END structure;
