CODEBASE="file:///home/"$1"/test/Restaurant/dirGeneralRepos/"
java -Djava.rmi.server.codebase=$CODEBASE\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     serverSide.main.ServerRestaurantGeneralRepos 22117 localhost 22110