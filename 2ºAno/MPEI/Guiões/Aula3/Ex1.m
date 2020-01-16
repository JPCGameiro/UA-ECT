%% a)
x = 1:6;
Px = ones(1,6)/6;
subplot(2,1,1);
plot(x, Px);
title("tabela");
xlabel("x");
ylabel("y");
axis([0 6 0 0.2])

%% b)
x = 1:6;
pl = [0 x 7];
Px = cumsum([0 ones(1,6)/6 7);
stairs(x, Px);