import pytest
from semantic_network import *
from tests.test_aula6 import sn_net

def compare_decl_lists(l1,l2):
    l1_tuples = [str(d) for d in l1]
    l2_tuples = [str(d) for d in l2]
    return len(l1_tuples)==len(l2_tuples) and set(l1_tuples)==set(l2_tuples)

def test_exercicio10(sn_net):
    assert sn_net.predecessor_path('vertebrado','socrates') == ['vertebrado', 'mamifero', 'homem', 'socrates']

def test_exercicio11(sn_net):
    assert compare_decl_lists(sn_net.query('socrates','altura'),[
Declaration('descartes',Association('mamifero','altura',1.2)), \
Declaration('descartes',Association('homem','altura',1.75)), \
Declaration('simao',Association('homem','altura',1.85)), \
Declaration('darwin',Association('homem','altura',1.75))] )

    assert compare_decl_lists(sn_net.query('platao'), [
Declaration('darwin',Association('mamifero','mamar','sim')), \
Declaration('descartes',Association('mamifero','altura',1.2)), \
Declaration('darwin',Association('homem','gosta','carne')), \
Declaration('descartes',Association('homem','altura',1.75)), \
Declaration('simao',Association('homem','altura',1.85)), \
Declaration('darwin',Association('homem','altura',1.75)), \
Declaration('descartes',Association('platao','professor','filosofia')), \
Declaration('simao',Association('platao','professor','filosofia')), \
Declaration('darwin',Association('platao','peso',75))] )

    assert compare_decl_lists(sn_net.query2('platao'), [
Declaration('darwin',Association('mamifero','mamar','sim')), \
Declaration('descartes',Association('mamifero','altura',1.2)), \
Declaration('darwin',Association('homem','gosta','carne')), \
Declaration('descartes',Association('homem','altura',1.75)), \
Declaration('simao',Association('homem','altura',1.85)), \
Declaration('darwin',Association('homem','altura',1.75)), \
Declaration('descartes',Association('platao','professor','filosofia')), \
Declaration('simao',Association('platao','professor','filosofia')), \
Declaration('darwin',Association('platao','peso',75)), \
Declaration('descartes',Member('platao','homem'))] )

def test_exercicio12(sn_net):
    assert compare_decl_lists(sn_net.query_cancel('socrates','altura'), [
Declaration('descartes',Association('homem','altura',1.75)), \
Declaration('simao',Association('homem','altura',1.85)), \
Declaration('darwin',Association('homem','altura',1.75))] )

def test_exercicio13(sn_net):
    assert compare_decl_lists(sn_net.query_down('vertebrado', 'altura'), [
Declaration('descartes',Association('mamifero','altura',1.2)), \
Declaration('descartes',Association('homem','altura',1.75)), \
Declaration('simao',Association('homem','altura',1.85)), \
Declaration('darwin',Association('homem','altura',1.75))] )

    assert compare_decl_lists(sn_net.query_down('mamifero', 'altura'), [
Declaration('descartes',Association('homem','altura',1.75)), \
Declaration('simao',Association('homem','altura',1.85)), \
Declaration('darwin',Association('homem','altura',1.75))] )

    assert compare_decl_lists(sn_net.query_down('homem', 'altura'), [])


