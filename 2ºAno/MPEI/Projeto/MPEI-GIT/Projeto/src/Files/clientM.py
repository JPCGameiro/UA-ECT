import sys, socket, csv, json, time, random, datetime
from Crypto.Cipher import PKCS1_OAEP
from Crypto.PublicKey import RSA

#verificaçao das condiçoes iniciais
if len(sys.argv)< 4:
	print("numero de argumentos invalido.\n")
	print("python3 client.py interval num [country or id]\n")
	quit()
	
try:
	interval = int(sys.argv[1])
except:
	print("intervalo deve ser do tipo inteiro positivo")
	quit()

try: 
	num = int(sys.argv[2])
except:
	print("numero de vezes deve ser do tipo inteiro positivo")
	quit()

if sys.argv[3].isdigit():
	try:
		id_serv = int(sys.argv[3])
		serv_in = True
	except:
		print('id deve ser do tipo inteiro positivo')
	
else:
	country = sys.argv[3]
	lista = []
	serv_in = False

tcp = "sem host"
	
#leitura dos servidores
f = open('servers.json','r')
data = json.load(f)	

if serv_in :	
	for serv in data['servers']:
		if(serv['id'] == id_serv):
			tcp = serv['host']
else:
	for serv in data['servers']:
		if serv['country'] == country:
			lista.append(serv['host'])
			id_serv = serv['id']
				
	if len(lista)!= 0:		
		tcp = lista[random.randint(0, len(lista)-1)]
	else:
		print("pais inexistente ou nenhum servidor encontrado")
		quit()
			
if tcp == "sem host":
	print("servidor nao encontrado")
	quit()
			
edr = tcp.split(':')

#cliente TCP
def test_speed(edr):
	
	tcp_s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	tcp_s.settimeout(1)
	try:
		tcp_s.connect( (str(edr[0]), int(edr[1])) )
	except:
		print("nao foi possivel connetar ao servidor %s" %(edr[0]))
		return [-1, 0]
		
	#Hi	
	str_data = "HI\n"
	b_data = str_data.encode("utf-8")
	tcp_s.send(b_data)
	b_data = tcp_s.recv(4096)
	str_data = b_data.decode("utf-8")
	
	print("Connectado com sucesso ao servidor {:s},\n {:s} ".format(edr[0], str_data))
	
	#Ping
	lat = 0
	for ping in range(10):
		p1 = int(time.time())
		str_data = "PING " + str(p1)+"\n"
		b_data = str_data.encode("utf-8")
		tcp_s.send(b_data)
			
		b_data = tcp_s.recv(4096)
		str_data = b_data.decode("utf-8")
		p2 = ""
		for number in str_data:
			if number.isdigit():
				p2 += number
		lat += int(p2) - p1
	lat = lat / 10	
	print("latencia : %f" %lat)
	
	#Download
	str_data = "DOWNLOAD 10485760\n"
	b_data = str_data.encode("utf-8")
	tcp_s.send(b_data)
		
	dt = 0
	tmp = 0
	tmp1 = time.time()
	while dt != 2560 and tmp <= 10 : 
		b_data = tcp_s.recv(4096)
		tmp2 = time.time()
		dt += 1
		tmp  = tmp2-tmp1
		if dt*4096 == 1048576:
			bnd =  1048576/ tmp
	print("banda larga : %f\n" %bnd)
	
	#Quit
	str_data = "QUIT\n"
	b_data = str_data.encode("utf-8")
	tcp_s.send(b_data)
	
	tcp_s.close()
	return [lat, bnd]



def main():
	
	
	fic = open('report.csv','w')
	writer = csv.DictWriter(fic, fieldnames = ['contador', 'id do servidor', 'data e hora no formato ISO', 'latência', 'largura de banda', 'check'])
	writer.writeheader()
	
	dia = datetime.datetime.now().isoformat()
	for i in range(1, num+1):
		dados = test_speed(edr)
		chk = str(i)+str(id_serv)+str(dia)+str(dados[0])+str(dados[1])
		writer.writerow({'contador': i, 'id do servidor': id_serv, 'data e hora no formato ISO': dia, 'latência': dados[0], 'largura de banda': dados[1], 'check': chk})
		time.sleep(interval)


					
main()


#ciptografia
key = RSA.generate(2048)

fic = open ("key.priv", "wb")
kp = key.exportKey("PEM","senha")
fic.write(kp)
fic.close()

fec = open("key.priv","rb")
keypair = RSA.importKey(fec.read(), "senha")
cipher = PKCS1_OAEP.new( keypair )
with open ("report.sig" , "wb") as file:
	with open("report.csv", "r") as fir:
		for line in fir.read():
			file.write(cipher.encrypt(line.encode("utf-8")))
