echo "Executing the GeneraRepos node."
cd /home/joao/Restaurant/dirGeneralRepos
java -cp "../genclass.jar:."  serverSide.main.ServerRestaurantGeneralRepos 22113
echo "GeneralRepos Server shutdown."