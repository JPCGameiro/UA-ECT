
from tree_search import *

class Cidades(SearchDomain):
    def __init__(self,connections, coordinates):
        self.connections = connections
        self.coordinates = coordinates
    def actions(self,city):
        actlist = []
        for (C1,C2,D) in self.connections:
            if (C1==city):
                actlist += [(C1,C2)]
            elif (C2==city):
               actlist += [(C2,C1)]
        return actlist 
    def result(self,city,action):
        (C1,C2) = action
        if C1==city:
            return C2
    def cost(self, city, action):
        if action[0]!=city:
            return None
        for (c1,c2,d) in self.connections:
            if (c1==action[0] and c2==action[1]) \
                    or (c2==action[0] and c1==action[1]):
                return d
        return None
    def heuristic(self, city, goal_city):
        if city not in self.coordinates:
            print(city)
        if goal_city not in self.coordinates:
            print(goal_city)
        (x1,y1) = self.coordinates[city]
        (x2,y2) = self.coordinates[goal_city]
        return math.hypot(x1-x2,y1-y2)
    def satisfies(self, city, goal_city):
        return goal_city==city

