echo "Transfering data to the registry node."
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password scp dirRegistry.zip sd102@l040101-ws01.ua.pt:test/Restaurant
echo "Decompressing data sent to the registry node."
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'cd test/Restaurant ; unzip -uq dirRegistry.zip'
echo "Executing program at the registry node."
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'cd test/Restaurant/dirRegistry ; ./registry_com_d.sh sd102'
echo "Registry node shutdown."