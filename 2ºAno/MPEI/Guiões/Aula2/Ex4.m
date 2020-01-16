%% a) lançar 20 dados um de cada vez para 100 alvos
n = 20;
m = 100;
N = 1e5;
experiencia = randi([1 m], n, N);
for i=1:N
    matriz(i) = length(unique(experiencia(:, i))) == n;
end
sucessos = sum(matriz);
prob = sucessos/1e5
%% b) pelo menos 1 alvo ter sido atingido duas ou mais vezes
lancamentos = randi(100, 20, 1e5);
for i = 1:1e5
    r(i) = length(unique(lancamentos(:,i))) < 20;
end 
sucessos = sum(r);
prob1 = sucessos/1e5

%% c) gráficos da variação em função de n o valor da probabilidade da alínea anterior
X = [];
Y = [];
for n = 100:50:1000
    X = [X n];
    Y = [Y lan(n)];
end
plot(X,Y)
xlabel('NºAlvos')
ylabel('Probabilidade')

function y = lan(N)
    lancamentos = randi(N, 20, 1e5);
    for i = 1:1e5
        r(i) = length(unique(lancamentos(:,i))) < 20;
    end 
    sucessos = sum(r);
    prob = sucessos/1e5;
    y = prob;
end
