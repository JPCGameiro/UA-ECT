C = 10e6;

pps64 = C / (64*8);
pps110 = C / (110*8);
pps1518 = C / (1518*8);

time = [1 / pps64, 1 / pps110, 1 / pps1518];

% ed -> exprimental delay
expdelay = [3.9676, 4.0095, 5.1133];

y = expdelay ./ time;

figure(1);
plot([1, 2, 3], y, LineStyle="-");

