library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity IterMultCore is
   generic(numBits : positive := 8);
   port(clk        : in  std_logic;
        reset      : in  std_logic;
        start      : in  std_logic;
        busy       : out std_logic;
        done       : out std_logic;
        operand0   : in  std_logic_vector(numBits - 1 downto 0);
        operand1   : in  std_logic_vector(numBits - 1 downto 0);
        result     : out std_logic_vector((2 * numBits) - 1 downto 0));
end IterMultCore;

architecture Structural of IterMultCore is

   signal s_regsInit, s_accEnable, s_regsShift : std_logic;

   signal s_extOperand0 : std_logic_vector((2 * numBits) - 1 downto 0);

   signal s_multiplicand : std_logic_vector((2 * numBits) - 1 downto 0);
   signal s_multiplier   : std_logic_vector((numBits - 1) downto 0);

begin
   control_unit : entity work.MultCtrlUnit(Behavioral_1Proc_SyncOut)
      generic map(numBits   => numBits)
      port map(clk          => clk,
               reset        => reset,
               start        => start,
               busy         => busy,
               done         => done,
               mulplierLSb  => s_multiplier(0),
               regsInit     => s_regsInit,
               accEnable    => s_accEnable,
               regsShift    => s_regsShift);

   s_extOperand0((2 * numBits) - 1 downto numBits) <= (others => '0');
   s_extOperand0((numBits - 1) downto 0) <= operand0;

   multiplicand_reg : entity work.ShiftRegN(Behavioral)
      generic map(N         => 2 * numBits)
      port map(asyncReset   => '0',
               syncReset    => reset,
               clk          => clk,
               enable       => '1',
               load         => s_regsInit,
               shiftLeft    => s_regsShift,
               shiftRight   => '0',
               shLeftSerIn  => '0',
               shRightSerIn => '0',
               parDataIn    => s_extOperand0,
               parDataOut   => s_multiplicand);

   multiplier_reg : entity work.ShiftRegN(Behavioral)
      generic map(N         => numBits)
      port map(asyncReset   => '0',
               syncReset    => reset,
               clk          => clk,
               enable       => '1',
               load         => s_regsInit,
               shiftLeft    => '0',
               shiftRight   => s_regsShift,
               shLeftSerIn  => '0',
               shRightSerIn => '0',
               parDataIn    => operand1,
               parDataOut   => s_multiplier);

   result_acc : entity work.AddSubRegN(Behavioral)
      generic map(N         => 2 * numBits)
      port map(asyncReset   => '0',
               syncReset    => reset,
               load         => s_regsInit,
               loadData     => (others => '0'),
               clk          => clk,
               enable       => s_accEnable,
               addSub_n     => '1',
               dataIn       => s_multiplicand,
               dataOut      => result);
end Structural;
