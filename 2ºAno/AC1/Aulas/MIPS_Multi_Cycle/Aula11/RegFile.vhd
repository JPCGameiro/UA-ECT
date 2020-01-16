library IEEE;
use IEEE.STD_LOGIC_1164.all;
library work;
use work.DisplayUnit_pkg.all;

entity RegFile is
	port(clk				: in  std_logic;
		  writeEnable	: in  std_logic;
		  writeReg		: in  std_logic_vector(4 downto 0);
		  writeData    : in  std_logic_vector(31 downto 0);
		  readReg1		: in  std_logic_vector(4 downto 0);
		  readReg2		: in  std_logic_vector(4 downto 0);
		  readData1		: out std_logic_vector(31 downto 0);
		  readData2		: out std_logic_vector(31 downto 0));
end RegFile;

architecture  Structural of RegFile is
begin
	rs_mem:	entity work.DP_Memory(Behavioral)
						port map(clk  			=> clk,
									readAddr 	=> readReg1,
									readData 	=> readData1,
									writeAddr	=> writeReg,
									writeData	=> writeData,
									writeEnable => writeEnable);
	
	rt_mem:	entity work.DP_Memory(Behavioral)
						port map(clk			=> clk,
									readAddr		=> readReg2,
									readData		=> readData2,
									writeAddr	=> writeReg,
									writeData	=> writeData,
									writeEnable	=> writeEnable);
									
	rt_debug:	entity work.DP_Memory(Behavioral)
						port map(clk			=> clk,
									readAddr		=> DU_RFaddr,
									readData		=> DU_RFdata,
									writeAddr	=> writeReg,
									writeData	=> writeData,
									writeEnable	=> writeEnable);
end Structural;