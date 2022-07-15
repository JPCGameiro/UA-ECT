echo "Compiling source code."
javac -source 8 -target 8 -cp ../../../SD/genclass.jar */*.java */*/*.java

echo "Distributing intermediate code to the different execution environments."


echo "  RMI registry"
rm -rf dirRMIRegistry/interfaces
mkdir -p dirRMIRegistry/interfaces
cp interfaces/*.class dirRMIRegistry/interfaces


echo "  Register Remote Objects"
rm -rf dirRegistry/serverSide dirRegistry/interfaces
mkdir -p dirRegistry/serverSide dirRegistry/serverSide/main dirRegistry/serverSide/objects dirRegistry/interfaces
cp serverSide/main/ServerRegisterRemoteObject.class dirRegistry/serverSide/main
cp serverSide/objects/RegisterRemoteObject.class dirRegistry/serverSide/objects
cp interfaces/Register.class dirRegistry/interfaces


echo "  General Repository of Information"
rm -rf dirGeneralRepos/serverSide dirGeneralRepos/clientSide dirGeneralRepos/interfaces
mkdir -p dirGeneralRepos/serverSide dirGeneralRepos/serverSide/main dirGeneralRepos/serverSide/objects dirGeneralRepos/interfaces \
         dirGeneralRepos/clientSide dirGeneralRepos/clientSide/entities
cp serverSide/main/ExecuteConst.class serverSide/main/ServerRestaurantGeneralRepos.class dirGeneralRepos/serverSide/main
cp serverSide/objects/GeneralRepos.class dirGeneralRepos/serverSide/objects
cp interfaces/Register.class interfaces/GeneralReposInterface.class dirGeneralRepos/interfaces
cp clientSide/entities/ChefStates.class clientSide/entities/WaiterStates.class clientSide/entities/StudentStates.class dirGeneralRepos/clientSide/entities


echo "  Kitchen"
rm -rf dirKitchen/serverSide dirKitchen/clientSide dirKitchen/interfaces dirKitchen/commInfra
mkdir -p dirKitchen/serverSide dirKitchen/serverSide/main dirKitchen/serverSide/objects dirKitchen/interfaces \
         dirKitchen/clientSide dirKitchen/clientSide/entities dirKitchen/commInfra
cp serverSide/main/ExecuteConst.class serverSide/main/ServerRestaurantKitchen.class dirKitchen/serverSide/main
cp serverSide/objects/Kitchen.class dirKitchen/serverSide/objects
cp interfaces/*.class dirKitchen/interfaces
cp clientSide/entities/ChefStates.class clientSide/entities/WaiterStates.class dirKitchen/clientSide/entities
cp commInfra/*.class dirKitchen/commInfra


echo "  Bar"
rm -rf dirBar/serverSide dirBar/clientSide dirBar/interfaces dirBar/commInfra
mkdir -p dirBar/serverSide dirBar/serverSide/main dirBar/serverSide/objects dirBar/interfaces \
         dirBar/clientSide dirBar/clientSide/entities dirBar/commInfra
cp serverSide/main/ExecuteConst.class serverSide/main/ServerRestaurantBar.class dirBar/serverSide/main
cp serverSide/objects/Bar.class dirBar/serverSide/objects
cp interfaces/*.class dirBar/interfaces
cp clientSide/entities/ChefStates.class clientSide/entities/WaiterStates.class clientSide/entities/StudentStates.class dirBar/clientSide/entities
cp commInfra/*.class dirBar/commInfra


echo "  Table"
rm -rf dirTable/serverSide dirTable/clientSide dirTable/interfaces dirTable/commInfra
mkdir -p dirTable/serverSide dirTable/serverSide/main dirTable/serverSide/objects dirTable/interfaces \
         dirTable/clientSide dirTable/clientSide/entities dirTable/commInfra
cp serverSide/main/ExecuteConst.class serverSide/main/ServerRestaurantTable.class dirTable/serverSide/main
cp serverSide/objects/Table.class dirTable/serverSide/objects
cp interfaces/*.class dirTable/interfaces
cp clientSide/entities/WaiterStates.class clientSide/entities/StudentStates.class dirTable/clientSide/entities
cp commInfra/*.class dirTable/commInfra


echo "  Chef"
rm -rf dirChef/serverSide dirChef/clientSide dirChef/interfaces
mkdir -p dirChef/serverSide dirChef/serverSide/main dirChef/clientSide dirChef/clientSide/main dirChef/clientSide/entities \
         dirChef/interfaces
cp serverSide/main/ExecuteConst.class dirChef/serverSide/main
cp clientSide/main/ClientRestaurantChef.class dirChef/clientSide/main
cp clientSide/entities/Chef.class clientSide/entities/ChefStates.class dirChef/clientSide/entities
cp interfaces/KitchenInterface.class interfaces/BarInterface.class interfaces/GeneralReposInterface.class dirChef/interfaces


echo "  Waiter"
rm -rf dirWaiter/serverSide dirWaiter/clientSide dirWaiter/interfaces
mkdir -p dirWaiter/serverSide dirWaiter/serverSide/main dirWaiter/clientSide dirWaiter/clientSide/main dirWaiter/clientSide/entities \
         dirWaiter/interfaces
cp serverSide/main/ExecuteConst.class dirWaiter/serverSide/main
cp clientSide/main/ClientRestaurantWaiter.class dirWaiter/clientSide/main
cp clientSide/entities/Waiter.class clientSide/entities/WaiterStates.class dirWaiter/clientSide/entities
cp interfaces/KitchenInterface.class interfaces/BarInterface.class interfaces/TableInterface.class interfaces/GeneralReposInterface.class interfaces/ReturnBoolean.class dirWaiter/interfaces


echo "  Student"
rm -rf dirStudent/serverSide dirStudent/clientSide dirStudent/interfaces
mkdir -p dirStudent/serverSide dirStudent/serverSide/main dirStudent/clientSide dirStudent/clientSide/main dirStudent/clientSide/entities \
         dirStudent/interfaces
cp serverSide/main/ExecuteConst.class dirStudent/serverSide/main
cp clientSide/main/ClientRestaurantStudent.class dirStudent/clientSide/main
cp clientSide/entities/Student.class clientSide/entities/StudentStates.class dirStudent/clientSide/entities
cp interfaces/BarInterface.class interfaces/TableInterface.class interfaces/GeneralReposInterface.class interfaces/ReturnBoolean.class dirStudent/interfaces


echo "Compressing execution environments."
echo "  RMI registry"
rm -f  dirRMIRegistry.zip
zip -rq dirRMIRegistry.zip dirRMIRegistry
echo "  Register Remote Objects"
rm -f  dirRegistry.zip
zip -rq dirRegistry.zip dirRegistry
echo "  General Repository of Information"
rm -f dirGeneralRepos.zip
zip -rq dirGeneralRepos.zip dirGeneralRepos
echo "  Kitchen"
rm -f dirKitchen.zip
zip -rq dirKitchen.zip dirKitchen
echo "  Bar"
rm -f dirBar.zip
zip -rq dirBar.zip dirBar
echo "  Table"
rm -f dirTable.zip
zip -rq dirTable.zip dirTable
echo "  Chef"
rm -f dirChef.zip
zip -rq dirChef.zip dirChef
echo "  Waiter"
rm -f dirWaiter.zip
zip -rq dirWaiter.zip dirWaiter
echo "  Student"
rm -f dirStudent.zip
zip -rq dirStudent.zip dirStudent