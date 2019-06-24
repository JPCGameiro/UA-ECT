-- Copyright (C) 2017  Intel Corporation. All rights reserved.
-- Your use of Intel Corporation's design tools, logic functions 
-- and other software and tools, and its AMPP partner logic 
-- functions, and any output files from any of the foregoing 
-- (including device programming or simulation files), and any 
-- associated documentation or information are expressly subject 
-- to the terms and conditions of the Intel Program License 
-- Subscription Agreement, the Intel Quartus Prime License Agreement,
-- the Intel FPGA IP License Agreement, or other applicable license
-- agreement, including, without limitation, that your use is for
-- the sole purpose of programming logic devices manufactured by
-- Intel and sold by Intel or its authorized distributors.  Please
-- refer to the applicable agreement for further details.

-- Generated by Quartus Prime Version 17.1.0 Build 590 10/25/2017 SJ Lite Edition
-- Created on Mon May 13 14:53:33 2019

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY ControlUnit IS
    PORT (
        clk : IN STD_LOGIC;
        reset : IN STD_LOGIC := '0';
        statop : IN STD_LOGIC := '0';
        laprst : IN STD_LOGIC := '0';
        cntRst : OUT STD_LOGIC;
        cntEnb : OUT STD_LOGIC;
        regEnb : OUT STD_LOGIC
    );
END ControlUnit;

ARCHITECTURE BEHAVIOR OF ControlUnit IS
    TYPE type_fstate IS (CLEARED,STARTED,STOPPED,LAPVIEW,LAPVIEWSTOP);
    SIGNAL fstate : type_fstate;
    SIGNAL reg_fstate : type_fstate;
BEGIN
    PROCESS (clk,reg_fstate)
    BEGIN
        IF (clk='1' AND clk'event) THEN
            fstate <= reg_fstate;
        END IF;
    END PROCESS;

    PROCESS (fstate,reset,statop,laprst)
    BEGIN
        IF (reset='1') THEN
            reg_fstate <= CLEARED;
            cntRst <= '0';
            cntEnb <= '0';
            regEnb <= '0';
        ELSE
            cntRst <= '0';
            cntEnb <= '0';
            regEnb <= '0';
            CASE fstate IS
                WHEN CLEARED =>
                    IF ((statop = '1')) THEN
                        reg_fstate <= STARTED;
                    -- Inserting 'else' block to prevent latch inference
                    ELSE
                        reg_fstate <= CLEARED;
                    END IF;

                    cntEnb <= '1';

                    regEnb <= '1';

                    cntRst <= '1';
                WHEN STARTED =>
                    IF ((laprst = '1')) THEN
                        reg_fstate <= LAPVIEW;
                    ELSIF ((NOT((laprst = '1')) AND (statop = '1'))) THEN
                        reg_fstate <= STOPPED;
                    -- Inserting 'else' block to prevent latch inference
                    ELSE
                        reg_fstate <= STARTED;
                    END IF;

                    cntEnb <= '1';

                    regEnb <= '1';

                    cntRst <= '0';
                WHEN STOPPED =>
                    IF ((statop = '1')) THEN
                        reg_fstate <= STARTED;
                    ELSIF ((NOT((statop = '1')) AND (laprst = '1'))) THEN
                        reg_fstate <= CLEARED;
                    -- Inserting 'else' block to prevent latch inference
                    ELSE
                        reg_fstate <= STOPPED;
                    END IF;

                    cntEnb <= '0';

                    regEnb <= '1';

                    cntRst <= '0';
                WHEN LAPVIEW =>
                    IF ((laprst = '1')) THEN
                        reg_fstate <= STARTED;
                    ELSIF (((statop = '1') AND NOT((laprst = '1')))) THEN
                        reg_fstate <= LAPVIEWSTOP;
                    -- Inserting 'else' block to prevent latch inference
                    ELSE
                        reg_fstate <= LAPVIEW;
                    END IF;

                    cntEnb <= '1';

                    regEnb <= '0';

                    cntRst <= '0';
                WHEN LAPVIEWSTOP =>
                    IF ((laprst = '1')) THEN
                        reg_fstate <= STOPPED;
                    -- Inserting 'else' block to prevent latch inference
                    ELSE
                        reg_fstate <= LAPVIEWSTOP;
                    END IF;

                    cntEnb <= '0';

                    regEnb <= '0';

                    cntRst <= '0';
                WHEN OTHERS => 
                    cntRst <= 'X';
                    cntEnb <= 'X';
                    regEnb <= 'X';
                    report "Reach undefined state";
            END CASE;
        END IF;
    END PROCESS;
END BEHAVIOR;
