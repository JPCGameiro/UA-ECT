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

co= Nodes(:,1)+j*Nodes(:,2);
L= inf(nNodes);    %Square matrix with arc lengths (in Km)
for i=1:nNodes
    L(i,i)= 0;
end
for i=1:nLinks
    d= abs(co(Links(i,1))-co(Links(i,2)));
    L(Links(i,1),Links(i,2))= d+5; %Km
    L(Links(i,2),Links(i,1))= d+5; %Km
end
L= round(L);  %Km

% Compute up to n paths for each flow:
n= inf;
[sP nSP]= calculatePaths(L,T,n);

tempo= 10;

%Optimization algorithm with greedy randomized:
t= tic;
bestEnergy= inf;
allValues= [];
while toc(t)<tempo
    continuar= true;
    while continuar
        continuar= false;
        ax2= randperm(nFlows);
        sol= zeros(1,nFlows);
        for i= ax2
            k_best= 0;
            best= inf;
            for k= 1:nSP(i)
                sol(i)= k;
                Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
                load= max(max(Loads(:,3:4)));
                if load <= 10
                    energy= 0;
                    for a= 1:nLinks
                        if Loads(a,3)+Loads(a,4)>0
                            energy= energy + L(Loads(a,1),Loads(a,2));
                        end
                    end
                else
                    energy= inf;
                end
                if energy<best
                    k_best= k;
                    best= energy;
                end            
            end
            if k_best>0
                sol(i)= k_best;
            else
                continuar= true;
                break;
            end
        end
    end 
    energy= best;
    allValues= [allValues energy];
    if energy<bestEnergy
        bestSol= sol;
        bestEnergy= energy;
    end
end
figure(1)
plot(sort(allValues));
fprintf('GREEDY RANDOMIZED:\n');
fprintf('   Best energy = %.1f\n',bestEnergy);
fprintf('   No. of solutions = %d\n',length(allValues));
fprintf('   Av. quality of solutions = %.1f\n',mean(allValues));


%Optimization algorithm with multi start hill climbing:
t= tic;
bestEnergy= inf;
allValues= [];
contadortotal= [];
while toc(t)<tempo
    %GREEDY RANDOMIZED:
    continuar= true;
    while continuar
        continuar= false;
        ax2= randperm(nFlows);
        sol= zeros(1,nFlows);
        for i= ax2
            k_best= 0;
            best= inf;
            for k= 1:nSP(i)
                sol(i)= k;
                Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
                load= max(max(Loads(:,3:4)));
                if load <= 10
                    energy= 0;
                    for a= 1:nLinks
                        if Loads(a,3)+Loads(a,4)>0
                            energy= energy + L(Loads(a,1),Loads(a,2));
                        end
                    end
                else
                    energy= inf;
                end
                if energy<best
                    k_best= k;
                    best= energy;
                end            
            end
            if k_best>0
                sol(i)= k_best;
            else
                continuar= true;
                break;
            end
        end
    end 
    energy= best;
    
    %HILL CLIMBING:
    continuar= true;
    while continuar
        i_best= 0;
        k_best= 0;
        best= energy;
        for i= 1:nFlows
            for k= 1:nSP(i)
                if k~=sol(i)
                    aux= sol(i);
                    sol(i)= k;
                    Loads= calculateLinkLoads(nNodes,Links,T,sP,sol);
                    load1= max(max(Loads(:,3:4)));
                    if load1 <= 10
                        energy1= 0;
                        for a= 1:nLinks
                            if Loads(a,3)+Loads(a,4)>0
                                energy1= energy1 + L(Loads(a,1),Loads(a,2));
                            end
                        end
                    else
                        energy1= inf;
                    end
                    if energy1<best
                        i_best= i;
                        k_best= k;
                        best= energy1;
                    end
                    sol(i)= aux;
                end
            end
        end
        if i_best>0
            sol(i_best)= k_best;
            energy= best;
        else
            continuar= false;
        end
    end    
    allValues= [allValues energy];
    if energy<bestEnergy
        bestSol= sol;
        bestEnergy= energy;
    end
end
hold on
plot(sort(allValues));
legend('Greedy Randomized','Hill Climbing');
title('No. of hill climbing iterations')

fprintf('MULTI START HILL CLIMBING:\n');
fprintf('   Best energy = %.1f\n',bestEnergy);
fprintf('   No. of solutions = %d\n',length(allValues));
fprintf('   Av. quality of solutions = %.1f\n',mean(allValues));



