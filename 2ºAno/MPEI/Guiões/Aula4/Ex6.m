%% Teste da função exponencial
N = 1e6;
X = Exponencial(10, N);
[n, xout] = hist(X,100);
bar(xout,n/N)