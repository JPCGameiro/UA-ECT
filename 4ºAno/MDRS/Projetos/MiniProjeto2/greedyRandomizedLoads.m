function [sol,load] = greedyRandomizedLoads(nFlows,nSP, nNodes, Links, T, sP, limit)
%UNTITLED Summary of this function goes here
%   Detailed explanation goes here
ax2 = randperm(nFlows); % array numa ordem aleatória
sol= zeros(1,nFlows);
for i= ax2
    k_best = 0;
    best = inf;
    n = min(limit, nSP(i));
    for k= 1:n
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
end