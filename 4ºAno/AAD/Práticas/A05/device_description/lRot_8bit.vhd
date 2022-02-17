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

ENTITY gateAnd2 IS
  PORT (x0, x1: IN STD_LOGIC;
        y: OUT STD_LOGIC);
END gateAnd2;

ARCHITECTURE logicFunction OF gateAnd2 IS
BEGIN
  y <= x0 AND x1;
END logicFunction;

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY gateOr2 IS
  PORT (x0, x1: IN STD_LOGIC;
        y: OUT STD_LOGIC);
END gateOr2;

ARCHITECTURE logicFunction OF gateOr2 IS
BEGIN
  y <= x0 OR x1;
END logicFunction;

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY mult2to1 IS
  PORT (x0, x1: IN STD_LOGIC;
        s: IN STD_LOGIC;
        y: OUT STD_LOGIC);
END mult2to1;

ARCHITECTURE structure OF mult2to1 IS
  SIGNAL iselx0, iselx1: STD_LOGIC;
  SIGNAL ins: STD_LOGIC;
  COMPONENT gateInv
    PORT (x: IN STD_LOGIC;
          y: OUT STD_LOGIC);
  END COMPONENT;
  COMPONENT gateAnd2
    PORT (x0, x1: IN STD_LOGIC;
          y: OUT STD_LOGIC);
  END COMPONENT;
  COMPONENT gateOr2
    PORT (x0, x1: IN STD_LOGIC;
          y: OUT STD_LOGIC);
  END COMPONENT;
BEGIN
  inv0: gateInv  PORT MAP (s, ins);
  and0: gateAnd2 PORT MAP (x0, ins, iselx0);
  and1: gateAnd2 PORT MAP (x1, s,   iselx1);
  or0:  gateOr2  PORT MAP (iselx0, iselx1, y);
 END structure;

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY blk8of2to1mult IS
  PORT (x00, x01: IN STD_LOGIC;
        x10, x11: IN STD_LOGIC;
        x20, x21: IN STD_LOGIC;
        x30, x31: IN STD_LOGIC;
        x40, x41: IN STD_LOGIC;
        x50, x51: IN STD_LOGIC;
        x60, x61: IN STD_LOGIC;
        x70, x71: IN STD_LOGIC;
        sel: IN STD_LOGIC;
        y0, y1, y2, y3, y4, y5, y6, y7: OUT STD_LOGIC);
END blk8of2to1mult;

ARCHITECTURE structure OF blk8of2to1mult IS
  COMPONENT mult2to1
    PORT (x0, x1: IN STD_LOGIC;
          s: IN STD_LOGIC;
          y: OUT STD_LOGIC);
  END COMPONENT;
BEGIN
  mult0: mult2to1 PORT MAP (x00, x01, sel, y0);
  mult1: mult2to1 PORT MAP (x10, x11, sel, y1);
  mult2: mult2to1 PORT MAP (x20, x21, sel, y2);
  mult3: mult2to1 PORT MAP (x30, x31, sel, y3);
  mult4: mult2to1 PORT MAP (x40, x41, sel, y4);
  mult5: mult2to1 PORT MAP (x50, x51, sel, y5);
  mult6: mult2to1 PORT MAP (x60, x61, sel, y6);
  mult7: mult2to1 PORT MAP (x70, x71, sel, y7);
END structure;

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY lRot_8bit IS
  PORT (dIn: IN STD_LOGIC_VECTOR (7 DOWNTO 0);
        sel: IN STD_LOGIC_VECTOR (2 DOWNTO 0);
        dOut: OUT STD_LOGIC_VECTOR (7 DOWNTO 0));
END lRot_8bit;

ARCHITECTURE structure OF lRot_8bit IS
  SIGNAL Z01: STD_LOGIC_VECTOR (7 DOWNTO 0);
  SIGNAL Z12: STD_LOGIC_VECTOR (7 DOWNTO 0);
  COMPONENT blk8of2to1mult
    PORT (x00, x01: IN STD_LOGIC;
          x10, x11: IN STD_LOGIC;
          x20, x21: IN STD_LOGIC;
          x30, x31: IN STD_LOGIC;
          x40, x41: IN STD_LOGIC;
          x50, x51: IN STD_LOGIC;
          x60, x61: IN STD_LOGIC;
          x70, x71: IN STD_LOGIC;
          sel: IN STD_LOGIC;
          y0, y1, y2, y3, y4, y5, y6, y7: OUT STD_LOGIC);
  END COMPONENT;
BEGIN
  blk0: blk8of2to1mult PORT MAP (dIn(0), dIn(7),
                                 dIn(1), dIn(0),
                                 dIn(2), dIn(1),
                                 dIn(3), dIn(2),
                                 dIn(4), dIn(3),
                                 dIn(5), dIn(4),
                                 dIn(6), dIn(5),
                                 dIn(7), dIn(6),
                                 sel(0),
                                 z01(0), z01(1),
                                 z01(2), z01(3),
                                 z01(4), z01(5),
                                 z01(6), z01(7));
  blk1: blk8of2to1mult PORT MAP (z01(0), z01(6),
                                 z01(1), z01(7),
                                 z01(2), z01(0),
                                 z01(3), z01(1),
                                 z01(4), z01(2),
                                 z01(5), z01(3),
                                 z01(6), z01(4),
                                 z01(7), z01(5),
                                 sel(1),
                                 z12(0), z12(1),
                                 z12(2), z12(3),
                                 z12(4), z12(5),
                                 z12(6), z12(7));
  blk2: blk8of2to1mult PORT MAP (z12(0), z12(4),
                                 z12(1), z12(5),
                                 z12(2), z12(6),
                                 z12(3), z12(7),
                                 z12(4), z12(0),
                                 z12(5), z12(1),
                                 z12(6), z12(2),
                                 z12(7), z12(3),
                                 sel(2),
                                 dOut(0), dOut(1),
                                 dOut(2), dOut(3),
                                 dOut(4), dOut(5),
                                 dOut(6), dOut(7));
END structure;
