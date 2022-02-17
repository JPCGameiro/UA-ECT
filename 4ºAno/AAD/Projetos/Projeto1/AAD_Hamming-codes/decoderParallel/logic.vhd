library ieee;
use ieee.std_logic_1164.all;

entity gateAnd2 is
	port(x0, x1		: in  std_logic;
		  y			: out std_logic);
end gateAnd2;

architecture logicFunction of gateAnd2 is
begin
	y <= x0 AND x1; 
end logicFunction;




library ieee;
use ieee.std_logic_1164.all;

entity gateXor2 is
	port(x0, x1		: in  std_logic;
		  y			: out std_logic);
end gateXor2;

architecture logicFunction of gateXor2 is
begin
	y <= x0 XOR x1;
end logicFunction;




library ieee;
use ieee.std_logic_1164.all;

entity gateNot is
	port(x		: in  std_logic;
		  y		: out std_logic);
end gateNot;

architecture logicFunction of gateNot is 
begin
	y <= NOT x;
end logicFunction;



