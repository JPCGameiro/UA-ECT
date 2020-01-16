%% Teste da função fmp
xi=[1 2 3 4 5 6];
pX=[0.1,0.1,0.1,0.1,0.1,0.5];
n=1e5;
hist(pmf(xi,pX,n),length(xi))