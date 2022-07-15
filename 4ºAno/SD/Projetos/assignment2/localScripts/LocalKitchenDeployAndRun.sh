echo "Executing the kitchen node."
cd /home/joao/Restaurant/dirKitchen
java -cp "../genclass.jar:."  serverSide.main.ServerRestaurantKitchen 22110 127.0.0.1 22113
echo "Kitchen Server shutdown."