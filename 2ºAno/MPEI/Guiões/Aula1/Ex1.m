%% Lançar 3 vezes uma moeda obter 2 caras (analiticamente)
p = 0.5;
k = 2;
n = 3;
prob = factorial(n)/(factorial(k)*factorial(n-k))*(p^k)*((1-p)^(n-k))

%% Lançar 3 vezes uma moeda obter 2 caras (simulação v1)
experiencias = rand(3, 100000);
lancamentos = experiencias > 0.5;
resultados = sum(lancamentos);
sucessos = resultados == 2;
prob = sum(sucessos)/100000

%% Lançar 3 vezes uma moeda obter 2 caras (simulação v2)
N = 1e5;    %NºExperiências
p = 0.5;    %probabilidade de cara
k = 2;      %Nºde sucessos por lançamento (2 caras)
n = 3;      %Nºlançamentos
experiencia = rand(n, N) > p;
sucessos = sum(experiencia) == k;
prob = sum(sucessos)/N