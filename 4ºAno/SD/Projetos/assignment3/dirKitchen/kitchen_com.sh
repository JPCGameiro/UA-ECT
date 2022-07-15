CODEBASE="http://localhost/"$1"/classes/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=true\
     -Djava.security.policy=java.policy\
     serverSide.main.ServerRestaurantKitchen 22111 localhost 22110