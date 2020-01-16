T = [0.8 0.1 0.05;
     0.2 0.6 0.2;
     0.0 0.3 0.75];
%a)
x0 = [100; 200; 30];

x3 = T^3*x0;
fprintf("\n4 de Janeiro de 2016: \n")
fprintf("Ana: "+x3(1))
fprintf("\nBernardo: "+x3(2))
fprintf("\nCatarina: "+x3(3)+"\n")

%b)
x365 = T^365*x0;
fprintf("\n1 de Janeiro de 2016 : \n")
fprintf("Ana: "+x365(1))
fprintf("\nBernardo: "+x365(2))
fprintf("\nCatarina: "+x365(3)+"\n")

%c)
check = 0;
i=2;
dia = 1;
while check==0
    xaux = T^i*x0;
    if(xaux(3)>=130)
        check=1;
    else
        check=0;
    end
    i = i+1;
    dia = dia+1;
end

fprintf("\nDia: "+dia+"-01-2015\n")  %como o resultado é dia 9, no formato d/m/a é 9-1-2015


