library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity ForwardingUnit is
	port(ExMem_Write	: in  std_logic;
		  MemWb_Write	: in  std_logic;
		  ExMem_RDD		: in  std_logic_vector(4 downto 0);
		  MemWb_RDD		: in  std_logic_vector(4 downto 0);
		  IdEx_RT		: in  std_logic_vector(4 downto 0);
		  IdEx_RS		: in  std_logic_vector(4 downto 0);
		  Forw_A			: out std_logic_vector(1 downto 0);
		  Forw_B			: out	std_logic_vector(1 downto 0));
end ForwardingUnit;

architecture Behavioral of ForwardingUnit is
begin
	process(ExMem_Write, MemWb_Write, ExMem_RDD, MemWb_RDD, IdEx_RT, IdEx_RS)
	begin
		Forw_A <= "00";
		Forw_B <= "00";
		
		if(ExMem_Write = '1' and ExMem_RDD /= "00000") then
			if(ExMem_RDD = IdEx_RT)then
				Forw_B <= "10";
			end if;
			if(ExMem_RDD = IdEx_RS)then
				Forw_A <= "10";
			end if;
		end if;
		
		if(MemWb_Write = '1' and MemWb_RDD /= "00000") then
			if(MemWb_RDD = IdEx_RT)then
				Forw_B <= "01";
			end if;
			if(MemWb_RDD = IdEx_RS)then
				Forw_A <= "01";
			end if;
		end if;	end process;
end Behavioral;