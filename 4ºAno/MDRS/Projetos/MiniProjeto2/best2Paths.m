function [sP nSP]= best2Paths(L,T)
    nFlows= size(T,1);
    nSP= zeros(1,(2*nFlows));
    for i=1:nFlows
        [shortestPath, totalCost] = kShortestPath(L,T(i,1),T(i,2),1);
        path = shortestPath{1};
        sP{i}= shortestPath;
        nSP(i)= length(totalCost);
        auxL = L;
        for j = 1: (length(path) - 1)
            auxL(path(j), path(j+1)) = inf;
            auxL(path(j+1), path(j)) = inf;
        end
        [shortestPath, totalCost] = kShortestPath(auxL,T(i,1),T(i,2),1);
        sP{nFlows + i} = shortestPath;
        nSP(nFlows + i) = length(totalCost);
    end
end