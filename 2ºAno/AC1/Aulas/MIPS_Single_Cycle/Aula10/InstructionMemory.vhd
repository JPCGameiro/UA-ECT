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
	constant s_memory	: TMemory := (X"20060000",
											  X"20050004",
											  X"00003820",
											  X"0005082A",
											  X"10200006",
											  X"00A54020",
											  X"ACC80000",
											  X"00E83820",
											  X"20C60004",
											  X"20A5FFFF",
											  X"08100017",
											  X"ACC70000",
											  X"1000FFFF",
								 others => X"00000000");
	
begin
	--Porto de Leitura da memória defenido na interface do módulo
	readdata <= s_memory(to_integer(unsigned(address)));
	
	--Porto de leitura para efeitos de vizualização (ligado ao módulo
	--de visualização através dos sinais globais DU_IMaddr e DU_IMdata
	--DU_IMaddr <= address;
	DU_IMData <= s_memory(to_integer(unsigned(DU_IMaddr)));
end Behavioral;