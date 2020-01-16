%% a) Gerar 10 experiências de Bernoulli com probabilidade sucesso p
N = 10;
p = 0.5;
sucesso = rand(1, N) >= p

%% b) 15 Lançamentos de um dado honesto (S = {1,2,3,4,5,6})
N = 15;
lancamentos = randi([1 6], 1, N)

%% c) 20 números reais igualmente distribuídos entre -4 e 10
a = -4;
b = 10;
N = 20;
random = a+(b-a).*rand(1,N)