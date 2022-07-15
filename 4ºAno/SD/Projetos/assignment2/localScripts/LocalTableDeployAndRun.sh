echo "Executing the table node."
cd /home/joao/Restaurant/dirTable
java -cp "../genclass.jar:."  serverSide.main.ServerRestaurantTable 22112 127.0.0.1 22113
echo "Table Server shutdown."