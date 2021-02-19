# Class Relation, with the following derived classes
#     - Subtype     - a generalization relation between two types
#     - Member      - connects an object to its type
#     - Association - any other relation between two entities (objects or types)
#
class Relation:
    def __init__(self,e1,relname,e2):
        self.entity1 = e1
        self.name = relname
        self.entity2 = e2
    def __str__(self):
        return self.name + "(" + str(self.entity1) + "," + \
               str(self.entity2) + ")"
    def __repr__(self):
        return str(self)

# Class Subtype, derived from Relation
class Subtype(Relation):
    def __init__(self,sub,super):
        Relation.__init__(self,sub,"subtype",super)

# Class Member, derived from Relation
class Member(Relation):
    def __init__(self,obj,type):
        Relation.__init__(self,obj,"member",type)

# Class Association, derived from Relation
class Association(Relation):
    def __init__(self,e1,assocname,e2,
            cardinality='multiple',inv=None,invcard='multiple'):
        Relation.__init__(self,e1,assocname,e2)
        self.cardinality = cardinality
        self.inverse = inv
        self.invcard = None if inv==None else invcard
    def assoc_properties(self):
        return (self.cardinality,self.inverse,self.invcard)


# Class Declaration
# -- associa um utilizador a uma relacao por si inserida
#    na rede semantica
class Declaration:
    def __init__(self,user,rel):
        self.user = user
        self.relation = rel
    def __str__(self):
        return "decl("+str(self.user)+","+str(self.relation)+")"
    def __repr__(self):
        return str(self)

# classe SemanticNetwork
# -- composta por um conjunto de declaracoes
#    armazenado na forma de uma lista
class SemanticNetwork:
    def __init__(self):
        self.declarations = []
    def __str__(self):
        return str(self.declarations)
    def insert(self,user,relation):
        self.declarations.append(Declaration(user,relation))
    def query_local(self,user=None,e1=None,relname=None,e2=None):
        self.query_result = \
            [ d for d in self.declarations
                if  (user == None or d.user==user)
                and (e1 == None or d.relation.entity1 == e1)
                and (relname == None or d.relation.name == relname)
                and (e2 == None or d.relation.entity2 == e2) ]
        return self.query_result
    def show_query_result(self):
        for d in self.query_result:
            print(str(d))


