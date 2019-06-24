from PIL import Image
import sqlite3 as sql
import sys
import hashlib
import json

db = sql.connect(':memory:')

res = db.execute("create table Imagem(id integer primary key autoincrement, img text)")
res = db.execute("create table Objeto(id integer primary key autoincrement, img text, type text, color text, id_orig integer)");


def main(argv):
	id_img=1
	hash_img = codif_img(argv[1])
	image_entry(hash_img)
	object_entry(imagem, tipo, cor, id_original)
	read_image(id_img)
	read_object(tipo, cor)
	del_image(id_img)
	read_image(id_img)
	
	db.close()

#codifica as imagens
def codif_img(img_name):
	h = hashlib.md5()
	h.update(img_name.encode('utf-8'))
	return h.hexdigest()

#entrada na database das imagens originais	
def image_entry(hash_img):		
	res = db.execute("insert into Imagem (img) values (?) ", (hash_img,))

#entrada na database dos objetos
def object_entry(imagem, tipo, cor, id_og):
	res = db.execute("insert into Objeto (img, type, color, id_orig) values (?, ?, ?, ?) ", (imagem, tipo, cor, id_og))
	
#apaga da database as imagens
def del_image(id_og):
	res = db.execute("delete from Imagem where id like ?", (id_og,))
	
#devolve o nome da imagem pelo id	
def read_image(id_og):
	res = db.execute("select * from Imagem where id like ?", (id_og,))
	print(res.fetchone())
	return res.fetchone()
	
#devolve o nome da imagem/objeto pelas carateristicas tipo ou cor	
def read_object(search):
	res = db.execute("select img from Object where type like or color like", (search,)) 	
	return res.fetchall()
		
main(sys.argv)


