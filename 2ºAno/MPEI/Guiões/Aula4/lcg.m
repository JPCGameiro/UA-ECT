function U=lcg(X0, a, c, m, N)
    U = zeros(1, N);
    U(1) = X0;
    for i=2:N
        U(i) = rem(a*U(i-1)+c, m);
    end
end