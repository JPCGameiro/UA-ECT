#Exercicio 1.1
def comprimento(lista):
	if(lista == []):
		return 0
	return 1 + comprimento(lista[1:])

#Exercicio 1.2
def soma(lista):
	if(lista == []):
		return 0
	return lista[0] + soma(lista[1:])

#Exercicio 1.3
def existe(lista, elem):
	if(lista == []):
		return False
	return lista[0] == elem or existe(lista[1:], elem)

#Exercicio 1.4
def concat(l1, l2):
	if(l2 == []):
		return l1[:]
	res = concat(l1, l2[0:len(l2)-1])
	res.append(l2[len(l2)-1])
	return res

#Exercicio 1.5
def inverte(lista):
	if(lista == []):
		return []
	res = inverte(lista[1:])
	res[len(lista):] = [lista[0]]
	return res

#Exercicio 1.6
def capicua(lista):
	if(lista == []):
		return True
	return lista[0] == lista[len(lista)-1] and capicua(lista[1:len(lista)-1])

#Exercicio 1.7
def explode(lista):
	if(lista == []):
		return []
	res = explode(lista[:len(lista)-1])
	res += lista[len(lista)-1]
	return res

#Exercicio 1.8
def substitui(lista, original, novo):
	if(lista == []):
		return []

	res = substitui(lista[:len(lista)-1], original, novo)
	if(lista[len(lista)-1] == original):
		res[len(lista)-1:] = [novo]
	else:
		res[len(lista)-1:] = [lista[len(lista)-1]]
	return res

#Exercicio 1.9
def junta_ordenado(lista1, lista2):
	if(lista2 == []):
		return lista1[:]
	
	res = junta_ordenado(lista1, lista2[0:len(lista2)-1])
	res.append(lista2[len(lista2)-1])
	if(res[len(res)-1] < res[len(res)-2]):
		insert_ord(res)
	
	return res

#Função auxiliar para Exercicio 1.9
def insert_ord(lista):
	index = len(lista)-2
	while(lista[index] > lista[index+1]):
		temp = lista[index]
		lista[index] = lista[index+1]
		lista[index+1] = temp
		index -= 1
	return lista

#Exercicio 1.10
def subconjuntos(lista):
    if lista == []:
        return [[]]

    res = subconjuntos(lista[1:])
    return res + [[lista[0]] + y for y in res]




#Exercicio 2.1
def separar(lista):
	if(lista == []):
		return ([],[])
	
	result = separar(lista[0:len(lista)-1])
	result[0].append(lista[len(lista)-1][0])
	result[1].append(lista[len(lista)-1][1])
	return result

#Exercicio 2.2
def remove_e_conta(lista, elem):
	if(lista == []):
		return ([], 0)
	
	result = remove_e_conta(lista[0:len(lista)-1], elem)
	if(lista[len(lista)-1] == elem):
		aux = list(result)
		aux[1] += 1
		result = tuple(aux)
	else:
		result[0].append(lista[len(lista)-1])
	return result

#Exercicio 2.3
def contar_elementos(lista):
    if(lista == []):
        return []
    result = contar_elementos(lista[0:len(lista)-1])

    if(len(result) == 0):
        result.append((lista[0], 1))
    else:
        elem  = lista[len(lista)-1]
        max = len(result)
        check = False
        for i in range(0, max):
            if(result[i][0] == elem):
                aux = list(result[i])
                aux[1] += 1
                result[i] = tuple(aux)
                check = True
        
        if(check == False):
            result.append((elem, 1))

    return result




#Exercicio 3.1
def cabeca(lista):
	if(lista == []):
		return None
	return lista[0]

#Exercicio 3.2
def cauda(lista):
	if(lista == []):
		return None
	return lista[1:]

#Exercicio 3.3
def juntar(l1, l2):
	if(len(l1)!=len(l2)):
		return None
	elif(l1 == [] and l2 == []):
		return []
	result = juntar(l1[0:len(l1)-1], l2[0:len(l2)-1])
	result.append((l1[len(l1)-1], l2[len(l2)-1]))
	return result

#Exercicio 3.4
def menor(lista):
	if(lista == []):
		return None
	min = menor(lista[0:len(lista)-1])
	
	if(min == None):
		min = lista[0]
	if(min > lista[len(lista)-1]):
		min = lista[len(lista)-1]
	return min

#Exercicio 3.5
def min_lista(lista):
	if(lista == []):
		return None
	min = menor(lista)
	result = []
	for value in lista:
		if value != min:
			result.append(value)
	return (min, result)

#Exercicio 3.6
def max_min(lista):
	if(lista == []):
		return None
	result = max_min(lista[0:len(lista)-1])
	
	if(result == None):
		result = (lista[0], lista[0])
	if(result[0] > lista[len(lista)-1]):
		result = (lista[len(lista)-1], result[1])
	if(result[1] < lista[len(lista)-1]):
		result = (result[0], lista[len(lista)-1])
	return result

#Exercicio 3.7
def mins_lista(lista):
	if(lista == [] or len(lista) < 2):
		return None
	
	r0 = min_lista(lista)
	r1 = min_lista(r0[1])
	return (r0[0], r1[0], r1[1])

#Exercicio 3.8
def media_mediana(lista):
	if(lista == []):
		return 0
	return (lista[0] + media_mediana(lista[1:]))/len(lista)




