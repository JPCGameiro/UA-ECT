%% a) Gerar um vector de comprimento 1000
vector = lcg(3,3,3,5,1000);           %geração dos números aleatórios
diferentes = length(unique(vector));  %Nº de números diferentes 
histogram(vector);
title('Histograma')
xlabel('Números Gerados')
ylabel('Quantidade de números Gerados')

%% b) Gerar números aleatório entre 0 e 1
m = 2;
a = 1;
X0 = 0.5;
vector = lcg(X0, a, a, m, 1000)/m;
diferent = length(unique(vector));
histogram(vector);
title('Histograma');
xlabel('Números Gerados');
ylabel('Quantidade de Números Gerados');


%% c)parâmetros utilizados na implementação incluída na biblioteca NAG
vector = lcg(3, 7^5, 2,(2^31)-1, 1000);
diferente = unique(vector);
histogram(vector);
title('Histograma');
xlabel('Números Gerados');
ylabel('Quantidade de Números Gerados');
