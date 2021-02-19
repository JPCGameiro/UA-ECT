# Pesquisa para resolucao de problemas de atribuicao
# 
# Introducao a Inteligencia Artificial
# DETI / UA
#
# (c) Luis Seabra Lopes, 2012-2021
#


class ConstraintSearch:

    # 'domains' é um dicionário com o domínio de cada variável;
    # constaints e' um dicionário com a restrição aplicável a cada aresta;
    def __init__(self,domains,constraints):
        self.domains = domains
        self.constraints = constraints


    # 'domains' é um dicionário com os domínios actuais
    # de cada variável
    # ( ver acetato "Pesquisa com propagacao de restricoes
    #   em problemas de atribuicao - algoritmo" )
    def search(self,domains=None):
        
        if domains==None:
            domains = self.domains

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
                    solution = self.search(newdomains)
                    if solution != None:
                        return solution
        return None

    def constraint_propagation(self,domains,edges):

        while edges!=[]:

            # retirar cabeca da fila de arestas
            (var1,var2) = edges[0]
            edges = edges[1:]

            # remover valores inconsistentes em var1
            constraint = self.constraints[var1,var2]
            domain = []
            for x in domains[var1]:
                possible = False
                for y in domains[var2]:
                    if constraint(var1,x,var2,y):
                        possible = True
                if possible:
                    domain += [x]

            # se removeu, acrescentar arestas que apontam para var1
            if len(domain)<len(domains[var1]):
               domains[var1] = domain
               edges += [(v1,v2) for (v1,v2) in self.constraints if v2==var1]
        return domains 


# -------------------------------------------------
# Funcoes  auxiliares
# -------------------------------------------------

# Filtrar um dominio tendo em conta uma restricao 
# unaria
def filter_domain(domain,constraint):
    return [ v for v in domain if constraint(v) ]

# Gerar o produto cartesiano dos dominios das 
# variaveis dadas.
def generate_product_domain(lvars,domains):
    if lvars == []:
        return [()]

    product = generate_product_domain(lvars[1:],domains)
     
    finalproduct = []
    for v in domains[lvars[0]]:
        for x in product:
            newx = (v,)+x
            finalproduct += [newx]
    return finalproduct


