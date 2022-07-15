echo "Transfering data to the RMIregistry node."
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'rm -rf test/Restaurant/*'
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'mkdir -p Public/classes/interfaces'
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'rm -rf Public/classes/interfaces/*'
sshpass -f password scp dirRMIRegistry.zip sd102@l040101-ws01.ua.pt:test/Restaurant
echo "Decompressing data sent to the RMIregistry node."
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'cd test/Restaurant ; unzip -uq dirRMIRegistry.zip'
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'cd test/Restaurant/dirRMIRegistry ; cp interfaces/*.class /home/sd102/Public/classes/interfaces ; cp set_rmiregistry_d.sh /home/sd102'
echo "Executing program at the RMIregistry node."
sshpass -f password ssh sd102@l040101-ws01.ua.pt './set_rmiregistry_d.sh sd102 22110'
echo "RMIregistry node shutdown."