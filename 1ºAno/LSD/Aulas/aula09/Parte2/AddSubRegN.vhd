library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity AddSubRegN is
   generic(N       : positive := 8);
   port(asyncReset : in  std_logic;
        syncReset  : in  std_logic;
        clk        : in  std_logic;
        load       : in  std_logic;
        loadData   : in  std_logic_vector((N - 1) downto 0);
        enable     : in  std_logic;
        addSub_n   : in  std_logic;
        dataIn     : in  std_logic_vector((N - 1) downto 0);
        dataOut    : out std_logic_vector((N - 1) downto 0));
end AddSubRegN;

architecture Behavioral of AddSubRegN is

    signal s_regData : unsigned((N - 1) downto 0);

begin
   process(asyncReset, clk)
   begin
      if (asyncReset = '1') then
         s_regData <= (others => '0');
      elsif (rising_edge(clk)) then
         if (syncReset = '1') then
            s_regData <= (others => '0');
         elsif (load = '1') then
            s_regData <= unsigned(loadData);
         elsif (enable = '1') then
            if (addSub_n = '1') then
               s_regData <= s_regData + unsigned(dataIn);
            else
               s_regData <= s_regData - unsigned(dataIn);
            end if;
         end if;
      end if;
   end process;

   dataOut <= std_logic_vector(s_regData);
end Behavioral;
