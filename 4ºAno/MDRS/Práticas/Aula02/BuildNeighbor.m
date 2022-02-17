function [nSol, nL] = BuildNeighbor(bestSol,i, sP, nSP, Links, nNodes, T, nLoad)
%Find best neighbor of a given solution
%   i - flow from whom we will choose neighbors
%   bestSol - current solution 
%   sP - Contains shortest paths among network nodes for each flow
%   nSP - Contains the number of shortest paths for each flow
%   Links - Links of the network
%   nNodes - Network Nodes
%   T - Network flows
%   nLoad - best load found with  greedy randomized solution

nFlows= size(bestSol);
nSol= bestSol;
nL= nLoad;

k=0;
value=0;

for n=1:nFlows
    if n~=i
        newNeighbor= bestSol;
        for j=1:nSP(n)
            if j~=bestSol(n)
                newNeighbor(n)= j;
                nLoads= calculateLinkLoads(nNodes,Links,T,sP,newNeighbor);
                load= max(max(nLoads(:,3:4)));
                if load < nL
                    k=n;
                    value= j; 
                end
            end
        end
    end
end
if k>0
    nSol(k)= value;
end

end