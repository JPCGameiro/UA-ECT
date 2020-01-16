function X = Binomial(n, p, N)
    Bern = rand(n, N)<=p;
    X = sum(Bern);
end

