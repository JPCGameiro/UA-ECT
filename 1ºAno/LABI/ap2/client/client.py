import sys, datetime, json, csv, socket, random, time
from Crypto.PublicKey import RSA
from Crypto.Cipher import PKCS1_OAEP

#verificação das condições iniciais
if len(sys.argv) <= 3:
	print("ERROR in entry arguments")
	print("Usage method: python3 client.py interval num [country or id]")
	sys.exit()
	
	
#intervalo ocorrido entre dois testes a realizar	
try: 
	interval = int(sys.argv[1])
	if(interval <= 0):
		sys.exit()
except:
	print("ERROR : interval must be int and positive")
	sys.exit()

#número de testes realizados
try: 
	num = int(sys.argv[2])
	if(num <= 0):
		sys.exit()
except:
	print("ERROR : num (number of tests) must be positive")
	sys.exit()
	
#pais ou id
try:
	if(sys.argv[3].isdigit()):
		id_s = sys.argv[3]
		x = 1
	else:
		country_s = sys.argv[3]
		x = 0 
except:
	print("ERROR : id must be int and country must be string")
	sys.exit()


#Abertura do ficheiro servers.json para verificar o país ou o ip
fich = open('servers.json', 'r')
info = json.load(fich)
l = []
i = []


#Obter o host a partir do id
if x==1:
	for serv in info['servers']:
		if(int(id_s) == serv['id']):
			break
		
	final_id = serv['id']
			
	l0 = []
	l0 = (serv['host'].split(':'))
	b_data = l0[0]
	addr = l0[1]
#obter o host a partir do country	
else:
	for serv in info['servers']:
		if(country_s == serv['country']):
			l.append(serv['host'])
			i.append(serv['id'])
			
	if(len(l) == 0):
		print("ERROR: Country does not exist")
		sys.exit()
		
	l1 = []
	l1 = (random.choice(l).split(':')) 
	b_data = l1[0]
	addr = l1[1]
	
	for n in range(0, len(l)):
		if(str(l1) == l[n]):
			break
			
	final_id = i[n]
		

	
	
#escrita dos resultados num ficheiro csv
fich = open("report.csv", "w")
writer = csv.DictWriter(fich, fieldnames=['contador' , 'id do servidor', 'data e hora no formato ISO', 'latência', 'largura de banda', 'check'])
writer.writeheader()	

for f in range(0 , num):
	print("Teste " + str(f+1) + "\n") 

	#Strings para comunicação com o servidor
	hello = "HI\n"
	ping  = str("PING " + str(time.time()) + "\n")
	download = "DOWNLOAD "
	quits = "QUIT\n" 

				
	#criação do socket que irá possibilitar a comunicação com o servidor
	try:		
		tcp_s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		tcp_s.connect((str(b_data), int(addr)))
		print("Connected to : " + str(b_data) + " " + str(addr))
		inicial_time = time.time()   #registo do tempo a partir do momento em que é aberto o servidor
	except:
		print("ERROR: Connection Failed")
		largura = 0
		latencia = -1

	 
	 #HI\n
	tcp_s.send(hello.encode())
	resulthello = tcp_s.recv(4096)
	print("HI")
	print(str(resulthello))
	
	#PING
	#criação de uma lista para armazenar o resultado dos pongs
	listpong = []
	#obtenção dos pongs
	for z in range(0 , 10):
		tcp_s.send(ping.encode())
		resultping = tcp_s.recv(4096)
		pong = str(resultping).split(' ')
		pong0 = (pong[1])
		listpong.append(pong0[0 : len(pong0)-3])
	z=1
	for i in range(0 , len(listpong)):
		print(("PONG "+str(listpong[i]))+" "+str(z))
		z = z + 1

	#DOWNLOAD
	down_time = time.time()
	if down_time - inicial_time < 10:   #até 10 segundos	
		numd = input("DOWNLOAD ")                           #o cliente insere o valor pretendido 
		assert (int(numd) >= 10 and int(numd) <= 100), "ERROR: DOWNLOAD belongs to [10, 100]" 
		download = str(download + str(int(numd)) + "\n")   
		begin_download = time.time()
		tcp_s.send(download.encode())                      # é envidado para o servidor
		resultdownload = tcp_s.recv(4096)          
		end_download = time.time()                          #regista o fim do download
		print(resultdownload)	
	
	#QUIT			
	print(quits)	
	tcp_s.send(quits.encode())
	tcp_s.close()

	#cálculo da latência
	soma = 0
	for z in range(0 ,len(listpong)):
		soma = soma + float(listpong[z])
	latencia = soma/len(listpong)
	print("Latência : " + str(latencia))
		

	#cálculo da largura de banda
	largura = (float(numd) / (end_download - begin_download))
	print("Largura de Banda : " + str(largura) + "\n")
	
	
	writer.writerow({'contador' : "Teste " + str(f+1)})
	writer.writerow({'id do servidor' : final_id})
	datetime_object = datetime.datetime.now()
	writer.writerow({'data e hora no formato ISO' : datetime_object})
	writer.writerow({'latência' : latencia})
	writer.writerow({'largura de banda' : largura})
	writer.writerow({'check' : "Teste " + str(f+1) + str(str(serv['id']) + str(datetime_object) + str(latencia) +  str(largura))})
	if f!=(num-1):
		time.sleep(interval)     #esperar até ao próximo teste interval secs
	
fich.close()

#Criação de chaves RSA e Leitura do ficheiro que as contem key.priv
key = RSA.generate(1024)
filef = open("key.priv", "wb")
k = key.exportKey("PEM", "senha")
filef.write(k)
filef.close()

filefi = open("key.priv", "rb")
k = RSA.importKey(filefi.read(), "senha")
filefi.close()

#Ficheiro report.sig com assinatura da chave
cipher = PKCS1_OAEP.new( k )
with open("report.sig", "wb") as file:
	with open("report.csv", "r") as ori:
		for line in ori.read():
			file.write(cipher.encrypt(line.encode("utf-8"))) 



	

