library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity AddSubRegN is
	generic(N         : positive := 8);
		port(clk       : in  std_logic;
			  reset     : in  std_logic;
			  load      : in  std_logic;
			  enable    : in  std_logic;
			  addSub    : in  std_logic;
			  loadData  : in  std_logic_vector((N-1) downto 0);
			  dataIn    : in  std_logic_vector((N-1) downto 0);
			  dataOut   : out std_logic_vector((N-1) downto 0));
end AddSubRegN;

architecture Behavioral of AddSubRegN is
	signal s_regData : unsigned((N-1) downto 0);
begin
	process(clk)
	begin
		if(rising_edge(clk))then
			if(reset = '1')then
				s_regData <= (others => '0');
			else
				if(load = '1')then
					s_regData <= unsigned(loadData);
				elsif(enable = '1')then
					if(addSub = '1')then
						s_regData <= s_regData + unsigned(dataIn);
					else
						s_regData <= s_regData - unsigned(dataIn);
					end if;
				end if;
			end if;
		end if;
	end process;
	dataOut <= std_logic_vector(s_regData);
end Behavioral;
					
