import java.util.Scanner;

public class jogo
{
	public static void main(String[] args)
	{
		Scanner ler = new Scanner (System.in);
		String resposta;
		do
		{
		int tentativa, i=0;
		int secret = (int)(100.0*Math.random()) + 1;	
			do
			{
				System.out.printf("Adivinhe o número (entre 0 e 100) : ");
				tentativa=ler.nextInt();
				i++;
				if(tentativa<secret)
				{
					System.out.printf("Muito baixo\n");
				}
				else
				if(tentativa>secret)
				{
					System.out.printf("Muito alto\n");
				}
			}
			while(tentativa!=secret);		
			System.out.printf("Parabéns adivinhou ao fim de %2d tentativas\n", i);			
			
			System.out.printf("Deseja continuar (s/n): ");
			resposta=ler.next();
		}
		while(resposta.equals("s"));
		System.out.printf("Obrigado por jogar!");
}
}


		
				

		
		
	

			
		
		
