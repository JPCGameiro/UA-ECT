CODEBASE="file:///home/"$1"/test/Restaurant/dirChef/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     clientSide.main.ClientRestaurantChef localhost 22110