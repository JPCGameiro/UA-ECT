%% a) Probabilidade do link em cada um dos estados

births = [8, 5, 2, 1];
deaths = [600, 100, 20, 5];

x = [births(1), births(1)*births(2), births(1)*births(2)*births(3), births(1)*births(2)*births(3)*births(4)];
y = [deaths(1), deaths(1)*deaths(2), deaths(1)*deaths(2)*deaths(3) , deaths(1)*deaths(2)*deaths(3)*deaths(4)];

pi0 = 1 / (1 + sum(x./y));
pi1 = pi0 * sum(x(1)./y(1));
pi2 = pi0 * sum(x(2)./y(2));
pi3 = pi0 * sum(x(3)./y(3));
pi4 = pi0 * sum(x(4)./y(4));
fprintf("Probability of link being in each of the five states\n")
fprintf("Estado 10^-6: %.2e\n", pi0);
fprintf("Estado 10^-5: %.2e\n", pi1);
fprintf("Estado 10^-4: %.2e\n", pi2);
fprintf("Estado 10^-3: %.2e\n", pi3);
fprintf("Estado 10^-5: %.2e\n\n", pi4);

%% b) Percentagem média do link em cada um dos estados

fprintf("Average percentage of time the link is in each of the five states\n")
fprintf("Estado 10^-6: %.2e\n", pi0);
fprintf("Estado 10^-5: %.2e\n", pi1);
fprintf("Estado 10^-4: %.2e\n", pi2);
fprintf("Estado 10^-3: %.2e\n", pi3);
fprintf("Estado 10^-5: %.2e\n\n", pi4);

%% c) Média do BER do link

avgber = (10^-6)*pi0 + (10^-5)*pi1 + (10^-4)*pi2 + (10^-3)*pi3 + (10^-2)*pi4;
fprintf("Average BER of the link: %.2e\n\n", avgber);

%% d) Tempo médio em cada estado
m = [0 600 0 0 0;
     8 0 100 0 0;
     0 5 0 20 0;
     0 0 2 0 5;
     0 0 0 1 0];


t00 = (1 / sum(m(:,1)))*60;
t01 = (1 / sum(m(:,2)))*60;
t02 = (1 / sum(m(:,3)))*60;
t03 = (1 / sum(m(:,4)))*60;
t04 = (1 / sum(m(:,5)))*60;

fprintf("Average time duration that the link stays in each state\n")
fprintf("Estado10^-6: %1.2f minutos\n", t00);
fprintf("Estado10^-5: %1.2f minutos\n", t01);
fprintf("Estado10^-4: %1.2f minutos\n", t02);
fprintf("Estado10^-3: %1.2f minutos\n", t03);
fprintf("Estado10^-2: %1.2f minutos\n\n", t04);

%% e) Probabilidade do link estar em estado normal e em estado de interferência

pinterf = pi3 + pi4;
pnormal = pi0 + pi1 + pi2;

fprintf("Probability of normal state: %.6f \n", pnormal);
fprintf("Probability of interference state: %.2e \n\n", pinterf);


%% f) Ber médio quando link está no estado normal e em estado de interferência

pinterf = pi3 + pi4;
pnormal = pi0 + pi1 + pi2;

bern = ((10^-6)*pi0 + (10^-5)*pi1 + (10^-4)*pi2) / pnormal;
beri = ((10^-3)*pi3 + (10^-2)*pi4) / pinterf;

fprintf("Average ber when normal state: %.2e \n", bern);
fprintf("Average ber when interference state: %.2e \n", beri);

%% g) Gráfico com a probabilidade de pelo menos um erro

bytes = 64:1500;


%avgber = (10^-6)*pi0 + (10^-5)*pi1 + (10^-4)*pi2 + (10^-3)*pi3 + (10^-2)*pi4;
prob = zeros(1, length(bytes));
perr_1 = zeros(1, length(bytes));
perr_2 = zeros(1, length(bytes));
perr_3 = zeros(1, length(bytes));
perr_4 = zeros(1, length(bytes));
perr_5 = zeros(1, length(bytes));


for i = 1:length(bytes)
    perr_1(i) = 1 - (( nchoosek(bytes(i)*8 , 0) * (10^-6)^0 * (1-(10^-6))^(bytes(i)*8 - 0) ));
    perr_2(i) = 1 - (( nchoosek(bytes(i)*8 , 0) * (10^-5)^0 * (1-(10^-5))^(bytes(i)*8 - 0) ));
    perr_3(i) = 1 - (( nchoosek(bytes(i)*8 , 0) * (10^-4)^0 * (1-(10^-4))^(bytes(i)*8 - 0) ));
    perr_4(i) = 1 - (( nchoosek(bytes(i)*8 , 0) * (10^-3)^0 * (1-(10^-3))^(bytes(i)*8 - 0) ));
    perr_5(i) = 1 - (( nchoosek(bytes(i)*8 , 0) * (10^-2)^0 * (1-(10^-2))^(bytes(i)*8 - 0) ));
    prob(i) = perr_1(i)*pi0 + perr_2(i)*pi1 + perr_3(i)*pi2 + perr_4(i)*pi3 + perr_5(i)*pi4; 
end

figure(1)
plot(bytes, prob)
title("Prob. of at least one error")
xlabel("B (Bytes)")
axis([0 1500 0 0.014])
grid on

%% h) Gráfico com a probabilidade de estar no estado normal

p_errs1 = 1 - (1 - 10^-6).^(bytes.*8);
p_errs2 = 1 - (1 - 10^-5).^(bytes.*8);
p_errs3 = 1 - (1 - 10^-4).^(bytes.*8);
p_errs4 = 1 - (1 - 10^-3).^(bytes.*8);
p_errs5 = 1 - (1 - 10^-2).^(bytes.*8);

pnormal_plot = (p_errs1.*pi0 + p_errs2.*pi1 + p_errs3.*pi2) ./ (p_errs1.*pi0 + p_errs2.*pi1 + p_errs3.*pi2 + p_errs4.*pi3 + p_errs5.*pi4);

figure(2)
plot(bytes, pnormal_plot)
title("Prob. of Normal State")
xlabel("B (Bytes)")
axis([0 1500 0.93 1])
grid on

%% i) Gráfico com a probabilidade de estar no estado de interferência

p_n1 = (1 - 10^-6).^(bytes.*8);
p_n2 = (1 - 10^-5).^(bytes.*8);
p_n3 = (1 - 10^-4).^(bytes.*8);
p_n4 = (1 - 10^-3).^(bytes.*8);
p_n5 = (1 - 10^-2).^(bytes.*8);

pint_plot = (p_n4.*pi3 + p_n5.*pi4) ./ (p_n1.*pi0 + p_n2.*pi1 + p_n3.*pi2 + p_n4.*pi3 + p_n5.*pi4);

figure(3)
semilogy(bytes, pint_plot)
title("Prob. of Interference State")
xlabel("B (Bytes)")
axis([0 1500 10^-10 10^-4])
grid on
 