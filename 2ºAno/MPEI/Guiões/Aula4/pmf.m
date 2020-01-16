function [X] = pmf(xi,pX,n)
    soma=cumsum(pX);
    xadi = [];
    for j=1:n
        index=1+sum(rand(1,1)>soma);
        xadi=[xadi xi(index)];
    end
    X = xadi;
end
