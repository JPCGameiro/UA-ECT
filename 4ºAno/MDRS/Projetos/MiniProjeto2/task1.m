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

% 1.a)
% Compute up to 100 paths for each flow:
n= inf;
[sP, nSP]= calculatePaths(L,T,n);

fprintf('Number of diferent paths for each flow: ');
nSP

%% 1.b) % random strat

limits = [inf, 10, 5];

fprintf('RANDOM:\n');
figure('Name','Ex. 1.b)','NumberTitle','off');
for limit = limits
    t= tic;
    bestLoad= inf;
    sol= zeros(1,nFlows);
    allValues= [];
    while toc(t)<10
        for i= 1:nFlows
            % choose n shortest path for each flow
            % the choise is made between limit and all k-shortest paths
            n = min(limit,nSP(i));
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
    plot(sort(allValues));
    hold on;
    fprintf('%d best paths\n', limit);
    fprintf('   Best load = %.2f Gbps\n', bestLoad);
    fprintf('   No. of solutions = %d\n',length(allValues));
    fprintf('   Averg. quality of solutions = %.2f Gbps\n',mean(allValues));
end
hold off;
title('Minimun worst link load (Random)');
legend({'all paths','10 shortest paths','5 shortest paths'}, 'Location', 'northwest');
ylabel('Minimun worst link load (Gbps)');


%% 1.c) % greedy randomized strat

limits = [inf, 10, 5];

fprintf('GREEDY RANDOMIZED:\n');
figure('Name','Ex. 1.c)','NumberTitle','off');
for limit = limits
    t= tic;
    bestLoad= inf;
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
                if load < best
                    k_best = k;
                    best = load;
                end
            end
            sol(i) = k_best;
        end
        load= best;
        allValues= [allValues load];
        if load<bestLoad
            bestSol= sol;
            bestLoad= load;
        end
    end
    plot(sort(allValues));
    hold on;
    fprintf('%d best paths\n', limit);
    fprintf('   Best load = %.2f Gbps\n', bestLoad);
    fprintf('   No. of solutions = %d\n',length(allValues));
    fprintf('   Averg. quality of solutions = %.2f Gbps\n',mean(allValues));
end
hold off;
title('Minimun worst link load (Greddy Randomized)');
legend({'all paths','10 shortest paths','5 shortest paths'}, 'Location', 'southeast');
ylabel('Minimun worst link load (Gbps)');


%% 1.d) % hill climbing multi start

fprintf('MULTI START HILL CLIMBING:\n');
figure('Name','Ex. 1.d)','NumberTitle','off');

limits = [inf, 10, 5];
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
legend({'all paths','10 shortest paths','5 shortest paths'}, 'Location', 'southeast');
ylabel('Minimun worst link load (Gbps)');


