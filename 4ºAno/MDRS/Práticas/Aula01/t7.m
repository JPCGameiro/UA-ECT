%% b) Run Simulator 3 100 times
P = 10000;
l = 1800;
C = 10;
f = 1000000;
n = 20;

%Number of simulations
N = 100;

PLd = zeros(1, N);
PLv = zeros(1, N);
APDd = zeros(1, N);
APDv = zeros(1, N);
MPDd = zeros(1, N);
MPDv = zeros(1, N);
TT = zeros(1, N);

for i = 1:N
    [PLd(i), PLv(i), APDd(i), APDv(i), MPDd(i), MPDv(i), TT(i)] = Simulator3(l,C,f,P,n);
end

alfa = 0.1;

media = mean(PLd);
term = norminv(1-alfa/2)*sqrt(var(PLd)/N);
fprintf("Packet Loss of data (%%)       = %.2e +- %.2e\n", media, term);
media = mean(PLv);
term = norminv(1-alfa/2)*sqrt(var(PLv)/N);
fprintf("Packet Loss of VoIP (%%)       = %.2e +- %.2e\n", media, term);
media = mean(APDd);
term = norminv(1-alfa/2)*sqrt(var(APDd)/N);
fprintf("Av. Packet Delay data (ms)    = %.2e +- %.2e\n", media, term);
media = mean(APDv);
term = norminv(1-alfa/2)*sqrt(var(APDv)/N);
fprintf("Av. Packet Delay VoIP (ms)    = %.2e +- %.2e\n", media, term);
media = mean(MPDd);
term = norminv(1-alfa/2)*sqrt(var(MPDd)/N);
fprintf("Max Packet Delay of data(ms)  = %.2e +- %.2e\n", media, term);
media = mean(MPDv);
term = norminv(1-alfa/2)*sqrt(var(MPDv)/N);
fprintf("Max Packet Delay of VoIP (ms) = %.2e +- %.2e\n", media, term);
media = mean(TT);
term = norminv(1-alfa/2)*sqrt(var(TT)/N);
fprintf("Throughut (Mbps)              = %.2e +- %.2e\n\n", media, term);



%% c) Run Simulator 3 100 times but with f=10000
P = 10000;
l = 1800;
C = 10;
f = 10000;
n = 20;

%Number of simulations
N = 100;

PLd = zeros(1, N);
PLv = zeros(1, N);
APDd = zeros(1, N);
APDv = zeros(1, N);
MPDd = zeros(1, N);
MPDv = zeros(1, N);
TT = zeros(1, N);

for i = 1:N
    [PLd(i), PLv(i), APDd(i), APDv(i), MPDd(i), MPDv(i), TT(i)] = Simulator3(l,C,f,P,n);
end

alfa = 0.1;

media = mean(PLd);
term = norminv(1-alfa/2)*sqrt(var(PLd)/N);
fprintf("Packet Loss of data (%%)       = %.2e +- %.2e\n", media, term);
media = mean(PLv);
term = norminv(1-alfa/2)*sqrt(var(PLv)/N);
fprintf("Packet Loss of VoIP (%%)       = %.2e +- %.2e\n", media, term);
media = mean(APDd);
term = norminv(1-alfa/2)*sqrt(var(APDd)/N);
fprintf("Av. Packet Delay data (ms)    = %.2e +- %.2e\n", media, term);
media = mean(APDv);
term = norminv(1-alfa/2)*sqrt(var(APDv)/N);
fprintf("Av. Packet Delay VoIP (ms)    = %.2e +- %.2e\n", media, term);
media = mean(MPDd);
term = norminv(1-alfa/2)*sqrt(var(MPDd)/N);
fprintf("Max Packet Delay of data(ms)  = %.2e +- %.2e\n", media, term);
media = mean(MPDv);
term = norminv(1-alfa/2)*sqrt(var(MPDv)/N);
fprintf("Max Packet Delay of VoIP (ms) = %.2e +- %.2e\n", media, term);
media = mean(TT);
term = norminv(1-alfa/2)*sqrt(var(TT)/N);
fprintf("Throughut (Mbps)              = %.2e +- %.2e\n\n", media, term);


%% d) Run Simulator 3 100 times but with f=2000
P = 10000;
l = 1800;
C = 10;
f = 2000;
n = 20;

%Number of simulations
N = 100;

PLd = zeros(1, N);
PLv = zeros(1, N);
APDd = zeros(1, N);
APDv = zeros(1, N);
MPDd = zeros(1, N);
MPDv = zeros(1, N);
TT = zeros(1, N);

for i = 1:N
    [PLd(i), PLv(i), APDd(i), APDv(i), MPDd(i), MPDv(i), TT(i)] = Simulator3(l,C,f,P,n);
end

alfa = 0.1;

media = mean(PLd);
term = norminv(1-alfa/2)*sqrt(var(PLd)/N);
fprintf("Packet Loss of data (%%)       = %.2e +- %.2e\n", media, term);
media = mean(PLv);
term = norminv(1-alfa/2)*sqrt(var(PLv)/N);
fprintf("Packet Loss of VoIP (%%)       = %.2e +- %.2e\n", media, term);
media = mean(APDd);
term = norminv(1-alfa/2)*sqrt(var(APDd)/N);
fprintf("Av. Packet Delay data (ms)    = %.2e +- %.2e\n", media, term);
media = mean(APDv);
term = norminv(1-alfa/2)*sqrt(var(APDv)/N);
fprintf("Av. Packet Delay VoIP (ms)    = %.2e +- %.2e\n", media, term);
media = mean(MPDd);
term = norminv(1-alfa/2)*sqrt(var(MPDd)/N);
fprintf("Max Packet Delay of data(ms)  = %.2e +- %.2e\n", media, term);
media = mean(MPDv);
term = norminv(1-alfa/2)*sqrt(var(MPDv)/N);
fprintf("Max Packet Delay of VoIP (ms) = %.2e +- %.2e\n", media, term);
media = mean(TT);
term = norminv(1-alfa/2)*sqrt(var(TT)/N);
fprintf("Throughut (Mbps)              = %.2e +- %.2e\n\n", media, term);



%% e) Run Simulator 4 100 times
P = 10000;
l = 1800;
C = 10;
f = 1000000;
n = 20;

%Number of simulations
N = 100;

PLd = zeros(1, N);
PLv = zeros(1, N);
APDd = zeros(1, N);
APDv = zeros(1, N);
MPDd = zeros(1, N);
MPDv = zeros(1, N);
TT = zeros(1, N);

for i = 1:N
    [PLd(i), PLv(i), APDd(i), APDv(i), MPDd(i), MPDv(i), TT(i)] = Simulator4(l,C,f,P,n);
end

alfa = 0.1;

media = mean(PLd);
term = norminv(1-alfa/2)*sqrt(var(PLd)/N);
fprintf("Packet Loss of data (%%)       = %.2e +- %.2e\n", media, term);
media = mean(PLv);
term = norminv(1-alfa/2)*sqrt(var(PLv)/N);
fprintf("Packet Loss of VoIP (%%)       = %.2e +- %.2e\n", media, term);
media = mean(APDd);
term = norminv(1-alfa/2)*sqrt(var(APDd)/N);
fprintf("Av. Packet Delay data (ms)    = %.2e +- %.2e\n", media, term);
media = mean(APDv);
term = norminv(1-alfa/2)*sqrt(var(APDv)/N);
fprintf("Av. Packet Delay VoIP (ms)    = %.2e +- %.2e\n", media, term);
media = mean(MPDd);
term = norminv(1-alfa/2)*sqrt(var(MPDd)/N);
fprintf("Max Packet Delay of data(ms)  = %.2e +- %.2e\n", media, term);
media = mean(MPDv);
term = norminv(1-alfa/2)*sqrt(var(MPDv)/N);
fprintf("Max Packet Delay of VoIP (ms) = %.2e +- %.2e\n", media, term);
media = mean(TT);
term = norminv(1-alfa/2)*sqrt(var(TT)/N);
fprintf("Throughut (Mbps)              = %.2e +- %.2e\n\n", media, term);



%% e) Run Simulator 4 100 times with f = 10000
P = 10000;
l = 1800;
C = 10;
f = 10000;
n = 20;

%Number of simulations
N = 100;

PLd = zeros(1, N);
PLv = zeros(1, N);
APDd = zeros(1, N);
APDv = zeros(1, N);
MPDd = zeros(1, N);
MPDv = zeros(1, N);
TT = zeros(1, N);

for i = 1:N
    [PLd(i), PLv(i), APDd(i), APDv(i), MPDd(i), MPDv(i), TT(i)] = Simulator4(l,C,f,P,n);
end

alfa = 0.1;

media = mean(PLd);
term = norminv(1-alfa/2)*sqrt(var(PLd)/N);
fprintf("Packet Loss of data (%%)       = %.2e +- %.2e\n", media, term);
media = mean(PLv);
term = norminv(1-alfa/2)*sqrt(var(PLv)/N);
fprintf("Packet Loss of VoIP (%%)       = %.2e +- %.2e\n", media, term);
media = mean(APDd);
term = norminv(1-alfa/2)*sqrt(var(APDd)/N);
fprintf("Av. Packet Delay data (ms)    = %.2e +- %.2e\n", media, term);
media = mean(APDv);
term = norminv(1-alfa/2)*sqrt(var(APDv)/N);
fprintf("Av. Packet Delay VoIP (ms)    = %.2e +- %.2e\n", media, term);
media = mean(MPDd);
term = norminv(1-alfa/2)*sqrt(var(MPDd)/N);
fprintf("Max Packet Delay of data(ms)  = %.2e +- %.2e\n", media, term);
media = mean(MPDv);
term = norminv(1-alfa/2)*sqrt(var(MPDv)/N);
fprintf("Max Packet Delay of VoIP (ms) = %.2e +- %.2e\n", media, term);
media = mean(TT);
term = norminv(1-alfa/2)*sqrt(var(TT)/N);
fprintf("Throughut (Mbps)              = %.2e +- %.2e\n\n", media, term);



%% f) Run Simulator 4 100 times with f = 2000
P = 10000;
l = 1800;
C = 10;
f = 2000;
n = 20;

%Number of simulations
N = 100;

PLd = zeros(1, N);
PLv = zeros(1, N);
APDd = zeros(1, N);
APDv = zeros(1, N);
MPDd = zeros(1, N);
MPDv = zeros(1, N);
TT = zeros(1, N);

for i = 1:N
    [PLd(i), PLv(i), APDd(i), APDv(i), MPDd(i), MPDv(i), TT(i)] = Simulator4(l,C,f,P,n);
end

alfa = 0.1;

media = mean(PLd);
term = norminv(1-alfa/2)*sqrt(var(PLd)/N);
fprintf("Packet Loss of data (%%)       = %.2e +- %.2e\n", media, term);
media = mean(PLv);
term = norminv(1-alfa/2)*sqrt(var(PLv)/N);
fprintf("Packet Loss of VoIP (%%)       = %.2e +- %.2e\n", media, term);
media = mean(APDd);
term = norminv(1-alfa/2)*sqrt(var(APDd)/N);
fprintf("Av. Packet Delay data (ms)    = %.2e +- %.2e\n", media, term);
media = mean(APDv);
term = norminv(1-alfa/2)*sqrt(var(APDv)/N);
fprintf("Av. Packet Delay VoIP (ms)    = %.2e +- %.2e\n", media, term);
media = mean(MPDd);
term = norminv(1-alfa/2)*sqrt(var(MPDd)/N);
fprintf("Max Packet Delay of data(ms)  = %.2e +- %.2e\n", media, term);
media = mean(MPDv);
term = norminv(1-alfa/2)*sqrt(var(MPDv)/N);
fprintf("Max Packet Delay of VoIP (ms) = %.2e +- %.2e\n", media, term);
media = mean(TT);
term = norminv(1-alfa/2)*sqrt(var(TT)/N);
fprintf("Throughut (Mbps)              = %.2e +- %.2e\n\n", media, term);
