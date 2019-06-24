from PIL import Image
import sys
import os.path

#função para verificar se um valor (x,y) pertence a uma caixa formada por (x1,y1) e (x2,y2)
def belongs_box(x, y, x1, y1, x2, y2):
	
	if((int(x)>=int(x1) and int(x)<=int(x2)) and (int(y)>=int(y1) and int(y<=int(y2)))):
		return True
	else:
		return False
		
			
#função para extrair os objetos das imagens
def main(fname, x1, y1, x2, y2):
	im = Image.open(fname)
	
	width, height = im.size	
	new_im = Image.new(im.mode, im.size)
	
	for x in range(width):
		for y in range(height):
			if(belongs_box(x, y, x1, y1, x2, y2) == True):       #verificar se o pixel (x,y) pertence à caixa
				p = im.getpixel( (x,y) )
				new_im.putpixel( (x,y), (p) )
	i=0
	name = str(fname) + str(i) + ".jpg"
	while(os.path.exists(name)):
		i=i+1
		name = str(fname) + str(i) + ".jpg"
	new_im.save(name)
main(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4], sys.argv[5])		 
