import java.util.Scanner;
public class contactos
{
	public static void main (String[] args)
	{
		Scanner ler = new Scanner(System.in);
		char opc;
		Contacto a[] = new Contacto[10];
		
		System.out.printf("Gestão de uma agenda:\n");
		System.out.printf("I - Inserir um contacto\n");
		System.out.printf("P - Pesquisar contacto por nome\n");
		System.out.printf("E - Eliminar um contacto\n");
		System.out.printf("M - Mostrar os contactos\n");
		System.out.printf("V - Validar endereços de email\n");
		System.out.printf("A - Apagar a agenda\n");
		System.out.printf("T - Terminar o programa\n");

		do
		{
			System.out.printf("\n\nOpção -> ");
			opc=Character.toUpperCase((ler.nextLine()).charAt(0));
			System.out.println();
			
			switch (opc)
			{
				case 'I':
				Inserir(a);
				break;
				
				case 'P':
				Pesquisar(a);
				break;
				
				case 'E':
				Eliminar(a);
				break;
				
				case 'M':
				Mostrar(a);
				break;
				
				case 'V':
				Validar(a);
				break;
				
				case 'A':
				a=Apagar(a);
				break;
				
				case 'T':
				System.out.printf("A encerrar ...");
				break;
			}
		}
		while(opc!='T');
	}
	//Função para adicionar contactos
	public static void Inserir(Contacto a[])
	{
		Scanner ler = new Scanner(System.in);
		int i=0, j=0;
		String resp;
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		j=i;
		do
		{
			Contacto f = new Contacto();
			System.out.printf("Nome do contacto: \n");
			f.nome=ler.nextLine();
			System.out.printf("Morada do contacto: \n");
			f.morada=ler.nextLine();
			System.out.printf("Número do contacto: \n");
			f.telefone=ler.nextInt();
			System.out.printf("Email do contacto: \n");
			String dumb=ler.nextLine();
			f.mail=ler.nextLine();			
			a[j]=f;
			j++;
			System.out.printf("Deseja inserir mais algum contacto?(s/n)");
			resp=ler.nextLine();
		}
		while(resp.equals("s"));		
	}
	//Função para Pesquisar um contacto pelo nome
	public static void Pesquisar(Contacto a[])
	{
		Scanner ler = new Scanner(System.in);
		String name;
		int i=0, j=0, b=-1;
		
		System.out.printf("Insira o nome do contacto que pretende procurar: ");
		name=ler.nextLine();
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		for(j=0;j<i;j++)
		{
			if(a[j].nome.equals(name))
			{
				b=j;
				break;
			}
		}
		if(b==-1)
		{
			System.out.printf("O nome digitado não pertence a nenhum contacto");
		}
		else
		{
			System.out.printf("Nome: " + a[b].nome +"\n");
			System.out.printf("Morada: " + a[b].morada +"\n");
			System.out.printf("Número: " + a[b].telefone +"\n");
			System.out.printf("Email: " + a[b].mail +"\n\n");
		}
	}
	//Função para apagar um contacto
	public static void Eliminar(Contacto a[])
	{
		Scanner ler = new Scanner(System.in);
		int num, i=0, j=0;
		
		System.out.printf("Insira o número do contacto que pretende apagar: ");
		num=ler.nextInt();
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		for(j=0;j<i;j++)
		{
			if(a[j].telefone==num)
			{
				a[j] = null;
			}
			else
			{
				System.out.printf("Número não encontrado na agenda");
				
			}
			
		}
	}		
	//Função para imprimir os contactos
	public static void Mostrar(Contacto a[])
	{
		int i=0, j=0;
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null) 
			{
				break;
			}
		}
		for(j=0;j<i;j++)
		{
			{
				System.out.printf("Nome: " + a[j].nome +"\n");
				System.out.printf("Morada: " + a[j].morada +"\n");
				System.out.printf("Número: " + a[j].telefone +"\n");
				System.out.printf("Email: " + a[j].mail +"\n\n");
			}
		}
	}
	//Função Para Validar emails
	public static Contacto[] Validar(Contacto a[])
	{
		Scanner ler = new Scanner(System.in);
		int i=0, j=0 , pos=0;
		boolean r=false, s=false, t=false, o=false;
		
		for(i=0;i<a.length;i++)
		{
			if(a[i]==null)
			{
				break;
			}
		}
		for(j=0;j<i;j++)
		{
			for(pos=0;pos<a[j].mail.length();pos++)
			{
				char b = a[j].mail.charAt(pos);
				if(b=='@')
				{
					r=true;
				}					
				else if(b=='.')
				{
					s=true;
				}
				else if(Character.isDigit(b))
				{
					t=true;
				}
				else if(Character.isLetter(b))
				{
					o=true;
				}
					
			}
			if(r==true && s==true && t==true && o==true)
			{
				System.out.printf(a[j].mail+"-> Email Válido\n");
			}
			else 
			{
				System.out.printf(a[j].mail+" -> Email Inválido\n");					
				do
				{
					System.out.printf("Insira um email novo válido: ");
					a[j].mail=ler.nextLine();
					for(pos=0;pos<a[j].mail.length();pos++)
					{
						char b = a[j].mail.charAt(pos);
						if(b=='@')
						{
							r=true;
						}					
						else if(b=='.')
						{
							s=true;
						}
						else if(Character.isDigit(b))
						{
							t=true;
						}
						else if(Character.isLetter(b))
						{
							o=true;
						}
							
					}
				}
				while(r==false || s==false || t==false || o==false);					
			}
		}
		return a;
	}
	//Função para Apagar a Agenda
	public static Contacto[] Apagar(Contacto a[])
	{
		a = new Contacto[10];
		System.out.printf("Agenda apagada com sucesso");
		return a;
	}
	
			
		
}

class Contacto
{
	String nome;
	String morada;
	int telefone;
	String mail;
}
