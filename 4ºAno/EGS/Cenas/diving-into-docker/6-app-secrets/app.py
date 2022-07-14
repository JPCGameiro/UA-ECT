import cherrypy
import os
import uuid

PROC_ID=uuid.uuid4()

class App(object):
    @cherrypy.expose
    def index(self):
        return f"[{PROC_ID}] Diving into docker"

cherrypy.server.socket_host = '0.0.0.0'

conf = {'/static': {'tools.staticdir.on': True,
                  'tools.staticdir.dir': os.path.join(os.getcwd(), 'www'),
	}}

cherrypy.quickstart(App(), config=conf)
