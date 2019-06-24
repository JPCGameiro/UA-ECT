from PIL import Image
import sys

def main(iname):
	im = Image.open(iname)
	
	width, height = im.size
	
	redcounter = 0
	bluecounter = 0
	greencounter = 0
	pinkcounter = 0
	orangecounter = 0
	yellowcounter = 0
	purplecounter = 0
	blackcounter = 0
	whitecounter = 0
	
	for x in range(width):
		for y in range(height):
			p = im.getpixel( (x,y) )
			r = p[0] & 0b11110000
			g = p[1] & 0b11110000
			b = p[2] & 0b11110000
			if(r<40 and b<40 and g<40 and r!=0 and b!=0 and g!=0):
				blackcounter = blackcounter + 1
			elif(r>220 and b>220 and g>200):
				whitecounter = whitecounter + 1 
			else:
				if(r>=g and r>b):
					if(g>b):
						if(g>=180):
							yellowcounter = yellowcounter+1
						elif(g>120 and g<170):
							orangecounter = orangecounter+1
						else:
							redcounter = redcounter+1
					elif(b>g):
						if(b>=120):
							pinkcounter = pinkcounter+1 
				elif(g>=r and g>b):
					greencounter = greencounter+1
				elif(b>=r and b>g):
					if(r>210):
						pinkcounter = pinkcounter+1
					elif(r>=100 and r<209):
						purplecounter = purplecounter+1
					else:
						bluecounter = bluecounter+1					
				
				
	finalcolor = max(redcounter, greencounter, bluecounter, yellowcounter, purplecounter, pinkcounter, orangecounter)
	
	if finalcolor == redcounter:
		print("Cor Dominante: Vermelho")
	elif finalcolor == greencounter:
		print("Cor Dominante: Verde")
	elif finalcolor == bluecounter:
		print("Cor Dominante: Azul")
	elif finalcolor == orangecounter:
		print("Cor Dominante: Laranja")
	elif finalcolor == purplecounter:
		print("Cor Dominante: Roxo")
	elif finalcolor == pinkcounter:
		print("Cor Dominante: Rosa")
	elif finalcolor == whitecounter:
		print("Cor Dominante: Branco")
	elif finalcolor == blackcounter:
		print("Cor Dominante: Preto")
	else:
		print("Cor Dominante: Amarelo")
				
main(sys.argv[1])


