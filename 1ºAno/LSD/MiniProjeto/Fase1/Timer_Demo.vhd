library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity Timer_Demo is
	port(CLOCK_50 : in  std_logic;
		  KEY      : in  std_logic_vector(1 downto 0);    --Start e reset
		  LEDR     : out std_logic_vector(0 downto 0);	  --Led que sinaliza o fim da contagem
		  HEX0     : out std_logic_vector(6 downto 0);	  --displays para o relógio
		  HEX1     : out std_logic_vector(6 downto 0);
		  HEX2     : out std_logic_vector(6 downto 0);
		  HEX3     : out std_logic_vector(6 downto 0));
end Timer_Demo;

architecture Shell of Timer_Demo is
	signal out_freq : std_logic;                          
	signal s_cent   : std_logic_vector(6 downto 0);
	signal s_sec    : std_logic_vector(6 downto 0);
	signal BCD0     : std_logic_vector(3 downto 0);
	signal BCD1     : std_logic_vector(3 downto 0);
	signal BCD2     : std_logic_vector(3 downto 0);
	signal BCD3     : std_logic_vector(3 downto 0);
begin
	
	freq_core    : entity work.FreqDivider(Behavioral)    --FreqDivider
							port map(clkIn  => CLOCK_50,			
										k      => x"0007A120",
										clkOut => out_freq);
	
	timer_core   : entity work.Timer(Behavioral)          --Timer
							port map(clk      => out_freq,
										start    => KEY(0),
										reset    => KEY(1),
										out_cent => s_cent,
										out_sec  => s_sec,
										out_led  => LEDR(0));
										
	BCD_Centcore  : entity work.Bin2BCDDecoder(Behavioral)   --Codificador de binário para BCD das Centésimas
							 port map(inBin   => s_cent,
								   	 outBCD0 => BCD0,
								 		 outBCD1 => BCD1);
										
	BCD_Seccore   : entity work.Bin2BCDDecoder(Behavioral)   --Codificador de binário para BCD dos Segundos
							 port map(inBin   => s_sec,
										 outBCD0 => BCD2,
										 outBCD1 => BCD3);
									
	Bin7_cent0    : entity work.Bin7SegDecoder(Behavioral)   --Display para o valor menos significativo das centésimas
							 port map(binInput  => BCD0,
										 decOut_n  => HEX0);
										 
	Bin7_cent1    : entity work.Bin7SegDecoder(Behavioral)  	--Display para o valor mais significativo das centésimas
							 port map(binInput  => BCD1,
										 decOut_n  => HEX1);
										 
	Bin7_sec0     : entity work.Bin7SegDecoder(Behavioral)   --Display para o valor menos significativo dos segundos
							  port map(binInput  => BCD2,
										  decOut_n  => HEX2);
	
	Bin7_sec1     : entity work.Bin7SegDecoder(Behavioral)   --Display para o valor mais significativo dos segundos
							  port map(binInput  => BCD3,
										  decOut_n  => HEX3);
end Shell;						 
										 
										