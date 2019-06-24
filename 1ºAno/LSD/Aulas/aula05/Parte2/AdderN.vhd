library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity AdderN is
	generic(N : positive := 4);
		port(operand0 : in  std_logic_vector(N downto 0);
			  operand1 : in  std_logic_vector(N downto 0);
			  result   : out std_logic_vector(N downto 0));
end AdderN;

architecture Behavioral of AdderN is
	signal op0, op1 : unsigned(N downto 0);
	signal res      : std_logic_vector(N downto 0);
begin
	op0 <= unsigned(operand0);
	op1 <= unsigned(operand1);
	
	res <= std_logic_vector(op0 + op1);
	
	result <= res;
end Behavioral;