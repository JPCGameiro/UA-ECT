p=0.85 ;
% prob aluno passar em 2015 2016
n=70*2;
% alunos de MPEI
N=1e6;
% experiências
k=fix(0.8 * n);
% os 80
aprovados = rand(n,N) < p; %% 1 indica aprovado
numOcorrencias =0;
for k1=k:n
sucessos= sum(aprovados)==k1 ;
fprintf('%d aprovados--> %8d , p=%.5f n',k1,sum(sucessos),sum(sucessos)/N);
numOcorrencias = numOcorrencias +sum(sucessos);
end
probSimulacao= numOcorrencias /N;
fprintf('prob de %d ou mais em %d passsarem é de %.4fn',k,n,probSimulacao);