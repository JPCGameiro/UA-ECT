---------------------------------------------------------------------------
-- University of Aveiro - DETI
-- "Computer Architecture I" course (Practical classes)
-- 
-- Display Unit
---------------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

library work;
use work.DisplayUnit_pkg.all;

entity DisplayUnit is
	generic(kHzClkFreq		: positive := 50000; 
           mSecMinInWidth	: positive := 100;
			  mSecMinInRepeat	: positive := 400;
			  mSecMinInRepeatFast : positive := 100;
			  IM_ADDR_SIZE	: positive := 6;
			  DM_ADDR_SIZE	: positive := 6;
			  datapathType		: std_logic := SINGLE_CYCLE_DP);	
	port(	RefClk	: in std_logic;
			InputSel	: in std_logic_vector(1 downto 0);	
			DispMode	: in std_logic;
			NextAddr	: in std_logic;
			Dir		: in std_logic;
			disp0		: out std_logic_vector(6 downto 0);
			disp1		: out std_logic_vector(6 downto 0);
			disp2		: out std_logic_vector(6 downto 0);
			disp3		: out std_logic_vector(6 downto 0);
			disp4		: out std_logic_vector(6 downto 0);
			disp5		: out std_logic_vector(6 downto 0);
			disp6		: out std_logic_vector(6 downto 0);
			disp7		: out std_logic_vector(6 downto 0));
end DisplayUnit;

architecture Behavioral of DisplayUnit is
	subtype T7segCode	is std_logic_vector(6 downto 0);
	type TMemory is array(0 to 20) of T7segCode;
	constant s_bin7seg : TMemory := ("1000000", "1111001", "0100100", "0110000", -- 0, 1, 2, 3
												"0011001", "0010010", "0000010", "1111000", -- 4, 5, 6, 7
												"0000000", "0010000", "0001000", "0000011", -- 8, 9, A, B
												"1000110", "0100001", "0000110", "0001110", -- C, D, E, F
												"0110110", "0001100", "1000110", "0001110", "0100001"); -- ///, P, C, F, d 
		
	type TCounter is array(0 to 3) of unsigned(5 downto 0);
	signal s_addrCounters : TCounter;

	constant MIN_IN_WIDTH_CYCLES : positive := mSecMinInWidth * kHzClkFreq; 
	constant MIN_IN_REPEAT_CYCLES : positive := mSecMinInRepeat * kHzClkFreq;
	constant MIN_IN_REPEAT_FAST_CYCLES : positive := mSecMinInRepeatFast * kHzClkFreq;
	signal s_count, s_repeatCount, s_thd : natural := 0;

	signal s_inc, s_dec : std_logic;
	signal s_data : unsigned(31 downto 0);
	signal s_addr : integer;
	signal s_inputSel : natural range 0 to 3;
begin
	assert IM_ADDR_SIZE <= 6 report "ROM_SIZE not supported" severity error;
	assert DM_ADDR_SIZE <= 6 report "RAM_SIZE not supported" severity error;

	s_inputSel <= to_integer(unsigned(InputSel));

-- Increment / Decrement address counters	
	process(refClk)
	begin
		if(rising_edge(refClk)) then
			if(s_inc = '1') then
				s_addrCounters(s_inputSel) <= s_addrCounters(s_inputSel) + 1;
			elsif(s_dec = '1') then
				s_addrCounters(s_inputSel) <= s_addrCounters(s_inputSel) - 1;
			end if;
		end if;
	end process;		

	DU_IMaddr <= std_logic_vector(s_addrCounters(1)(IM_ADDR_SIZE-1 downto 0));
	DU_RFaddr <= std_logic_vector(s_addrCounters(2)(4 downto 0));
	DU_DMaddr <= std_logic_vector(s_addrCounters(3)(DM_ADDR_SIZE-1 downto 0));
	
-- Select address and data to display
	process(DU_PC,DU_IMdata,DU_IMADDR,DU_RFADDR,DU_DMADDR,s_data,s_addr, InputSel, DU_RFdata, DU_DMdata)
	begin
		case InputSel is
			when "00" =>	-- PC
				s_data <= unsigned(DU_PC);
				s_addr <= 0;
			when "01" =>	-- Instruction Memory
				s_data <= unsigned(DU_IMdata);
				s_addr <= to_integer(unsigned(DU_IMADDR) & "00") ;
			when "10" => 	-- Register File
				s_data <= unsigned(DU_RFdata);
				s_addr <= to_integer(unsigned(DU_RFADDR)) ;
			when "11" =>	-- Data Memory
				s_data <= unsigned(DU_DMdata);
				s_addr <= to_integer(unsigned(DU_DMADDR) & "00") ;
		end case;
	end process;

-- Display address and data
	process(s_data, s_InputSel, DU_CState, s_addr, InputSel, DU_RFdata, DU_DMdata, DispMode)
	begin
		disp0 <= s_bin7seg(to_integer(s_data(3 downto 0)));
		disp1 <= s_bin7seg(to_integer(s_data(7 downto 4)));
		disp2 <= s_bin7seg(to_integer(s_data(11 downto 8)));
		disp3 <= s_bin7seg(to_integer(s_data(15 downto 12)));
		if(DispMode = '0') then
			disp4 <= s_bin7seg(to_integer(s_data(19 downto 16)));
			disp5 <= s_bin7seg(to_integer(s_data(23 downto 20)));
			disp6 <= s_bin7seg(to_integer(s_data(27 downto 24)));
			disp7 <= s_bin7seg(to_integer(s_data(31 downto 28)));
		else
			disp7 <= s_bin7seg(16);	-- ///
			disp6 <= s_bin7seg(17 + s_InputSel);
			if(InputSel = "00") then
				if(datapathType = SINGLE_CYCLE_DP) then
					disp5 <= "0111111"; -- -
					disp4 <= "0111111"; -- -
				else		-- multi-cycle (show current state)
					disp5 <= s_bin7seg(to_integer(unsigned(DU_CState(4 downto 4))));
					disp4 <= s_bin7seg(to_integer(unsigned(DU_CState(3 downto 0))));
				end if;
			else
				disp5 <= s_bin7seg(to_integer(to_unsigned(s_addr, 8)(7 downto 4)));
				disp4 <= s_bin7seg(to_integer(to_unsigned(s_addr, 8)(3 downto 0)));
			end if;
		end if;
	end process;
			
-- Debounce "NextAddr" input (active low) and generate increment/decrement enables (normal, fast)				
	process(refClk)
	begin
		if(rising_edge(refClk)) then
			s_inc <= '0';
			s_dec <= '0';
			if(NextAddr = '0') then
				if(s_count = s_thd) then
					if(Dir = '1') then s_inc <= '1'; else s_dec <= '1'; end if;
					s_repeatCount <= s_repeatCount + 1;
					s_count <= 0;
				else
					s_count <= s_count + 1;
				end if;
			else
				s_count <= 0;
				s_repeatCount <= 0;
			end if;
		end if;
	end process;

	s_thd <= MIN_IN_WIDTH_CYCLES 	when s_repeatCount =  0 else
				MIN_IN_REPEAT_CYCLES when s_repeatCount <= 4 else
				MIN_IN_REPEAT_FAST_CYCLES;
end Behavioral;
