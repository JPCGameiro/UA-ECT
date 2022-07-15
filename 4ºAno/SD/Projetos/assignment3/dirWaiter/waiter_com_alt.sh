CODEBASE="file:///home/"$1"/test/Restaurant/dirWaiter/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     clientSide.main.ClientRestaurantWaiter localhost 22110