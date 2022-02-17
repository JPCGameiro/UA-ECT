%% b) Run Simulator 2 100 times
P = 10000;
l = 1800;
C = 10;
f = 1000000;
b = 10^-6;

%Number of simulations
N = 100;

PL = zeros(1, N);
APD = zeros(1, N);
MPD = zeros(1, N);
TT = zeros(1, N);

for i = 1:N
    [PL(i), APD(i), MPD(i), TT(i)] = Simulator2(l,C,f,P, b);
end

alfa = 0.1;

media = mean(PL);
term = norminv(1-alfa/2)*sqrt(var(PL)/N);
fprintf("Packet Loss (%%)       = %.2e +- %.2e\n", media, term);
media = mean(APD);
term = norminv(1-alfa/2)*sqrt(var(APD)/N);
fprintf("Av. Packet Delay (ms) = %.2e +- %.2e\n", media, term);
media = mean(MPD);
term = norminv(1-alfa/2)*sqrt(var(MPD)/N);
fprintf("Max Packet Delay (ms) = %.2e +- %.2e\n", media, term);
media = mean(TT);
term = norminv(1-alfa/2)*sqrt(var(TT)/N);
fprintf("Throughut (Mbps)      = %.2e +- %.2e\n\n", media, term);


%% c) Run Simulator 2 100 times but with f = 10 000 Bytesx
P = 10000;
l = 1800;
C = 10;
f = 10000;
b = 10^-6;

%Number of simulations
N = 100;

PL = zeros(1, N);
APD = zeros(1, N);
MPD = zeros(1, N);
TT = zeros(1, N);

for i = 1:N
    [PL(i), APD(i), MPD(i), TT(i)] = Simulator2(l,C,f,P, b);
end

alfa = 0.1;

media = mean(PL);
term = norminv(1-alfa/2)*sqrt(var(PL)/N);
fprintf("Packet Loss (%%)       = %.2e +- %.2e\n", media, term);
media = mean(APD);
term = norminv(1-alfa/2)*sqrt(var(APD)/N);
fprintf("Av. Packet Delay (ms) = %.2e +- %.2e\n", media, term);
media = mean(MPD);
term = norminv(1-alfa/2)*sqrt(var(MPD)/N);
fprintf("Max Packet Delay (ms) = %.2e +- %.2e\n", media, term);
media = mean(TT);
term = norminv(1-alfa/2)*sqrt(var(TT)/N);
fprintf("Throughut (Mbps)      = %.2e +- %.2e\n\n", media, term);


%% d) Run Simulator 2 100 times but with f = 2 000 Bytesx
P = 10000;
l = 1800;
C = 10;
f = 2000;
b = 10^-6;

%Number of simulations
N = 100;

PL = zeros(1, N);
APD = zeros(1, N);
MPD = zeros(1, N);
TT = zeros(1, N);

for i = 1:N
    [PL(i), APD(i), MPD(i), TT(i)] = Simulator2(l,C,f,P, b);
end

alfa = 0.1;

media = mean(PL);
term = norminv(1-alfa/2)*sqrt(var(PL)/N);
fprintf("Packet Loss (%%)       = %.2e +- %.2e\n", media, term);
media = mean(APD);
term = norminv(1-alfa/2)*sqrt(var(APD)/N);
fprintf("Av. Packet Delay (ms) = %.2e +- %.2e\n", media, term);
media = mean(MPD);
term = norminv(1-alfa/2)*sqrt(var(MPD)/N);
fprintf("Max Packet Delay (ms) = %.2e +- %.2e\n", media, term);
media = mean(TT);
term = norminv(1-alfa/2)*sqrt(var(TT)/N);
fprintf("Throughut (Mbps)      = %.2e +- %.2e\n\n", media, term);

