for i in $(seq 1 10000)
do
echo -e "\nRun n.o " $i
	java -cp .:../../../SD/genclass.jar main.Restaurant
done

