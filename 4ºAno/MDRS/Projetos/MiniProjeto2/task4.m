%% task 4
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

co= Nodes(:,1)+1i*Nodes(:,2);

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

MTBF= (450*365*24)./L;
A= MTBF./(MTBF + 24);
% matrix A, matrix com a disonibilidade de cada ligação
A(isnan(A))= 0;

% links com valor mais perto de 0 são os mais curtos
logA = -log(A);

%% 4.a)
clc
k= 10;
[sP1, nSP1, sP2, nSP2] = bestKpaths(logA, T, k);

% 4.b)
allValues = [];

globalbestcell1 = 0;
globalbestcell2 = 0;

globalbestpairindex = [];
globalbestload = inf;
t=tic;
while toc(t) < 30
    ax2 = randperm(nFlows); % array numa ordem aleatória    
    bestcell1 = cell(nFlows, 1);
    bestcell2 = cell(nFlows, 1); 
    for xx= 1:nFlows
        bestcell1{xx} = {[]};
        bestcell2{xx} = {[]};
    end
    bestpairindex = []; 
    for i= ax2
        k_best = 0;
        best = inf;
        sp1K = sP1{i};
        sp2K = sP2{i};
        for j= 1:k
            path1 = {sp1K{j}};
            path2 = {sp2K{j}}; 
            cell1 = bestcell1;
            cell2 = bestcell2;
            cell1{i} = path1;
            cell2{i} = path2;
            Loads= calculateLinkLoads1to1(nNodes,Links,T,cell1, cell2);
            load= max(max(Loads(:,3:4)));
            if load < best
                k_best = j;
                best = load;
            end
        end
        bestcell1{i} = {sp1K{k_best}};
        bestcell2{i} = {sp2K{k_best}};
        bestpairindex = [bestpairindex k_best];
    end
    repeat = true;
    
    neighborBestLoad = inf;
    neighborBestcell1 = 0;
    neighborBestcell2 = 0;
    while repeat
        for i= 1:nFlows
            kk = 0;
            value = 0;
            for n=1:nFlows
                if n~= i
                    newNeighbor1= bestcell1;
                    newNeighbor2= bestcell2;
                    for j= 1:k
                        if j~= bestpairindex(n)
                            newNeighbor1{n} = sP1{n}(j);
                            newNeighbor2{n} = sP2{n}(j);
                            Loads= calculateLinkLoads1to1(nNodes, Links, T, newNeighbor1, newNeighbor2);
                            load= max(max(Loads(:,3:4)));
                            if load < neighborBestLoad
                                bestcell1 = newNeighbor1;
                                bestcell2 = newNeighbor2;
                                neighborBestLoad = load;
                                bestpairindex(n) = j;
                            end
                        end
                    end
                end
            end
        end
        if neighborBestLoad < best
            best = neighborBestLoad;
            neighborBestcell1 = bestcell1;
            neighborBestcell2 = bestcell2;
        else
            repeat = false;
        end
    end
    allValues = [allValues best];
    if best < globalbestload
        globalbestload = best;
        globalbestcell1 = neighborBestcell1;
        globalbestcell2 = neighborBestcell2;
        globalbestpairindex = bestpairindex;
    end
end
fprintf("Best solution found\n:");
avail = zeros(1, nFlows);      
avail2nd = zeros(1, nFlows);
for n = 1 : nFlows
    path1 = globalbestcell1{n}{1};
    path2 = globalbestcell2{n}{1};
    fprintf('flow %d, %2d <-> %2d:\n', n, T(n,1), T(n,2))
    fprintf('%21s', 'best path: ');
    printPath(path1, '  ');
    avail(n) = calculateAvailability(A, path1);
    fprintf('%13s | availability: %.4f', '', avail(n));
    fprintf('\n');
    fprintf('\tprotection path: ');
    printPath(path2, '  ');
    avail2nd(n) = calculateAvailability(A, path2);
    fprintf('%5s | availability: %.4f', '', avail2nd(n));
    fprintf('\n');
end
fprintf('average availability for best paths: %.4f\n', mean(avail));
fprintf('average availability for 2nd best paths disjoint with best path: %.4f\n', mean(avail2nd));
fprintf('\n');
plot(sort(allValues));
hold on;
fprintf('Best load = %.2f Gbps\n', globalbestload);
fprintf('No. of solutions = %d\n',length(allValues));
fprintf('Averg. quality of solutions = %.2f Gbps\n',mean(allValues));
hold off;
title('Minimun worst link load (Multi Start Hill Climbing) with 1 to 1 protection flows');
ylabel('Minimun worst link load (Gbps)');
Loads = calculateLinkLoads1to1(nNodes, Links, T, globalbestcell1, globalbestcell2);
fprintf('Sum of all links in both directions: %.2f Gb\n', sum(Loads(:,3)) + sum(Loads(:,4)))
fprintf('\nProteção 1:1 para a Best solution:\n')
for i = 1 : nLinks
    fprintf('link nº%-2d || %-2d -> %2d : %-5.2f Gb ', i, Loads(i,1), Loads(i,2), Loads(i,3))
    if Loads(i,3) > 10
        fprintf("> 10 Gb");
    else
        fprintf("       ");
    end
    fprintf(' | %-2d -> %2d : %-5.2f Gb ', Loads(i,2), Loads(i,1), Loads(i,4))
    if Loads(i,3) > 10
        fprintf("> 10 Gb");
    else
        fprintf("       ");
    end
    fprintf("\n");
end




