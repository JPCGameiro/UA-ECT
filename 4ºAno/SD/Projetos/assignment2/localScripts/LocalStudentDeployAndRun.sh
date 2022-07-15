echo "Transfering data to the Student node."
cd /home/joao/Restaurant/dirStudent
java -cp "../genclass.jar:."  clientSide.main.ClientStudent 127.0.0.1 22111 127.0.0.1 22112 127.0.0.1 22113
echo "Student server shutdown"