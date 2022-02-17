%% a) Tamanho médio do pacote e tempo médio de transmissão
C = 10;     % Capacidade do link
Y = 1000;   % Taxa de chegada de novos pacotes

numelems = (109 - 65 + 1) + (1517 - 111 + 1);
probrestante = 100 - (19 + 23 + 17);
probcadaelem = (probrestante / numelems);

a = 65:109;
a = a*(probcadaelem/100);
b = 111:1517;
b = b*(probcadaelem/100);

numMedioBytes = 0.19*64 + 0.23*110 + 0.17*1518 + sum(a) + sum(b);
tmpMedio = (numMedioBytes * 8) / (C*10^6);

fprintf("Average packet size: %.2f Bytes\nAverage packet transmission time: %.2e seconds\n\n", numMedioBytes, tmpMedio);

%% b) Throughput médio Mbps

throughput =  Y * (numMedioBytes / 125000);
fprintf("Average throughput: %.2f Mbps\n\n", throughput);

%% c) Capacidade do link em pps

numPacotes = C /  (numMedioBytes / 125000);
fprintf("Capacity of the link: %.2f pps\n\n", numPacotes);

%% d) Delay médio dos pacotes na queue e system delay

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

queuing = Y*ES2 / (2*(1-Y*ES));

propagation_delay = 10*10^-6; 
system = queuing + tmpMedio + propagation_delay;

fprintf("Queuing: %.2e seconds\nSystem packet delay: %.2e\n\n", queuing, system);

%% e) Gráfico com o average system delay em função do lambda (taxa de chegada dos pacotes)

lambdas = 100:2000;

queue = lambdas.*ES2 ./ (2*(1-lambdas*ES));
systemd = queue + tmpMedio + propagation_delay;

figure(1);
plot(lambdas, systemd);
title("Average system delay (seconds)");
xlabel("{\lambda} (pps)")
grid on;

%% f) Gráfico com o average system delay em função do lambda (taxa de chegada dos pacotes) mas para vários valores de lamda e de C (capacidade do link)

lambdas1 = 100:2000;
lambdas2 = 200:4000;
lambdas3 = 1000:20000;

C1 = 10;
C2 = 20;
C3 = 100;

x1 = (lambdas1 ./ (C1 /  (numMedioBytes / 125000)))*100;
x2 = (lambdas2 ./ (C2 /  (numMedioBytes / 125000)))*100;
x3 = (lambdas3 ./ (C3 /  (numMedioBytes / 125000)))*100;


S1 = (bytes .* 8)./(C1*10^6);
S12 = (bytes .* 8)./(C1*10^6);
S2 = (bytes .* 8)./(C2*10^6);
S22 = (bytes .* 8)./(C2*10^6);
S3 = (bytes .* 8)./(C3*10^6);
S32 = (bytes .* 8)./(C3*10^6);


for i = 1:length(bytes)
    if i == 1
        S1(i) = S1(i)*0.19;
        S12(i) = S12(i)^2*0.19;
        S2(i) = S2(i)*0.19;
        S22(i) = S22(i)^2*0.19;
        S3(i) = S3(i)*0.19;
        S32(i) = S32(i)^2*0.19;
    elseif i == 110-64+1
        S1(i) = S1(i)*0.23;
        S12(i) = S12(i)^2*0.23;
        S2(i) = S2(i)*0.23;
        S22(i) = S22(i)^2*0.23;
        S3(i) = S3(i)*0.23;
        S32(i) = S32(i)^2*0.23;
    elseif i == 1518-64+1
        S1(i) = S1(i)*0.17;
        S12(i) = S12(i)^2*0.17;
        S2(i) = S2(i)*0.17;
        S22(i) = S22(i)^2*0.17;
        S3(i) = S3(i)*0.17;
        S32(i) = S32(i)^2*0.17;
    else
        S1(i) = S1(i)*(probcadaelem/100);
        S12(i) = S12(i)^2*(probcadaelem/100);
        S2(i) = S2(i)*(probcadaelem/100);
        S22(i) = S22(i)^2*(probcadaelem/100);
        S3(i) = S3(i)*(probcadaelem/100);
        S32(i) = S32(i)^2*(probcadaelem/100);
    end
end

Q1 = lambdas1.*sum(S12) ./ (2*(1-lambdas1.*sum(S1)));
Q2 = lambdas2.*sum(S22) ./ (2*(1-lambdas2.*sum(S2)));
Q3 = lambdas3.*sum(S32) ./ (2*(1-lambdas3.*sum(S3)));

tmp1 = (numMedioBytes * 8) / (C1*10^6);
tmp2 = (numMedioBytes * 8) / (C2*10^6);
tmp3 = (numMedioBytes * 8) / (C3*10^6);

system1 = Q1 + tmp1 + propagation_delay;
system2 = Q2 + tmp2 + propagation_delay;
system3 = Q3 + tmp3 + propagation_delay;

figure(2);
plot(x1, system1, x2, system2, x3, system3);
title("Average system delay (seconds)");
xlabel("{\lambda} (% of the link capacity)");
legend({'C = 10 Mbps','C = 20 Mbps', 'C = 100 Mbps'},'Location','northwest')
axis([0 100 0 0.06]);
grid on;
