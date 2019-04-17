import java.util.Scanner;

public class TestaHora 
{
  static final Scanner sc = new Scanner(System.in);
  
  public static void main(String[] args) 
  {
    Hora inicio; 
    Hora fim;
    
    inicio = new Hora();
    fim = new Hora();
    inicio.h = 9;
    inicio.m = 23;
    inicio.s = 5;
    
    System.out.print("Começou às ");
    printHora(inicio);
    System.out.println(".");
    System.out.println("Quando termina?");
    fim = lerHora(); 
    System.out.print("Início: ");
    printHora(inicio);
    System.out.print(" Fim: ");
    printHora(fim);
  }
  
  public static void printHora(Hora inicio)
  {
	  System.out.print(inicio.h + ":" + inicio.m + ":" + inicio.s);
  }
  
  public static Hora lerHora()
  {
	  Hora fim;
	  fim = new Hora();
	  
	  do
	  {
		  System.out.printf("Horas?");
		  fim.h=sc.nextInt();
	  }
	  while(fim.h<0 || fim.h>=24);
	  do
	  {
		  System.out.printf("Minutos?");
	      fim.m=sc.nextInt();
	  }
	  while(fim.m<0 || fim.m>=60);
	  do
	  {
		  System.out.printf("Segundos?");
	      fim.s=sc.nextInt();
	  }
	  while(fim.s<0 || fim.s>=60);
	  return fim;	 		
  }
  
  
   
}
class Hora
{
	int h,m,s;
}

