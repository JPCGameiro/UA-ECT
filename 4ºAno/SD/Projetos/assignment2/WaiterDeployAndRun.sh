echo "Transfering data to the Waiter node."
sshpass -f password ssh sd102@l040101-ws02.ua.pt 'mkdir -p Restaurant'
sshpass -f password ssh sd102@l040101-ws02.ua.pt 'rm -rf Restaurant/*'
sshpass -f password scp dirWaiter.zip sd102@l040101-ws02.ua.pt:Restaurant
echo "Decompressing data sent to Waiter node."
sshpass -f password ssh sd102@l040101-ws02.ua.pt 'cd Restaurant ; unzip -uq dirWaiter.zip'
echo "Executing program at the Waiter node."
sshpass -f password ssh sd102@l040101-ws02.ua.pt 'cd Restaurant/dirWaiter ; java clientSide.main.ClientWaiter l040101-ws05.ua.pt 22110 l040101-ws04.ua.pt 22111 l040101-ws06.ua.pt 22112 l040101-ws07.ua.pt 22113'
echo "Waiter server shutdown."