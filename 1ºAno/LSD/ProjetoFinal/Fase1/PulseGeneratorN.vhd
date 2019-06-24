library IEEE;
use IEEE.STD_LOGIC_1164.all;
use IEEE.NUMERIC_STD.all;

entity PulseGeneratorN is
	generic(numberSteps  : positive := 4;
			  outCompVal   : natural  := 2);
		port(clkIn    : in  std_logic;
			  enable0  : in  std_logic_vector(0 downto 0);      
			  enable1  : in  std_logic_vector(0 downto 0);
			  enable2  : in  std_logic_vector(0 downto 0);
			  enable3  : in  std_logic_vector(0 downto 0);
			  pulseOut : out std_logic);
end PulseGeneratorN;

architecture Behavioral of PulseGeneratorN is
	subtype TCounter is natural range 0 to (numberSteps - 1);
	signal s_counter : TCounter;
begin
	count_proc : process(clkIn)
	begin
		if (rising_edge(clkIn)) then    --se algum dos enables estiver ativo o sinal é ativado
			if(enable0(0) = '1' or enable1(0) = '1' or enable2(0) = '1' or enable3(0) = '1')then
				if (s_counter >= (numberSteps - 1)) then
					s_counter <= 0;
				else
					s_counter <= s_counter + 1;
				end if;
			else
				s_counter <= outCompVal;
			end if;
		end if;
	end process;
	
	pulseOut <= '1' when (s_counter < outCompVal) else
					 '0';
end Behavioral;
	