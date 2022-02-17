LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.ALL;

LIBRARY gates;
USE gates.all;

-- 1 not gate + 2 and gates + 1 xor gate
ENTITY mux_2to1 IS
 PORT(A,B 		: IN  STD_LOGIC;
		S			: IN  STD_LOGIC;
		Z			: OUT STD_LOGIC);
end mux_2to1;

ARCHITECTURE Behavioral OF mux_2to1 IS
	signal sig_nS 		: STD_LOGIC;
	signal aux1, aux2 : STD_LOGIC;

	COMPONENT gateNot
    PORT(x0				: IN  STD_LOGIC;
         y				: OUT STD_LOGIC);
	END COMPONENT;
	
	COMPONENT gateAnd2
    PORT(x0, x1		: IN  STD_LOGIC;
         y				: OUT STD_LOGIC);
	END COMPONENT;
	
	COMPONENT gateXOr2
    PORT(x0, x1		: IN  STD_LOGIC;
         y				: OUT STD_LOGIC);
	END COMPONENT;
	
BEGIN
	not1 : gateNot PORT MAP(S, sig_nS);
	
	and1 : gateAnd2 PORT MAP(sig_nS, A, aux1);
	and2 : gateAnd2 PORT MAP(S, B, aux2);
	
	xor1 : gateXor2 PORT MAP(aux1, aux2, Z);
END Behavioral;


-------------------------


LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.all;

-- 3 not gates + 6 and gates + 3 xor gates
ENTITY mux_4to1 IS
 PORT(
     A,B,C,D 	: IN STD_LOGIC;
     S0,S1		: IN STD_LOGIC;
     Z			: OUT STD_LOGIC
  );
END mux_4to1;

ARCHITECTURE bhv OF mux_4to1 IS
	SIGNAL aux1, aux2 : STD_LOGIC;	

	COMPONENT mux_2to1
	 PORT(A,B 			: in  STD_LOGIC;
			S   			: in  STD_LOGIC;
			Z   			: out STD_LOGIC);
	END COMPONENT;

BEGIN
	mux211 : mux_2to1 PORT MAP(A, B, S0, aux1);
	mux212 : mux_2to1 PORT MAP(C, D, S0, aux2);
	
	mux213 : mux_2to1 PORT MAP(aux1, aux2, s1, Z);
END bhv;