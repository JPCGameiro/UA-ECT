CODEBASE="file:///home/"$1"/test/Restaurant/dirBar/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     serverSide.main.ServerRestaurantBar 22112 localhost 22110