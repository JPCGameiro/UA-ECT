library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity OutMux is
	generic(numBits     : positive := 8); 
		port(l0			  : in  std_logic_vector((numBits-1) downto 0);
			  l1			  : in  std_logic_vector((numBits-1) downto 0);
			  l2			  : in  std_logic_vector(((2*numBits) - 1) downto 0);
			  l3			  : in  std_logic_vector((numBits-1) downto 0);
			  SEL			  : in  std_logic_vector(1 downto 0);
			  FINALRESULT : out std_logic_vector(((2*numBits) - 1) downto 0);
			  SIGNALMULT  : out std_logic);    --vai identificar a operação é a multiplicação
end OutMux;

architecture Behavior of OutMux is

begin
	process(SEL, l0, l1, l2, l3)
	begin
		case SEL is
			when "00" =>              --adição
				FINALRESULT(((2*numBits) - 1) downto numBits) <= (others => '0');
				FINALRESULT((numBits - 1) downto 0) <= l0;
				SIGNALMULT <= '0'; 
			when "01" =>              --subtracção
				FINALRESULT(((2*numBits) - 1) downto numBits) <= (others => '0');
				FINALRESULT((numBits - 1) downto 0) <= l1;
				SIGNALMULT <= '0';
			when "10" =>              --multiplicação
				FINALRESULT(((2*numBits) - 1) downto 0) <= l2;
				SIGNALMULT <= '1';
			when "11" =>              --divisão
				FINALRESULT(((2*numBits) - 1) downto numBits) <= (others => '0');
				FINALRESULT((numBits - 1) downto 0) <= l3;
				SIGNALMULT <= '0';
		end case;
	end process;
end Behavior;
		
		