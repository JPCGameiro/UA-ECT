%% Analiticamente - 6 caras em 15 lançamentos
n = 15;      %nºlançamentos
k = 6;       %nº de caras
p = 0.5;     %probabilidade de cara
probSim = (factorial(n)/(factorial(k)*factorial(n-k))) * p^k * p^(n-k)

%% Simulação v1
experiencias = rand(15, 10000);
lancamentos = experiencias > 0.5;
resultados = sum(lancamentos);
sucessos = resultados==6;
probSim = sum(sucessos)/10000

%% Simulação v2
N = 1e5;    %nº experiências
p = 0.5;    %probabilidade de obter cara
k = 6;      %Número de sucessos
n = 15;     %Número de lançamentos
lancamentos = rand(n, N) > p;
sucessos = sum(lancamentos) == k;
probSim = sum(sucessos)/N
