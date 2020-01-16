---------------------------------------------------------------------------
-- University of Aveiro - DETI
-- "Computer Architecture I" course (Practical classes)
-- 
-- MIPS system (minimal system with a MIPS multi-cycle CPU)
---------------------------------------------------------------------------
--Não chegou a ser testado na fpga
 
library ieee;
use ieee.std_logic_1164.all;

library work;
use work.DisplayUnit_pkg.all;
use work.MIPS_pkg.all;

entity MIPS_System is
	port(	CLOCK_50 : in std_logic;
			KEY  		: in std_logic_vector(3 downto 0);
			SW   		: in std_logic_vector(17 downto 0);
			LEDR  	: out std_logic_vector(17 downto 0);
			LEDG  	: out std_logic_vector(7 downto 0);
			HEX0  	: out std_logic_vector(6 downto 0);
			HEX1  	: out std_logic_vector(6 downto 0);
			HEX2  	: out std_logic_vector(6 downto 0);
			HEX3  	: out std_logic_vector(6 downto 0);
			HEX4  	: out std_logic_vector(6 downto 0);
			HEX5  	: out std_logic_vector(6 downto 0);
			HEX6  	: out std_logic_vector(6 downto 0);
			HEX7  	: out std_logic_vector(6 downto 0));
end MIPS_System;


architecture shell of MIPS_System is
	signal cpu_clock : std_logic;
	signal cpu_read, cpu_write : std_logic;
	signal cpu_dataBus : std_logic_vector(31 downto 0);
	signal cpu_addressBus : std_logic_vector(31 downto 0);
begin

-- CPU (MIPS multi-cycle)
cpu:		entity work.MIPS_MultiCycle(struct)
			port map(clk 			=> cpu_clock,
						reset			=> not KEY(1),
						cpu_rd		=> cpu_read,
						cpu_wr		=> cpu_write,
						cpu_addrBus	=> cpu_addressBus,
						cpu_dataBus	=> cpu_dataBus);
	
-- Memory (64x32)
mem:		entity work.RAM(Behavioral)
			generic map(ADDR_BUS_SIZE	=> 6,
							DATA_BUS_SIZE	=> 32)
			port map(clk	=> cpu_clock,
						ce		=> '1',
						rd		=> cpu_read,
						wr		=> cpu_write,
						addr 	=> cpu_addressBus(7 downto 2),
						dio	=> cpu_dataBus);

-- Support modules	
-- Debouncer
debncer:	entity work.DebounceAndClock(Behavioral)
			port map(refClk	=> CLOCK_50, 
						dirtyIn	=> KEY(0), 
						pulsedOut=> cpu_clock);

-- Frequency divider [ fin=50 MHz, fout = 50E6 / 6.25E6) = 8Hz ]
divf:		entity work.DivFreq(Behavioral)
			generic map(KDIV => 625E4)
			port map(clkIn => CLOCK_50,
						clkOut=> open);
	
-- Display Unit
display:	entity work.DisplayUnit(Behavioral)
			generic map(datapathType => MULTI_CYCLE_DP)
			port map(RefClk	=> CLOCK_50,
						InputSel	=> SW(1 downto 0),	
						DispMode	=> SW(2),
						NextAddr	=> KEY(3),
						Dir		=> KEY(2),
						disp0		=> HEX0,
						disp1		=> HEX1,
						disp2		=> HEX2,
						disp3		=> HEX3,
						disp4		=> HEX4,
						disp5		=> HEX5,
						disp6		=> HEX6,
						disp7		=> HEX7);
end shell;

