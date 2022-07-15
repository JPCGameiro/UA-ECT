xterm  -T "General Repository" -hold -e "./LocalGeneralReposDeployAndRun.sh" &
xterm  -T "Table" -hold -e "./LocalTableDeployAndRun.sh" &
xterm  -T "Kitchen" -hold -e "./LocalKitchenDeployAndRun.sh" &
xterm  -T "Bar" -hold -e "./LocalBarDeployAndRun.sh" &
sleep 1
xterm  -T "Chef" -hold -e "./LocalChefDeployAndRun.sh" &
xterm  -T "Waiter" -hold -e "./LocalWaiterDeployAndRun.sh" &
xterm  -T "Student" -hold -e "./LocalStudentDeployAndRun.sh" &