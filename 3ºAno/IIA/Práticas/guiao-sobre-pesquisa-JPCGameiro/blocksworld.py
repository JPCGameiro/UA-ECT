#
#  Module: blocksworld
# 
#  Based on the imported "strips" module, the "blocksworld" module
#  defines a set of predicates and operators for representing
#  the "Blocks World" planning domain.
#
#  (c) Luis Seabra Lopes
#  Inteligência Artificial & Introducao a Inteligencia Artificial, 2019-2020
#


import time
from strips import *

# Blocks world predicates

class Floor(Predicate):
    def __init__(self,block):
        self.args = [block]

class On(Predicate):
    def __init__(self,b1,b2):
        self.args = [b1,b2]

class Free(Predicate):
    def __init__(self,block):
        self.args = [block]

class Holds(Predicate):
    def __init__(self,block):
        self.args = [block]

class HandFree(Predicate):
    def __init__(self):
        self.args = []


# Blocks world operators

X='X'
Y='Y'
Z='Z'

class Stack(Operator):
    args = [X,Y]
    pc   = [Holds(X),Free(Y)]
    neg  = [Holds(X),Free(Y)]
    pos  = [On(X,Y),HandFree(),Free(X)]

class Unstack(Operator):
    args = [X,Y]
    pc   = [On(X,Y),HandFree(),Free(X)]
    neg  = [On(X,Y),HandFree(),Free(X)]
    pos  = [Holds(X),Free(Y)]

class Putdown(Operator):
    args = [X]
    pc   = [Holds(X)]
    neg  = [Holds(X)]
    pos  = [Floor(X),HandFree(),Free(X)]
    
class Pickup(Operator):
    args = [X]
    pc   = [Floor(X),HandFree(),Free(X)]
    neg  = [Floor(X),HandFree(),Free(X)]
    pos  = [Holds(X)]
    

a='a'
b='b'
c='c'
d='d'
e='e'

initial_state = [ Floor(a), Floor(b), Floor(d), Holds(e), On(c,d), 
                  Free(a), Free(b), Free(c) ] 
#    _
#   / \
#  |  (e)
#  |
#  |                  |c|
# _|___|a|____|b|_____|d|_    
# 

goal_state    = [ Floor(c), On(d,c), On(e,d), On(a,e), Floor(b) ]

#    _
#   / \
#  |  ( )           |a|
#  |                |e|
#  |                |d|
# _|__________|b|___|c|___    
#




print('Substitute:',On(X,Y).substitute({ X : a, Y : b, Z : c}))

print('Instanciate:',Stack.instanciate([a,b]))


bwdomain = STRIPS()

print('Actions:',bwdomain.actions(initial_state))

"""
# uncomment to test

inittime = time.time()

p = SearchProblem(bwdomain,initial_state,goal_state)
t = SearchTree(p)
t.search()

print(t.plan)
print('time=',time.time()-inittime)
print(len(t.open_nodes),' nodes')
"""


