import pytest
from exampleApp import color_detetion


def test_red():
	print("Teste da cor vermelho")
	assert color_detetion('color/test_red.jpeg') == "red"

def test_orange():
	print("Teste da cor laranja")
	assert color_detetion('color/test_orange.jpeg') == "orange"

def test_yellow():
	print("Teste da cor amarelo")
	assert color_detetion('color/test_yellow.png') == "yellow"

def test_green():
	print("Teste da cor verde")
	assert color_detetion('color/test_green.png') == "green"

def test_blue():
	print("Teste da cor azul")
	assert color_detetion('color/test_blue.png') == "azul"

def test_purple():
	print("Teste da cor roxo")
	assert color_detetion('color/test_purple.jpeg') == "purple"

def test_black():
	print("Teste da cor preto")
	assert color_detetion('color/test_black.png') == "black"

def test_white():
	print("Teste da cor branco")
	assert color_detetion('color/test_white.png') == "white"
	
def test_pink():
	print("Teste da cor rosa")
	assert color_detetion('color/test_pink.png') == "pink"



