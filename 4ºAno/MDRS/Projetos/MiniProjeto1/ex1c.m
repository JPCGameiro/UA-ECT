% ex 1.c
N = 50;

lambda = 1800;  % pps
f = 1000000;
C = [10, 20, 30, 40];         % Mbps
P = 10000;      % packets

alfa = 0.1;

APD = zeros(4, N);
media = zeros(4, 1);
term = zeros(4, 1);
for i = [1:4]  
    for n = [1:N]
        [~, APD(i, n), ~, ~] = Simulator1(lambda, C(i), f, P);
    end
    
    media(i, 1) = mean(APD(1,:));
    term(i, 1) = norminv(1-alfa/2) * sqrt(var(APD(1,:))/N);
end

bar(C, APD(:, 1))
hold on
er = errorbar(C, APD(:, 1), term(:, 1) * -1, term(:, 1));    
er.Color = [0 0 0];                            
er.LineStyle = 'none';  
hold off
title("Avg. Packet Delay");
xlabel("C, link capacity (Mbs)");
ylabel("APD, Avg. Packet Delay (ms)");
