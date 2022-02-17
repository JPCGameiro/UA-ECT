clear all;
close all;

Nodes= [30 70
       350 40
       550 180
       310 130
       100 170
       540 290
       120 240
       400 310
       220 370
       550 380];
   
Links= [1 2
        1 5
        2 3
        2 4
        3 4
        3 6
        3 8
        4 5
        4 8
        5 7
        6 8
        6 10
        7 8
        7 9
        8 9
        9 10];

T= [1  3  1.0 1.0
    1  4  0.7 0.5
    2  7  2.4 1.5
    3  4  2.4 2.1
    4  9  1.0 2.2
    5  6  1.2 1.5
    5  8  2.1 2.5
    5  9  1.6 1.9
    6 10  1.4 1.6];

nNodes= 10;
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
[sP, nSP]= calculatePaths(L,T,n);

%% 2.a) % random strat

limits = [inf, 10, 5];

fprintf('RANDOM:\n');
figure('Name','Ex. 2.a)','NumberTitle','off');
for limit = limits
    t= tic;
    bestEnergy= inf;
    sol= zeros(1,nFlows);
    allValues= [];
    while toc(t)<10
        for i= 1:nFlows
            n = min(limit, nSP(i));
            sol(i)= randi(n);
        end
        Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
        load= max(max(Loads(:,3:4)));
        if load<=10
            energy = 0;
            for a=1:nLinks
                if Loads(a,3)+Loads(a,4)>0 % link tem que estar a ser usado e por isso uma carga > 0
                    % pelo enunciado consumo de energia é igual ao comprimento
                    % dos links
                    energy = energy + L(Loads(a,1),Loads(a,2)); %a energia e igual a energia + o comprimento do link (L -> comprimento do link)
                end
            end
        else
            energy=inf; %garante que nao escolhe se a capacidade/carga maior que 10 gigabits
        end
        allValues= [allValues energy];
        if energy<bestEnergy
            bestSol= sol;
            bestEnergy= energy;
        end
    end
    plot(sort(allValues));
    hold on;
    fprintf('%d best paths\n', limit);
    fprintf('   Best energy = %.1f\n', bestEnergy);
    fprintf('   No. of solutions = %d\n', length(allValues));
    aux = allValues(allValues ~= inf);
    fprintf('   Av. quality of solutions = %.1f\n' ,mean(aux));
end
hold off;
title('Minimun energy consumption of network (Random)');
legend({'all paths','10 shortest paths','5 shortest paths'}, 'Location', 'southeast');
ylabel('Energy (J)');



%% 2.b) % greedy randomized strat

limits = [inf, 10, 5];

fprintf('GREEDY RANDOMIZED:\n');
figure('Name','Ex. 2.b)','NumberTitle','off');
for limit = limits
    t= tic;
    bestEnergy= inf;
    sol= zeros(1,nFlows);
    allValues= [];
    while toc(t)<10
        ax2 = randperm(nFlows); % array numa ordem aleatória
        sol= zeros(1,nFlows);
        for i= ax2
            k_best = 0;
            best = inf;
            n = min(limit, nSP(i));
            for k = 1:n
                sol(i)= k;
                Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
                load= max(max(Loads(:,3:4)));
                if load<=10
                    energy = 0;
                    for a=1:nLinks
                        if Loads(a,3)+Loads(a,4)>0
                            energy = energy + L(Loads(a,1),Loads(a,2)); %a energia e igual a energia + o comprimento do link (L -> comprimento do link)
                        end
                    end
                else
                    energy=inf;
                end
                if energy < best
                    k_best = k;
                    best = energy;
                end
            end
            sol(i) = k_best; % percurso com a melhor escolha
        end
        Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
        load= max(max(Loads(:,3:4)));
        if load<=10
            energy = 0;
            for a=1:nLinks
                if Loads(a,3)+Loads(a,4)>0 %esta a suportar trafego nao pode dormir
                    energy = energy + L(Loads(a,1),Loads(a,2)); %a energia e igual a energia + o comprimento do link (L -> comprimento do link)
                end
            end
        else
            energy=inf; %garante que nao escolhe se a capacidade/carga for maior que 10 gigabits
        end
        allValues= [allValues energy];
        if energy <bestEnergy
            bestSol= sol;
            bestEnergy= energy;
        end
    end
    plot(sort(allValues));
    hold on;
    fprintf('%d best paths\n', limit);
    fprintf('   Best energy = %.1f\n', bestEnergy);
    fprintf('   No. of solutions = %d\n', length(allValues));
    aux = allValues(allValues ~= inf);
    fprintf('   Av. quality of solutions = %.1f\n' ,mean(aux));
end
hold off;
title('Minimun energy consumption of network (Greddy Randomized)');
legend({'all paths','10 shortest paths','5 shortest paths'}, 'Location', 'southeast');
ylabel('Energy (J)');

%% 2.c) % hill climb multi start

fprintf('MULTI START HILL CLIMBING:\n');
figure('Name','Ex. 2.c)','NumberTitle','off');

limits = [inf, 10, 5];
for limit = limits
    %Build a multi start hill climbing solution
    globalBestEnergy= inf;
    allValues= []; 
    t=tic;
    while toc(t) < 10
        %Greedy Randomized Solution
        [bestSol,bestEnergy] = greedyRandomizedEnergy(nFlows,nSP, nNodes, Links, T, L, sP, limit);
        repeat = true;
        while repeat
            %Iterate through all values of the solution (to calculate best neighbor) 
            neighborBest= inf;
            for i=1:nFlows
                [nS, nE]= BuildNeighborEnergy(bestSol,i, sP, nSP, Links, nNodes, T, L, bestEnergy);
                if nE < neighborBest
                    neighborBest= nE;
                    neighborSol= nS;
                end
            end
            if neighborBest < bestEnergy
                bestSol= neighborSol;
                bestEnergy= neighborBest;
            else
                repeat= false;
            end
        end
        allValues= [allValues bestEnergy];
        if bestEnergy < globalBestEnergy
            globalBestEnergy= bestEnergy;
            globalSol= bestSol;
        end
    end
    plot(sort(allValues));
    hold on;
    fprintf('%d best paths\n', limit);
    fprintf('   Best energy = %.1f\n', globalBestEnergy);
    fprintf('   No. of solutions = %d\n', length(allValues));
    aux = allValues(allValues ~= inf);
    fprintf('   Av. quality of solutions = %.1f\n' ,mean(aux));
end
hold off;
title('Minimun energy consumption of network (Multi Start Hill Climbing)');
legend({'all paths','10 shortest paths','5 shortest paths'}, 'Location', 'southeast');
ylabel('Energy (J)');
