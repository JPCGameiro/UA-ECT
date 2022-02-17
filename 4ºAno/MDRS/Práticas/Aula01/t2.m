%% a)Probabilidade de um pacote de 100 Bytes ser recebido sem erros quando ber(bit error rate) = 10^-2

p = 10^-2;      % BER
n = 100 * 8;    % Bits do pacote

prob = (nchoosek(n, 0) * p^0 * (1-p)^(n-0))*100;
fprintf("Probabilidade do pacote ser recebido sem erros: %1.3g%%\n", prob)

%% b) Probabilidade de um pacote de 1000 Bytes ser recebido com um erro quanto ber = 10^-3

p = 10^-3;      % BER
n = 1000 * 8;   % Bits do pacote

prob = (nchoosek(n,1) * p^1 * (1-p)^(n-1))*100;
fprintf("Probabilidade do pacote ser recebido sem erros: %1.4g%%\n", prob)

%% c) Probabilidade de um pacote de 200 Bytes ser recebido com um ou mais erros quando ber = 10^-4

p = 10^-4;      % BER
n = 200 * 8;    % Bits do Pacote

prob = (1 - (nchoosek(n,0) * p^0 * (1-p)^(n-0)))*100;
fprintf("Probabilidade do pacote ser recebido sem erros: %1.6g%%\n", prob)

%% d) Desenhar um gráfico com a probabilidade da não existência erros na receção dos pacotes em função do ber

p = logspace(-8, -2);   % BER
n1 = 100 * 8;           % Bits do Pacote
n2 = 200 * 8;           % Bits do Pacote
n3 = 1000 * 8;          % Bits do Pacote

prob1 = (nchoosek(n1, 0) * 1 * (1-p).^(n1-0))*100;
prob2 = (nchoosek(n2, 0) * 1 * (1-p).^(n2-0))*100;
prob3 = (nchoosek(n3, 0) * 1 * (1-p).^(n3-0))*100;

figure(1)
semilogx(p, prob1, p, prob2,'--',  p, prob3,':')
xlabel("Bit Error Rate")
title("Probability of packet reception without errors(%)")
legend({'100 Bytes','200 Bytes', '1000 Bytes'},'Location','southwest')
grid on

%% e) Desenhar um gráfico com a probabilidade da receção de pacotes em erros m função do tamanho dos pacotes

psize = 64:1518;        % Tamanho dos pacotes
p1 = 10^-4;             % BER
p2 = 10^-3;             % BER
p3 = 10^-2;             % BER

prob1 = zeros(1, length(psize));
prob2 = zeros(1, length(psize));
prob3 = zeros(1, length(psize));

for i = 1:length(psize)
    prob1(i) = ( nchoosek(psize(i)*8, 0) * 1 * (1-p1)^(psize(i)*8-0) )*100;
    prob2(i) = ( nchoosek(psize(i)*8, 0) * 1 * (1-p2)^(psize(i)*8-0) )*100;
    prob3(i) = ( nchoosek(psize(i)*8, 0) * 1 * (1-p3)^(psize(i)*8-0) )*100;
end

figure(2)
semilogy(psize, prob1, psize, prob2, '--', psize, prob3, ':')
axis([0 1601 10^-60 100])
title("Probability of packet reception without errors")
xlabel("Packet Size (Bytes)")
legend({'ber=1e-4','ber=1e-3', 'ber=1e-2'},'Location','southwest')
grid on





