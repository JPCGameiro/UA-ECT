echo "Transfering data to the Waiter node."
cd /home/joao/Restaurant/dirWaiter
java -cp "../genclass.jar:." clientSide.main.ClientWaiter 127.0.0.1 22110 127.0.0.1 22111 127.0.0.1 22112 127.0.0.1 22113
echo "Waiter Server shutdown"