from constraintsearch import *

#   A---B
#   |\ /|
#   | E-C
#   |/ /
#   D--

def map_constraint(r1,c1,r2,c2):
    return c1==c2

region = ['A', 'B', 'C', 'D', 'E']
colors = ['red', 'blue', 'green', 'yellow', 'white']

def make_constraint_graph():
    l = [('A','B'), ('A','D'), ('A','E'),
         ('B','A'), ('B','C'), ('B','E'),
         ('C','B'), ('C','D'), ('C','E'),
         ('D','A'), ('D','C'), ('D','E'),
         ('E','A'), ('E','B'), ('E','C'), ('E','D')]
    return { value:map_constraint for value in l }

def make_domains():
    return { r:colors for r in region }

cs = ConstraintSearch(make_domains(), make_constraint_graph())

print(cs.search())
