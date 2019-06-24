library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity SeqDetFSM_Tb is
end SeqDetFSM_Tb;

architecture Stimulus of SeqDetFSM_Tb is
	signal s_clk, s_reset, s_xin, s_yout : std_logic;
begin
	uut : entity work.SeqDetFSM(MooreArch)
				port map(clk   => s_clk,
							reset => s_reset,
							xin   => s_xin,
							yout  => s_yout);
	clk_proc: process
		begin
			s_clk <= '0';
			wait for 20 ns;
			s_clk <= '1';
			wait for 20 ns;
		end process;
		
					
	stim_proc : process
		begin
			s_reset <= '0';
			wait for 50 ns;
			s_reset <= '1';
			wait for 40 ns;
			s_xin <= '0';
			wait for 40 ns;
			s_xin <= '0';
			wait for 20 ns;
			s_xin <= '1';
			wait for 40 ns;
			s_xin <= '0';
			wait for 40 ns;
			s_xin <= '1';
			wait for 20 ns;
			s_xin <= '0';
			wait for 40 ns;
			s_xin <= '0';
			wait for 20 ns;
			s_xin <= '1';
			wait for 40 ns;
			s_xin <= '0';
			wait for 20 ns;
			s_xin <= '1';
			wait for 40 ns;
			s_xin <= '0';
			wait for 40 ns;
			s_xin <= '0';
			wait for 40 ns;
			s_xin <= '1';
			wait for 60 ns;
			s_xin <= '0';
			wait for 20 ns;
			s_xin <= '0';
			wait for 40 ns;
			s_xin <= '1';
			wait for 20 ns;
			s_xin <= '1';
			wait for 10 ns;
		end process;
end Stimulus;
							