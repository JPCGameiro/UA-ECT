---------------------------------------------------------------------------
-- University of Aveiro - DETI
-- "Computer Architecture I" course (Practical classes)
-- 
-- Debounce Unit
---------------------------------------------------------------------------
library IEEE; 
use IEEE.STD_LOGIC_1164.all; 
use IEEE.NUMERIC_STD.all; 
 
entity DebounceUnit is 
    generic(kHzClkFreq     : positive := 50000; 
            mSecMinInWidth : positive := 100; 
            inPolarity     : std_logic := '0'; 
            outPolarity    : std_logic := '1'); 
    port(refClk    : in  std_logic; 
         dirtyIn   : in  std_logic; 
         pulsedOut : out std_logic); 
end DebounceUnit;  
  
architecture Behavioral of DebounceUnit is 
    constant MIN_IN_WIDTH_CYCLES : positive := mSecMinInWidth * kHzClkFreq; 
    subtype TCounter is natural range 0 to (MIN_IN_WIDTH_CYCLES + 1); 
 
    signal s_debounceCnt : TCounter := 0; 
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
					if(s_debounceCnt = MIN_IN_WIDTH_CYCLES) then
						s_pulsedOut <= '1'; 
					else
						s_pulsedOut <= '0';
					end if;
					if(s_debounceCnt < (MIN_IN_WIDTH_CYCLES + 1)) then
						s_debounceCnt <= s_debounceCnt + 1; 
					end if;
				else
					s_debounceCnt <= 0; 
					s_pulsedOut <= '0';
				end if;
			end if;
    end process; 
 
    pulsedOut <= s_pulsedOut when (outPolarity = '1') else 
                 not s_pulsedOut; 
end Behavioral; 
