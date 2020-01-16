%% a)
T = [0.7 0.8;
     0.3 0.2];   %matriz

x0 = [1
      0];    %Vetor inical (esteve na primeira aula)
 
x2 = T^2*x0;
prob = x2(1)    %Probabilidade de estar presente na próxima aula

%% b)
T = [0.7 0.8;
     0.3 0.2];   %matriz
 
x0 = [0
      1];     %Faltou à primeira aula

x2 = T^2*x0;
prob = x2(1)    %Probabilidade de estar presente na próxima aula

%% c)
T = [0.7 0.8;
     0.3 0.2];     %Matriz transição

x0 = [1
      0];           %Esteve na primeira aula

x15 = T^15*x0;
prob = x15(1)        %Probabilidade de estar na última aula

%% d)
T = [0.7 0.8;           %Matriz Transição
     0.3 0.2];

x0 = [0.85; 
      0.15];            %Probabilidades da primeira aula

res = zeros(1,30);
res(1) = 0.15;

for i=2:30
  m = (T^(i+1))*x0;
  res(i) = m(2);
  plot(res)
  drawnow
end
