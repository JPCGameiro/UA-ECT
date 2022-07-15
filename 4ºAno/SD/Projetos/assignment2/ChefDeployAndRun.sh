echo "Transfering data to the chef node."
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'mkdir -p Restaurant'
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'rm -rf Restaurant/*'
sshpass -f password scp dirChef.zip sd102@l040101-ws01.ua.pt:Restaurant
echo "Decompressing data sent to Chef node."
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'cd Restaurant ; unzip -uq dirChef.zip'
echo "Executing program at the Chef node."
sshpass -f password ssh sd102@l040101-ws01.ua.pt 'cd Restaurant/dirChef ; java clientSide.main.ClientChef l040101-ws05.ua.pt 22110 l040101-ws04.ua.pt 22111 l040101-ws07.ua.pt 22113'
echo "Chef client shutdown."