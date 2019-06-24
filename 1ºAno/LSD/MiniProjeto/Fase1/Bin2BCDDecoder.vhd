library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity Bin2BCDDecoder is
	port(inBin	 : in  std_logic_vector(6 downto 0);
		  outBCD0 : out std_logic_vector(3 downto 0);
		  outBCD1 : out std_logic_vector(3 downto 0));
end Bin2BCDDecoder;

architecture Behavioral of Bin2BCDDecoder is
	signal num,num1,uni 	:	natural;
begin
	num <= to_integer(unsigned(inBin));  --converter um unsigned(std_logic_vector) to integer
	
	outBCD1 <=	"0000" when num<10 else  --para saber qual o valor das dezenas procura-se o intervalo
					"0001" when num<20 else  --no qual n está situado para assim obtermos o valor
					"0010" when num<30 else  --posteriormente converte-se para binário o resultado obtido
					"0011" when num<40 else
					"0100" when num<50 else
					"0101" when num<60 else
					"0110" when num<70 else
					"0111" when num<80 else
					"1000" when num<90 else
					"1001";					
	num1		<=	0  when num<10 else       --para calcular o algarismo das unidades, criamos uma "variável"
					10 when num<20 else       --num1 que irá auxiliar no cálculo do mesmo
					20 when num<30 else
					30 when num<40 else
					40 when num<50 else
					50 when num<60 else
					60 when num<70 else
					70 when num<80 else
					80 when num<90 else
					90;
	uni <= num-num1;                      --subtrai-se ao valor total o valor obtido anteriormente por num1 para
													  --saber qual o valor das unidades
	outBCD0 <=	"0000" when uni=0 else    --posteriormente converte-se em binário o valor das unidades
					"0001" when uni=1 else
					"0010" when uni=2 else
					"0011" when uni=3 else
					"0100" when uni=4 else
					"0101" when uni=5 else
					"0110" when uni=6 else
					"0111" when uni=7 else
					"1000" when uni=8 else
					"1001";

end Behavioral;