%% Probabilidade(ter cancro sabendo que mamograma deu positivo)
pmamogramacancro = 0.9;
pmamogramancancro = 0.1;
pcancro = 1/10000;
pncancro = 1-pcancro;
p1 = pmamogramacancro*pcancro;
p2 = p1+pmamogramancancro*pncancro;
prob = p1/p2
