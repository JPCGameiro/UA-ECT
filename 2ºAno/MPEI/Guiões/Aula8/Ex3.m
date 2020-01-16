%% Exercicio 3
Matriz = zeros(20, 20);
var = 1;
for y=1:20
    var = 1;
    vetor = [1:20];
    for x=1:20
        pos = randi(length(vetor));
        card = vetor(pos);
        while card == 0
            pos = randi(length(vetor));
            card = vetor(pos);
        end
        vetor(pos) = 0;
        k = rand()*var;
        var = var - k;
        Matriz(card,y)= k;
    end 
end
Matriz;
M = sum(Matriz);
x0 = [1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0]';
x20 = Matriz^20*x0;
x2 = x20(20)
x40 = Matriz^40*x0;
x4 = x40(20)
x100 = Matriz^100*x0;
x1 = x100(20)
