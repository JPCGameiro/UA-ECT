%% a) Matriz de Transição
H = [0.8 0  0.3 0;
     0.2 0.9 0.2 0;
     0 0.1 0.4 0;
     0 0 0.1 1];
% b) 
prob =  [1 0 0 0] * H^1000;
prob(2);

% c)
steps = [1 2 10 100];

for i = 1:4
    x0 = zeros(1,4);
    x0(i) = 1;
    for n = steps
        p0 = x0*(H^n);
        fprintf("\n"+n+" Passos\n")
        fprintf("Partindo da "+i+"as probabilidades são -> "+p0(1)+" ,"+p0(2)+" ,"+p0(3)+" ,"+p0(4))
    end 
end 
%d)
Q = H(1:3,1:3)

%e)
F=inv(eye(3)-Q);

%f)
media = sum(F)
fprintf("media do número de passos necessários para atingir a página 4 é:")
fprintf("\nComeçando na página1 -> "+media(1))
fprintf("\nComeçando na página2 -> "+media(2))
fprintf("\nComeçando na página3 -> "+media(3)+"\n")

%g)
tempo = sum(sum(F))

%h)
H = [0.8 0 0.35 0;
    0.2 0.9 0.2 0;
    0 0.1 0.4 0;
    0 0 0.05 1];
Q = H(1:3, 1:3)
F = inv(eye(size(Q)) - Q)
tempo = sum(sum(F))
