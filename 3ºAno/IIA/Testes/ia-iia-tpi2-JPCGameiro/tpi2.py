#encoding: utf8

from semantic_network import *
from bayes_net import *
from constraintsearch import *

from itertools import product

class MyBN(BayesNet):
    def individual_probabilities(self):
        dic = {}
        for k in self.dependencies.keys():
            dic[k] = (self.individualProb(k, True))
        return dic

    #Auxiliary functions to calculate individual probability of a given (var, value)
    def individualProb(self,var,val):
        prob = 0
        ancestors = self.conjunction(self.ancestors(var))
        for conj in ancestors:
            prob += self.jointProb(conj + [(var, val)])
        return prob

    def conjunction(self, lvars):
        l = product([True,False], repeat=len(lvars))
        return list(map(lambda c : list(zip(lvars, c)),l))

    def ancestors(self, var):
        lvars = [ v for (v,x) in list(self.dependencies[var].keys())[0] ]
        res = lvars

        for v in lvars:
            res += self.ancestors(v)

        return list(set(res))




class MySemNet(SemanticNetwork):
    def __init__(self):
        SemanticNetwork.__init__(self)

    def translate_ontology(self):
        #Key of dictionary is entity2 of each Subtype relation and value is a list of all corresponding entity1(s) 
        dic = {}
        #Get all of the subtype relations and save them in a dictionary
        for d in self.declarations:
            if isinstance(d.relation, Subtype):
                if d.relation.entity2 in dic:
                    if d.relation.entity1 not in dic[d.relation.entity2]:
                        dic[d.relation.entity2].append(d.relation.entity1)
                else:
                   dic[d.relation.entity2] = [d.relation.entity1] 
        
        #Sort list with all dictionary keys
        res = list(dic.keys())
        res.sort()

        #For each key construct the solution
        for j in range(len(res)):
            dic[res[j]].sort()
            word = "Qx "
            for i in range(len(dic[res[j]])):
                if i != len(dic[res[j]])-1:
                    word += dic[res[j]][i].capitalize()+"(x) or "
                else:
                    word += dic[res[j]][i].capitalize()+"(x) => "
            word+=res[j].capitalize()+"(x)"
            res[j] = word

        return res



    def query_inherit(self,entity,assoc):
        #List to store all the inverted declarations
        inv = []
        for d in self.declarations:
            #Check if assoc is inverse and if inverse is declared
            if isinstance(d.relation, Association) and d.relation.inverse==assoc:
                if d not in inv and d.relation.entity2==entity:
                    inv.append(d)


        #Get local declarations
        ldecl = [d for d in self.query_local(e1=entity, relname=assoc) if isinstance(d.relation, Association)]
        #List to find inherited declarations
        parents = [d.relation.entity2 for d in self.query_local(e1=entity) if not isinstance(d.relation, Association)]
        #Get inherited declarations
        for p in parents:
            res = self.query_inherit(p, assoc)
            if res:
                ldecl+=res
        
        #If there weren't inverted declarations return local+inherited
        if inv==[]:
            return ldecl
        #else return local+inherited+inverted
        else:
            return list(set(ldecl+inv))



    def query(self,entity,relname):
        #If relation is member
        if relname == 'member':
            return list(set([d.relation.entity2 for d in self.declarations if isinstance(d.relation, Member) and d.relation.entity1 == entity]))
        #If relation is subtype
        elif relname == 'subtype':
            return list(set([d.relation.entity2 for d in self.declarations if isinstance(d.relation, Subtype) and d.relation.entity1 == entity]))
        else:
            #Dictionary used to find the most common propertie
            dic = {}
            #Get all of the associations with relname
            for d in self.query_local(relname=relname):
                if isinstance(d.relation, Association):
                    if d.relation.assoc_properties() not in dic:
                        dic[d.relation.assoc_properties()]=1
                    else:
                        dic[d.relation.assoc_properties()]+=1
            #Get the most common propertie of the dictionary
            propertie = max(dic, key=dic.get)

            #If cardinality is multiple
            if propertie[0] == 'multiple':
                #Return process without cancelling (return only if properties are the same as the most common)
                return list(set([d.relation.entity2 for d in self.query_inherit(entity, relname) if d.relation.assoc_properties()==propertie]))
            #If cardinality is single
            elif propertie[0] == 'single':
                #Dictionary to verify the most common declaration
                dic = {}
                res = []
                #Get the most common declaration for all of those that were declared with the same association in the entity
                for d in [d for d in self.query_local(e1=entity, relname=relname) if isinstance(d.relation, Association) and d.relation.assoc_properties()==propertie] :
                    if d.relation.entity2 in dic:
                        dic[d.relation.entity2]+=1
                    else:
                        dic[d.relation.entity2]=1
                res.append(max(dic, key=dic.get))
                
                #Cancel the inheritance, if the associations exist in the entity the similar ones are cancelled in predecessors
                if res == []:
                    parents = [d.relation.entity2 for d in self.query_local(e1=entity) if not isinstance(d.relation, Association)]
                    for p in parents:
                        res += self.query(p, assoc)
                return res
        return None



class MyCS(ConstraintSearch):

    def search_all(self,domains=None,xpto=None):
        
        if domains==None:
            domains = self.domains
        
        #Use xpto to save all solutions
        xpto = []

        # se alguma variavel tiver lista de valores vazia, falha
        if any([lv==[] for lv in domains.values()]):
            return None

        # se nenhuma variavel tiver mais do que um valor possivel, sucesso
        if all([len(lv)==1 for lv in list(domains.values())]):
            return { v:lv[0] for (v,lv) in domains.items() }
       
        # continuação da pesquisa
        for var in domains.keys():
            if len(domains[var])>1:
                for val in domains[var]:
                    newdomains = dict(domains)
                    newdomains[var] = [val]
                    edges = [(v1,v2) for (v1,v2) in self.constraints if v2==var]
                    newdomains = self.constraint_propagation(newdomains,edges)
                    solution = self.search_all(newdomains)
                    #If solution found
                    if solution != None:
                            #If solution wasn't added to the list
                            if solution not in xpto:
                                #If solution is a dictionary
                                if isinstance(solution, dict):
                                    #Add to the list
                                    xpto.append(solution)
                                #If solution is a list with previous values
                                else:
                                    #add each value of the solution
                                    [ xpto.append(v) for v in solution ]
                return xpto




