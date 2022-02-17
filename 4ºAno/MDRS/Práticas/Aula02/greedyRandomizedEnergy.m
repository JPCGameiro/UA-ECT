function [sol,eN] = greedyRandomizedEnergy(nFlows,nSP, nNodes, Links, T, L, sP, limit)
%UNTITLED2 Summary of this function goes here
%   Detailed explanation goes here
t= tic;
nLinks= size(Links,1);
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
        if load<=10
            energy = 0;
            for a=1:nLinks
                if Loads(a,3)+Loads(a,4)>0
                    energy = energy + L(Loads(a,1),Loads(a,2)); %a energia e igual a energia + o comprimento do link (L -> comprimento do link)
                end
            end
        else
            energy=inf;
        end
        if energy < best
            k_best= k;
            best= energy;
        end
    end
    if k_best>0
        sol(i) = k_best;
    else
        break;
    end
end
eN= best;

end