M = [0.84 0.1 0 0.5;
     0.1  0.7 0 0.1;
     0.05 0.1 0.8 0.1;
     0.01 0.1 0.2 0.3];
v0 = [1; 2; 10; 5];          %valores das toneladas iniciais

v8 = M^8*v0;                 %Toneladas em agosto

fprintf("\nTonelagem em agosto\n");
fprintf("\nAngola -> "+v8(1)+" milhões de toneladas");
fprintf("\nBrasil -> "+v8(2)+" milhões de toneladas");
fprintf("\nChile -> "+v8(3)+" milhões de toneladas");
fprintf("\nDinamarca -> "+v8(4)+" milhões de toneladas\n");

Mangola = [v0(1)];
Mbrasil = [v0(2)];
Mchile  = [v0(3)];
Mdinamarca = [v0(4)];
for i=2:7
    vaux = M^i*v0;
    Mangola = [Mangola vaux(1)];
    Mbrasil = [Mbrasil vaux(2)];
    Mchile = [Mchile vaux(3)];
    Mdinamarca = [Mdinamarca vaux(4)];
end
Mangola = [Mangola v8(1)];
Mbrasil = [Mbrasil v8(2)];
Mchile = [Mchile v8(3)];
Mdinamarca = [Mdinamarca v8(4)];

fprintf("\nO valor máximo de tonelagem atinjido entre janeiro e agosto por cada país é:\n");
fprintf("\nAngola -> "+max(Mangola));
fprintf("\nBrasil -> "+max(Mbrasil));
fprintf("\nChile -> "+max(Mchile));
fprintf("\nDinamarca -> "+max(Mdinamarca)+"\n");


MinDinamarca = v0(4);
i = 1;
while (MinDinamarca >= 2)
    i = i+1;
    aux = M^i*v0;
    if (aux(4)<MinDinamarca)
        MinDinamarca = aux(4);
    end
end
%i = 11 ou seja é no mês de novembro
fprintf("\nA partir do dia 1 do mês de novembro -> "+i+ " a Dinamarca passa a ter menos de 2 milhões de toneladas\n");

