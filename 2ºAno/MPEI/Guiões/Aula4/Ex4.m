%% Testar a função Binomial
N=1e4; n=20; p=0.3;
test = Binomial(n, p, N);
histogram(test)
