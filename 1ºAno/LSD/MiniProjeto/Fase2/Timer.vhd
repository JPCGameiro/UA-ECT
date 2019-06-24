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
	signal counting   : std_logic;                --sinal para verificar se está a ocorrer contagem
begin
	process(clk)
	begin
		if(rising_edge(clk))then
			if(reset = '0')then                  --os keys vão ser usados para o reset e para o start logo vão funcionar com lógica inversa
				count_cent <= "1100011";          --se a qualquer momento for premido reset a contagem retorna ao valor 59:99
				count_sec  <= "0111011";
				out_led    <= '0';
			else
			
			
				if((count_cent = "1100011") and (count_sec = "0111011"))then --valor 59:99
					if(start = '0')then                           
						count_cent <= "1100011";                   
						count_sec  <= "0111011";							
						count_cent <= count_cent - 1;						
						out_led    <= '0';
						counting   <= '1';                --contagem é ativada
					end if;
					
					
				elsif((not(count_cent = "0000000")) and (not(count_sec="0000000")))then -- valor xx:xx
					if(((counting = '0') and (start = '0')) or ((counting = '1') and (start = '1')))then
						count_cent <= count_cent - 1;              --este bloco de código representa a contagem                  
						out_led    <= '0';                         --ativa se start estiver ou não ativo
						counting   <= '1';                         --a contagem ocorre
					elsif(((counting = '1') and (start = '0')) or ((counting = '0') and (start = '1')))then
						count_cent <= count_cent;                 --contrariamente este bloco respresenta
						count_sec  <= count_sec;                  --a contagem desativa se start estiver ou não ativo
						counting   <= '0';                        --a contagem não ocorre
					end if;
					
					
				elsif((count_cent = "0000000") and (not(count_sec="0000000")))then --valor xx:00
					if(((counting = '0') and (start = '0')) or ((counting = '1') and (start = '1')))then
						count_cent <= "1100011";						--contagem ativa de acordo com o valor de start							
						count_sec  <= count_sec - 1;					--contagem ocorre							
						out_led    <= '0';
						counting   <= '1';
					elsif(((counting = '1') and (start = '0')) or ((counting = '0') and (start = '1')))then
						count_cent <= count_cent;                 --contagem desativa de acordo com o valor de start
						count_sec  <= count_sec;                  --contagem não ocorre
						counting   <= '0';
					end if;
					
					
				elsif((not(count_cent = "0000000")) and (count_sec = "0000000"))then -- valor 00:xx
					if(((counting = '0') and (start = '0')) or ((counting = '1') and (start = '1')))then
						out_led    <= '0';								--contagem ativa de acordo com o valor de start									
						count_cent <= count_cent - 1;             --contagem ocorre
						counting   <= '1';
					elsif(((counting = '1') and (start = '0')) or ((counting = '0') and (start = '1')))then
						count_cent <= count_cent;                 --contagem ativa de acordo com o valor de start
						count_sec  <= count_sec;                  --contagem não ocorre
						counting   <= '0';
					end if;
					
					
				elsif((count_cent = "0000000") and (count_sec = "0000000"))then -- valor 00:00
					out_led  <= '1';
					counting <= '0';                              --contagem para e é acendido o led que sinaliza o fim da mesma
					
				end if;
			end if;
		end if;
		out_sec  <= std_logic_vector(count_sec);
		out_cent <= std_logic_vector(count_cent);
	end process;
end Behavioral;