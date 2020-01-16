---------------------------------------------------------------------------
-- University of Aveiro - DETI
-- "Computer Architecture I" course (Practical classes)
-- 
-- Debounce&Clock Unit (to be used in the MIPS multi-cycle minimal system)
---------------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all; 
 
entity DebounceAndClock is 
    generic(kHzClkFreq     : positive := 50000; 
            mSecMinInWidth : positive := 180; 
				mSecMinInRepeat : positive := 400;
				mSecMinInRepeatFast : positive := 150;
				mSecMinInRepeatVeryFast : positive := 40;
            inPolarity     : std_logic := '0'; 
            outPolarity    : std_logic := '1'); 
    port(refClk    : in  std_logic; 
         dirtyIn   : in  std_logic; 
         pulsedOut : out std_logic); 
end DebounceAndClock;  
  
architecture Behavioral of DebounceAndClock is 
   constant MIN_IN_WIDTH_CYCLES : positive := mSecMinInWidth * kHzClkFreq; 
	constant MIN_IN_REPEAT_CYCLES : positive := mSecMinInRepeat * kHzClkFreq;
	constant MIN_IN_REPEAT_FAST_CYCLES : positive := mSecMinInRepeatFast * kHzClkFreq;
	constant MIN_IN_REPEAT_VERY_FAST_CYCLES : positive := mSecMinInRepeatVeryFast * kHzClkFreq;

   signal s_counter : natural;
	signal s_thd, s_repeatCount : natural;
   signal s_dirtyIn, s_pulsedOut : std_logic;  
begin  
		
in_sync_proc : process(refClk)  
		begin 
			if (rising_edge(refClk)) then 
				if (inPolarity = '1') then 
					s_dirtyIn <= dirtyIn; 
				else 
					s_dirtyIn <= not dirtyIn; 
				end if; 
			end if; 
		end process; 
 
count_proc : process(refClk)  
		begin  
			if (rising_edge(refClk)) then 
				if(s_dirtyIn = '1') then
					if(s_counter = s_thd) then
						s_pulsedOut <= '1'; 
						s_counter <= 0;
						s_repeatCount <= s_repeatCount + 1;
					else
						s_counter <= s_counter + 1;
						s_pulsedOut <= '0';
					end if;
				else
					s_counter <= 0; 
					s_repeatCount <= 0;
					s_pulsedOut <= '0';
				end if;
			end if;
    end process; 
 
 	s_thd <= MIN_IN_WIDTH_CYCLES 	when s_repeatCount =  0 else
				MIN_IN_REPEAT_CYCLES when s_repeatCount <= 12 else
				MIN_IN_REPEAT_FAST_CYCLES when s_repeatCount <= 30 else
				MIN_IN_REPEAT_VERY_FAST_CYCLES;

    pulsedOut <= s_pulsedOut when (outPolarity = '1') else 
                 not s_pulsedOut; 
end Behavioral; 

