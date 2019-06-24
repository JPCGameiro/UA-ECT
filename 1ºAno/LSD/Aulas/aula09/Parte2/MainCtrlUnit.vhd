library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity MainCtrlUnit is
   port(clk       : in  std_logic;
        reset     : in  std_logic;
        multDiv_n : in  std_logic;
        start     : in  std_logic;
        multStart : out std_logic;
        divStart  : out std_logic;
        multDone  : in  std_logic;
        divDone   : in  std_logic;
        multSel   : out std_logic);
end MainCtrlUnit;


architecture Behavioral of MainCtrlUnit is

   type TState is (ST_IDLE, ST_MULT_START, ST_MULT_WAIT,
                   ST_DIV_START, ST_DIV_WAIT);
   signal s_state : TState;

begin
   process(clk)
   begin
      if (rising_edge(clk)) then
         if (reset = '1') then
            s_state   <= ST_IDLE;
            multStart <= '0';
            divStart  <= '0';
            multSel   <= '1';

         else
            case s_state is
            when ST_IDLE       =>
               if (start = '1') then
                  if (multDiv_n = '1') then
                     s_state <= ST_MULT_START;
                     multStart <= '1';
                     divStart  <= '0';
                     multSel   <= '1';
                  else
                     s_state <= ST_DIV_START;
                     multStart <= '0';
                     divStart  <= '1';
                     multSel   <= '0';
                  end if;
               end if;

            when ST_MULT_START =>
               s_state <= ST_MULT_WAIT;
               multStart <= '0';
               divStart  <= '0';
               multSel   <= '1';

            when ST_MULT_WAIT  =>
               if (multDone = '1') then
                  s_state <= ST_IDLE;
                  multStart <= '0';
                  divStart  <= '0';
                  multSel   <= '1';
               end if;

            when ST_DIV_START  =>
               s_state <= ST_DIV_WAIT;
               multStart <= '0';
               divStart  <= '0';
               multSel   <= '0';

            when ST_DIV_WAIT   =>
               if (divDone = '1') then
                  s_state <= ST_IDLE;
                  multStart <= '0';
                  divStart  <= '0';
                  multSel   <= '0';
               end if;
            end case;
         end if;
      end if;
   end process;
end Behavioral;
