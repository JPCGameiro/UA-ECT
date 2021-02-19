import pytest
from semantic_network import *
from tests.test_aula6 import sn_net


@pytest.fixture
def sn_net2():

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

    z.insert(Declaration('descartes', AssocNum('socrates','pulsacao',51)))
    z.insert(Declaration('darwin', AssocNum('socrates','pulsacao',61)))
    z.insert(Declaration('darwin', AssocNum('platao','pulsacao',65)))

    z.insert(Declaration('descartes',AssocNum('homem','temperatura',36.8)))
    z.insert(Declaration('simao',AssocNum('homem','temperatura',37.0)))
    z.insert(Declaration('darwin',AssocNum('homem','temperatura',37.1)))
    z.insert(Declaration('descartes',AssocNum('mamifero','temperatura',39.0)))

    z.insert(Declaration('simao',Association('homem','gosta','carne')))
    z.insert(Declaration('darwin',Association('homem','gosta','peixe')))
    z.insert(Declaration('simao',Association('homem','gosta','peixe')))
    z.insert(Declaration('simao',Association('homem','gosta','couves')))

    z.insert(Declaration('damasio', AssocOne('socrates','pai','sofronisco')))
    z.insert(Declaration('darwin', AssocOne('socrates','pai','pericles')))
    z.insert(Declaration('descartes', AssocOne('socrates','pai','sofronisco')))

    return z


def compare_decl_lists(l1, l2):
    l1_tuples = [str(d) for d in l1]
    l2_tuples = [str(d) for d in l2]
    return len(l1_tuples)==len(l2_tuples) and set(l1_tuples) == set(l2_tuples)


def test_exercicio14(sn_net):
    assert sn_net.query_induce('vertebrado', 'altura') == 1.75


def test_exercicio15(sn_net2):
    assert sn_net2.query_local_assoc('socrates', 'pai') == ('sofronisco', 2/3)

    assert sn_net2.query_local_assoc('socrates', 'pulsacao') == 56

    assert sn_net2.query_local_assoc('homem', 'gosta') == [('carne', 0.40), ('peixe', 0.40)]


def test_exercicio16(sn_net2):
    assert sn_net2.query_assoc_value('socrates', 'altura') == 1.75
    assert sn_net2.query_assoc_value('socrates', 'peso') == 80
