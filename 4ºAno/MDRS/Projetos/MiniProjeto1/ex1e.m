% ex 1.e
N = 50;

lambda = 1800;  % pps
f = 1000000;
C = [10, 20, 30, 40];         % Mbps
P = 10000;      % packets

alfa = 0.1;

APD_s64 = zeros(4, N);
APD_s110 = zeros(4, N);
APD_s1518 = zeros(4, N);

media = zeros(4, 3);
term = zeros(4, 3);

for i = [1:4]  
    for n = [1:N]
        [~, ~, APD_s64(i, n), APD_s110(i, n), APD_s1518(i, n), ~, ~] = ex1e_Simulator1(lambda, C(i), f, P);
    end
    
    media(i, 1) = mean(APD_s64(i,:));
    media(i, 2) =  mean(APD_s110(i,:));
    media(i, 3) =  mean(APD_s1518(i,:));

    term(i, 1) = norminv(1-alfa/2) * sqrt(var(APD_s64(i,:))/N);
    term(i, 2) = norminv(1-alfa/2) * sqrt(var(APD_s110(i,:))/N);
    term(i, 3) = norminv(1-alfa/2) * sqrt(var(APD_s1518(i,:))/N);
end

disp(media(1,:));

C = categorical({'10','20','30','40'});
C = reordercats(C,{'10','20','30','40'});
b = bar(C, media, "grouped");
hold on
% Calculate the number of groups and number of bars in each group
[ngroups,nbars] = size(media);
% Get the x coordinate of the bars
x = nan(nbars, ngroups);
for i = 1:nbars
    x(i,:) = b(i).XEndPoints;
end
er = errorbar(x', media, term, 'k','linestyle','none');
hold off

title("Avg. Packet Delay");
xlabel("C, link capacity (Mbs)");
ylabel("Delay (ms)");
legend({'64 bytes', '110 bytes', '1518 bytes'});