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

--------------------------

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY XORLOGIC IS
  PORT  (m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11:IN STD_LOGIC;
			x12, x13, x14, x15 : OUT STD_LOGIC);
END XORLOGIC;

ARCHITECTURE structure OF XORLOGIC IS
  SIGNAL sig_m23, sig_m45, sig_m24, sig_m35, sig_m23_9, sig_m45_10, sig_m24_7, sig_m35_8: STD_LOGIC;
  SIGNAL sig_A, sig_B, sig_C, sig_D, sig_E, sig_F: STD_LOGIC;
  
  COMPONENT gateXOr2
    PORT (x0, x1: IN STD_LOGIC;
          y: OUT STD_LOGIC);
  END COMPONENT;
  
BEGIN
  xor0: gateXOr2 PORT MAP (m2, m3, sig_m23);
  xor1: gateXOr2 PORT MAP (m4, m5, sig_m45);
  xor2: gateXOr2 PORT MAP (m2, m4, sig_m24);
  xor3: gateXOr2 PORT MAP (m3, m5, sig_m35);
  xor4: gateXOr2 PORT MAP (m11, m1, sig_A);
  xor5: gateXOr2 PORT MAP (m7, m8, sig_D);
  xor6: gateXOr2 PORT MAP (m9, m10, sig_C);
  xor7: gateXOr2 PORT MAP (m6, m11, sig_B);
  
  xor8: gateXOr2 PORT MAP (sig_m23, m9, sig_m23_9);
  xor9: gateXOr2 PORT MAP (sig_m45, m10, sig_m45_10);
  xor10: gateXOr2 PORT MAP (sig_m24, m7, sig_m24_7);
  xor11: gateXOr2 PORT MAP (sig_m35, m8, sig_m35_8);
  xor12: gateXOr2 PORT MAP (sig_A, sig_D, sig_E);
  xor13: gateXOr2 PORT MAP (sig_C, sig_B, sig_F);
	
  xor14: gateXOr2 PORT MAP (sig_m23_9, sig_E, x12);
  xor15: gateXOr2 PORT MAP (sig_m45_10, sig_E, x13);
  xor16: gateXOr2 PORT MAP (sig_m24_7, sig_F, x14);
  xor17: gateXOr2 PORT MAP (sig_m35_8, sig_F, x15);
	
 END structure;
 
 --------------------------
 
LIBRARY ieee;
USE ieee.std_logic_1164.all;
 
ENTITY Hamming_encoder_parallel IS
  PORT  (m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11:IN STD_LOGIC;
			x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15 : OUT STD_LOGIC);
END Hamming_encoder_parallel;

ARCHITECTURE structure OF Hamming_encoder_parallel IS
	signal sig_x12, sig_x13, sig_x14, sig_x15 : STD_LOGIC;


  COMPONENT XORLOGIC
    PORT (m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11:IN STD_LOGIC;
			 x12, x13, x14, x15 : OUT STD_LOGIC);
	END COMPONENT;
BEGIN
  x1 <= m1;
  x2 <= m2;
  x3 <= m3;
  x4 <= m4;
  x5 <= m5;
  x6 <= m6;
  x7 <= m7;
  x8 <= m8;
  x9 <= m9;
  x10 <= m10;
  x11 <= m11;
  
  xl: XORLOGIC PORT MAP(m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, sig_x12, sig_x13, sig_x14, sig_x15);
  
  x12 <= sig_x12;
  x13 <= sig_x13;
  x14 <= sig_x14;
  x15 <= sig_x15;
  
 END structure;