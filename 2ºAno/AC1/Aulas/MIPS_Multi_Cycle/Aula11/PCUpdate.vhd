library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity PCUpdate is
	port(clk				:	in  std_logic;
		  reset			:	in  std_logic;
		  zero			:	in  std_logic;
		  PCSource		:	in  std_logic_vector(1 downto 0);
		  PCWrite		:	in  std_logic;
		  PCWriteCond	:	in  std_logic;
		  PC4				:	in  std_logic_vector(31 downto 0);
		  BTA				:	in  std_logic_vector(31 downto 0);
		  jAddr			:	in  std_logic_vector(25 downto 0);
		  pc				:	out std_logic_vector(31 downto 0));
end PCUpdate;

architecture Behavioral of PCUpdate is
	signal s_pc			:	std_logic_vector(31 downto 0);
	signal s_pcEnable	:	std_logic;
begin
	s_pcEnable	<=	PCWrite or (PCWriteCond and zero);
	process(clk)
	begin
		if(rising_edge(clk))then
			if(reset = '1')then
				s_pc <= (others => '0');
			elsif(s_pcEnable = '1')then 
				case PCSource is
					when "01" =>	--BTA
						s_pc	<= BTA;
					when "10" => 	--JTA
						s_pc	<=	s_pc(31 downto 28) & jAddr & "00";
					when others =>	--PC+4
						s_pc	<=	PC4;
				end case;
			end if;
		end if;
	end process;
	pc <= s_pc;
end Behavioral;