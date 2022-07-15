echo "Transfering data to the Student node."
sshpass -f password ssh sd102@l040101-ws07.ua.pt 'mkdir -p test/Restaurant'
sshpass -f password ssh sd102@l040101-ws07.ua.pt 'rm -rf test/Restaurant/*'
sshpass -f password scp dirStudent.zip sd102@l040101-ws07.ua.pt:test/Restaurant
echo "Decompressing data sent to Student node."
sshpass -f password ssh sd102@l040101-ws07.ua.pt 'cd test/Restaurant ; unzip -uq dirStudent.zip'
echo "Executing program at the Student node."
sshpass -f password ssh sd102@l040101-ws07.ua.pt 'cd test/Restaurant/dirStudent ; ./student_com_d.sh'
echo "Student client shutdown."