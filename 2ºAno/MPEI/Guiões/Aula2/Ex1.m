%% a) Obter pelo menos um rapaz em dois filhos
experiencias = rand(2, 1e5) > 0.5;
sucessos = sum(experiencias) >= 1;
prob = sum(sucessos)/1e5         

%% c) Obter um rapaz sabendo que o outro filho é rapaz

experiencias = rand(2, 1e5) > 0.5;
sucessos = sum(experiencias)== 2;
casostotais = sum(experiencias) >= 1;
prob = sum(sucessos)/sum(casostotais)

%% d) Obter um rapaz sabendo que o primeiro é rapaz

experiencias = rand(2, 1e5) > 0.5;
sucessos = sum(experiencias) == 2;
casostotais = experiencias(1,:);
N = sum(casostotais);
prob = sum(sucessos)/N

%% e) Em 5 filhos obter apenas um rapaz sabendo que já exite um

experiencias = rand(5, 1e5) > 0.5;
s1 = sum(experiencias) >= 1;        %pelo menos 1 filho
s2 = sum(experiencias) == 2;        %2 filhos
prob = sum(s2)/sum(s1)

%% f) Em 5 filhos sabendo que já existe um, pelo menos um dos outros é rapaz

experiencias = rand(5, 1e5) > 0.5;
s1 = sum(experiencias) >= 1;
s2 = sum(experiencias) >= 2;
prob = sum(s2)/sum(s1)



