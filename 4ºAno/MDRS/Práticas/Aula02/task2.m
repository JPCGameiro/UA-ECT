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

%A & C) Single Routing Path multi-start hill climbing heuristic
fprintf('MULTI START HILL CLIMBING (LOAD):\n');
figure(1);


limits = [inf, 6];
for limit = limits
    %Build a multi start hill climbing solution
    globalBestLoad= inf;
    allValues= []; 
    t=tic;
    while toc(t) < 10
        %Greedy Randomized Solution
        [bestSol,bestLoad] = greedyRandomizedLoads(nFlows,nSP, nNodes, Links, T, sP, limit);
        repeat = true;
        while repeat
            %Iterate through all values of the solution (to calculate best neighbor) 
            neighborBest= inf;
            for i=1:nFlows
                [nS, nL]= BuildNeighbor(bestSol,i, sP, nSP, Links, nNodes, T, bestLoad);
                if nL < neighborBest
                    neighborBest= nL;
                    neighborSol= nS;
                end
            end
            if neighborBest < bestLoad
                bestSol= neighborSol;
                bestLoad= neighborBest;
            else
                repeat= false;
            end
        end
        allValues= [allValues bestLoad];
        if bestLoad < globalBestLoad
            globalBestLoad= bestLoad;
            globalSol= bestSol;
        end
    end
    plot(sort(allValues));
    hold on;
    fprintf('%d best paths\n', limit);
    fprintf('   Best load = %.2f Gbps\n', globalBestLoad);
    fprintf('   No. of solutions = %d\n',length(allValues));
    fprintf('   Averg. quality of solutions = %.2f Gbps\n',mean(allValues));
end
hold off;
title('Minimun worst link load (Multi Start Hill Climbing)');
legend({'all paths','6 shortest paths'}, 'Location', 'southeast');
ylabel('Minimun worst link load (Gbps)');




fprintf('MULTI START HILL CLIMBING (ENERGY):\n');
figure(2);

limits = [inf,6];
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
legend({'all paths','6 shortest paths'}, 'Location', 'southeast');
ylabel('Energy (J)');








