% 2 prioridades:
%   n = 1 -> VoIP
%   n = 2 -> data

% tamanho medio dos pacotes de data
prob = (1 - (0.19 + 0.23 + 0.17)) / ((109 - 65 + 1) + (1517 - 111 + 1));
AVGpckt_size = round(64*0.19 + 110*0.23 + 1518*0.17 + sum([65:109] * prob) + sum([111:1517] * prob), 2);
% tamanho medio dos pacotes de VoIP = 120
% taxa de chegada de pacotes do tipo VoIP é 1 a cada 20 ms em media
lambda1 = (1/20e-3);   % pps
lambda2 = 1500;     % pps
u1 = 10e6/(8 * 120);    % pps
u2 = 10e6/(8 * AVGpckt_size);   %pps
ES1= 1/u1;  % seg
ES2 = 1/u2;     % seg
ES1_2 = 2/(u1^2);   % seg^2
ES2_2 = 2/(u2^2);   % seg^2

n = [10, 20, 30, 40];
y = zeros(1,4);

for i = n
    lambda1 = (1/20e-3) * i;
    p1 = lambda1 * ES1;
    w1 = ((lambda1 * ES1_2 + lambda2 * ES2_2) / (2 * (1 - p1))) + ES1;
    % to ms
    w1 = w1 * 1e3;
    fprintf('n=%i -> w1 = %0.2f ms\n', i, w1);
    y(i/10) = w1;
end

figure(2);
bar(n, y);
title('Average Voip Packet Delay (Teorical)')
xlabel('Number of VoIP packet Flows')
ylabel('Average Voip Packet Delay (ms)')
