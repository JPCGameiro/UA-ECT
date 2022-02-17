function [PL , APD , APD_s64, APD_s110, APD_s1518,  MPD , TT] = ex1e_Simulator1(lambda,C,f,P)
% INPUT PARAMETERS:
%  lambda - packet rate (packets/sec)
%  C      - link bandwidth (Mbps)
%  f      - queue size (Bytes)
%  P      - number of packets (stopping criterium)
% OUTPUT PARAMETERS:
%  PL   - packet loss (%)
%  APD  - average packet delay (milliseconds)
%  MPD  - maximum packet delay (milliseconds)
%  TT   - transmitted throughput (Mbps)

%Events:
ARRIVAL= 0;       % Arrival of a packet            
DEPARTURE= 1;     % Departure of a packet

%State variables:
STATE = 0;          % 0 - connection free; 1 - connection bysy
QUEUEOCCUPATION= 0; % Occupation of the queue (in Bytes)
QUEUE= [];          % Size and arriving time instant of each packet in the queue

%Statistical Counters:
TOTALPACKETS= 0;       % No. of packets arrived to the system
LOSTPACKETS= 0;        % No. of packets dropped due to buffer overflow
TRANSMITTEDPACKETS= 0; % No. of transmitted packets
TRANSMITTEDPACKETS_s64 = 0;
TRANSMITTEDPACKETS_s110 = 0;
TRANSMITTEDPACKETS_s1518 = 0;
TRANSMITTEDBYTES= 0;   % Sum of the Bytes of transmitted packets
DELAYS= 0;             % Sum of the delays of transmitted packets
DELAYS_s64 = 0;
DELAYS_s110 = 0;
DELAYS_s1518 = 0;
MAXDELAY= 0;           % Maximum delay among all transmitted packets

% Initializing the simulation clock:
Clock= 0;

% Initializing the List of Events with the first ARRIVAL:
tmp= Clock + exprnd(1/lambda);
EventList = [ARRIVAL, tmp, GeneratePacketSize(), tmp];

%Similation loop:
while TRANSMITTEDPACKETS<P               % Stopping criterium
    EventList= sortrows(EventList,2);    % Order EventList by time
    Event= EventList(1,1);               % Get first event and 
    Clock= EventList(1,2);               %   and
    PacketSize= EventList(1,3);          %   associated
    ArrivalInstant= EventList(1,4);      %   parameters.
    EventList(1,:)= [];                  % Eliminate first event
    switch Event
        case ARRIVAL                     % If first event is an ARRIVAL
            TOTALPACKETS= TOTALPACKETS+1;
            tmp= Clock + exprnd(1/lambda);
            EventList = [EventList; ARRIVAL, tmp, GeneratePacketSize(), tmp];
            if STATE==0
                STATE= 1;
                EventList = [EventList; DEPARTURE, Clock + 8*PacketSize/(C*10^6), PacketSize, Clock];
            else
                if QUEUEOCCUPATION + PacketSize <= f
                    QUEUE= [QUEUE;PacketSize , Clock];
                    QUEUEOCCUPATION= QUEUEOCCUPATION + PacketSize;
                else
                    LOSTPACKETS= LOSTPACKETS + 1;
                end
            end
        case DEPARTURE                     % If first event is a DEPARTURE
            TRANSMITTEDBYTES= TRANSMITTEDBYTES + PacketSize;
            aux =  (Clock - ArrivalInstant);
            DELAYS= DELAYS + aux;
            switch PacketSize
                case 110
                    DELAYS_s110 = DELAYS_s110 + aux;
                    TRANSMITTEDPACKETS_s110 = TRANSMITTEDPACKETS_s110 + 1;
                case 64
                    DELAYS_s64 = DELAYS_s64 + aux;
                    TRANSMITTEDPACKETS_s64 = TRANSMITTEDPACKETS_s64 + 1;
                case 1518
                    DELAYS_s1518 = DELAYS_s1518 + aux;
                    TRANSMITTEDPACKETS_s1518 = TRANSMITTEDPACKETS_s1518 + 1;
                otherwise
            end
            if Clock - ArrivalInstant > MAXDELAY
                MAXDELAY= Clock - ArrivalInstant;
            end
            TRANSMITTEDPACKETS= TRANSMITTEDPACKETS + 1;
            if QUEUEOCCUPATION > 0
                EventList = [EventList; DEPARTURE, Clock + 8*QUEUE(1,1)/(C*10^6), QUEUE(1,1), QUEUE(1,2)];
                QUEUEOCCUPATION= QUEUEOCCUPATION - QUEUE(1,1);
                QUEUE(1,:)= [];
            else
                STATE= 0;
            end
    end
end

%Performance parameters determination:
PL= 100*LOSTPACKETS/TOTALPACKETS;      % in %
APD= 1000*DELAYS/TRANSMITTEDPACKETS;   % in milliseconds
APD_s64 = 1000*DELAYS_s64/TRANSMITTEDPACKETS_s64;
APD_s110 = 1000*DELAYS_s110/TRANSMITTEDPACKETS_s110;
APD_s1518 = 1000*DELAYS_s1518/TRANSMITTEDPACKETS_s1518;
MPD= 1000*MAXDELAY;                    % in milliseconds
TT= 10^(-6)*TRANSMITTEDBYTES*8/Clock;  % in Mbps

end

function out= GeneratePacketSize()
    aux= rand();
    aux2= [65:109 111:1517];
    if aux <= 0.19
        out= 64;
    elseif aux <= 0.19 + 0.23
        out= 110;
    elseif aux <= 0.19 + 0.23 + 0.17
        out= 1518;
    else
        out = aux2(randi(length(aux2)));
    end
end