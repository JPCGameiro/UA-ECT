library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

library work;
use work.DisplayUnit_pkg.all;

entity InstructionMemory is
	generic(ADDR_BUS_SIZE	: positive := 6);
		port(address	:	in  std_logic_vector(ADDR_BUS_SIZE-1 downto 0);
			  readData :	out std_logic_vector(31 downto 0));
end InstructionMemory;

architecture Behavioral of InstructionMemory is
	constant NUM_WORDS	: positive := (2 ** ADDR_BUS_SIZE);
	subtype TData is std_logic_vector(31 downto 0);
	type TMemory is array(0 to NUM_WORDS-1) of TData;
	constant s_memory	: TMemory := (X"2002FFF9",
											  X"2003001A",
											  X"00432020",
											  X"00432822",
											  X"00433024",
											  X"00433825",
											  X"00434027",
											  X"00434826",
											  X"0043502A",
											  X"0062582A",
											  X"284C001A",
											  X"286DFFFC",
											  others => X"00000000");
	
begin
	--Porto de Leitura da memória defenido na interface do módulo
	readdata <= s_memory(to_integer(unsigned(address)));
	
	--Porto de leitura para efeitos de vizualização (ligado ao módulo
	--de visualização através dos sinais globais DU_IMaddr e DU_IMdata
	--DU_IMaddr <= address;
	DU_IMData <= s_memory(to_integer(unsigned(DU_IMaddr)));
end Behavioral;