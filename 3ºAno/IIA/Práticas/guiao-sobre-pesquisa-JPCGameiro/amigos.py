from constraintsearch import *

#Andre-------Bernardo
#  |          /
#  |         /
#  |        /
#Claudio---/

amigos = ["Andre", "Bernardo", "Claudio"]

def map_constraint(a1,v1,a2,v2):
    bike1, hat1 = v1
    bike2, hat2 = v2
    
    if a1 in [bike1, hat1]:
        return True
    if a2 in [bike2, hat2]:
        return True
    
    if bike1 == hat1:
        return True
    if bike2 == hat2:
        return True
    
    if bike1 == bike2:
        return True
    if hat1 == hat2:
        return True
    
    return False

def make_constraint_graph():
    l = [(bike, hat) for bike in amigos for hat in amigos if bike!=hat]
    return { value:map_constraint for value in l }

def make_domains():
    return { amigo: [(bike, hat) for bike in amigos for hat in amigos] for amigo in amigos}

cs = ConstraintSearch(make_domains(), make_constraint_graph())

print(cs.search())
