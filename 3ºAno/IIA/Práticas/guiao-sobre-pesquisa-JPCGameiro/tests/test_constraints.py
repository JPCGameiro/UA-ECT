import pytest
import mapas
import amigos

def test_exercicio1_4():
    assert mapas.cs.search() == {'A': 'red', 'B': 'blue', 'C': 'red', 'D': 'blue', 'E': 'green'}

def test_exercicio1_5():
    solution = amigos.cs.search()

    for amigo, (bicicleta, chapeu) in solution.items():
        assert amigo != bicicleta
        assert amigo != chapeu
        if chapeu == "Claudio":
            assert bicicleta == "Bernardo"
        
    bicicletas = [ bicicleta for _, (bicicleta, _) in solution.items() ]
    assert len(bicicletas) == len(set(bicicletas))

    chapeus = [ chapeu for _, (_, chapeu) in solution.items() ]
    assert len(chapeus) == len(set(chapeus))


def test_exercicio2():
    assert amigos.cs.calls == 3

