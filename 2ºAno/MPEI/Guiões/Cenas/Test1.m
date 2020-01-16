N = 1e5;
lancamentos = rand(1,3) > 0.5;
sucessos = sum(lancamentos == 2);
fabsol = cumsum(sucessos);
frel = fabsol ./(1:N);
plot(1:N, frel);