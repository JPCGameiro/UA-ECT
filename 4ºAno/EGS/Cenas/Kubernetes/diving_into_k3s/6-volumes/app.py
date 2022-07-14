import cherrypy
import random

appid=random.randint(1,100000000)

class App(object):
    @cherrypy.expose
    def index(self):
        return f'Diving into k3s - {appid} <br/><img src="/static/img.gif"></img>'

cherrypy.server.socket_host = '0.0.0.0'
cherrypy.quickstart(App())
