library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity Timer is
	port(clk      : in  std_logic;
		  start    : in  std_logic;
		  reset    : in  std_logic;                  
		  out_cent : out std_logic_vector(6 downto 0);    --valor de saída das centésimas
		  out_sec  : out std_logic_vector(6 downto 0);	  --valor de saída dos segundos
		  out_led  : out std_logic);							  --led que sinaliza o fim da contagem
end Timer;

architecture Behavioral of Timer is
	signal count_cent : unsigned(6 downto 0);     --sinal para o contador das centésimas
	signal count_sec  : unsigned(6 downto 0);		 --sinal para o contador dos segundos
begin
	process(clk)
	begin
		if(rising_edge(clk))then
			if(reset = '0')then            --os keys vão ser usados para o reset e para o start logo vão funcionar com lógica inversa
				count_cent <= "1100011";    --se a qualquer momento for premido o reset, a contagem regressa a 59:99
				count_sec  <= "0111011";
				out_led    <= '0';
			else
				if((count_cent = "1100011") and (count_sec = "0111011"))then --valor 59:99
					if(start = '0')then                                        
						count_cent <= "1100011";                     --se o start for ativado começa a contagem decrescente
						count_sec  <= "0111011";							--a contagem só irá iniciar se o valor for 59:99
						count_cent <= count_cent - 1;						--o led que sinaliza o fim da contagem permanece apagado
						out_led <= '0';										
					end if;
				elsif((not(count_cent = "0000000")) and (not(count_sec="0000000")))then -- valor xx:xx
					count_cent <= count_cent - 1;                                        --apenas decrescem as centésimas
					out_led <= '0';                                                      --o led permanece apagado
				elsif((count_cent = "0000000") and (not(count_sec="0000000")))then --valor xx:00
					count_cent <= "1100011";													--as centésimas regressam ao valor máximo(99)
					count_sec <= count_sec - 1;												--os segundos decrescem um valor
					out_led <= '0';                                               
				elsif((not(count_cent = "0000000")) and (count_sec = "0000000"))then -- valor 00:xx
					out_led <= '0';																	--as centésimas decrescem
					count_cent <= count_cent - 1;
				elsif((count_cent = "0000000") and (count_sec = "0000000"))then -- valor 00:00
					out_led <= '1';			                                   --a contagem para e o led que sinaliza o fim da mesma é ativado
				end if;
			end if;
		end if;
		out_sec  <= std_logic_vector(count_sec);
		out_cent <= std_logic_vector(count_cent);
	end process;
end Behavioral;	
	
						