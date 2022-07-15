echo "Executing the bar node."
cd /home/joao/Restaurant/dirBar
java -cp "../genclass.jar:." serverSide.main.ServerRestaurantBar 22111 127.0.0.1 22113 127.0.0.1 22112
echo "Bar Server shutdown."