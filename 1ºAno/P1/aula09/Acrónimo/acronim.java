import java.util.Scanner;
public class acronim
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner(System.in);
		String acr;
		String a[] = new String[999999];
		int i=0, n=0;
		
		System.out.printf("Digite os nomes que deseja transformar em acrónimo (termina com linha vazia)\n");
		do
		{
			acr=ler.nextLine();
			a[i]=ACR(acr);
			i++;
		}
		while(acr.length()!=0);
		for(n=0;n<i;n++)
		{
			System.out.print(a[n]+" ");
		}
	
	}
	
	public static String ACR(String fr)
	{
		Scanner ler = new Scanner (System.in);
		int pos=0;
		String acron="";
		
		for(pos=0;pos<fr.length();pos++)
		{
			char letra = fr.charAt(pos);
			if(Character.isUpperCase(letra))
			{
				acron=acron+letra;
			}
		}
		return acron;		
	}
}
		
	
		
		
			
			
				
		
		
