%% a) Probabilidade de acertar
p = 0.6;     % percentagem estudada do conteúdo do teste
n = 4;       % número de alíneas

pa = (1*p + 1/n*(1-p)) * 100; 
fprintf("Probabilidade de acertar: %d%%\n", pa)

%% b) Probabilidade de saber sabendo que acertou a resposta

p = 0.7;    % percentagem estudada do conteúdo do teste
n = 5;      % número de alíneas

psa = (1*p/(1*p + 1/n*(1-p)))*100;
fprintf("Probabilidade de saber sabendo que acertou a respota: %1.3g%%\n", psa)

%% c) Desenhar um gráfico com a probabilidade de selecionar a resposta correta como função de p

n1 = 3;
n2 = 4;
n3 = 5;
p = [0, 100];

pa1 = (1*(p./100) + 1/n1*(1-(p./100))) * 100;
pa2 = (1*(p./100) + 1/n2*(1-(p./100))) * 100;
pa3 = (1*(p./100) + 1/n3*(1-(p./100))) * 100;

figure(1)
plot(p,pa1,  p,pa2,'--',  p,pa3,':')
title('Probability of right answer (%)')
xlabel('p(%)')
legend({'n = 3','n = 4', 'n = 5'},'Location','northwest')
axis([0 100 0 100])
grid on


%% d) Desenhar um gráfico com a probabilidade do estudante saber a resposta sabendo que selecionou a alínea correta

n1 = 3;
n2 = 4;
n3 = 5;
p = linspace(0,100,100);

psa1 = (1* (p./100)./ (1*(p./100) + 1/n1*(1-(p./100))))*100;
psa2 = (1* (p./100)./ (1*(p./100) + 1/n2*(1-(p./100))))*100;
psa3 = (1* (p./100)./ (1*(p./100) + 1/n3*(1-(p./100))))*100;

figure(2)
plot(p,psa1, p,psa2,'--', p,psa3,':')
title('Probability of knowing the answer (%)')
xlabel('p(%)')
legend({'n = 3','n = 4', 'n = 5'},'Location','northwest')
axis([0 100 0 100])
grid on



