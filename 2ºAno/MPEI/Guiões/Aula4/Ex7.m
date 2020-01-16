%% Testar a função BoxMuller
for i=1:3
    subplot(3,3,i)
    [X,Y]=(BoxMuller(30));
    hist(sqrt(2)*X+14)
    title('Gráfico Box-Muller')
    subplot(3,3,i+3)
    hist(sqrt(2)*randn(1,30)+14)
    title('Gráfico RandN')
end



