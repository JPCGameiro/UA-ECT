function [sP1 nSP1 sP2 nSP2]= bestKpaths(L,T,k)
    nFlows= size(T,1);
    nSP1= zeros(1,nFlows);
    % e k = 10 most available routing paths provided by the network to each traffic flow
    for f = 1:nFlows
        [shortestPath, totalCost] = kShortestPath(L,T(f,1),T(f,2),k);
        sP1{f}= shortestPath;
        nSP1(f)= length(totalCost);
    end
    nSP2 = zeros(1,nFlows);
    for f = 1:nFlows
        flowK_shortest = sP1{f};
        for x = 1:nSP1(f) % k
            path = flowK_shortest{x};
            auxL = L;
            for j = 1: (length(path) - 1)
                auxL(path(j), path(j+1)) = inf;
                auxL(path(j+1), path(j)) = inf;
            end
            [shortestPath, totalCost] = kShortestPath(auxL,T(f,1),T(f,2),1);
            if ~isempty(shortestPath)
                aux{x} = shortestPath{1};
            else 
                aux{x} = [];
            end
            nSP2(f) = length(totalCost);
        end
        sP2{f} = aux;
    end
end