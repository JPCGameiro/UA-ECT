library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity IterMultDiv is
   port(CLOCK_50 : in  std_logic;
        KEY      : in  std_logic_vector(1 downto 0);
        SW       : in  std_logic_vector(17 downto 0);
        LEDR     : out std_logic_vector(15 downto 0);
        LEDG     : out std_logic_vector(8 downto 0));
end IterMultDiv;

architecture Structural of IterMultDiv is

   signal s_globalClk, s_globalReset      : std_logic;
   signal s_start, s_multDiv_n, s_multSel : std_logic;
   signal s_multStart, s_divStart         : std_logic;
   signal s_multDone, s_divDone           : std_logic;

   signal s_operand0     : std_logic_vector(7 downto 0);
   signal s_operand1     : std_logic_vector(7 downto 0);
   signal s_multResult   : std_logic_vector(15 downto 0);
   signal s_divQuotient  : std_logic_vector(7 downto 0);
   signal s_divRemainder : std_logic_vector(7 downto 0);

begin
   process(CLOCK_50)
   begin
      if (rising_edge(CLOCK_50)) then
         s_globalReset <= not KEY(0);
         s_start       <= not KEY(1);
         s_operand0    <= SW(7 downto 0);
         s_operand1    <= SW(15 downto 8);
         s_multDiv_n   <= SW(17);
      end if;
   end process;

   s_globalClk <= CLOCK_50;

   LEDG(8) <= s_globalClk;

   main_ctrl_unit : entity work.MainCtrlUnit(Behavioral)
      port map(clk        => s_globalClk,
               reset      => s_globalReset,
               multDiv_n  => s_multDiv_n,
               start      => s_start,
               multStart  => s_multStart,
               divStart   => s_divStart,
               multDone   => s_multDone,
               divDone    => s_divDone,
               multSel    => s_multSel);

   mult_core : entity work.IterMultCore(Structural)
      generic map(numBits => 8)
      port map(clk        => s_globalClk,
               reset      => s_globalReset,
               start      => s_multStart,
               busy       => LEDG(0),
               done       => s_multDone,
               operand0   => s_operand0,
               operand1   => s_operand1,
               result     => s_multResult);

   LEDG(1) <= s_multDone;

   div_core : entity work.IterDivCore(Structural)
      generic map(numBits => 8)
      port map(clk        => s_globalClk,
               reset      => s_globalReset,
               start      => s_divStart,
               busy       => LEDG(2),
               done       => s_divDone,
               operand0   => s_operand0,
               operand1   => s_operand1,
               quotient   => s_divQuotient,
               remainder  => s_divRemainder);

   LEDG(3) <= s_divDone;

   process(CLOCK_50)
   begin
      if (rising_edge(CLOCK_50)) then
         if (s_multSel = '1') then
            LEDR <= s_multResult;
         else
            LEDR <= s_divRemainder & s_divQuotient;
         end if;
      end if;
   end process;
 end Structural;
