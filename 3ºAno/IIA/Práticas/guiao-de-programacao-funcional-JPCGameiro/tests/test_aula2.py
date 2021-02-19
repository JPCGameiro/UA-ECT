import mock
import types
import aula2
from aula2 import *

#Exercicio 4.1
def test_par_impar():
    assert isinstance(impar, types.FunctionType)
    assert impar(3) 
    assert not impar(4)

#Exercicio 4.2
def test_positivo():
    assert isinstance(positivo, types.FunctionType)
    assert positivo(3) 
    assert not positivo(-4)

#Exercicio 4.3
def test_comparar_modulo():
    assert isinstance(comparar_modulo, types.FunctionType)
    assert not comparar_modulo(-4, 2)
    assert comparar_modulo(3, -4) 
    
#Exercicio 4.4
def test_cart2pol():
    assert isinstance(cart2pol, types.FunctionType)
    assert cart2pol(0, 1) == (1.0, 1.5707963267948966)

#Exercicio 4.5
def test_ex5():
    assert isinstance(ex5, types.FunctionType)
    t = ex5(lambda x,y: x+y, lambda x,y: x*y, lambda x,y: x < y)
    assert isinstance(t, types.FunctionType)
    assert t(1,2,3) == True 

#Exercicio 4.6
@mock.patch('aula2.quantificador_universal', side_effect = aula2.quantificador_universal)
def test_quantificador_universal(mock_qt_uni):
    assert mock_qt_uni([11,12,13,14], lambda n: n > 10)

#Exercicio 4.9
@mock.patch('aula2.ordem', side_effect = aula2.ordem)
def test_ordem(mock_ordem):
    assert mock_ordem([1,-1,4,0], lambda x,y: x < y) == -1
    assert mock_ordem([1,-1,4,0], lambda x,y: x > y) == 4

#Exercicio 4.10
@mock.patch('aula2.filtrar_ordem', side_effect = aula2.filtrar_ordem)
def test_filtrar_ordem(mock_filtrar_ordem):
    assert mock_filtrar_ordem([1,-1,4,0], lambda x, y: x < y) == (-1, [1,4,0])
    assert mock_filtrar_ordem([1,-1,4,0], lambda x, y: x > y) == (4, [1,-1,0])

#Exercicio 5.2a
@mock.patch('aula2.ordenar_seleccao', side_effect = aula2.ordenar_seleccao)
def test_ordenar_seleccao(mock_ordenar):
    assert mock_ordenar([1,-1,4,0], lambda x, y: x < y) == [-1, 0, 1, 4]
    assert mock_ordenar([1,-1,4,0], lambda x, y: x > y) == [4, 1, 0, -1]
