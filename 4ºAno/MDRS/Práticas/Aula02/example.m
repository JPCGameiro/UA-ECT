clear all;
close all;

Nodes= [20 60
       250 30
       550 150
       310 100
       100 130
       580 230
       120 190
       400 220
       220 280];
   
Links= [1 2
        1 5
        2 4
        3 4
        3 6
        3 8
        4 5
        4 8
        5 7
        6 8
        7 8
        7 9
        8 9];

T= [1  3  1.0 1.0
    1  4  0.7 0.5
    2  7  3.4 2.5
    3  4  2.4 2.1
    4  9  2.0 1.4
    5  6  1.2 1.5
    5  8  2.1 2.7
    5  9  2.6 1.9];

nNodes= 9;
nLinks= size(Links,1);
nFlows= size(T,1);

B= 625;  %Average packet size in Bytes

co= Nodes(:,1)+j*Nodes(:,2);

L= inf(nNodes);    %Square matrix with arc lengths (in Km)
for i=1:nNodes
    L(i,i)= 0;
end
C= zeros(nNodes);  %Square matrix with arc capacities (in Gbps)
for i=1:nLinks
    C(Links(i,1),Links(i,2))= 10;  %Gbps
    C(Links(i,2),Links(i,1))= 10;  %Gbps
    d= abs(co(Links(i,1))-co(Links(i,2)));
    L(Links(i,1),Links(i,2))= d+5; %Km
    L(Links(i,2),Links(i,1))= d+5; %Km
end
L= round(L);  %Km
% Compute up to 100 paths for each flow:
n= 100;
[sP nSP]= calculatePaths(L,T,n);


%Compute the link loads using the first (shortest) path of each flow:
sol= ones(1,nFlows);
Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
maxLoad= max(max(Loads(:,3:4)))
%Optimization algorithm resorting to the random strategy:
t= tic;
bestLoad= inf;
sol= zeros(1,nFlows);
allValues= [];
while toc(t)<10
    for i= 1:nFlows
        sol(i)= randi(nSP(i));
    end
    Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
    load= max(max(Loads(:,3:4)));
    allValues= [allValues load];
    if load<bestLoad
        bestSol= sol;
        bestLoad= load;
    end
end

figure(1)
plot(sort(allValues));
bestSol
bestLoad

%A) Optimization algorithm resorting to the greedy randomized strategy:
t= tic;
bestLoad= inf;
sol= zeros(1,nFlows);
allValues= [];
while toc(t)<10
    ax2= randperm(nFlows); %Choose randomized order of the flows
    sol= zeros(1,nFlows);
    for i= ax2
        k_best= 0;
        best= inf;
        for k= 1:nSP(i)
            sol(i)= k;
            Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
            load= max(max(Loads(:,3:4)));
            if load<best
                k_best= k;
                best= load;
            end
        end
        sol(i)= k_best;
    end
    Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
    load= max(max(Loads(:,3:4)));
    allValues= [allValues load];
    if load<bestLoad
        bestSol= sol;
        bestLoad= load;
    end
end

hold on;
plot(sort(allValues))
bestSol
bestLoad

%B) Greedy Randomized é muito mais exigente computacionalmente mas
% apresenta resultados bastante melhores que a estratégia Random 
% No entanto para este problema temos uma rede muito pequena
% logo a estratégia random é melhor. Em problemas com redes maiores
% a estratégia greedy vai oferecer melhores resultados


%C) Random but with 6 shortest paths for each flow
t= tic;
bestLoad= inf;
sol= zeros(1,nFlows);
allValues= [];
while toc(t)<10
    for i= 1:nFlows
        n = min(6, nSP(i));
        sol(i)= randi(n);
    end
    Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
    load= max(max(Loads(:,3:4)));
    allValues= [allValues load];
    if load<bestLoad
        bestSol= sol;
        bestLoad= load;
    end
end
hold on;
plot(sort(allValues))
bestSol
bestLoad

%C) Greedy Randomized but with 6 shortest paths for each flow
t= tic;
bestLoad= inf;
sol= zeros(1,nFlows);
allValues= [];
while toc(t)<10
    ax2= randperm(nFlows); %Choose randomized order of the flows
    sol= zeros(1,nFlows);
    for i= ax2
        k_best= 0;
        best= inf;
        n = min(6, nSP(i));
        for k= 1:n
            sol(i)= k;
            Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
            load= max(max(Loads(:,3:4)));
            if load<best
                k_best= k;
                best= load;
            end
        end
        sol(i)= k_best;
    end
    Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
    load= max(max(Loads(:,3:4)));
    allValues= [allValues load];
    if load<bestLoad
        bestSol= sol;
        bestLoad= load;
    end
end

hold on;
plot(sort(allValues))
title("Greedy Randomized and Random Strategy - Link Load ")
legend({'Random','Greedy Randomized', 'Random with 6 SPs', 'Greedy Randomized with 6 SPs'},'Location','northwest')
bestSol
bestLoad

%C) Em termos de melhorias ao nível da complexidade computacional, estas
% são praticamente nulas. No entanto os valores gerados por ambas as
% estratégias são melhores pelo facto de que as melhores soluções se
% encontram nos primeiros short paths gerados. As soluções que são
% extraídas não são tão boas como as obtidas logo não têm impacto no
% resultado final. O objetivo é gerar poucas soluções com bons resultados
% ao invés de gerar muitas soluções com bons e maus resultados.




%D) Optimization algorithm resorting to the random strategy but for energy consumption:
t= tic;
bestEnergy= inf;
sol= zeros(1,nFlows);
allValues= [];
while toc(t)<10
    for i= 1:nFlows
        sol(i)= randi(nSP(i));
    end
    Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
    load= max(max(Loads(:,3:4)));
    if load <= 10 %Load cannot be bigger thant 10 Gbps because link cannot support it
        energy=0;
        for a=1:nLinks
            if (Loads(a,3) + Loads(a, 4)) > 0
                energy = energy + L(Loads(a,1),Loads(a,2));
            end
        end
    else
        energy= inf;
    end
    allValues= [allValues energy];
    if energy<bestEnergy
        bestSol= sol;
        bestEnergy= energy;
    end
end

figure(2)
plot(sort(allValues));
bestSol
bestEnergy


%D) Optimization Greedy randomized strategy but for energy consumption:
t= tic;
bestEnergy= inf;
sol= zeros(1,nFlows);
allValues= [];
while toc(t)<10
    ax2= randperm(nFlows); %Choose randomized order of the flows
    sol= zeros(1,nFlows);
    for i= ax2
        k_best= 0;
        bestEnergy= inf;
        for k= 1:nSP(i)
            sol(i)= k;
            Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
            load= max(max(Loads(:,3:4)));
            
            if load <= 10 %Load cannot be bigger thant 10 Gbps because link cannot support it
                energy=0;
                for a=1:nLinks
                    if (Loads(a,3) + Loads(a, 4)) > 0
                        energy = energy + L(Loads(a,1),Loads(a,2));
                    end
                end
            else
                energy= inf;
            end

            if energy<bestEnergy
                k_best= k;
                bestEnergy= energy;
            end
        end
        sol(i)= k_best;
    end
    Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
    load= max(max(Loads(:,3:4)));
    if load <= 10 %Load cannot be bigger thant 10 Gbps because link cannot support it
        energy=0;
        for a=1:nLinks
            if (Loads(a,3) + Loads(a, 4)) > 0
                energy = energy + L(Loads(a,1),Loads(a,2));
            end
        end
    else
        energy= inf;
    end
    allValues= [allValues energy];
    if energy<bestEnergy
        bestSol= sol;
        bestEnergy= load;
    end
end

hold on;
plot(sort(allValues));
bestSol
bestEnergy

%E)Novamente verifica-se que a solução greedy apresenta valores muito
% melhores em relação à random. No entanto é computacionalmente mais pesada
% Neste caso a diferença entre as duas é mais acentuada que nos casos
% anteriores.






%F) Optimization random strategy but for energy consumption (6 SPs):
t= tic;
bestEnergy= inf;
sol= zeros(1,nFlows);
allValues= [];
while toc(t)<10
    for i= 1:nFlows
        n = min(6, nSP(i));
        sol(i)= randi(n);
    end
    Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
    load= max(max(Loads(:,3:4)));
    if load <= 10 %Load cannot be bigger thant 10 Gbps because link cannot support it
        energy=0;
        for a=1:nLinks
            if (Loads(a,3) + Loads(a, 4)) > 0
                energy = energy + L(Loads(a,1),Loads(a,2));
            end
        end
    else
        energy= inf;
    end
    allValues= [allValues energy];
    if energy<bestEnergy
        bestSol= sol;
        bestEnergy= energy;
    end
end
hold on;
plot(sort(allValues));
bestSol
bestEnergy


%F) Optimization Greedy randomized strategy but for energy consumption:
t= tic;
bestEnergy= inf;
sol= zeros(1,nFlows);
allValues= [];
while toc(t)<10
    ax2= randperm(nFlows); %Choose randomized order of the flows
    sol= zeros(1,nFlows);
    for i= ax2
        k_best= 0;
        bestEnergy= inf;
        n = min(6,nSP(i));
        for k= 1:n
            sol(i)= k;
            Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
            load= max(max(Loads(:,3:4)));
            
            if load <= 10 %Load cannot be bigger thant 10 Gbps because link cannot support it
                energy=0;
                for a=1:nLinks
                    if (Loads(a,3) + Loads(a, 4)) > 0
                        energy = energy + L(Loads(a,1),Loads(a,2));
                    end
                end
            else
                energy= inf;
            end

            if energy<bestEnergy
                k_best= k;
                bestEnergy= energy;
            end
        end
        sol(i)= k_best;
    end
    Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
    load= max(max(Loads(:,3:4)));
    if load <= 10 %Load cannot be bigger thant 10 Gbps because link cannot support it
        energy=0;
        for a=1:nLinks
            if (Loads(a,3) + Loads(a, 4)) > 0
                energy = energy + L(Loads(a,1),Loads(a,2));
            end
        end
    else
        energy= inf;
    end
    allValues= [allValues energy];
    if energy<bestEnergy
        bestSol= sol;
        bestEnergy= load;
    end
end

hold on;
plot(sort(allValues));
title("Greedy Randomized and Random Strategy - Link Energy ")
legend({'Random','Greedy Randomized', 'Random with 6 SPs', 'Greedy Randomized with 6 SPs'},'Location','northwest')
bestSol
bestEnergy

