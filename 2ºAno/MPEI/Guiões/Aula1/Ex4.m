%% Código 1
N = 1e5; 
p = 0.5;
k = 2;
n = 3;
probSim = probKCarasNLancamentos(N, k, n);
stem(n, probSim)

function y=probKCarasNLancamentos(N, k, n)
    lancamentos = rand(n, N) > 0.5;
    sucessos = sum(lancamentos) == k;
    y = sum(sucessos)/N;
end