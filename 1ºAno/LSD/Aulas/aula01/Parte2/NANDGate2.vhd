--Bibliotecas
library IEEE;
use IEEE.STD_LOGIC_1164.all;

--Descrição dos Componentes
entity NANDGate is
	port(inPort0  : in  std_logic;
		  inPort1  : in  std_logic;
		  outPort : out std_logic);
end NANDGate;

--Arquitetura (Funcionamento)
architecture Structural of NANDGate is
	signal s_andout : std_logic;

begin
	and_gate : entity work.AND2Gate(Behavioral)
					port map(inPort0  => inPort0,
								inPort1  => inPort1,
								outPort0 => s_andout);
							
	not_gate : entity work.NOTGate(Behavioral)
					port map(inPort  => s_andout,
								outPort => outPort);
end Structural;


  