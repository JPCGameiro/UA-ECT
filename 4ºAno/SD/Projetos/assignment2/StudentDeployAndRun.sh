echo "Transfering data to the Student node."
sshpass -f password ssh sd102@l040101-ws03.ua.pt 'mkdir -p Restaurant'
sshpass -f password ssh sd102@l040101-ws03.ua.pt 'rm -rf Restaurant/*'
sshpass -f password scp dirStudent.zip sd102@l040101-ws03.ua.pt:Restaurant
echo "Decompressing data sent to Student node."
sshpass -f password ssh sd102@l040101-ws03.ua.pt 'cd Restaurant ; unzip -uq dirStudent.zip'
echo "Executing program at the Student node."
sshpass -f password ssh sd102@l040101-ws03.ua.pt 'cd Restaurant/dirStudent ; java clientSide.main.ClientStudent l040101-ws04.ua.pt 22111 l040101-ws06.ua.pt 22112 l040101-ws07.ua.pt 22113'
echo "Student client shutdown."