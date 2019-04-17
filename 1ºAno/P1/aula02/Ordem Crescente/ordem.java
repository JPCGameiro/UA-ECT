import java.util.Scanner;
public class ordem
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner(System.in);
		int n1, n2, n3;
		
		System.out.printf("Digite três números: ");
		n1=ler.nextInt();
		n2=ler.nextInt();
		n3=ler.nextInt();
		 if(n1>n2 && n2>n3)
		 {
			 System.out.printf("%d < %d < %d ",n3, n2, n1);
		 }
		 else if(n1>n3 && n3>n2)
		 {
			 System.out.printf("%d < %d < %d ", n2, n3, n1);
		 }
		 else if(n2>n1 && n1>n3)
		 {
			 System.out.printf("%d < %d < %d ", n3, n1, n2);
		 }
		 else if(n2>n3 && n3>n1)
		 {
			 System.out.printf("%d < %d < %d ", n1, n3, n2);
		 }
		 else if(n3>n2 && n2>n1)
		 {
			 System.out.printf("%d < %d < %d " ,n1, n2, n3);
		 }
		 else if(n3>n1 && n1>n2)
		 {
			 System.out.printf("%d < %d < %d ", n2, n1, n3);
		 }
	 }
 }
		
		
