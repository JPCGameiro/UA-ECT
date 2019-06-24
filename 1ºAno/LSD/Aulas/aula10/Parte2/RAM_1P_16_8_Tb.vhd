library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity Ram_1P_16_8_tb is
end Ram_1P_16_8_tb;

architecture Stimulus of Ram_1P_16_8_tb is

	signal s_clk, s_writeEnable	:	std_logic;
	signal s_writeData, s_readData:	std_logic_vector(7 downto 0);
	signal s_address					:	std_logic_vector(3 downto 0);

begin

	uut	:	entity work.Ram_1P_16_8(Behavioral)
					port map(clk 			=> s_clk,
								writeEnable => s_writeEnable,
								address		=> s_address,
								writeData	=> s_writeData,
								readData		=> s_readData);

	clk_proc:process
	begin
		s_clk <= '1';
		wait for 10 ns;
		s_clk <= '0';
		wait for 10 ns;
	
	end process;

	stim_process : process
	begin
		wait for 5 ns;
		s_writeEnable <= '1';
		s_writeData <= "11111111";
		s_address <= "0000";
		wait for 20 ns;
		
		s_address <= "0001";
		wait for 20 ns;
		
		s_writeData <= "11101110";
		s_address <= "0100";
		wait for 20 ns;
		
		s_address <= "0111";
		wait for 20 ns;
		
		s_writeEnable <= '0';
		s_address <= "0000";
		wait for 20 ns;
		
		s_address <= "0001";
		wait for 20 ns;
		
		s_address <= "0011";
		wait for 20 ns;
		
		s_address <= "0100";
		wait for 20 ns;
		
		s_address <= "0111";
		wait for 20 ns;
		
	end process;

end Stimulus;