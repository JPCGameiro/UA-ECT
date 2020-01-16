%% Código 1 - pelo menos 6 caras em 15 lançamentos
N = 1e5; 
p = 0.5;
k = 6;
n = 15;
lancamentos = rand(n, N) > p;
sucessos = sum(lancamentos) >= k;
probSim = sum(sucessos)/N

