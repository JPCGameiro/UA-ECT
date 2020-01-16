library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity ALUControlUnit is
	port(ALUop			:	in  std_logic_vector(1 downto 0);
		  funct 			:	in  std_logic_vector(5 downto 0);
		  ALUControl	:	out std_logic_vector(2 downto 0));
end ALUControlUnit;

architecture Behavioral of ALUControlUnit is
begin
	process(ALUop, funct)
	begin
		case ALUop is
			when "00" => --LW, SW, ADDI
				ALUControl <= "010";
			when "01" => --BEQ
				ALUControl <= "110";
			when "10" => --RType Instructions
				case funct is
					when "100000"	=> ALUControl <= "010";	--ADD
					when "100010"	=> ALUControl <= "110";	--SUB
					when "100100"	=> ALUControl <= "000";	--AND
					when "100101"	=> ALUControl <= "001";	--OR
					when "100110"	=> ALUControl <= "011";	--XOR
					when "100111"	=> ALUControl <= "100";	--NOR
					when "101010"	=> ALUControl <= "111";	--SLT
					when others 	=> ALUControl <= "010";
				end case;
			when "11" => --SLTI
				ALUControl <= "111";
		end case;
	end process;
end Behavioral;