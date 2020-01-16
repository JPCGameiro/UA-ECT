%% Gráfico
x = [];
for w = 1:0.0001:10
    x = [x sin(w)];
end 
plot(x);
w=0:pi/100:2*pi;
x1 = sin(w);
x2= sin(w+pi/2);
x3= x1.*x2;
plot(w,x1,w,x2,w,x3);
legend("sin","cos","asin*cos");
x = linspace(-pi, pi, 100)

%% Exercicio 25 - plot da função sen de dois períodos [0, 4pi]
%%x = [];
%%for p = 0:0.001:4pi
%    x = [x sin(p)];
%%end
%plot(x)
sin(4*pi) 