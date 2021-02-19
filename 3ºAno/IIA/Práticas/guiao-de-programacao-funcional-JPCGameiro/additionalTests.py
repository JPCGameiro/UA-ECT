from aula1 import subconjuntos
from aula1 import contar_elementos
from aula1 import max_min
from aula1 import min_lista
from aula1 import mins_lista
from aula1 import media_mediana

from aula2 import quantificador_existencial
from aula2 import subconjunto
from aula2 import cart2pol_list
from aula2 import selection_sort
from aula2 import bubble_sort


#Testes Ex1 -------------------------------
print("TESTAR EXERCICIO 1.10")
lista = [1, 2, 3]
print(subconjuntos(lista))
print(max_min([]))
print("\n")


#Testes Ex2 -------------------------------
print("TESTAR EXERCICIO 2.3")
l2 = [1, 2, 3, 4, 2, 3, 4, 4, 4, 4, 3, 1, 2 ,55]
print(contar_elementos(l2))
print(l2)
print(max_min([]))
print("\n")



#Testes Ex3 -------------------------------
print("TESTAR EXERCICIO 3.5")
l2 = [-51, 222, 3, -58, 12]
print(min_lista(l2))
print(l2)
print(min_lista([]))
print("\n")

print("TESTAR EXERCICIO 3.6")
l2 = [-51, 222, 3, -5, 12222]
print(max_min(l2))
print(l2)
print(max_min([]))
print("\n")

print("TESTAR EXERCICIO 3.7")
l2 = [-51, -52, -53, 222, 3, 1, 12222, -512]
print(mins_lista(l2))
print(l2)
print(mins_lista([]))
print("\n")

print("TESTAR EXERCICIO 3.8")
l2 = [1, 2, 3, 4, 5]
print(media_mediana(l2))
print(l2)
print(mins_lista([]))
print("\n")





#Testes Ex4 -------------------------------
print("TESTAR EXERCICIO 4.7")
x = lambda num : True if(num > 0) else False
l2 = [-1, -8, -3, -4]
print(quantificador_existencial(l2, x))
print(l2)
print("\n")

print("TESTAR EXERCICIO 4.8")
l1 = [0, 2, 3]
l2 = [1, 2, 3, 4]
print(subconjunto(l1, l2))
print(l1)
print(l2)
print("\n")

print("TESTAR EXERCICIO 4.12")
l1 = cart2pol_list([(1, 2), (3, 4), (7, 8)])
print(l1)
print("\n")



#Testes Ex5 -------------------------------
print("TESTAR EXERCICIO 5.1a")
l2 = [4, 5, 1, 2, 3, 6, -9]
l1 = selection_sort(l2)
print(l1)
print("\n")

print("TESTAR EXERCICIO 5.1b")
l2 = [4, 5, 1, 2, 3, 6, -9]
bubble_sort(l2)
print(l2)
print("\n")
