import pytest
from cidades import SearchProblem, SearchTree, cidades_portugal

@pytest.fixture
def braga_faro():
    return SearchProblem(cidades_portugal,'Braga','Faro')

def test_exercicio11(braga_faro):
    t = SearchTree(braga_faro, 'depth')

    assert t.search() == ['Braga', 'Porto', 'Agueda', 'Aveiro', 'Coimbra', 'Leiria', 'Castelo Branco', 'Santarem', 'Lisboa', 'Evora', 'Beja', 'Faro']
    assert t.solution.heuristic == 0

    assert t.solution.parent.state == 'Beja' 
    assert round(t.solution.parent.heuristic, 2) == 140.09 

def test_exercicio12(braga_faro):
    assert round(cidades_portugal.heuristic('Aveiro', 'Agueda'),2) == 18.38
    assert round(cidades_portugal.heuristic('Agueda', 'Aveiro'),2) == 18.38
    assert round(cidades_portugal.heuristic('Aveiro', 'Lisboa'),2) == 218.87

def test_exercicio13(braga_faro):
    t = SearchTree(braga_faro, 'greedy')

    assert t.search() == ['Braga', 'Porto', 'Agueda', 'Coimbra', 'Leiria', 'Santarem', 'Evora', 'Beja', 'Faro'] 
    assert t.cost == 706 
    assert t.length == 8
    assert round(t.avg_branching,2) == round((17+8-1)/8,2)

def test_exercicio14(braga_faro):
    t = SearchTree(braga_faro, 'a*')

    assert t.search() == ['Braga', 'Porto', 'Agueda', 'Coimbra', 'Leiria', 'Santarem', 'Evora', 'Beja', 'Faro'] 
    assert t.cost == 706 
    assert t.length == 8
    assert round(t.avg_branching,2) == round((160+84-1)/84,2)
