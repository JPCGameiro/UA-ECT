from studentFunctions import *
import asyncio
import random
from itertools import permutations
import heapq

#Matrix of random numbers to implement Zobrist hashing
class Matrix:
    def __init__(self, maxx, maxy, n):
        self.matrix = [[[random.randint(0, 999) for x in range(maxx)] for y in range(maxy)] for b in range(n)]
    def __str__(self):
        return str(self.matrix) 

#State
class State:
    #Constructor
    def __init__(self, blist, kpos, path, matrix):
        self.blist = blist
        self.kpos = kpos
        self.path = path
        self.matrix = matrix
        #self.heuristic = 0
    def __str__(self):
        return "state(Boxes:" + str(self.blist) + ",Keeper:" + str(self.kpos) + ")"
    def __repr__(self):
        return str(self)
    #Zobrist Hashing 
    def __hash__(self):
        hash = 0
        for box in self.blist:
            hash ^= self.matrix.matrix[0][box[1]][box[0]]
        hash ^= self.matrix.matrix[1][self.kpos[1]][self.kpos[0]]
        return hash
    def __eq__(self, other):
        b = set(self.blist) - set(other.blist)
        k = set(self.kpos) - set(other.kpos)
        return list(b) == [] and list(k) == []

#Search Node
class NodeSolver:
    #Constructor
    def __init__(self, state, parent):
        self.state = state
        self.parent = parent
    def __str__(self):
        return "no(" + str(self.state) + "," + str(self.parent) + ")"
    def __repr__(self):
        return str(self)
    def __lt__(self, other):
        #A* Search
        return (self.state.heuristic + len(self.state.path)) < (other.state.heuristic + len(other.state.path))
        #Greedy Search
        #return (self.state.heuristic) < (other.state.heuristic)

#Search Tree
class SearchTreeSolver:
    #Constructor
    def __init__(self, goal_list, state, is_blocked, deadlock_list):
        root = NodeSolver(state, None)
        self.open_nodes = [root]
        self.goal_list = goal_list
        self.solution = None
        self.is_blocked = is_blocked
        self.deadlock_list = deadlock_list
        self.nodes = 0
    
    #Heuristic (sum of distances from boxes to nearest goal) using manhatan distance    
    def heuristic(self, node):
        blist = node.state.blist[:]
        glist = self.goal_list[:]

        boxIndexes = list(permutations(range(len(blist)), len(blist)))

        distList = []

        for l in boxIndexes:
            dst = 0
            for i in range(len(l)):
                dst+=(abs(glist[i][0]-blist[l[i]][0])+abs(glist[i][1]-blist[l[i]][1]))
            distList.append(dst)
        res = min(distList)

        empty_goals = 0
        for goal in glist:
            if goal not in blist:
                empty_goals += 1

        return (empty_goals+res)**16

    #Cost of a state (number of steps so far)
    def cost(self, node):
        return len(node.state.path)

    #Test if state is solution
    def test(self, state):
        for pos in self.goal_list:
            if (pos[0], pos[1]) not in set(state.blist):
                return False
        return True

    #get path from root until a node
    def get_path(self,node):
        if node.parent == None:
            return [node.state]
        path = self.get_path(node.parent)
        path += [node.state]
        return(path)                    

    #Expand the search tree
    def expand(self, nstate):
        res = []
        print("----------------------")
        for box in set(nstate.blist):
            print(box)
            for direction in set(['w', 's', 'a', 'd']):
                #Position must not be a deadlock unless it has an objective
                if( (not dead_lock(box, direction, self.is_blocked, nstate.blist, self.deadlock_list) and not has_obstacle(box, direction, self.is_blocked, nstate.blist))
                 or (in_goal(box, direction, self.goal_list) and not has_obstacle(box, direction, self.is_blocked, nstate.blist)) ):
                    #If keeper can find a path to move a box
                    path = move_box(box, direction, nstate.kpos, self.is_blocked, nstate.blist)
                    if path != None:
                        newpos = []
                        #New state is other boxes on the same postion + the new box position + new keeper position
                        if direction == 'w':
                            print('UP')
                            newpos = (box[0], box[1]-1)
                        elif direction == 's':
                            print('DOWN')
                            newpos = (box[0], box[1]+1)
                        elif direction == 'a':
                            print('LEFT')
                            newpos = (box[0]-1, box[1])
                        elif direction == 'd':
                            print('RIGHT')        
                            newpos = (box[0]+1, box[1])

                        aux = nstate.blist[:]
                        aux.remove(box)
                        aux.append(newpos)

                        res.append(State(aux, box, nstate.path+path, nstate.matrix))    
        return res

    #Search for the Solution
    async def search(self):
        visitedStates = set()
        while self.open_nodes != []:
            await asyncio.sleep(0)  
            node = heapq.heappop(self.open_nodes)

            #Solution found
            self.nodes += 1
            if self.test(node.state):
                print("SOLUTION FOUND!")
                self.solution = node
                return self.get_path(node)
            
            lnewnodes = []
            for newstate in self.expand(node.state):
                if newstate not in visitedStates:
                    if newstate not in set(self.get_path(node)):
                        visitedStates.add(newstate)
                        newnode = NodeSolver(newstate, node)
                        newnode.state.heuristic = self.heuristic(newnode)
                        heapq.heappush(self.open_nodes, newnode)
        return None