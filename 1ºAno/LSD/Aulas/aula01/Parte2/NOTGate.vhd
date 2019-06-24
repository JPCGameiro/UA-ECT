--Bibliotecas
library IEEE;
use IEEE.STD_LOGIC_1164.all;

--Interface (Portas)
entity NOTGate is 
	port( inPort  : in  std_logic;
			outPort : out std_logic);
end NOTGate;

--Implementação (Descrição da Funcionalidade)
architecture Behavioral of NOTGate is
begin
	outPort <= not inPort;
end Behavioral;
