LIBRARY ieee;
USE ieee.std_logic_1164.all;

LIBRARY gates;
USE gates.all;

LIBRARY mux;
USE mux.all;

LIBRARY binCounter5Bit;
USE binCounter5Bit.all;

LIBRARY controlUnit;
USE controlUnit.all;

LIBRARY flipFlopDedicated;
USE flipFlopDedicated.all;

ENTITY Hamm_encoder_serial IS
  PORT (clk				: IN STD_LOGIC;
        nGRst			: IN STD_LOGIC;
		  din				: IN STD_LOGIC;
		  COUT1			: OUT STD_LOGIC_VECTOR(4 downto 0);
        dout			: OUT STD_LOGIC);
END Hamm_encoder_serial;

ARCHITECTURE structure OF Hamm_encoder_serial IS
	SIGNAL sig_a, sig_b, sig_c, sig_d 						: STD_LOGIC;
	SIGNAL sig_x12, sig_x13, sig_x14, sig_x15 			: STD_LOGIC;
	SIGNAL sig_x12_a, sig_x13_b, sig_x14_c, sig_x15_d  : STD_LOGIC;
	
	SIGNAL sig_mux41out 	: STD_LOGIC;
	SIGNAL sig_dout		: STD_LOGIC;
	SIGNAL nRst				: std_logic;
	
	SIGNAL cOut : STD_LOGIC_VECTOR(4 DOWNTO 0);
	SIGNAL sOut : STD_LOGIC_VECTOR(6 DOWNTO 0);
	
	COMPONENT gateAnd2
    PORT (x0, x1		: IN STD_LOGIC;
          y				: OUT STD_LOGIC);
	END COMPONENT;
	
	COMPONENT gateXOr2
    PORT (x0, x1		: IN STD_LOGIC;
          y				: OUT STD_LOGIC);
	END COMPONENT;
	
	COMPONENT flipFlopDPET
	 PORT (clk, D		: IN STD_LOGIC;
          nSet, nRst	: IN STD_LOGIC;
          Q, nQ		: OUT STD_LOGIC);
	 end component;
	
	component mux_4to1
	 port (A,B,C,D 	: IN STD_LOGIC;
		    S0,S1		: IN STD_LOGIC;
		    Z				: OUT STD_LOGIC);
	end component;
	
	component mux_2to1
	 port (A,B 			: IN STD_LOGIC;
			 S				: IN STD_LOGIC;
			 Z				: OUT STD_LOGIC);
	end component;
	
	COMPONENT binCounter5Bit
		PORT (nRst		: IN  STD_LOGIC;
				clk		: IN  STD_LOGIC;
				c			: OUT STD_LOGIC_VECTOR (4 DOWNTO 0));
	END COMPONENT;
	
	COMPONENT controlUnit
	PORT (nGRst			: 	IN STD_LOGIC;
		   binRst		:	In std_logic;
		   addr			:  IN STD_LOGIC_VECTOR (3 DOWNTO 0);
         dOut			: 	OUT STD_LOGIC_VECTOR (6 DOWNTO 0);
		   nRst			:	OUT std_logic);
	END COMPONENT;
	
BEGIN
	bincnt : binCounter5Bit PORT MAP(nRst, clk, cOut);
	
	ctrlunit : controlUnit PORT MAP(nGRst, cOut(4), cOut(3 downto 0), sOut, nRst);
	
	COUT1 <= cOut;

	and1 : gateAnd2 PORT MAP(sOut(6), din, sig_a);
	and2 : gateAnd2 PORT MAP(sOut(5), din, sig_b);
	and3 : gateAnd2 PORT MAP(sOut(4), din, sig_c);
	and4 : gateAnd2 PORT MAP(sOut(3), din, sig_d);

	xor1 : gateXOr2 PORT MAP(sig_a, sig_x12, sig_x12_a);
	xor2 : gateXOr2 PORT MAP(sig_b, sig_x13, sig_x13_b);
	xor3 : gateXOr2 PORT MAP(sig_c, sig_x14, sig_x14_c);
	xor4 : gateXOr2 PORT MAP(sig_d, sig_x15, sig_x15_d);

	ffp0 : flipFlopDPET PORT MAP(clk, sig_x12_a, '1', nRst, sig_x12, open);
	ffp1 : flipFlopDPET PORT MAP(clk, sig_x13_b, '1', nRst, sig_x13, open);
	ffp2 : flipFlopDPET PORT MAP(clk, sig_x14_c, '1', nRst, sig_x14, open);
	ffp3 : flipFlopDPET PORT MAP(clk, sig_x15_d, '1', nRst, sig_x15, open);
	
	m410 : mux_4to1 PORT MAP(sig_x12, sig_x13, sig_x14, sig_x15, sOut(0), sOut(1), sig_mux41out);
	
	m210 : mux_2to1 PORT MAP(din, sig_mux41out, sOut(2), sig_dout);
	
	ffd  : flipFlopDPET PORT MAP(clk, sig_dout, '1', nRst, dout);

END structure;