library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity IterSqrUnit is
	generic(numBits    : positive := 16);
		port(clk        : in  std_logic;
			  reset      : in  std_logic;
			  start      : in  std_logic;
			  d          : in  std_logic_vector((numBits-1) downto 0);
			  s          : in  std_logic_vector((numBits-1) downto 0);
			  o          : in  std_logic_vector((numBits-1) downto 0);
			  operand    : in  std_logic_vector((numBits-1) downto 0);
			  result     : out std_logic_vector((numBits-1) downto 0);
			  test       : out std_logic_vector((numBits-1) downto 0);     --para a implementação alternativa
			  done       : out std_logic);
end IterSqrUnit;

architecture Shell of IterSqrUnit is
	signal s_regsShift, s_regsInit, s_loadShift, s_enables, s_enabled : std_logic;
	signal s_regdout, s_regsout, s_regdplus                           : std_logic_vector((numBits-1) downto 0);
begin
	
	--Máquina de estados finitos para a unidade de controlo da raiz
	fsm_core : entity work.SqrCtrlUnit(Behavioral)
						generic map(numBits   => 16)
							port map(clk       => clk,
									   reset     => reset,
									   start     => start,
									   s         => s_regsout,
									   operand   => operand,
									   regsShift => s_regsShift,
									   regsInit  => s_regsInit,
									   loadShift => s_loadShift,
									   enables   => s_enables,
									   enabled   => s_enabled,
									   done      => done,
										result    => test);    --saída da implementação alternativa
	
	addSub_d : entity work.AddSubRegN(Behavioral)
						generic map(N         => 16)
							port map(clk       => clk,
									   reset     => reset,
										load      => s_regsInit,
										enable    => s_enabled,
										addSub    => '1',
										loadData  => d,
										dataIn    => d,
										dataOut   => s_regdout);
										
	s_regdplus <= std_logic_vector(unsigned(s_regdout) + unsigned(o));
	
	addSub_s : entity work.AddSubRegN(Behavioral)
						generic map(N         => 16)
							port map(clk       => clk,
									   reset     => reset,
										load      => s_regsInit,
										enable    => s_enables,
										addSub    => '1',
										loadData  => s,
										dataIn    => s_regdplus,
										dataOut   => s_regsout);
	
	result_reg : entity work.ShiftRegister(Behavioral)
							generic map(N          => 16)
								port map(clk        => clk,
											reset      => reset,
											load       => s_loadShift,
											shiftLeft  => '0',
											shiftRight => s_regsShift,
											shLeftin   => '0',
											shRightin  => '0',
											dataIn     => s_regdout,
											dataOut    => result);
end Shell;
					