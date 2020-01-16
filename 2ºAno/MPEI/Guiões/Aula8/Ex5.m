%% a)

T = [0.7 0.2 0.3;
     0.2 0.3 0.3;
     0.1 0.5 0.4]

sum(T)

%% b)
T = [0.7 0.2 0.3;
     0.2 0.3 0.3;
     0.1 0.5 0.4];
 
x0 = [1; 0; 0];  %dia inicial é sol

x2 = T^2*x0

probChuva = x2(3)

%% c)
T = [0.7 0.2 0.3;
     0.2 0.3 0.3;
     0.1 0.5 0.4];
N=20;
M = zeros(9,N);

for i=1:20
    M0 = T^i;
    M1 = [M0(:,1); M0(:,2); M0(:,3)];
    M(:,i) = M1;
end
plot(1:20,M)

%% d)
T = [0.7 0.2 0.3;
     0.2 0.3 0.3;
     0.1 0.5 0.4];

M = [];
check = 0;
Maux = [T(:,1); T(:,2); T(:,3)];
M = [M Maux];
i = 2;


while check == 0
    M0 = T^i;
    M1 = [M0(:,1); M0(:,2); M0(:,3)];
    M = [M M1];
    diference = abs(M(:,(i))-M(:,i-1));
    if(diference >= 1e-4)
        check = 1;
    end
    i = i+1;
    plot(M)
    drawnow
end
 

