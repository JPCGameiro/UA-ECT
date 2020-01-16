function [X,Y] = BoxMuller(N)
    Var1 = rand(1,N);
    Var2 = rand(1,N);
    X=(-2*log(Var1)).^(1/2).* cos(2*pi*Var2);
    Y=(-2*log(Var1)).^(1/2).* sin(2*pi*Var2);
end

