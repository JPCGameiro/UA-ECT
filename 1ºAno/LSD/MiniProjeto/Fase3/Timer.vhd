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
	signal c_cent     : unsigned(6 downto 0);     --contador das centésimas que está ativo independentemente do estado do reset
	signal c_sec      : unsigned(6 downto 0);     --contador dos segundos que está ativo independentemente do estado do reset
	signal pause      : std_logic;                --sinal para verificar se está a ocorrer pausa
begin
	process(clk)
	begin
		if(rising_edge(clk))then
				if(counting = '1')then --contagem ON
					if(((reset = '0') and (pause = '0')) or ((reset = '1') and (pause = '1')))then --(pausar o tempo)  ou (tempo pausado)
						count_cent <= count_cent;  --o valor dos displays fica estático
						count_sec  <= count_sec;   --a contagem continua a ocorrer mas não é representada nos displays
						pause      <= '1';			--sinal de pausa ativado
					elsif(((reset = '0') and (pause = '1')) or ((reset = '1') and (pause = '0')))then--(retomar o tempo) ou (tempo a correr)
						count_cent <= c_cent;     --o valor dos displays retoma a contagem
						count_sec  <= c_sec;      --a contagem é representada nos displays
						pause      <= '0';        --sinal de pausa destivado
					end if;
				elsif(counting = '0')then --contagem OFF
					if(reset = '0')then
						c_cent     <= "1100011";   --retomam ao valor 59:99        
						c_sec      <= "0111011";
						count_cent <= "1100011";
						count_sec  <= "0111011";
						pause      <= '1';         --pausa ativada
						out_led    <= '0';
						counting   <= '0';         --contagem OFF
					end if;
				end if;
						
			
				if((c_cent = "1100011") and (c_sec = "0111011"))then --valor 59:99
					if(start = '0')then                           
						c_cent     <= "1100011";                   
						c_sec      <= "0111011";							
						c_cent     <= c_cent - 1;						
						out_led    <= '0';
						counting   <= '1'; 
						pause      <= '0';
					end if;
				
--estes blocos de código representam uma contagem ininterrupta que posteriormente será representada nos displays de acordo com o valor do reset e do start				
				elsif((not(c_cent = "0000000")) and (not(c_sec="0000000")))then -- valor xx:xx
					if(((counting = '0') and (start = '0')) or ((counting = '1') and (start = '1')))then--Contagem ON
						c_cent     <= c_cent - 1;                               
						out_led    <= '0';                        
						counting   <= '1';
					elsif(((counting = '1') and (start = '0')) or ((counting = '0') and (start = '1')))then -- Contagem OFF
							if(reset = '0')then   -- se for premido o reset regressa ao valo 59 : 99
								c_cent <= "1100011";          
								c_sec  <= "0111011";
								out_led    <= '0';
								counting   <= '0';
							else                  -- caso contrário fica estática
								c_cent     <= c_cent;                 
								c_sec      <= c_sec;                  
								counting   <= '0';
							end if;
					end if;
					
					
				elsif((c_cent = "0000000") and (not(c_sec="0000000")))then --valor xx:00
					if(((counting = '0') and (start = '0')) or ((counting = '1') and (start = '1')))then -- contagem ON
						c_cent     <= "1100011";												
						c_sec      <= c_sec - 1;												
						out_led    <= '0';
						counting   <= '1';
					elsif(((counting = '1') and (start = '0')) or ((counting = '0') and (start = '1')))then -- Contagem OFF
						if(reset = '0')then        -- se for premido o reset regressa ao valo 59 : 99
								c_cent <= "1100011";          
								c_sec  <= "0111011";
								out_led    <= '0';
								counting   <= '0';
							else                      -- caso contrário fica estática
								c_cent     <= c_cent;                 
								c_sec      <= c_sec;                  
								counting   <= '0';
							end if;
					end if;
					
					
					
				elsif((not(c_cent = "0000000")) and (c_sec = "0000000"))then -- valor 00:xx
					if(((counting = '0') and (start = '0')) or ((counting = '1') and (start = '1')))then--Contagem ON
						out_led    <= '0';																
						c_cent <= c_cent - 1;
						counting   <= '1';
					elsif(((counting = '1') and (start = '0')) or ((counting = '0') and (start = '1')))then--Contagem OFF
						if(reset = '0')then        -- se for premido o reset regressa ao valo 59 : 99
								c_cent <= "1100011";          
								c_sec  <= "0111011";
								out_led    <= '0';
								counting   <= '0';
							else                     -- caso contrário fica estática
								c_cent     <= c_cent;                 
								c_sec      <= c_sec;                  
								counting   <= '0';
							end if;
					end if;
					
					
					
				elsif((c_cent = "0000000") and (c_sec = "0000000"))then -- valor 00:00
					if(reset = '0')then             -- se for premido o reset regressa ao valo 59 : 99        
						count_cent <= "1100011";          
						count_sec  <= "0111011";
						out_led    <= '0';
						counting   <= '0';
					else                       --caso contrário a contagem fica parada no valor 00:00 até que seja premido reset
						out_led  <= '1';
						counting <= '0';            
					end if;
			end if;
		
		end if;		
		out_sec  <= std_logic_vector(count_sec);
		out_cent <= std_logic_vector(count_cent);
	end process;
end Behavioral;