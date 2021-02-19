#João Gameiro
#Nº 93097

from tree_search import *
from cidades import *
from strips import *

#Extends SearchNode(additional attributes: deph and offset)
class MySearchNode(SearchNode):
    def __init__(self,state,parent):
        super().__init__(state, parent)

        #If the parent is the root
        if self.parent.__class__.__name__ == "SearchNode": 
            #if parent does not have deph atribute
            if not hasattr(self.parent, 'deph'):
                #Initialize deph and offset atributes
                self.parent.deph = 0
                self.parent.offset = 0
        self.deph = self.parent.deph + 1
        #Temporary value for the offset, will be updated with the help of offsetlist
        self.offset = 0 



class MyTree(SearchTree):

    def __init__(self,problem, strategy='breadth'): 
        super().__init__(problem,strategy)

    def hybrid1_add_to_open(self,lnewnodes):
        for i in range (0, len(lnewnodes)):
            if i%2 == 0:  #Even positions
                self.open_nodes.insert(0, lnewnodes[i]) 
            else:         #Odd positions
                self.open_nodes.append(lnewnodes[i])

    def hybrid2_add_to_open(self,lnewnodes):
        self.open_nodes += lnewnodes
        self.open_nodes.sort(key=lambda node: node.deph - node.offset)


    def search2(self):

        #offsetList[0] has number of nodes with 0 deph, 
        #offsetList[1] has number of nodes with 1 deph, ...
        offsetList = [1]

        while self.open_nodes != []:
            node = self.open_nodes.pop(0)
            if self.problem.goal_test(node.state):
                self.terminal = len(self.open_nodes)+1
                self.solution = node
                return self.get_path(node)
            self.non_terminal+=1
            node.children = []
            for a in self.problem.domain.actions(node.state):
                newstate = self.problem.domain.result(node.state,a)
                if newstate not in self.get_path(node):
                    newnode = MySearchNode(newstate,node)
                    
                    #If there are no nodes with newnode.deph
                    if(len(offsetList) <= newnode.deph): 
                        #Register on the offsetlist that there is one now
                        offsetList.append(1)
                    #If there nodes with newnode.deph
                    else: 
                        #Increase the number of nodes with that deph
                        offsetList[newnode.deph] += 1 

                    #Offset == (number of nodes with the same deph - 1)
                    newnode.offset = offsetList[newnode.deph] - 1

                    node.children.append(newnode)
            self.add_to_open(node.children)
        return None


    def search_from_middle(self):
        #Middle of initial and goal
        middle = self.problem.domain.middle(self.problem.initial, self.problem.goal)
        #Search Problem initial-middle
        spIM = SearchProblem(self.problem.domain, self.problem.initial, middle)
        #Search Problem middle-goal
        spMG = SearchProblem(self.problem.domain, middle, self.problem.goal)

        #Instanciate SearchTrees for both problems
        st1 = SearchTree(spIM)
        st2 = SearchTree(spMG)
        #Searching for the both solutions
        st1.search()
        st2.search()

        #Creating the additional atributes
        self.from_init = st1
        self.to_goal = st2

        return st1.get_path(st1.solution) + st2.get_path(st2.solution)


class MinhasCidades(Cidades):

    # state that minimizes heuristic(state1,middle)+heuristic(middle,state2)
    def middle(self,city1,city2):
        citys = []
        for value in self.connections:  #Find all citys and store in citys list    
            citys.append(value[0]) if value[0] not in citys else False
            citys.append(value[1]) if value[1] not in citys else False
        
        l1 = [] #list to store all the (city, heuristic(city1,city)) values
        l2 = [] #list to store all the (city, heuristic(city,city2)) values
        for city in citys:
            l1.append((city, self.heuristic(city1, city))) 
            l2.append((city, self.heuristic(city, city2)))
        
        #Sort l1 and l2 according to the heuristic values
        l1.sort(key=lambda x: x[1]) 
        l2.sort(key=lambda x: x[1])

        res = []        
        for i1 in l1:
            for i2 in l2:         #If i1 is city1 or city2 don't add to result list
                if i1[0] == i2[0] and i1[0]!=city1 and i1[0]!=city2: 
                    res.append((i1[0], (i1[1]+i2[1])))  #(city, sum of heurist l1 and heuristic l2)
        
        #Sorting according to the heuristic values
        res.sort(key=lambda x: x[1]) 

        return res[0][0]

class MySTRIPS(STRIPS):
    def result(self, state, action):
        newstate = []
        #NewState = State - NegativeEffects + PositiveEffects
        for value in state:
            if not value in action.neg:
                newstate.append(value)
        newstate += action.pos
        return newstate

    def sort(self,state):
        res = state[:]
        #Sorting all the states alphabetically
        res.sort(key=lambda x: str(x))
        return res


