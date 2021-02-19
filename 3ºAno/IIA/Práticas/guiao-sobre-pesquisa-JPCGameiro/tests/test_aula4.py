import pytest
from cidades import SearchProblem, SearchTree, cidades_portugal

@pytest.fixture
def braga_faro():
    return SearchProblem(cidades_portugal,'Braga','Faro')

def test_exercicio7(braga_faro):
    assert cidades_portugal.cost('Aveiro', ('Aveiro', 'Agueda')) == 35 
    assert cidades_portugal.cost('Agueda', ('Agueda', 'Aveiro')) == 35
    assert cidades_portugal.cost('Aveiro', ('Aveiro', 'Lisboa')) == None 

def test_exercicio8(braga_faro):
    t = SearchTree(braga_faro, 'depth')

    assert t.search() == ['Braga', 'Porto', 'Agueda', 'Aveiro', 'Coimbra', 'Leiria', 'Castelo Branco', 'Santarem', 'Lisboa', 'Evora', 'Beja', 'Faro']
    assert t.solution.cost == 1104 

def test_exercicio9(braga_faro):
    t = SearchTree(braga_faro, 'depth')

    assert t.search() == ['Braga', 'Porto', 'Agueda', 'Aveiro', 'Coimbra', 'Leiria', 'Castelo Branco', 'Santarem', 'Lisboa', 'Evora', 'Beja', 'Faro']
    assert t.cost == 1104 

def test_exercicio10(braga_faro):
    t = SearchTree(braga_faro, 'uniform')

    assert t.search() == ['Braga', 'Porto', 'Agueda', 'Coimbra', 'Leiria', 'Santarem', 'Evora', 'Beja', 'Faro'] 
    assert t.cost == 706 
    assert t.length == 8 
