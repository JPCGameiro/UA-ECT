echo "Transfering data to the table node."
sshpass -f password ssh sd102@l040101-ws06.ua.pt 'mkdir -p Restaurant'
sshpass -f password ssh sd102@l040101-ws06.ua.pt 'rm -rf Restaurant/*'
sshpass -f password scp dirTable.zip sd102@l040101-ws06.ua.pt:Restaurant
echo "Decompressing data sent to the table node."
sshpass -f password ssh sd102@l040101-ws06.ua.pt 'cd Restaurant ; unzip -uq dirTable.zip'
echo "Executing program at the table server."
sshpass -f password ssh sd102@l040101-ws06.ua.pt 'cd Restaurant/dirTable ; java serverSide.main.ServerRestaurantTable 22112 l040101-ws07.ua.pt 22113'
echo "Table server shutdown."