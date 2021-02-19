import pytest
from semantic_network import *

@pytest.fixture
def sn_net():

    a = Association('socrates','professor','filosofia')
    s = Subtype('homem','mamifero')
    m = Member('socrates','homem')

    da = Declaration('descartes',a)
    ds = Declaration('darwin',s)
    dm = Declaration('descartes',m)

    z = SemanticNetwork()
    z.insert(da)
    z.insert(ds)
    z.insert(dm)
    z.insert(Declaration('darwin',Association('mamifero','mamar','sim')))
    z.insert(Declaration('darwin',Association('homem','gosta','carne')))

    # novas declaracoes

    z.insert(Declaration('darwin',Subtype('mamifero','vertebrado')))
    z.insert(Declaration('descartes', Member('aristoteles','homem')))

    b = Association('socrates','professor','matematica')
    z.insert(Declaration('descartes',b))
    z.insert(Declaration('simao',b))
    z.insert(Declaration('simoes',b))

    z.insert(Declaration('descartes', Member('platao','homem')))

    e = Association('platao','professor','filosofia')
    z.insert(Declaration('descartes',e))
    z.insert(Declaration('simao',e))

    z.insert(Declaration('descartes',Association('mamifero','altura',1.2)))
    z.insert(Declaration('descartes',Association('homem','altura',1.75)))
    z.insert(Declaration('simao',Association('homem','altura',1.85)))
    z.insert(Declaration('darwin',Association('homem','altura',1.75)))

    z.insert(Declaration('descartes', Association('socrates','peso',80)))
    z.insert(Declaration('darwin', Association('socrates','peso',75)))
    z.insert(Declaration('darwin', Association('platao','peso',75)))


    z.insert(Declaration('damasio', Association('filosofo','gosta','filosofia')))
    z.insert(Declaration('damasio', Member('socrates','filosofo')))

    return z

def test_exercicio1(sn_net):
    assert sorted(sn_net.list_associations()) ==  ['altura', 'gosta', 'mamar', 'peso', 'professor']

def test_exercicio2(sn_net):
    assert sorted(sn_net.list_objects()) == ['aristoteles', 'platao', 'socrates']

def test_exercicio3(sn_net):
    assert sorted(sn_net.list_users()) == ['damasio', 'darwin', 'descartes', 'simao', 'simoes']

def test_exercicio4(sn_net):
    assert sorted(sn_net.list_types()) == ['filosofo', 'homem', 'mamifero', 'vertebrado']

def test_exercicio5(sn_net):
    assert sorted(sn_net.list_local_associations('socrates')) == ['peso', 'professor']

def test_exercicio6(sn_net):
    assert sorted(sn_net.list_relations_by_user('descartes')) == ['altura', 'member', 'peso', 'professor']

def test_exercicio7(sn_net):
    assert sn_net.associations_by_user('descartes') == 3

def test_exercicio8(sn_net):
    assert sorted(sn_net.list_local_associations_by_user('socrates')) == [('peso', 'darwin'), ('peso', 'descartes'), ('professor', 'descartes'), ('professor', 'simao'), ('professor', 'simoes')]

def test_exercicio9(sn_net):
    assert sn_net.predecessor('vertebrado','socrates')
    assert not sn_net.predecessor('vertebrado','filosofo')