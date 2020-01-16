%% a)
H = [0 0 1/2 0 1/2 1/5;
     1/4 1/3 1/2 1/2 1/2 1/5;
     1/4 0 0 0 0 1/5;
     1/4 1/3 0 0 0 1/5;
     1/4 0 0 1/2 0 1/5;
     0 1/3 0 0 0 0];

r0 = [1/6; 1/6; 1/6; 1/6; 1/6; 1/6];

r3 = H^3*r0;

fprintf("PageRank de A após 3 iterações é "+r3(1)+"\n");
fprintf("PageRank de B após 3 iterações é "+r3(2)+"\n");
fprintf("PageRank de C após 3 iterações é "+r3(3)+"\n");
fprintf("PageRank de D após 3 iterações é "+r3(4)+"\n");
fprintf("PageRank de E após 3 iterações é "+r3(5)+"\n");
fprintf("PageRank de F após 3 iterações é "+r3(6)+"\n");

%% b) e c)
H = [0 0 1/2 0 1/2 1/5;
     1/4 1/3 1/2 1/2 1/2 1/5;
     1/4 0 0 0 0 1/5;
     1/4 1/3 0 0 0 1/5;
     1/4 0 0 1/2 0 1/5;
     0 1/3 0 0 0 0];

r0 = [1/6; 1/6; 1/6; 1/6; 1/6; 1/6];

r_atual = r0;
N=100;
Resultado = zeros(length(H), N);
for k = 1:100
    Resultado(:,k) = r_atual;
    r_atual = H^k*r0;
end

plot(1:N, Resultado)
row = find(r_atual == max(r_atual));
fprintf("\nO valor máximo de pageRank obtido é "+max(r_atual)+" na página Nº "+row+"\n");