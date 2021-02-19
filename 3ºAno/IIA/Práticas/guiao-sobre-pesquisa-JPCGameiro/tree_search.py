
# Module: tree_search
# 
# This module provides a set o classes for automated
# problem solving through tree search:
#    SearchDomain  - problem domains
#    SearchProblem - concrete problems to be solved
#    SearchNode    - search tree nodes
#    SearchTree    - search tree with the necessary methods for searhing
#
#  (c) Luis Seabra Lopes
#  Introducao a Inteligencia Artificial, 2012-2019,
#  Inteligência Artificial, 2014-2019

from abc import ABC, abstractmethod

# Dominios de pesquisa
# Permitem calcular
# as accoes possiveis em cada estado, etc
class SearchDomain(ABC):

    # construtor
    @abstractmethod
    def __init__(self):
        pass

    # lista de accoes possiveis num estado
    @abstractmethod
    def actions(self, state):
        pass

    # resultado de uma accao num estado, ou seja, o estado seguinte
    @abstractmethod
    def result(self, state, action):
        pass

    # custo de uma accao num estado
    @abstractmethod
    def cost(self, state, action):
        pass

    # custo estimado de chegar de um estado a outro
    @abstractmethod
    def heuristic(self, state, goal):
        pass

    # test if the given "goal" is satisfied in "state"
    @abstractmethod
    def satisfies(self, state, goal):
        pass


# Problemas concretos a resolver
# dentro de um determinado dominio
class SearchProblem:
    def __init__(self, domain, initial, goal):
        self.domain = domain
        self.initial = initial
        self.goal = goal
    def goal_test(self, state):
        return self.domain.satisfies(state,self.goal)

# Nos de uma arvore de pesquisa
class SearchNode:
    def __init__(self,state,parent,heuristic): 
        self.state = state
        self.parent = parent
        self.depth = self.parent.depth+1 if self.parent!=None else 0
        self.cost = 0
        self.heuristic = heuristic
    def __str__(self):
        return "no(" + str(self.state) + "," + str(self.parent) + ")"
    def __repr__(self):
        return str(self)

# Arvores de pesquisa
class SearchTree:

    # construtor
    def __init__(self,problem, strategy='breadth'): 
        self.problem = problem
        root = SearchNode(problem.initial, None, self.problem.domain.heuristic(problem.initial, problem.goal))
        self.open_nodes = [root]
        self.strategy = strategy
        self.solution = None
        self.terminals = 0
        self.non_terminals = 0
        self.avg_branching = 0

    # obter o caminho (sequencia de estados) da raiz ate um no
    def get_path(self,node):
        if node.parent == None:
            return [node.state]
        path = self.get_path(node.parent)
        path += [node.state]
        return(path)
    
    @property
    def get_plan(self, node): 
        if node.parent == None:
            return []
        plan = self.get_plan(node.parent)
        plan += [node.action] 
        return plan
    # procurar a solucao
    def search(self, limit=None):
        totalnodes = []
        while self.open_nodes != []:
            node = self.open_nodes.pop(0)

            #Solução encontrada
            if self.problem.goal_test(node.state):
                self.solution = node
                #Calcular número de nós terminais
                self.terminals = len(self.open_nodes)+1
                #Calcular o factor de ramificação média
                self.avg_branching = round(((self.non_terminals+self.terminals-1) / self.non_terminals), 2)
                #Calcular o custo
                self.cost = node.cost
                self.length = self.solution.depth
                return self.get_path(node)
            
            #Número de nós não terminais
            self.non_terminals += 1
            #Pesquisa com limite
            if limit != None and node.depth >= limit:
                continue

            lnewnodes = []
            for a in self.problem.domain.actions(node.state):
                newstate = self.problem.domain.result(node.state,a)
                if newstate not in self.get_path(node):
                    newnode = SearchNode(newstate,node, self.problem.domain.heuristic(newstate, self.problem.goal))
                    newnode.cost = node.cost + self.problem.domain.cost(node.state,a)
                    lnewnodes.append(newnode)
            self.add_to_open(lnewnodes)
        return None

    # juntar novos nos a lista de nos abertos de acordo com a estrategia
    def add_to_open(self,lnewnodes):
        if self.strategy == 'breadth':
            self.open_nodes.extend(lnewnodes)
        elif self.strategy == 'depth':
            self.open_nodes[:0] = lnewnodes
        elif self.strategy == 'uniform':
            self.open_nodes += lnewnodes
            self.open_nodes.sort(key=(lambda city: city.cost))
        elif self.strategy == 'greedy':
            self.open_nodes += lnewnodes
            self.open_nodes.sort(key=(lambda h: h.heuristic))
        elif self.strategy == 'a*':
            self.open_nodes += lnewnodes
            self.open_nodes.sort(key=(lambda f: f.cost + f.heuristic))

