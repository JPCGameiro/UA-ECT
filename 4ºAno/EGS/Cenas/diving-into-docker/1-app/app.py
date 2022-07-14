import cherrypy

class App(object):
    @cherrypy.expose
    def index(self):
        return "Diving into docker"

cherrypy.server.socket_host = '0.0.0.0'
cherrypy.quickstart(App())
