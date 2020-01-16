library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity ControlUnit is
	port(OpCode		: in  std_logic_vector(5 downto 0);
		  RegDst		: out std_logic; 
		  Branch		: out std_logic;
		  MemRead	: out std_logic;
		  MemWrite	: out std_logic;
		  MemtoReg	: out std_logic;
		  ALUsrc		: out std_logic;
		  RegWrite	: out std_logic;
		  Jump		: out std_logic;
		  ALUop		: out std_logic_vector(1 downto 0));
end ControlUnit;

architecture Behavioral of ControlUnit is
begin
	process(opCode)
	begin
		RegDst	<= '0';	Branch <= '0';	MemRead 	<= '0'; MemWrite <= '0';
		MemtoReg <= '0';	ALUsrc <= '0';	RegWrite <= '0'; Jump	  <= '0';
		ALUop	  <= "00";
		case OpCode is
			when "000000" =>		-- r-type
				ALUop		<= "10";
				RegDst	<= '1';
				RegWrite	<= '1';
			when "000100" =>		-- beq
				ALUop 	<= "01";
				Branch	<= '1';
			when "100011" =>		-- lw
				ALUsrc	<= '1';
				MemtoReg	<= '1';
				MemRead	<= '1';
				RegWrite	<= '1';
			when "101011" =>		-- sw
				ALUsrc	<= '1';
				MemWrite	<= '1';
			when "001000" =>		-- addi
				ALUsrc	<= '1';
				RegWrite	<= '1';
			when "001010" =>		-- slti
				ALUop 	<= "11";
				ALUsrc	<= '1';
				RegWrite	<= '1';
			when "000010" =>		-- j
				Jump 		<= '1';
			when others 	=>
		end case;
	end process;
end Behavioral;
				