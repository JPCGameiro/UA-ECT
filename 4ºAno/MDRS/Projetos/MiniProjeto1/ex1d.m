l = 1800;               %Packet rate
C = [10, 20, 30, 40];   %Link Capacity
f = 1000000;            %Queue

%Calculation of the probability associated to each packet size 
% (other than the sizes 64, 110 and 1518 bytes) 
numelems = (109 - 65 + 1) + (1517 - 111 + 1);
probrestante = 100 - (19 + 23 + 17);
probcadaelem = (probrestante / numelems);

bytes = 64:1518;

for j = 1:length(C)
    S = (bytes .* 8)./(C(j)*10^6);
    S2 = (bytes .* 8)./(C(j)*10^6);
    for i = 1:length(bytes)
        if i == 1
            S(i) = S(i)*0.19;
            S2(i) = S2(i)^2*0.19;
        elseif i == 110-64+1
            S(i) = S(i)*0.23;
            S2(i) = S2(i)^2*0.23;
        elseif i == 1518-64+1
            S(i) = S(i)*0.17;
            S2(i) = S2(i)^2*0.17;
        else
            S(i) = S(i)*(probcadaelem/100);
            S2(i) = S2(i)^2*(probcadaelem/100);
        end
    end
    
    ES = sum(S);
    ES2 = sum(S2);
    
    W = ((l*ES2/(2*(1-l*(ES)))) + ES)*10^3;
    fprintf("Link Capacity: %2d Mbps -> ", C(j));
    fprintf("Average Packet Delay:  %.2e ms\n", W);
end