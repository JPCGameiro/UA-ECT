#!python
# Example of a cherrypy application that serves static content,
# as well as dynamic content.
#
# JMR@ua.pt 2016
#
# To run:
#        python exampleApp.py
from PIL import Image
import sqlite3 as sql
import cherrypy, requests, json, os.path, sys, hashlib

class Root():	
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
	
	@cherrypy.expose
	def put(self):
		return open('html/page3.html', 'r')
	
	@cherrypy.expose
	def dynamic4(self):
		return open('html/page4.html', 'r')
	
	@cherrypy.expose
	def dynamic5(self):
		return open('html/page5.html', 'r')
		
	
cherrypy.quickstart(Root(), "/", config = conf)


