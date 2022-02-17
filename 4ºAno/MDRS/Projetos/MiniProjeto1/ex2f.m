N = 50;

lambda = 1500;  % pps
f = 10000;
C = 10;         % Mbps
P = 10000;      % packets

alfa = 0.1;

n = [10, 20, 30, 40];

APD = zeros(4, N);
APDv = zeros(4, N);
PL = zeros(4, N);
PLv =zeros(4, N);

media = zeros(4, 4);
term = zeros(4, 4);

%{
+----+-----+------+----+-----+
|    | APD | APDv | PL | PLv |
+----+-----+------+----+-----+
| 10 |     |      |    |     |
+----+-----+------+----+-----+
| 20 |     |      |    |     |
+----+-----+------+----+-----+
| 30 |     |      |    |     |
+----+-----+------+----+-----+
| 40 |     |      |    |     |
+----+-----+------+----+-----+
%}

for i = [1:4]  
    for x = [1:N]
        [PL(i, x), PLv(i, x), APD(i, x), APDv(i, x), ~, ~, ~] = ex2f_Simulator4(lambda, C, f, P, n(i));
    end
    
    media(i, 1) = mean(APD(i,:));
    media(i, 2) = mean(APDv(i,:));
    media(i, 3) = mean(PL(i,:));
    media(i, 4) = mean(PLv(i,:));

    term(i, 1) = norminv(1-alfa/2) * sqrt(var(APD(i,:))/N);
    term(i, 2) = norminv(1-alfa/2) * sqrt(var(APDv(i,:))/N);
    term(i, 3) = norminv(1-alfa/2) * sqrt(var(PL(i,:))/N);
    term(i, 4) = norminv(1-alfa/2) * sqrt(var(PLv(i,:))/N);
end

% display
n = categorical({'APD','APDv','PL','PLv'});
n = reordercats(n,{'APD','APDv','PL','PLv'});
f = figure('Name','Ex. 2.e)','NumberTitle','off');
f.Position = [100 100 1050 400];

t = tiledlayout(1,4);
t1 = nexttile;
bar(n, media(1, :));
hold on
er = errorbar(n,  media(1, :), term(1, :) * -1, term(1, :));    
er.Color = [0 0 0];                            
er.LineStyle = 'none'; 
xlabel(t1, "n = 10");
grid on
hold off

t2 = nexttile;
bar(n, media(2, :));
hold on
er = errorbar(n,  media(2, :), term(2, :) * -1, term(2, :));    
er.Color = [0 0 0];                            
er.LineStyle = 'none';
xlabel(t2, "n = 20");
grid on
hold off

t3 = nexttile;
bar(n, media(3, :));
hold on
er = errorbar(n,  media(3, :), term(3, :) * -1, term(3, :));    
er.Color = [0 0 0];                            
er.LineStyle = 'none';
xlabel(t3, "n = 30");
grid on
hold off

t4 = nexttile;
bar(n, media(4, :));
hold on
er = errorbar(n,  media(4, :), term(4, :) * -1, term(4, :));    
er.Color = [0 0 0];                            
er.LineStyle = 'none';
xlabel(t4, "n = 40");
grid on
hold off

linkaxes([t1, t2, t3, t4], 'y');

title(t, "APD and PL for data and VoIP with diferent number of VoIP flows(n), considering Priority Queuing (Simplified WRED)");
ylabel(t, "APD, APDv (ms) | PL, PLv (%)")