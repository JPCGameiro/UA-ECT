import cherrypy
import os
import sqlite3 as sql
import json
import hashlib
import requests


class HelloWorld(object):
	@cherrypy.expose
	def index(self):
		# Método serve_file tb poderia ser utilizado
		f = open("index.html")
		data = f.read()
		f.close()
		return data
		
	@cherrypy.expose
	def upload(self, myFile):
		fo = open(os.getcwd()+'/uploads/'+ myFile.filename,'wb')
		while True:
			data = myFile.file.read(8192)
			if not data:
				break
			fo.write(data)
		fo.close()

	@cherrypy.expose
	def list(self, n_img):
	session = requests.Session(n_img)
	URL="http://image-dnn-sgh-jpbarraca.ws.atnog.av.it.pt/process"
	with open(n_img, 'rb') as f:
		file = {'img': f.read()}
		r = session.post(url=URL, files=file, data=dict(thr=0.5))
	if r.status_code == 200:
		print(r.json())
		
	
	
		

cherrypy.server.socket_port = 8080
cherrypy.server.socket_host = "0.0.0.0"
cherrypy.tree.mount(HelloWorld(),"/","app.config")
cherrypy.engine.start()
cherrypy.engine.block()
