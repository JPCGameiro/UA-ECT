library ieee;
use ieee.std_logic_1164.all;

library logic;
use logic.all;

library decoder4to11;
use decoder4to11.all;

entity decoderParallel is
	port(y   	: in  std_logic_vector(14 downto 0);
		  ml		: out std_logic_vector(10 downto 0));
end decoderParallel;

architecture structural of decoderParallel is
	signal s0 : std_logic_vector(11 downto 0);
	signal s1 : std_logic_vector(5 downto 0);
	signal s2 : std_logic_vector(3 downto 0);
	signal s3 : std_logic_vector(10 downto 0);

	component gateXor2
		port(x0, x1		: in  std_logic;
			  y			: out std_logic);
	end component;
	component decoder4to11
		port(p		: in  std_logic_vector(3 downto 0);
			  a		: out std_logic_vector(10 downto 0));
	end component; 
begin
	
	xor00l0 : gateXor2 port map (y(1), y(2), s0(0));	
	xor01l0 : gateXor2 port map (y(8), y(11), s0(1));
	xor02l0 : gateXor2 port map (y(3), y(4), s0(2));
	xor03l0 : gateXor2 port map (y(9), y(12), s0(3));
	xor04l0 : gateXor2 port map (y(0), y(10), s0(4));
	xor05l0 : gateXor2 port map (y(6), y(7), s0(5));
	xor06l0 : gateXor2 port map (y(8), y(9), s0(6));
	xor07l0 : gateXor2 port map (y(5), y(10), s0(7));
	xor08l0 : gateXor2 port map (y(1), y(3), s0(8));
	xor09l0 : gateXor2 port map (y(6), y(13), s0(9));
	xor10l0 : gateXor2 port map (y(2), y(4), s0(10));
	xor11l0 : gateXor2 port map (y(7), y(14), s0(11));
	
	xor00l1 : gateXor2 port map (s0(0), s0(1), s1(0));
	xor01l1 : gateXor2 port map (s0(2), s0(3), s1(1));
	xor02l1 : gateXor2 port map (s0(4), s0(5), s1(2));
	xor03l1 : gateXor2 port map (s0(6), s0(7), s1(3));
	xor04l1 : gateXor2 port map (s0(8), s0(9), s1(4));
	xor05l1 : gateXor2 port map (s0(10), s0(11), s1(5));
	
	xor00l2 : gateXor2 port map (s1(0), s1(2), s2(0));
	xor01l2 : gateXor2 port map (s1(1), s1(2), s2(1));
	xor02l2 : gateXor2 port map (s1(3), s1(4), s2(2));
	xor03l2 : gateXor2 port map (s1(3), s1(5), s2(3));
	
	dec4to11 : decoder4to11 port map (s2, s3);
	
	xor00l3 : gateXor2 port map (s3(0), y(0), ml(0));	
	xor01l3 : gateXor2 port map (s3(1), y(1), ml(1));
	xor02l3 : gateXor2 port map (s3(2), y(2), ml(2));
	xor03l3 : gateXor2 port map (s3(3), y(3), ml(3));
	xor04l3 : gateXor2 port map (s3(4), y(4), ml(4));
	xor05l3 : gateXor2 port map (s3(5), y(5), ml(5));
	xor06l3 : gateXor2 port map (s3(6), y(6), ml(6));
	xor07l3 : gateXor2 port map (s3(7), y(7), ml(7));
	xor08l3 : gateXor2 port map (s3(8), y(8), ml(8));
	xor09l3 : gateXor2 port map (s3(9), y(9), ml(9));
	xor10l3 : gateXor2 port map (s3(10), y(10), ml(10));
	
end structural;