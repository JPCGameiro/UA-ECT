echo "Transfering data to the Chef node."
cd /home/joao/Restaurant/dirChef
java -cp "../genclass.jar:." clientSide.main.ClientChef 127.0.0.1 22110 127.0.0.1 22111 127.0.0.1 22113
echo "Chef Server shutdown"