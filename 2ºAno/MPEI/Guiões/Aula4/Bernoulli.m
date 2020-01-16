function [X] = Bernoulli(p,N)
    X = rand(1,N)<=p;
end

