%% a) Run Simulator 3 50 times with a stopping criterion of P = 10000
P = 10000;
l = 1500;
C = 10;
f = 1000000;
n = [10, 20, 30, 40];

delay = zeros(4, 2);
errors = zeros(4, 2);

N = 50;
for j = 1:length(n)
    PLd = zeros(1, N);
    PLv = zeros(1, N);
    APDd = zeros(1, N);
    APDv = zeros(1, N);
    MPDd = zeros(1, N);
    MPDv = zeros(1, N);
    TT = zeros(1, N);
    
    for i = 1:N
        [PLd(i), PLv(i), APDd(i), APDv(i), MPDd(i), MPDv(i), TT(i)] = Simulator3(l,C,f,P,n(j));
    end    
    alfa = 0.1;    
    
    media = mean(APDd);
    term = norminv(1-alfa/2)*sqrt(var(APDd)/N);
    fprintf("Av. Packet Delay data (ms)    = %.2e +- %.2e\n", media, term);
    delay(j, 1) = media;
    errors(j, 1) = term;

    media = mean(APDv);
    term = norminv(1-alfa/2)*sqrt(var(APDv)/N);
    fprintf("Av. Packet Delay VoIP (ms)    = %.2e +- %.2e\n", media, term);
    delay(j, 2) = media;
    errors(j, 2) = term;
end

f = figure('Name','Ex. 2.a)','NumberTitle','off');
b = bar(n, delay, "grouped");
hold on
% Calculate the number of groups and number of bars in each group
[ngroups,nbars] = size(delay);
% Get the x coordinate of the bars
x = nan(nbars, ngroups);
for i = 1:nbars
    x(i,:) = b(i).XEndPoints;
end
er = errorbar(x', delay, errors, 'k','linestyle','none');
grid on
hold off

title("Average Packet Delay")
xlabel("Number VoIP packet Flows(n)")
ylabel("Delay (ms)")
legend({'Data', 'VoIP'}, 'Location', 'northwest');
