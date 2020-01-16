%% Matrizes
zeros(2,5);
ones(2, 5);
eye(3, 3);
rand(2,2);
randn(3,3);
a = [1 2];
b = [3 4];
A = [a b a;b a b;a b a];
A(:,6) = []
%% Exercio 15 - matriz 3x3 com tudo == 3
ones(3,3)*3

%% Exercicio 17 - matriz 4x4 com tudo = 1 + j^2
ones(4,4)*(1+(j*j))

%% Exercicio 18 - com a função eye criar uma matriz identidade em que os elementos da diagonal são todos 3
eye(4)*3

%% Exercicio 19 - criar um vector [0 1 0 -1 0 1 0 -1 ....]
m1 = [0 1];
mm1 = [0 -1];
matrizfinal = [0 1];
for var = 1:1:10
    matrizfinal = [matrizfinal mm1 m1 mm1]
end 

%% Exercicio 20 - Criar uma matriz 4x4 em que cada coluna é [1 2 3 4]
matriz = []
for i = 1:1:4
    matriz = [matriz; i i i i]
end

%% Exercio 21 - Matriz 6x6 em que cada quadrante é uma matriz 3x3 com 1s e 0s
matriz = [ones(3) zeros(3); zeros(3) ones(3)]