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

%Matrix A -> availability of each flow
MTBF= (450*365*24)./L;
A= MTBF./(MTBF + 24);
A(isnan(A))= 0;

logA = -log(A);


%A) For each flow the most available path (and its availability value)
% Most available path will be stored in sP
n= 1;
[sP nSP]= calculatePathsFailure(logA,T,n);
avail= [];


for i=1:nFlows
    v= 1;
    for j=1:length(sP{i}{1})-1
        v = v * A(sP{i}{1}(j),sP{i}{1}(j+1));  
    end
    avail= [avail v];
end
avail


%B) Second routing path given by the most available path
avail=[];
for i=1:nFlows
    tmpA= logA; 
    for j=1:length(sP{i}{1})-1 
        tmpA(sP{i}{1}(j),sP{i}{1}(j+1))= inf;
        tmpA(sP{i}{1}(j+1),sP{i}{1}(j))= inf;
    end

    for i=1:nFlows
        [shortestPath, totalCost] = kShortestPath(tmpA,T(i,1),T(i,2),n);
        sAP{i}= shortestPath;
        nASP(i)= length(totalCost);
    end
    v= 1;
    sAP
    for j=1:length(sAP{i}{1})-1
        v = v * A(sAP{i}{1}(j),sAP{i}{1}(j+1));  
    end
    avail= [avail v];
end
avail
