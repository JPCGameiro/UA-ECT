%% b) Correr simulador1 50 vezes com critério de paragem P=10000 mas lambda = 1800
P = 10000;
l = 1800;
C = 10;
f = [100000, 20000, 10000, 2000];

delays = zeros(1, length(f));
errorsd = zeros(1, length(f));
loss = zeros(1, length(f));
errorsl = zeros(1, length(f));

N = 50;
for j = 1:length(f)
    
    PL = zeros(1, N);
    APD = zeros(1, N);
    MPD = zeros(1, N);
    TT = zeros(1, N);

    for i = 1:N
        [PL(i), APD(i), MPD(i), TT(i)] = Simulator1(l,C,f(j),P);
    end
    alfa = 0.1;
    
    media = mean(PL);
    term = norminv(1-alfa/2)*sqrt(var(PL)/N);
    fprintf("Packet Loss of data (%%)       = %.2e +- %.2e\n", media, term);
    loss(j) = media;
    errorsl(j) = term;

    media = mean(APD);
    term = norminv(1-alfa/2)*sqrt(var(APD)/N);
    fprintf("Av. Packet Delay (ms)         = %.2e +- %.2e\n", media, term);
    delays(j) = media;
    errorsd(j) = term;
end

figure(1)
bar(categorical(f), delays)
xlabel("Queue Size (Bytes)")
ylabel("Average Packet Delay (ms)")
hold on
er = errorbar(categorical(f), delays, errorsd, errorsd);
er.Color = [0 0 0];
er.LineStyle = 'none';
hold off;


figure(2)
bar(categorical(f), loss)
xlabel("Queue Size (Bytes)")
ylabel("Packet Loss (%)")
hold on
er = errorbar(categorical(f), loss, errorsl, errorsl);
er.Color = [0 0 0];
er.LineStyle = 'none';
hold off;

