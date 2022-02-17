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

MTBF= (450*365*24)./L;
A= MTBF./(MTBF + 24);
A(isnan(A))= 0;
Alog= -log(A);

[sP1 a1 sP2 a2]= calculateDisjointPaths(Alog,T);

clc
for i= 1:nFlows
    fprintf('Flow %d:\n',i);
    fprintf('   First path: %d',sP1{i}{1}(1));
    for j= 2:length(sP1{i}{1})
        fprintf('-%d',sP1{i}{1}(j));
    end
    if ~isempty(sP2{i}{1})
        fprintf('\n   Second path: %d',sP2{i}{1}(1));
        for j= 2:length(sP2{i}{1})
            fprintf('-%d',sP2{i}{1}(j));
        end
    end
    fprintf('\n   Availability of First Path= %.5f%%\n',100*a1(i));
    if ~isempty(sP2{i}{1})
        fprintf('   Availability of Second Path= %.5f%%\n',100*a2(i));
    end
end

Loads= calculateLinkLoads1plus1(nNodes,Links,T,sP1,sP2)
totalLoad= sum(sum(Loads(:,3:4)))

Loads= calculateLinkLoads1to1(nNodes,Links,T,sP1,sP2)
totalLoad= sum(sum(Loads(:,3:4)))



