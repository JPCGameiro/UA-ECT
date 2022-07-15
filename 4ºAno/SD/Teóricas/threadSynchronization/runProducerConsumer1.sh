for i in $(seq 1 100)
do
     echo -e "\nRun n.º " $i
     java -cp .:../../Recursos/genclass.jar producerConsumer.ProducerConsumer1.ProducerConsumer
done
