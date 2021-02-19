

import math

from tpi1 import *

#################################################
# MySTRIPS tests
#################################################

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

state = [ Holds(e), Floor(b), Floor(d), On(c,d), Floor(a), 
                  Free(b), Free(a), Free(c) ] 

bwdomain = MySTRIPS()

print('\n##########################################')
print('#Ex. 1')
print('##########################################\n')
actions = bwdomain.actions(state)
for a in actions:
    result = bwdomain.result(state,a)
    print(result,bwdomain.sort(result))

#################################################
# Tree search tests in the "MinhasCidades" domain
#################################################

cidades_portugal = MinhasCidades( 
                    # Ligacoes por estrada
                    [
                      ('Coimbra', 'Leiria', 73),
                      ('Aveiro', 'Agueda', 35),
                      ('Porto', 'Agueda', 79),
                      ('Agueda', 'Coimbra', 45),
                      ('Viseu', 'Agueda', 78),
                      ('Aveiro', 'Porto', 78),
                      ('Aveiro', 'Coimbra', 65),
                      ('Figueira', 'Aveiro', 77),
                      ('Braga', 'Porto', 57),
                      ('Viseu', 'Guarda', 75),
                      ('Viseu', 'Coimbra', 91),
                      ('Figueira', 'Coimbra', 52),
                      ('Leiria', 'Castelo Branco', 169),
                      ('Figueira', 'Leiria', 62),
                      ('Leiria', 'Santarem', 78),
                      ('Santarem', 'Lisboa', 82),
                      ('Santarem', 'Castelo Branco', 160),
                      ('Castelo Branco', 'Viseu', 174),
                      ('Santarem', 'Evora', 122),
                      ('Lisboa', 'Evora', 132),
                      ('Evora', 'Beja', 105),
                      ('Lisboa', 'Beja', 178),
                      ('Faro', 'Beja', 147),
                      ('Braga', 'Guimaraes', 25),
                      ('Porto', 'Guimaraes', 44),
                      ('Guarda', 'Covilha', 46),
                      ('Viseu', 'Covilha', 57),
                      ('Castelo Branco', 'Covilha', 62),
                      ('Guarda', 'Castelo Branco', 96),
                      ('Lamego','Guimaraes', 88),
                      ('Lamego','Viseu', 47),
                      ('Lamego','Guarda', 64),
                      ('Portalegre','Castelo Branco', 64),
                      ('Portalegre','Santarem', 157),
                      ('Portalegre','Evora', 194) ],

                    # City coordinates
                     { 'Aveiro': (41,215),
                       'Figueira': ( 24, 161),
                       'Coimbra': ( 60, 167),
                       'Agueda': ( 58, 208),
                       'Viseu': ( 104, 217),
                       'Braga': ( 61, 317),
                       'Porto': ( 45, 272),
                       'Lisboa': ( 0, 0),
                       'Santarem': ( 38, 59),
                       'Leiria': ( 28, 115),
                       'Castelo Branco': ( 140, 124),
                       'Guarda': ( 159, 204),
                       'Evora': (120, -10),
                       'Beja': (125, -110),
                       'Faro': (120, -250),
                       'Guimaraes': ( 71, 300),
                       'Covilha': ( 130, 175),
                       'Lamego' : (125,250),
                       'Portalegre': (130,170) }
                     )

print('\n##########################################')
print('#Ex. 2')
print('##########################################\n')
print(cidades_portugal.middle('Braga','Faro'))
print(cidades_portugal.middle('Lisboa','Guarda'))

print('\n##########################################')
print('#Ex. 3')
print('##########################################\n')

for (i,g) in [ ('Braga','Faro'),('Guimaraes','Evora'),
               ('Aveiro','Castelo Branco'),('Santarem','Guarda'),
               ('Braga','Portalegre')]:

    print('***',i,'-->',g,'***')
    p = SearchProblem(cidades_portugal,i,g)

    t = MyTree(p,'breadth')
    sol = t.search2()
    print('   ',len(sol)-1,t.non_terminal+t.terminal)

    t = MyTree(p,'depth')
    sol = t.search2()
    print('   ',len(sol)-1,t.non_terminal+t.terminal)

    t = MyTree(p,'hybrid1')
    sol = t.search2()
    print('   ',len(sol)-1,t.non_terminal+t.terminal)

    t = MyTree(p,'hybrid2')
    sol = t.search2()
    print('   ',len(sol)-1,t.non_terminal+t.terminal)

print('\n##########################################')
print('#Ex. 4')
print('##########################################\n')

p = SearchProblem(cidades_portugal,'Braga','Faro')
t = MyTree(p)
print(t.search_from_middle())
print(t.from_init.non_terminal,t.from_init.terminal)
print(t.to_goal.non_terminal,t.to_goal.terminal)
print(t.solution)

print('\n')

print(t.search())
print(t.non_terminal,t.terminal)


