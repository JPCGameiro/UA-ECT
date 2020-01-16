library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity StallingUnit is
	port(RS				:	in  std_logic_vector(4 downto 0);
		  RT				:  in  std_logic_vector(4 downto 0);
		  MemWrite		:	in  std_logic;
		  AluOp			:	in  std_logic_vector(1 downto 0);
		  Branch			:	in  std_logic;
		  Jump			:  in  std_logic;
		  IdEx_RDD		:  in  std_logic_vector(4 downto 0);
		  IdEx_MemRead	:	in  std_logic;
		  IdEx_RegWrite:	in  std_logic;
		  ExMem_RDD		:	in  std_logic_vector(4 downto 0);
		  ExMem_MemRead:	in  std_logic;
		  Reset_IdEx	:	out std_logic;
		  Enable_PC		:  out std_logic;
		  Enable_ifId	:	out std_logic);
end StallingUnit;

architecture Behavioral of StallingUnit is
begin
	process(RS, RT, MemWrite, AluOp, Branch, Jump, IdEx_RDD, IdEx_MemRead, IdEx_RegWrite, ExMem_RDD, ExMem_MemRead)
	begin
		Reset_IdEx <= '0';
		Enable_PC <= '1';
		Enable_ifId <= '1';
		
		if(Branch = '1')then 	--Branch instructions in ID
			if(IdEx_RegWrite = '1' and IdEx_RDD /= "00000") then
				if(IdEx_RDD = RT or IdEx_RDD = RS) then
					Enable_PC <= '0'; Enable_IfId <= '0'; Reset_IdEx <= '1';
				end if;
			end if;
			if(ExMem_MemRead = '1' and ExMem_RDD /= "00000") then
				if(ExMem_RDD = RT or ExMem_RDD = RS) then
					Enable_PC <= '0'; Enable_IfId <= '0'; Reset_IdEx <= '1';
				end if;
			end if;
		elsif(jump = '0') then -- R-type/lw/sw/addi/slti in ID
			if(IdEx_MemRead = '1' and IdEx_RDD /= "00000") then
				if(IdEx_RDD = RS or (IdEx_RDD = RT and AluOp = "10")) then
					Enable_PC <= '0'; Enable_IfId <= '0'; Reset_IdEx <= '1';
				end if;
				if(RT = IdEx_RDD and MemWrite = '1') then
					Enable_PC <= '0'; Enable_IfId <= '0'; Reset_IdEx <= '1';
				end if;
			end if;
		end if;	
	end process;
end Behavioral;