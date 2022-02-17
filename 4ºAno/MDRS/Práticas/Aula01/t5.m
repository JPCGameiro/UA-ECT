%% a) Correr simulador1 10 vezes com critério de paragem P=10000
P = 10000;
l = 1800;
C = 10;
f = 1000000;

%Number of simulations
N = 10;

PL = zeros(1, N);
APD = zeros(1, N);
MPD = zeros(1, N);
TT = zeros(1, N);

for i = 1:N
    [PL(i), APD(i), MPD(i), TT(i)] = Simulator1(l,C,f,P);
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


%% b) Correr simulador1 100 vezes com critério de paragem P=10000
P = 10000;
l = 1800;
C = 10;
f = 1000000;

%Number of simulations
N = 100;

PL = zeros(1, N);
APD = zeros(1, N);
MPD = zeros(1, N);
TT = zeros(1, N);

for i = 1:N
    [PL(i), APD(i), MPD(i), TT(i)] = Simulator1(l,C,f,P);
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


%% c) Correr simulador1 100 vezes com critério de paragem P=10000 mas f=10000 
P = 10000;
l = 1800;
C = 10;
f = 10000;

%Number of simulations
N = 100;

PL = zeros(1, N);
APD = zeros(1, N);
MPD = zeros(1, N);
TT = zeros(1, N);

for i = 1:N
    [PL(i), APD(i), MPD(i), TT(i)] = Simulator1(l,C,f,P);
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



%% d) Correr simulador1 100 vezes com critério de paragem P=10000 mas f=2000 
P = 10000;
l = 1800;
C = 10;
f = 2000;

%Number of simulations
N = 100;

PL = zeros(1, N);
APD = zeros(1, N);
MPD = zeros(1, N);
TT = zeros(1, N);

for i = 1:N
    [PL(i), APD(i), MPD(i), TT(i)] = Simulator1(l,C,f,P);
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


%% e) Considerando que o sistema é modelado por M/G/1 calcular os valores teóricos para Packet loss, Avg Packet Delay e Throughput
P = 10000;
l = 1800;
C = 10;
f = 1000000;

numelems = (109 - 65 + 1) + (1517 - 111 + 1);
probrestante = 100 - (19 + 23 + 17);
probcadaelem = (probrestante / numelems);

a = 65:109;
a = a*(probcadaelem/100);
b = 111:1517;
b = b*(probcadaelem/100);

numMedioBytes = 0.19*64 + 0.23*110 + 0.17*1518 + sum(a) + sum(b);
tmpMedio = (numMedioBytes * 8) / (C*10^6);

%Cálculo do throughput
throughput =  l * (numMedioBytes / 125000);

bytes = 64:1518;
S = (bytes .* 8)./(C*10^6);
S2 = (bytes .* 8)./(C*10^6);

for i = 1:length(bytes)
    if i == 1
        S(i) = S(i)*0.19;
        S2(i) = S2(i)^2*0.19;
    elseif i == 110-64+1
        S(i) = S(i)*0.23;
        S2(i) = S2(i)^2*0.23;
    elseif i == 1518-64+1
        S(i) = S(i)*0.17;
        S2(i) = S2(i)^2*0.17;
    else
        S(i) = S(i)*(probcadaelem/100);
        S2(i) = S2(i)^2*(probcadaelem/100);
    end
end

ES = sum(S);
ES2 = sum(S2);

%Cálculo do delay médio por pacote
queuing = l*ES2 / (2*(1-l*ES)) + ES;

%Cálculo da percentagem de perda de pacotes
ploss = ( (numMedioBytes / 125000) / ((f/125000)+C*10^6))*100;

fprintf("Packet Loss (%%)        = %.4f\n", ploss);
fprintf("Av. Packet Delay(ms)   = %.4f\n", queuing*1000);
fprintf("Throughput (Mbps)      = %.4f \n\n", throughput);

