library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity IterMultDiv_Tb is
end IterMultDiv_Tb;

architecture Stimulus of IterMultDiv_Tb is

   signal s_clk  : std_logic;
   signal s_key  : std_logic_vector(1 downto 0);
   signal s_sw   : std_logic_vector(17 downto 0);
   signal s_ledr : std_logic_vector(15 downto 0);
   signal s_ledg : std_logic_vector(8 downto 0);

begin
   uut : entity work.IterMultDiv(Structural)
      port map(CLOCK_50 => s_clk,
               KEY      => s_key,
               SW       => s_sw,
               LEDR     => s_ledr,
               LEDG     => s_ledg);

   clk_proc : process
   begin
      s_clk <= '0'; wait for 10 ns;
      s_clk <= '1'; wait for 10 ns;
   end process;
    
   stim_proc : process
   begin
      wait for 5 ns;

      s_key(0) <= '0';
      wait for 20 ns;

      s_key(0) <= '1';
      wait for 20 ns;

      s_sw(17) <= '1';

      s_sw(15 downto 0) <= X"FFFF";
      wait for 20 ns;

      s_key(1) <= '0';
      wait for 20 ns;

      s_key(1) <= '1';
      wait for 600 ns;

      s_sw(15 downto 0) <= X"AA55";
      wait for 20 ns;

      s_key(1) <= '0';
      wait for 20 ns;

      s_key(1) <= '1';
      wait for 600 ns;

      s_sw(15 downto 0) <= X"048C";
      wait for 20 ns;

      s_key(1) <= '0';
      wait for 20 ns;

      s_key(1) <= '1';
      wait for 600 ns;

      wait;
    end process;
end Stimulus;
