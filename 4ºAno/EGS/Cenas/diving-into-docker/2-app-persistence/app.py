import cherrypy
import os

class App(object):
    @cherrypy.expose
    def index(self):
        return "<html><body><h2>Diving into docker</h2><img src=\"/static/img.gif\" alt=\"static image\"></img></body></html>"

cherrypy.server.socket_host = '0.0.0.0'

# Extra conf to add a static dir
conf = {'/static': {'tools.staticdir.on': True,
                  'tools.staticdir.dir': os.path.join(os.getcwd(), 'www'),
	}}

cherrypy.quickstart(App(), config=conf)
