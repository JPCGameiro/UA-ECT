library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity DP_Memory is
	generic(WORD_BITS		:	integer range 1 to 128 := 32;
			  ADDR_BITS 	:	integer range 1 to  10 := 5);
		port(clk				:	in  std_logic;
			  -- asynchronous read port
			  readAddr		:	in  std_logic_vector(ADDR_BITS-1 downto 0);
			  readData		:	out std_logic_vector(WORD_BITS-1 downto 0);
			  --syncronous write port
			  writeAddr		:	in  std_logic_vector(ADDR_BITS-1 downto 0);
			  writeData		:  in  std_logic_vector(WORD_BITS-1 downto 0);
			  writeEnable	:  in  std_logic);
end DP_Memory;

architecture Behavioral of DP_Memory is
	subtype TDataWord is std_logic_vector(WORD_BITS-1 downto 0);
	type TMem is array (0 to 2**ADDR_BITS-1) of TDataWord;
	signal s_memory : TMem := (others => (others => '0'));
begin
	process(clk, writeEnable) is
	begin
		if(rising_edge(clk))then
			if(writeEnable = '1')then
				s_memory(to_integer(unsigned(writeAddr))) <= writeData;
			end if;
		end if;
	end process;
	readData <= (others => '0') when (to_integer(unsigned(readAddr))=0) else
														s_memory(to_integer(unsigned(readAddr)));
end Behavioral;
		  