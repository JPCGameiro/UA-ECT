%% a) 3 peças em 5 serem defeituosas
n = 5;      %nºpeças
k = 3;      %nº peças defeituosas
p = 0.3;    %probabilidade de ser defeituosa
N = 1e5;    %nºexperiências
%analiticamente
probAnalitica = factorial(n)/(factorial(k)*factorial(n-k))*(p^k)*((1-p)^(n-k))
%simulacao
experiencias = rand(n, N)<0.3;
sucessos = sum(experiencias) == 3;
probSimulacao = sum(sucessos)/N

%% b) pelo menos 2 defeituosas
n = 5;
k = 2;
p = 0.3;
N = 1e5;
experiencias = rand(n, N)>0.7;
sucessos = sum(experiencias)<=2;
prob = sum(sucessos)/N

%% c) histograma do número de peças defeituosas
n = 5;
k = 2;
p = 0.3;
N = 1e5;
experiencias = rand(n, N)>0.7;
sucessos = sum(experiencias)<=2;
prob = sum(sucessos)/N;
hist(sucessos)