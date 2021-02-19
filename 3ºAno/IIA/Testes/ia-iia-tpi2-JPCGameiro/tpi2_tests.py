
#encoding: utf8

from tpi2 import *
# ----------------------------------------------------------------------
# Redes de Bayes
# ----------------------------------------------------------------------

bn = MyBN()

bn.add('a',[],0.003)

bn.add('b_a',[],0.002)

bn.add('c_s',[('a',True )],0.48)
bn.add('c_s',[('a',False)],0.08)

bn.add('d',[],0.01)

bn.add('m_f',[],0.01)

bn.add('b_v',[('c_s',True ),('b_a',True )],0.18)
bn.add('b_v',[('c_s',True ),('b_a',False)],0.02)
bn.add('b_v',[('c_s',False),('b_a',True )],0.90)
bn.add('b_v',[('c_s',False),('b_a',False)],0.68)

bn.add('s_m',[],0.05)

bn.add('s_p',[],0.3)

bn.add('v_p',[('m_f',True),('d',True ),('b_v',True )],0.003)
bn.add('v_p',[('m_f',True),('d',True ),('b_v',False )],0.12)
bn.add('v_p',[('m_f',True),('d',False ),('b_v',True)],0.08)
bn.add('v_p',[('m_f',True),('d',False),('b_v',False )],0.01)
bn.add('v_p',[('m_f',False),('d',True),('b_v',True)],0.04)
bn.add('v_p',[('m_f',False),('d',True ),('b_v',False)],0.07)
bn.add('v_p',[('m_f',False),('d',False),('b_v',True )],0.13)
bn.add('v_p',[('m_f',False),('d',False),('b_v',False)],0.09)

bn.add('h',[('b_v',True )],0.44)
bn.add('h',[('b_v',False)],0.89)

bn.add('s_s',[('s_m',True),('m_f',True ),('b_v',True )],0.3)
bn.add('s_s',[('s_m',True),('m_f',True ),('b_v',False )],0.21)
bn.add('s_s',[('s_m',True),('m_f',False ),('b_v',True)],0.34)
bn.add('s_s',[('s_m',True),('m_f',False),('b_v',False )],0.12)
bn.add('s_s',[('s_m',False),('m_f',True),('b_v',True)],0.15)
bn.add('s_s',[('s_m',False),('m_f',True ),('b_v',False)],0.14)
bn.add('s_s',[('s_m',False),('m_f',False),('b_v',True )],0.132)
bn.add('s_s',[('s_m',False),('m_f',False),('b_v',False)],0.44)

bn.add('s_t',[('d',True )],0.08)
bn.add('s_t',[('d',False)],0.002)

bn.add('s_q',[('s_p',True ),('v_p',True )],0.008)
bn.add('s_q',[('s_p',True ),('v_p',False)],0.4)
bn.add('s_q',[('s_p',False),('v_p',True )],0.51)
bn.add('s_q',[('s_p',False),('v_p',False)],0.13)

bn.add('f_s',[],0.1)

bn.add('c_c',[('s_s',True )],0.49)
bn.add('c_c',[('s_s',False)],0.023)

bn.add('car_s',[('c_c',True),('s_t',True),('s_q',True ),('f_s',True )],0.091)
bn.add('car_s',[('c_c',True),('s_t',True),('s_q',True ),('f_s',False )],0.081)
bn.add('car_s',[('c_c',True),('s_t',True),('s_q',False ),('f_s',True )],0.045)
bn.add('car_s',[('c_c',True),('s_t',True),('s_q',False ),('f_s',False )],0.065)
bn.add('car_s',[('c_c',True),('s_t',False),('s_q',True ),('f_s',True)],0.087)
bn.add('car_s',[('c_c',True),('s_t',False),('s_q',True),('f_s',False )],0.043)
bn.add('car_s',[('c_c',True),('s_t',False),('s_q',False ),('f_s',True)],0.035)
bn.add('car_s',[('c_c',True),('s_t',False),('s_q',False),('f_s',False )],0.067)
bn.add('car_s',[('c_c',False),('s_t',True),('s_q',True),('f_s',True)],0.052)
bn.add('car_s',[('c_c',False),('s_t',True),('s_q',True),('f_s',False)],0.054)
bn.add('car_s',[('c_c',False),('s_t',True),('s_q',False),('f_s',True)],0.056)
bn.add('car_s',[('c_c',False),('s_t',True),('s_q',False),('f_s',False)],0.078)
bn.add('car_s',[('c_c',False),('s_t',False),('s_q',True),('f_s',True )],0.045)
bn.add('car_s',[('c_c',False),('s_t',False),('s_q',True),('f_s',False)],0.031)
bn.add('car_s',[('c_c',False),('s_t',False),('s_q',False),('f_s',True )],0.034)
bn.add('car_s',[('c_c',False),('s_t',False),('s_q',False),('f_s',False)],0.023)

print('Individual probabilities:\n',bn.individual_probabilities(),'\n')


# ----------------------------------------------------------------------
# Redes semânticas
# ----------------------------------------------------------------------

z = MySemNet()

z.insert('descartes',Subtype('mammal','vertebrate'))
z.insert('darwin',Subtype('mammal','vertebrate'))
z.insert('darwin',Association('mammal','likes','milk'))

z.insert('darwin',Association('man','likes','meat'))
z.insert('bacon',Association('man','likes','vegetables','single'))

z.insert('bacon',Association('philosopher','likes','philosophy'))

z.insert('descartes',Member('socrates','man'))
z.insert('damasio',Member('socrates','philosopher'))
z.insert('descartes',Association('socrates','professorOf','philosophy'))
z.insert('descartes',Association('socrates','professorOf','mathematics'))
z.insert('simao',Association('socrates','professorOf','mathematics','single'))
z.insert('simao',Association('socrates','hasFather','sophroniscus','single'))
z.insert('nunes',Association('socrates','hasFather','sophroniscus','single'))
z.insert('knowledgeengineer',Association('socrates','hasFather','pericles'))
z.insert('aristotle',Association('socrates','hasFather','plato','single'))
z.insert('simao',Association('socrates','hasMother','phaenarete','single'))
z.insert('socrates',Association('socrates','likes','sophroniscus'))
z.insert('sophroniscus',Association('socrates','likes','phaenarete'))
z.insert('bacon',Association('socrates','likes','mathematics'))

z.insert('descartes',Member('plato','man'))
z.insert('descartes',Association('plato','professorOf','philosophy'))
z.insert('simao',Association('plato','professorOf','philosophy'))
z.insert('simao',Association('aristotle','hasFather','ariston'))

z.insert('descartes',Member('aristotle','man'))
z.insert('simao',Association('aristotle','hasFather','nicomachus','single'))

z.insert('knowledgeengineer',Subtype('cat','feline'))
z.insert('knowledgeengineer',Subtype('tiger','feline'))
z.insert('knowledgeengineer',Subtype('lion','feline'))
z.insert('knowledgeengineer',Subtype('feline','mammal'))
z.insert('knowledgeengineer',Subtype('bird','vertebrate'))
#z.insert('descartes',Subtype('man','mammal'))
#z.insert('darwin',Subtype('man','mammal'))
z.insert('knowledgeengineer',Subtype('man','primate'))
z.insert('knowledgeengineer',Subtype('primate','mammal'))

z.insert('knowledgeengineer',Association('mammal','eats','bird',inv='eatenBy'))
z.insert('knowledgeengineer',Association('plato','eats','bird',inv='eatenBy'))
z.insert('socrates',Association('man','eats','apple'))

# Tests:

print('translate_ontology():\n',z.translate_ontology(),'\n')

print('query_inherit(socrates,eats):\n',z.query_inherit('socrates','eats'),'\n')
print('query_inherit(bird,eatenBy):\n',z.query_inherit('bird','eatenBy'),'\n')
print('query_inherit(apple,eatenBy):\n',z.query_inherit('apple','eatenBy'),'\n')


query_cases = [ ('socrates','likes'),
                ('socrates','hasFather'),
                ('socrates','member'),
                ('man','subtype'),
                ('plato','eats'),
                ('philosopher','subtype') ]

for (entity,relname) in query_cases:
    print('query('+entity+','+relname+'):\n',z.query(entity,relname))
print('\n')


# ----------------------------------------------------------------------
# Pesquisa com restrições
# ----------------------------------------------------------------------

digits = list(range(0,10))

domains = { D:digits for D in ['O','R','T','U','W'] }
domains['F']  = [0,1]
domains['X1'] = [0,1]
domains['X2'] = [0,1]


def all_different(Aux1):
    return len(set(Aux1)) == len(Aux1)

def orx1(Aux2):
    return 2*Aux2[0] == Aux2[1]+10*Aux2[2]

def wx1ux2(Aux3):
    return 2*Aux3[0]+Aux3[1] == Aux3[2]+10*Aux3[3]

def tx2of(Aux4):
    return 2*Aux4[0]+Aux4[1] == Aux4[2]+10*Aux4[3]

domains['FORTUW'] = generate_product_domain(['F','O','R','T','U','W'],domains)
domains['FORTUW'] = filter_domain(domains['FORTUW'],all_different)

domains['ORX1'] = generate_product_domain(['O','R','X1'],domains)
domains['ORX1'] = filter_domain(domains['ORX1'],orx1)

domains['WX1UX2'] = generate_product_domain(['W','X1','U','X2'],domains)
domains['WX1UX2'] = filter_domain(domains['WX1UX2'],wx1ux2)

domains['TX2OF'] = generate_product_domain(['T','X2','O','F'],domains)
domains['TX2OF'] = filter_domain(domains['TX2OF'],tx2of)


constraints = []

constraints += [ (edge,lambda var,val,auxvar,auxval : val==auxval[0]) 
    for edge in [('F','FORTUW'),('O','ORX1'),('W','WX1UX2'),('T','TX2OF')] ]
constraints += [ (edge,lambda auxvar,auxval,var,val : val==auxval[0]) 
    for edge in [('FORTUW','F'),('ORX1','O'),('WX1UX2','W'),('TX2OF','T')] ]

constraints += [ (edge,lambda var,val,auxvar,auxval : val==auxval[1]) 
    for edge in [('O','FORTUW'),('R','ORX1'),('X1','WX1UX2'),('X2','TX2OF')] ]
constraints += [ (edge,lambda auxvar,auxval,var,val : val==auxval[1]) 
    for edge in [('FORTUW','O'),('ORX1','R'),('WX1UX2','X1'),('TX2OF','X2')] ]

constraints += [ (edge,lambda var,val,auxvar,auxval : val==auxval[2]) 
    for edge in [('R','FORTUW'),('X1','ORX1'),('U','WX1UX2'),('O','TX2OF')] ]
constraints += [ (edge,lambda auxvar,auxval,var,val : val==auxval[2]) 
    for edge in [('FORTUW','R'),('ORX1','X1'),('WX1UX2','U'),('TX2OF','O')] ]

constraints += [ (edge,lambda var,val,auxvar,auxval : val==auxval[3]) 
    for edge in [('T','FORTUW'),('X2','WX1UX2'),('F','TX2OF')] ]
constraints += [ (edge,lambda auxvar,auxval,var,val : val==auxval[3]) 
    for edge in [('FORTUW','T'),('WX1UX2','X2'),('TX2OF','F')] ]

constraints += [ (edge,lambda var,val,auxvar,auxval : val==auxval[4]) 
    for edge in [('U','FORTUW')] ]
constraints += [ (edge,lambda auxvar,auxval,var,val : val==auxval[4]) 
    for edge in [('FORTUW','U')] ]

constraints += [ (edge,lambda var,val,auxvar,auxval : val==auxval[5]) 
    for edge in [('W','FORTUW')] ]
constraints += [ (edge,lambda auxvar,auxval,var,val : val==auxval[5]) 
    for edge in [('FORTUW','W')] ]


cs = MyCS(domains,dict(constraints))

import time
t0 = time.process_time() #clock()
lsols = cs.search_all()

print("TWO+TWO=FOUR all solutions:")
for s in lsols:
    print([(v,s[v]) for v in ['F','O','R','T','U','W']])

print("Time:",time.process_time()-t0) # clock()-t0)

print(len(lsols)," solutions")

