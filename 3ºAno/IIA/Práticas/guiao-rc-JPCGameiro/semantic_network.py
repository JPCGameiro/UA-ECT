

# Guiao de representacao do conhecimento
# -- Redes semanticas
# 
# Inteligencia Artificial & Introducao a Inteligencia Artificial
# DETI / UA
#
# (c) Luis Seabra Lopes, 2012-2020
# v1.9 - 2019/10/20
#


# Classe Relation, com as seguintes classes derivadas:
#     - Association - uma associacao generica entre duas entidades
#     - Subtype     - uma relacao de subtipo entre dois tipos
#     - Member      - uma relacao de pertenca de uma instancia a um tipo
#

class Relation:
    def __init__(self,e1,rel,e2):
        self.entity1 = e1
#       self.relation = rel  # obsoleto
        self.name = rel
        self.entity2 = e2
    def __str__(self):
        return self.name + "(" + str(self.entity1) + "," + \
               str(self.entity2) + ")"
    def __repr__(self):
        return str(self)


# Subclasse Association
class Association(Relation):
    def __init__(self,e1,assoc,e2):
        Relation.__init__(self,e1,assoc,e2)

#   Exemplo:
#   a = Association('socrates','professor','filosofia')

# Subclasse Subtype
class Subtype(Relation):
    def __init__(self,sub,super):
        Relation.__init__(self,sub,"subtype",super)


#   Exemplo:
#   s = Subtype('homem','mamifero')

# Subclasse Member
class Member(Relation):
    def __init__(self,obj,type):
        Relation.__init__(self,obj,"member",type)

#Subclasse AssocNum
class AssocNum(Relation):
    def __init__(self, ent, assoc, num):
        Relation.__init__(self, ent, assoc, num)

#Subclasse AssocOne
class AssocOne(Relation):
    def __init__(self, e1, assoc, e2):
        Relation.__init__(self, e1, assoc, e2)

#   Exemplo:
#   m = Member('socrates','homem')

# classe Declaration
# -- associa um utilizador a uma relacao por si inserida
#    na rede semantica
#
class Declaration:
    def __init__(self,user,rel):
        self.user = user
        self.relation = rel
    def __str__(self):
        return "decl("+str(self.user)+","+str(self.relation)+")"
    def __repr__(self):
        return str(self)

#   Exemplos:
#   da = Declaration('descartes',a)
#   ds = Declaration('darwin',s)
#   dm = Declaration('descartes',m)

# classe SemanticNetwork
# -- composta por um conjunto de declaracoes
#    armazenado na forma de uma lista
#
class SemanticNetwork:
    def __init__(self,ldecl=None):
        self.declarations = [] if ldecl==None else ldecl
    def __str__(self):
        return my_list2string(self.declarations)
    def insert(self,decl):
        self.declarations.append(decl)
    def query_local(self,user=None,e1=None,rel=None,e2=None):
        self.query_result = \
            [ d for d in self.declarations
                if  (user == None or d.user==user)
                and (e1 == None or d.relation.entity1 == e1)
                and (rel == None or d.relation.name == rel)
                and (e2 == None or d.relation.entity2 == e2) ]
        return self.query_result
    def show_query_result(self):
        for d in self.query_result:
            print(str(d))
    
    #Exercicio 1
    def list_associations(self):
        return list(set([d.relation.name for d in self.declarations if isinstance(d.relation, Association)]))

    #Exercicio 2
    def list_objects(self):
        return list(set([d.relation.entity1 for d in self.declarations if isinstance(d.relation, Member)]))
    
    #Exercicio 3
    def list_users(self):
        return list(set([d.user for d in self.declarations]))
    
    #Exercicio 4
    def list_types(self):
        res = []
        for d in self.declarations:
            if isinstance(d.relation, Subtype) and d.relation.entity1 not in res:
                res.append(d.relation.entity1)
        for d in self.declarations:
            if (isinstance(d.relation, Subtype) or isinstance(d.relation, Member)) and d.relation.entity2 not in res:
                res.append(d.relation.entity2)
        return res
    
    #Exericio 5
    def list_local_associations(self, entity):
        return list(set([d.relation.name for d in self.declarations if d.relation.entity1==entity and isinstance(d.relation, Association)]))

    #Exercicio 6
    def list_relations_by_user(self, user):
        return list(set([d.relation.name for d in self.declarations if d.user == user]))
    
    #Exercicio 7
    def associations_by_user(self, user):
        return len(list(set([d.relation.name for d in self.declarations if d.user == user and isinstance(d.relation, Association)])))

    #Exercicio 8
    def list_local_associations_by_user(self, entity):
        return list(set([(d.relation.name, d.user) for d in self.declarations if d.relation.entity1==entity and isinstance(d.relation, Association)]))

    #Exercicio 9
    def predecessor(self, A, B):
        children = list(set([d.relation.entity2 for d in self.declarations if not isinstance(d.relation, Association) and d.relation.entity1 == B]))

        if A in children:
            return True
        elif children == None:
            return False

        for c in children:
            if self.predecessor(A, c):
                return True
        return False

    #Exercicio 10
    def predecessor_path(self, A, B):
        result = [A]
        children = list(set([d.relation.entity1 for d in self.declarations if not isinstance(d.relation, Association) and d.relation.entity2 == A]))

        if B in children:
            return [A, B]
        elif children == None:
            return None

        for c in children:
            res = self.predecessor_path(c, B)
            if B in res:
                result+=res
        return result
    
    #Exercicio 11 a
    def query(self, e, assoc=None):
        parents = [d.relation.entity2 for d in self.query_local(e1=e) if not isinstance(d.relation, Association)]        
        ldecl = [d for d in self.query_local(e1=e, rel=assoc) if isinstance(d.relation, Association)]
        for p in parents:
            ldecl+=self.query(p, assoc)
        return ldecl
    
    #Exercicio 11 b
    def query2(self, e, assoc=None):
        ldecl = self.query(e, assoc)
        return ldecl + [d for d in self.query_local(e1=e, rel=assoc) if not isinstance(d.relation, Association)]
    
    #Exercicio 12
    def query_cancel(self, entity, assoc=None):
        ldecl = [d for d in self.query_local(e1=entity, rel=assoc) if isinstance(d.relation, Association)]

        if ldecl == []:
            parents = [d.relation.entity2 for d in self.query_local(e1=entity) if not isinstance(d.relation, Association)]
            for p in parents:
                ldecl += self.query_cancel(p, assoc)
        return ldecl

    #Exercicio 13
    def query_down(self, tp, assoc):
        children = [d.relation.entity1 for d in self.declarations if d.relation.entity2 == tp and not isinstance(d.relation, Association)]
        
        if children == []:
            return []
        
        decl = []
        for c in children:
            for d in self.declarations:
                if isinstance(d.relation, Association) and assoc==d.relation.name and (d.relation.entity1 == c or d.relation.entity2 == c):
                    decl.append(d)
        
        for c in children:
            decl+=self.query_down(c, assoc)
        return decl 

    #Exercicio 14
    def query_induce(self, tp, assoc):
        descendentes = self.query_down(tp, assoc)
        res = [d.relation.entity1 if d.relation.entity2==assoc else d.relation.entity2 for d in descendentes]

        dic = {}
        for value in res:
            if value in dic:
                dic[value]+=1
            else:
                dic[value] = 0
        return max(dic, key=dic.get)

    #Exercicio 15
    def query_local_assoc(self, ent, assoc):
        dicAssocOne = {}
        average = 0
        total = 0
        dicAssoc = {}

        for d in self.declarations:
            if d.relation.entity1 == ent and d.relation.name == assoc:
                if isinstance(d.relation, AssocOne):
                    if d.relation.entity2 in dicAssocOne:
                        dicAssocOne[d.relation.entity2]+=1
                    else:
                        dicAssocOne[d.relation.entity2]=1
                elif isinstance(d.relation, AssocNum):
                    total+=1
                    average+=d.relation.entity2
                elif isinstance(d.relation, Association):
                    if d.relation.entity2 in dicAssoc:
                        dicAssoc[d.relation.entity2]+=1
                    else:
                        dicAssoc[d.relation.entity2]=1
                    

        if dicAssocOne:
            val = max(dicAssocOne, key=dicAssocOne.get)
            freq = dicAssocOne[max(dicAssocOne, key=dicAssocOne.get)]/sum(dicAssocOne.values())
            i=0
            return (val, freq)
        elif average!=0:
            return average/total
        elif dicAssoc:
            total = sum(dicAssoc.values())
            res = [(value, (dicAssoc[value]/total)) for value in dicAssoc]
            res.sort(key=(lambda x: x[1]), reverse=True)
            
            result = []
            total = 0
            for v in res:
                if (v[1] + total) > 0.75:
                    result.append(v)
                    break
                result.append(v)
                total+=v[1]
            return result
    
    #Exercicio 16
    def query_assoc_value(self, E, A):
        ldecl = [d.relation.entity2 for d in self.query_local(e1=E, rel=A) if isinstance(d.relation, Association)]
        
        if(len(set([d for d in ldecl]))):
            return ldecl[0]
        h = [d.relation.entity2 for d in self.query(E,A) if (d.relation.entity2 not in ldecl)]
            

        dic={}
        for d in ldecl+h:
            if d in dic:
                dic[d]+=1
            else:
                dic[d]=1
        
        for v in dic:
            ll = 0
            hh = 0
            if len(ldecl) > 0:
                ll = (dic[v]/len(ldecl))*100
            if len(h) > 0:
                hh = (dic[v]/len(h))*100
            dic[v] = (ll + hh)/2
        
        return max(dic, key=dic.get)


# Funcao auxiliar para converter para cadeias de caracteres
# listas cujos elementos sejam convertiveis para
# cadeias de caracteres
def my_list2string(list):
   if list == []:
       return "[]"
   s = "[ " + str(list[0])
   for i in range(1,len(list)):
       s += ", " + str(list[i])
   return s + " ]"
    

