library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity ShiftRegN is
   generic(N         : positive := 8);
   port(asyncReset   : in  std_logic;
        syncReset    : in  std_logic;
        clk          : in  std_logic;
        enable       : in  std_logic;
        load         : in  std_logic;
        shiftLeft    : in  std_logic;
        shiftRight   : in  std_logic;
        shLeftSerIn  : in  std_logic;
        shRightSerIn : in  std_logic;
        parDataIn    : in  std_logic_vector((N - 1) downto 0);
        parDataOut   : out std_logic_vector((N - 1) downto 0));
end ShiftRegN;

architecture Behavioral of ShiftRegN is

    signal s_regData : std_logic_vector((N - 1) downto 0);

begin
   process(asyncReset, clk)
   begin
      if (asyncReset = '1') then
         s_regData <= (others => '0');
      elsif (rising_edge(clk)) then
         if (syncReset = '1') then
            s_regData <= (others => '0');
         elsif (enable = '1') then
            if (load = '1') then
               s_regData <= parDataIn;
            elsif (shiftLeft = '1') then
               s_regData <= s_regData((N - 2) downto 0) & shLeftSerIn;
            elsif (shiftRight = '1') then
               s_regData <= shRightSerIn & s_regData((N - 1) downto 1);
            end if;
         end if;
      end if;
   end process;

   parDataOut <= s_regData;
end Behavioral;
