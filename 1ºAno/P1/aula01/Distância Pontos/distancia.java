//João Gameiro  Nº93097
//P1-ECT-UA

import java.util.Scanner;
public class distancia
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner (System.in);
		double x1, y1, x2, y2;
		double dist;
		
		System.out.printf("Indique as coordenadas do primeiro ponto A(cm): ");
		x1=ler.nextDouble();
		y1=ler.nextDouble();
		System.out.printf("Indique as coordenadas do segundo ponto B(cm): ");
		x2=ler.nextDouble();
		y2=ler.nextDouble();
		
		dist=Math.sqrt((Math.pow((x2-x1),2.0))+(Math.pow((y2-y1),2.0)));
		dist=dist*100;
		
		System.out.printf("A distância entre A e B é de %4.2f km", dist);
	}
}
