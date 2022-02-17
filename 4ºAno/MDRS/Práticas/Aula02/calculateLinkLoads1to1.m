function Loads= calculateLinkLoads1to1(nNodes,Links,T,sP1,sP2)
    nFlows= size(T,1);
    nLinks= size(Links,1);
    aux= zeros(nNodes);
    for i= 1:nFlows
        path= sP1{i}{1};
        for j=2:length(path)
            aux(path(j-1),path(j))= aux(path(j-1),path(j)) + T(i,3); 
            aux(path(j),path(j-1))= aux(path(j),path(j-1)) + T(i,4);
        end
    end
    for link= 1:nLinks
        aux2= zeros(nNodes);
        t1= Links(link,1);
        t2= Links(link,2);
        for i= 1:nFlows
            path= sP1{i}{1};
            pathdif= find(path==t1 | path==t2);
            if length(pathdif)<2 || pathdif(2)-pathdif(1)>1
                for j=2:length(path)
                    aux2(path(j-1),path(j))= aux2(path(j-1),path(j)) + T(i,3); 
                    aux2(path(j),path(j-1))= aux2(path(j),path(j-1)) + T(i,4);
                end
            elseif ~isempty(sP2{i}{1})
                path= sP2{i}{1};
                for j=2:length(path)
                    aux2(path(j-1),path(j))= aux2(path(j-1),path(j)) + T(i,3); 
                    aux2(path(j),path(j-1))= aux2(path(j),path(j-1)) + T(i,4);
                end
            end
        end
        aux=max(aux,aux2);
    end
    Loads= [Links zeros(nLinks,2)];
    for i= 1:nLinks
        Loads(i,3)= aux(Loads(i,1),Loads(i,2));
        Loads(i,4)= aux(Loads(i,2),Loads(i,1));
    end
end