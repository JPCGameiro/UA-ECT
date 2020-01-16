%% Versão 1
N = 1000;
n = round(N/0.8);
matriz = zeros(1,n);
for i=1:N
    a = GeraChaves(3, 20)
    h = mod(string2hash(a),n);
    matriz(h) = matriz(h)+1;
    subplot(1,2,1)
    bar(matriz)
    drawnow
    subplot(1,2,2)
    hist(matriz)
    drawnow
end;

%% Versão 2
alpha = ['A':'Z' 'a':'z'];
%  ficheiros a serem processados (do projecto Gutemberg
ficheiros={'pg21209.txt','pg26017.txt'};

% obter função massa de probabilidade (pmf em Inglês)
pmfPT=pmfLetrasPT(ficheiros,alpha);

N=1000;
table = zeros(1, N);
  
for i=1:N
  chave = GeraChavesV2(10, 5, alpha, pmfPTAcumulada);
  a = mod(string2hash(chave), N)+1;
  table(1, a) =table(1, a)+ 1;
  subplot(1,2,1)
  hist(table)
  drawnow
  subplot(1,2,2)
  bar(table)
  drawnow
end


