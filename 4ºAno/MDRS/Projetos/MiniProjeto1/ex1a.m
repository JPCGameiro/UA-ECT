%% a) Correr simulador1 50 vezes com critério de paragem P=10000
P = 10000;
l = [400, 800, 1200, 1600, 2000];
C = 10;
f = 1000000;
delays = zeros(1, length(l));
errors = zeros(1, length(l));

N = 50;

for j = 1:length(l)
    
    PL = zeros(1, N);
    APD = zeros(1, N);
    MPD = zeros(1, N);
    TT = zeros(1, N);
    
    for i = 1:N
        [PL(i), APD(i), MPD(i), TT(i)] = Simulator1(l(j),C,f,P);
    end
    alfa = 0.1;    
    
    media = mean(APD);
    term = norminv(1-alfa/2)*sqrt(var(APD)/N);
    fprintf("Av. Packet Delay (ms) = %.2e +- %.2e\n", media, term);
    delays(j) = media;
    errors(j) = term;
end

figure(1)
bar(l, delays)
xlabel("Packet Rate (pps)")
ylabel("Average Packet Delay (ms)")
hold on

er = errorbar(l, delays, errors, errors);
er.Color = [0 0 0];
er.LineStyle = 'none';
hold off





