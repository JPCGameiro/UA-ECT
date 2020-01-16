%% expressões e números
2*sin(2);
ans = ans/102;
a = 1 + 12j;
acos(1);
abs(-1);
real(2 + 10j);
a = [1 2 4 10 4];
[b, index] = max(a);
b = i;
c = j

%% polinómios
p = [1 -3 2];    %%polinómio x^2-3x+2
r = roots(p);
coef = poly(r);
polyval(p,r);
p1= [1 0 1];
p2= [1 0 1 -1];
conv(p1,p2)

%% Exercico 7 - sen  cos  tg  raiz quadradada  e  raiz cúbica de pi/2
sin(pi/2);
cos(pi/2);
tan(pi/2);
sqrt(pi/2);
nthroot(pi/2, 3);

%% Exercicio 8 - logaritmo e a raíz quadrada de -1
log(-1);
sqrt(-1);

%% Exercicio 9 -  e^x em 100 pontos do intervalo [?1 . . . 1]
for I = -1:0.01:1
    exp(I)
end

%% Exercicio 10 - função sin(x+pi/10) ? cos(x) em 100 pontos do intevalo [?? . . . ?]
for i = -1:0.01:1
    sin(i+pi/10)*cos(i)
end

%% Exercicio 11 - produto dos polinómios x^6 + 10 e x^2 ? 2x + 3
p1 = [1 0 0 0 0 0 10];
p2 = [1 -2 3];
conv(p1,p2)

%% Exercicio 12 - polinómio cujas raízes são os números inteiros 1, 2 e 3
r = [1 2 3];
poly(r)

%% Exercico 13 - zeros de p(x) = x3 + 4x2 ? 3x + 1
p = [1 4 -3 1];
roots(r);