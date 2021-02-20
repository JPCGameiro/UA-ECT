import math

#Search Node
class Node:
    #Constructor
    def __init__(self, state, parent, cost):
        self.state = state
        self.parent = parent
        self.cost = cost
    def __str__(self):
        return "no(" + str(self.state) + "," + str(self.parent) + ")"
    def __repr__(self):
        return str(self)  
    
#Search Tree
class SearchTree:
    #Constructor
    def __init__(self, strategy, goal, initial, is_blocked, boxlist):
        root = Node(initial, None, 0)
        self.strategy = strategy
        self.open_nodes = [root]
        self.goal = goal
        self.solution = None
        self.is_blocked = is_blocked
        self.boxlist = boxlist
    
    #Estimated cost from one state to goal
    def heuristic(self, state):
            return abs(state.state[0] - self.goal[0]) + abs(state.state[1] - self.goal[1])
    
    #get path from root until a node
    def get_path(self,node):
        if node.parent == None:
            return [node.state]
        path = self.get_path(node.parent)
        path += [node.state]
        return(path) 

    #Add new nodes to the open_nodes list according to the strategy
    def add_to_open(self,lnewnodes):
        if self.strategy == 'breadth':
            self.open_nodes.extend(lnewnodes)
        elif self.strategy == 'depth':
            self.open_nodes[:0] = lnewnodes
        elif self.strategy == 'greedy':
            self.open_nodes += lnewnodes
            self.open_nodes.sort(key=(lambda x: self.heuristic(x)))
        elif self.strategy == 'a*':
            self.open_nodes += lnewnodes
            self.open_nodes.sort(key=(lambda x: x.cost + self.heuristic(x)))
    
    #Expand the search tree
    def expand(self, nstate):
        res = []
        
        pw = (nstate[0], nstate[1]-1)
        ps = (nstate[0], nstate[1]+1)
        pd = (nstate[0]+1, nstate[1])
        pa = (nstate[0]-1, nstate[1])

        if not self.is_blocked(pd) and pd not in set(self.boxlist):
            res.append(pd)
        if not self.is_blocked(pa) and pa not in set(self.boxlist):
            res.append(pa)
        if not self.is_blocked(pw) and pw not in set(self.boxlist):
            res.append(pw)
        if not self.is_blocked(ps) and ps not in set(self.boxlist):
            res.append(ps)        
        return res

    #Search for the Solution
    def search(self):
        visitedStates = set()
        while self.open_nodes != []:          
            node = self.open_nodes.pop(0)

            #Solution found
            if self.goal == node.state:
                self.solution = node
                return self.get_path(node)
            
            lnewnodes = []
            for newstate in self.expand(node.state):
                if newstate not in visitedStates:
                    if newstate not in set(self.get_path(node)):
                        visitedStates.add(newstate)
                        newnode = Node(newstate,node, node.cost+1)
                        lnewnodes.append(newnode)
            self.add_to_open(lnewnodes)
        return None