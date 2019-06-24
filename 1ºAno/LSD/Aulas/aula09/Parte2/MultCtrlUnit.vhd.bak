library IEEE;
use IEEE.STD_LOGIC_1164.all;

entity MultCtrlUnit is
   generic(numBits  : positive);
   port(clk         : in  std_logic;
        reset       : in  std_logic;
        start       : in  std_logic;
        busy        : out std_logic;
        done        : out std_logic;
        mulplierLSb : in  std_logic;
        regsInit    : out std_logic;
        accEnable   : out std_logic;
        regsShift   : out std_logic);
end MultCtrlUnit;


---------------------------------------------------------------------------
-- 4 processes architecture
-- (state register + counter + state transitions + outputs)
---------------------------------------------------------------------------

architecture Behavioral_4Proc_AsyncOut of MultCtrlUnit is

   type TState is (ST_IDLE, ST_INIT, ST_BIT_TEST,
                   ST_RES_ACC, ST_SHIFT);
   signal s_currentState, s_nextState : TState;

   subtype TCounter is natural range 0 to numBits;
   signal s_iterCnt : TCounter;

begin
   process(clk)
   begin
      if (rising_edge(clk)) then
         if (reset = '1') then
            s_currentState <= ST_IDLE;
         else
            s_currentState <= s_nextState;
         end if;
     end if;
   end process;

   process(clk)
   begin
      if (rising_edge(clk)) then
         if (s_currentState = ST_IDLE) then
            s_iterCnt <= 0;
         elsif (s_currentState = ST_BIT_TEST) then
            s_iterCnt <= s_iterCnt + 1;
         end if;
      end if;
   end process;
   
   process(s_currentState, start, mulplierLSb)
   begin
      s_nextState <= s_currentState;

      case s_currentState is
      when ST_IDLE =>
         if (start = '1') then
            s_nextState <= ST_INIT;
         end if;

      when ST_INIT =>
         s_nextState <= ST_BIT_TEST;

      when ST_BIT_TEST =>
         if (mulplierLSb = '1') then
            s_nextState <= ST_RES_ACC;
         else
            s_nextState <= ST_SHIFT;
         end if;

      when ST_RES_ACC =>
         s_nextState <= ST_SHIFT;

      when ST_SHIFT =>
         if (s_iterCnt < numBits) then
            s_nextState <= ST_BIT_TEST;
         else
            s_nextState <= ST_IDLE;
         end if;
      end case;
   end process;

   process(s_currentState)
   begin
      busy      <= '0';
      done      <= '0';
      regsInit  <= '0';
      accEnable <= '0';
      regsShift <= '0'; 

      case s_currentState is
      when ST_IDLE =>
         done      <= '1';

      when ST_INIT =>
         busy      <= '1';
         regsInit  <= '1';

      when ST_BIT_TEST =>
         busy      <= '1';

      when ST_RES_ACC =>
         busy      <= '1';
         accEnable <= '1';

      when ST_SHIFT =>
         busy      <= '1';
         regsShift <= '1'; 
      end case;
   end process;
end Behavioral_4Proc_AsyncOut;


---------------------------------------------------------------------------
-- State transition and counter with
-- synchronous outputs single process architecture
---------------------------------------------------------------------------

architecture Behavioral_1Proc_SyncOut of MultCtrlUnit is

   type TState is (ST_IDLE, ST_INIT, ST_BIT_TEST, ST_RES_ACC, ST_SHIFT);
   signal s_state : TState;

   subtype TCounter is natural range 0 to numBits;
   signal s_iterCnt : TCounter;

begin
   process(clk)
   begin
      if (rising_edge(clk)) then
         if (reset = '1') then
            s_state   <= ST_IDLE;
            busy      <= '0';
            done      <= '1';
            regsInit  <= '0';
            accEnable <= '0';
            regsShift <= '0';

         else
            case s_state is
            when ST_IDLE =>
               if (start = '1') then
                  s_state  <= ST_INIT;
                  busy     <= '1';
                  done     <= '0';
                  regsInit <= '1';
               end if;
               s_iterCnt <= 0;

            when ST_INIT =>
               s_state  <= ST_BIT_TEST;
               regsInit <= '0';

            when ST_BIT_TEST =>
               if (mulplierLSb = '1') then
                  s_state   <= ST_RES_ACC;
                  accEnable <= '1';
               else
                  s_state   <= ST_SHIFT;
                  regsShift <= '1';
               end if;
               s_iterCnt <= s_iterCnt + 1;

            when ST_RES_ACC =>
               s_state   <= ST_SHIFT;
               accEnable <= '0';
               regsShift <= '1';

            when ST_SHIFT =>
               if (s_iterCnt < numBits) then
                  s_state <= ST_BIT_TEST;
               else
                  s_state <= ST_IDLE;
                  busy    <= '0';
                  done    <= '1';
               end if;
               regsShift <= '0';
            end case;
         end if;
      end if;
   end process;
end Behavioral_1Proc_SyncOut;
