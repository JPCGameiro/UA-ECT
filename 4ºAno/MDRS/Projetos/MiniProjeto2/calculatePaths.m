function [sP nSP]= calculatePaths(L,T,n)
    nFlows= size(T,1);
    nSP= zeros(1,nFlows);
    for i=1:nFlows
        [shortestPath, totalCost] = kShortestPath(L,T(i,1),T(i,2),n);
        sP{i}= shortestPath;
        nSP(i)= length(totalCost);
    end
end