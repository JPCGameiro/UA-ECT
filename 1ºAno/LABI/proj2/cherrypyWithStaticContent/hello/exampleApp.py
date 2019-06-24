#
#
# To run:
#        python exampleApp.py
from PIL import Image
import sqlite3 as sql
import cherrypy, requests, json, hashlib
import os.path, sys


# The absolute path to this file's base directory:
baseDir = os.path.dirname(os.path.abspath(__file__))

#Dicionário com a configuração da aplicação
conf = {
  "/":     { "tools.staticdir.root": baseDir },
  "/js":   { "tools.staticdir.on": True,
             "tools.staticdir.dir": "js" },
  "/css":  { "tools.staticdir.on": True,
             "tools.staticdir.dir": "css" },
  "/html": { "tools.staticdir.on": True,
             "tools.staticdir.dir": "html" },
}


#base de dados e funçoes associadas
db = sql.connect('database.db', check_same_thread=False)
res = db.execute("CREATE TABLE IF NOT EXISTS Imagem(id INTEGER PRIMARY KEY AUTOINCREMENT, img TEXT)")
res = db.execute("CREATE TABLE IF NOT EXISTS Objeto(id INTEGER PRIMARY KEY AUTOINCREMENT, img TEXT, type TEXT, color TEXT, confianca TEXT, id_orig INTEGER)");

#inserir uma imagem na tabela Imagem
def image_entry(name_img):
	h_img = codif_img(name_img)
	res = db.execute("INSERT INTO Imagem (img) VALUES (?) ", (h_img,))
	db.commit()
	
#entrada na database dos objetos(recote da imagem original)
def object_entry(imagem, tipo, cor, conf, id_og):
	h_img = codif_img(imagem)
	res = db.execute("INSERT INTO Objeto (img, type, color, confianca, id_orig) VALUES (?, ?, ?, ?, ?) ", (h_img, tipo, cor, conf, id_og))
	db.commit()

#devolve o id da imagem pelo nome
def get_img_id(name_img):
	h_img = codif_img(name_img)
	res = db.execute("SELECT id FROM Imagem WHERE img LIKE ?", (h_img,))
	return res.fetchone()

#devolve o nome da imagem original pelo id
def get_id_im(id_im):
	res = db.execute("SELECT img FROM Imagem WHERE id LIKE ?", (id_im,))
	return res.fetchone()
	
#devolve o nome da imagem/objeto pelo tipo de objeto	
def read_object(search):
	res = db.execute("SELECT img FROM Objeto WHERE type LIKE ?", (search,)) 	
	return res.fetchall()

#devolve o nome da imagem/objeto pelas caratecristicas (tipo e cor)
def read_obj_col(obj, col):
	res = db.execute("SELECT img FROM Objeto WHERE type LIKE ? and color LIKE ?", (obj, col,))
	return res.fetchall()
	
#devolve todos os tipos de objetos detetados
def return_obj():
	res = db.execute("SELECT type FROM Objeto")
	return res.fetchall()

#sinteze do nome das imagens 
def codif_img(img_name):
	h = hashlib.md5()
	with open(img_name, 'rb') as f:
		buf = f.read()
		h.update(buf)
	return str(h.hexdigest())



#função para verificar se um valor (x,y) pertence a uma caixa formada por (x1,y1) e (x2,y2)
def belongs_box(x, y, x1, y1, x2, y2):
	
	if((int(x)>=int(x1) and int(x)<=int(x2)) and (int(y)>=int(y1) and int(y<=int(y2)))):
		return True
	else:
		return False
		
	
#função para extrair os objetos das imagens
def object_extraction(fname, x1, y1, x2, y2):
	im = Image.open(fname)
	
	width, height = im.size	
	new_im = Image.new(im.mode, im.size)
	
	for x in range(width):
		for y in range(height):
			if(belongs_box(x, y, x1, y1, x2, y2) == True):       #verificar se o pixel (x,y) pertence à caixa
				p = im.getpixel( (x,y) )
				new_im.putpixel( (x,y), (p) )
	i=0
	name = str(fname) + str(i) + ".jpg"
	while(os.path.exists(name)):
		i=i+1
		name = str(fname) + str(i) + ".jpg"
	new_im.save(name)
	return(name)	



#Função que deteta a cor de um objeto (sujeita  alterações nos intervalos de deteção de cor) ainda não funciona perfeitamente
def color_detetion(iname):
	im = Image.open(iname)
	
	width, height = im.size
	#Contadores para calcular o número de pixeis de uma certa cor
	redcounter = 0
	bluecounter = 0
	greencounter = 0
	pinkcounter = 0
	orangecounter = 0
	yellowcounter = 0
	purplecounter = 0
	blackcounter = 0
	whitecounter = 0
	#Cálculo da cor
	for x in range(width):
		for y in range(height):
			p = im.getpixel( (x,y) )         
			r = p[0] & 0b11110000
			g = p[1] & 0b11110000
			b = p[2] & 0b11110000
			if(r<40 and b<40 and g<40 and r!=0 and b!=0 and g!=0):
				blackcounter = blackcounter + 1
			elif(r>220 and b>220 and g>200):
				whitecounter = whitecounter + 1 
			else:
				if(r>=g and r>b):
					if(g>b):
						if(g>=180):
							yellowcounter = yellowcounter+1
						elif(g>100 and g<170):
							orangecounter = orangecounter+1
						else:
							redcounter = redcounter+1
					elif(b>g):
						if(b>=120):
							pinkcounter = pinkcounter+1 
				elif(g>=r and g>b):
					greencounter = greencounter+1
				elif(b>=r and b>g):
					if(r>210):
						pinkcounter = pinkcounter+1
					elif(r>=100 and r<209):
						purplecounter = purplecounter+1
					else:
						bluecounter = bluecounter+1					
				
				
	finalcolor = max(redcounter, greencounter, bluecounter, yellowcounter, purplecounter, pinkcounter, orangecounter)
	
	if finalcolor == redcounter:
		return("red")
	elif finalcolor == greencounter:
		return("green")
	elif finalcolor == bluecounter:
		return("blue")
	elif finalcolor == orangecounter:
		return("orange")
	elif finalcolor == purplecounter:
		return("purple")
	elif finalcolor == pinkcounter:
		return("pink")
	elif finalcolor == whitecounter:
		return("white")
	elif finalcolor == blackcounter:
		return("black")
	else:
		return("yellow")




#Lista onde são armazenados os resultados das deteções
lstimn = []            #nomes das imgens originais
lstobj = []            #nomes dos objetos extraídos
lstbox = []            #coordenadas onde estão contidos os objetos de cada imagem
lstcfn = []            #confiança
lstcol = []            #cores dominantes
lstimo = []            #lista com o nome das imagens com os objetos extraídos
lstcod = []            #lista com os nomes das imagens originais mas sintetizados
cnt1    = 0


class Root(object):
	#interligação de todas as 6 páginas
	@cherrypy.expose
	def index(self):
		return open('html/main.html', 'r')				
	
	@cherrypy.expose
	def dynamic1(self):
		return open('html/page.html', 'r')
	
	@cherrypy.expose
	def dynamic2(self):
		return open('html/page2.html', 'r')
	
	#Função que permite enviar uma nova imagem para o sistema para ser processada e analizada
	@cherrypy.expose
	def put(self):
		return open('html/page3.html', 'r')		
		
	#função list que por enquanto serve para armazenamento principal da imformação (sujeita a alterações)	
	@cherrypy.expose		
	def list(self, names=None, name=None, nome=None ,color=None, detected=None):
		
		#Devolver um json com os objetos detetados
		if(names!=None):
			session = requests.Session()
			URL="http://image-dnn-sgh-jpbarraca.ws.atnog.av.it.pt/process"
			with open(names, 'rb') as f:
				file = {'img': f.read()}
			r = session.post(url=URL, files=file, data=dict(thr=0.5))
			if r.status_code == 200:
				s = r.json()
			
			#adição à base de dados das imagens com objetos extraídos e da cor detetada
			if len(s):
				image_entry(names)

				for i in range(0, len(s)):
					name_obj = object_extraction(names, s[i]['box']['x'], s[i]['box']['y'], s[i]['box']['x1'], s[i]['box']['y1'])
					lstimo.append(name_obj)
					lstcol.append(color_detetion(name_obj))
					object_entry(name_obj, s[i]['class'],  color_detetion(name_obj), str(s[i]['confidence']), get_img_id(str(names))[0])

			
			#adicionar elementos à lista dos objetos
			for i in range(0, len(s)):
				try:
					lstobj.append(str(s[i]['class']))
					lstimn.append(names)                   #adiciona o nome da imagem à lista dos nomes das imagens
					lstcod.append(codif_img(names))
				except:
					break		
					
			#adicionar elementos à lista das boxes no formato x,y,x1,x2
			for i in range(0, len(s)):
				try:
					lstbox.append(str(s[i]['box']['x']) + "," + str(s[i]['box']['y']) + "," + str(s[i]['box']['x1']) + "," + str(s[i]['box']['y1']))
				except:
					break	
					
			#adicionar elementos à lista da confiança
			for i in range(0, len(s)):
				try:
					lstcfn.append(str(s[i]['confidence']))
				except:
					break
					
			#Devolve os objetos detetados
			try:
				jsono = '['
				for obj in return_obj():
					jsono = jsono+'{"Objeto": "'+str(obj)+'"},'
				jsono = jsono[0:(len(jsono)-1)] + ']'
				jsono0 = json.loads(jsono)
				cherrypy.response.headers["Content-Type"] = "application/json"
				return json.dumps(jsono0).encode('utf-8')
			except:
				return "ERRO: Nenhum objeto detetado"			
				
				
		#Devolve um json de acordo com o objeto especificado
		elif(name!=None):
			try:
				jsono = '['
				for obj in read_object(name):
					jsono = jsono+'{"image": "'+str(obj)+'"},'
				jsono = jsono[0:(len(jsono)-1)] + ']'
				jsono0 = json.loads(jsono)
				cherrypy.response.headers["Content-Type"] = "application/json"
				return json.dumps(jsono0).encode('utf-8')
			except:
				return "ERRO: Nenhum objeto Encontrado"
		
		
		#Devolve um json de acordo com o objeto e com a cor
		elif(nome!=None and color!=None):
			try:
				jsono = '['
				for obj in read_obj_col(nome, color):
					jsono = jsono+'{"image": "'+str(obj)+'"},'
				jsono = jsono[0:(len(jsono)-1)] + ']'
				jsono0 = json.loads(jsono)
				cherrypy.response.headers["Content-Type"] = "application/json"
				return json.dumps(jsono0).encode('utf-8')
			except:
				return "ERRO: Nenhum objeto encontrado"
				
		#Devolver todos os objetos detetados e outras imformações
		else:
			try:
				jsono = ''
				for i in range(0, len(lstimn)):
					jsono = jsono+'[{"Objeto": "'+str(lstobj[i])+'", "original": "'+str(codif_img(lstimn[i]))+'", "imagem": "'+str(codif_img(lstimo[i]))+'", "confianca": "'+ str(lstcfn[i])+'"}],'
				jsono = jsono[0:len(jsono)-1]
				jsono0 = json.loads(jsono)
				cherrypy.response.headers["Content-Type"] = "application/json"
				return json.dumps(jsono0).encode('utf-8')
			except:
				return "ERRO"
			
		
		
	
	@cherrypy.expose
	def upload(self, myFile):
		fo = open(os.getcwd()+'/imagem/'+ myFile.filename,'wb')
		while True:
			data = myFile.file.read(8192)
			if not data:
				break
			fo.write(data)
		#myFile.filename = codif_img(myFile.filename)	
		fo.close()
				
	#interligação das restantes páginas
	@cherrypy.expose
	def dynamic4(self):
		return open('html/page4.html', 'r')
	
	@cherrypy.expose
	def dynamic5(self):
		return open('html/page5.html', 'r')

cherrypy.server.socket_port = 10006
cherrypy.config.update({'server.socket_port': 10006,})
cherrypy.server.socket_host = "0.0.0.0"
cherrypy.tree.mount(Root(), "/", conf)
cherrypy.engine.start()
cherrypy.engine.block()

