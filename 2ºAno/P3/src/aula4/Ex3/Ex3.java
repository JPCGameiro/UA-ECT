//João Gameiro  Nº93097
//P3-ECT-UA

package aula4.Ex3;
import java.util.*;

import aula4.Ex3.Data;
import aula4.Ex3.Estudante;
import aula4.Ex3.Funcionarios;
import aula4.Ex3.Video;

public class Ex3 {
	
	//scanner
	static Scanner ler = new Scanner(System.in);
	//Lista de vídeos e de utilizadores
	static LinkedList<Video> Videos = new LinkedList<Video>();
	static LinkedList<Utilizador> Users = new LinkedList<Utilizador>();
	//Variáveis para controlar os ids sequencias dos filmes e dos utilizadores
	static int userid = 0;
	static int videoid = 0;
	//Número máximo de empréstimos por utilizador
	static int max;
	
	public static void main(String[] args) {
		
		int option = -1;
		
		System.out.print("Insira o Nº máximo de empréstimo: ");
		max = ler.nextInt();
		
		//MENU
		do 
		{
			System.out.println("------------------------------------");
			System.out.println("|            VIDEOCLUBE            |");
			System.out.println("------------------------------------");
			System.out.println("|1 -> Introduzir um novo utilizador|");
			System.out.println("|2 -> Remover um utilizador        |");
			System.out.println("|3- > Listar os utilizadores       |");
			System.out.println("|4 -> Introduzir um filme          |");
			System.out.println("|5 -> Remover um filme             |");
			System.out.println("|6 -> Listar todos os filmes       |");
			System.out.println("|7 -> Disponibilidade dos filmes   |");
			System.out.println("|8 -> Requisição de um filme       |");
			System.out.println("|9 -> Devolução de um filme        |");
			System.out.println("|10-> Listar filmes por rating     |");
			System.out.println("|11-> Histórico de um utilizador   |");
			System.out.println("|12 -> Encerrar                    |");
			System.out.println("------------------------------------");
			System.out.print("Opção-> ");
			option = ler.nextInt();
			
			
			switch(option)
			{
				case 1:
					addUser();
					break;
				case 2:
					removeUser();
					break;
				case 3:
					listUsers();
					break;	
				case 4:
					addVideo();
					break;
				case 5:
					removeVideo();
					break;
				case 6:
					listVideos();
					break;
				case 7:
					listAvailable();
					break;
				case 8:
					checkout();
					break;
				case 9:
					checkin();
					break;
				case 10:
					ratingsVideos();
					break;		
				case 11:
					printHistory();
					break;
				case 12:
					System.out.println("Sistema a encerrar...");
					System.exit(0);
					break;
					
				default:
					System.out.println("Opção Inválida\n");
			}

		}
		while(option!=12);
	}
	
	
	
	//----------------------------------------------------------------------//
	//--------------------------FUNÇÕES-ADICIONAIS--------------------------//
	//----------------------------------------------------------------------//
		
	
	//-----------------------//
	//--------OPÇÃO1---------//
	//Adicionar-utilizador---//
	//-----------------------//
	public static void addUser()
	{
		int op = -1;
		userid++;
		
		do {
			System.out.print("Estudante(0) ou Funcionario(1): ");
			op = ler.nextInt();
		}
		while(op!=0 && op!=1);
			
		System.out.println("Nº de Sócio: " + userid);
		System.out.print("Nome: ");
		ler.nextLine();
		String nome = ler.nextLine();
		System.out.print("Cartão de Cidadão: ");
		int cc = ler.nextInt();
		System.out.print("Data de nascimento\nDia: ");
		int dn = ler.nextInt();
		System.out.print("Mês: ");
		int mn = ler.nextInt();
		System.out.print("Ano: ");
		int an = ler.nextInt();
		Data dnasc = new Data(dn, mn, an);
		if(op==0) {
			System.out.print("NºMecanográfico: ");
			int nmec = ler.nextInt();
			ler.nextLine();
			System.out.print("Curso: ");
			String curso = ler.nextLine();
			Users.add(new Estudante(nome, cc, dnasc, userid, new LinkedList<Video>(), nmec, curso));
			System.out.println("Estudante adicionado com sucesso!\n");
			}
		else {
			System.out.print("NºFuncionário: ");
			int nfunc = ler.nextInt();
			System.out.print("NºFiscal: ");
			int nfisc =  ler.nextInt();
			Users.add(new Funcionarios(nome, cc, dnasc, userid, new LinkedList<Video>(), nfunc, nfisc));
			System.out.println("Funcionário adicionado comm sucesso!\n");
			ler.nextLine();
		}
	}
	

	//----------------------//
	//--------OPÇÃO2--------//
	//Remover-utilizador----//
	//----------------------//
	public static void removeUser()
	{		
		boolean undone = false;
		System.out.print("Insira o NºSócio: ");
		int num = ler.nextInt();
		
		for(int i=0;i<Users.size();i++)
			if(Users.get(i).numsoc() == num) {
				Users.remove(i);
				undone = true;
			}
		
		if(undone)
			System.out.println("Utilizador removido com sucesso!\n");
		else
			System.out.println("ERRO: NºSócio Inválido!\n");
	}
	
	
	//----------------------//
	//--------OPÇÃO3--------//
	//Mostrar-utilizadores--//
	//----------------------//
	public static void listUsers()
	{
		if(Users.size()==0)
			System.out.println("Ainda não foram adicionados utilizadores\n");
		else {
			for(int i=0;i<Users.size();i++)
				System.out.println(Users.get(i).toString());
			System.out.println();
		}
	}
	
	
	//----------------------//
	//--------OPÇÃO4--------//
	//Inserir-filme---------//
	//----------------------//
	public static void addVideo()
	{
		videoid++;
		int opcat, opage;
			
		System.out.print("ID: " + videoid+"\n");
		ler.nextLine();
			
		System.out.print("Título: ");
		String title = ler.nextLine();
		do {
			System.out.print("Categoria: 1-Ação, 2-Comédia, 3-Infantil, 4-Drama, 5-Terror: ");
			opcat = ler.nextInt();
		}
		while(opcat!=1 && opcat!=2 && opcat!=3 && opcat!=4 && opcat!=5);
			
		do {
			System.out.print("Idade: 0-ALL, 6-M6, 12-M12, 16-M16, 18-M18: ");
			opage = ler.nextInt();
		}
		while(opage!=0 && opage!=6 && opage!=12 && opage!=16 && opage!=18);
		Videos.add(new Video(videoid, title, catSelect(opcat), opage, -1, new LinkedList<Integer>()));
		System.out.println("Filme inserido com sucesso!\n");
	}
	//Selecionar a categoria
	public static String catSelect(int i)
	{
		if(i==1)
			return "Ação";
		else if(i==2)
			return "Comédia";
		else if(i==3)
			return "Infantil";
		else if(i==4)
			return "Drama";
		else
			return "Terror";
	}
	
	
	//----------------------//
	//--------OPÇÃO5--------//
	//Remover-filme---------//
	//----------------------//
	public static void removeVideo()
	{
		boolean done = false;
		System.out.print("Insira o ID do filme: ");
		int id = ler.nextInt();
		
		for(int i=0;i<Videos.size();i++) {
			if(Videos.get(i).id()==id) {
				Videos.remove(i);
				done = true;
			}
		}
		if(done)
			System.out.println("Filme removido com sucesso!\n");
		else
			System.out.println("ERRO: ID inválido\n");
	}
	
	
	//----------------------//
	//--------OPÇÃO6--------//
	//Listar-filmes---------//
	//----------------------//
	public static void listVideos()
	{
		if(Videos.size()==0)
			System.out.println("Ainda não foram adicionados filmes\n");
		else
			for(int i=0;i<Videos.size();i++)
				System.out.println(Videos.get(i).toString());
	}
	


	//----------------------//
	//--------OPÇÃO7--------//
	//Disponibilidade-------//
	//----------------------//
	public static void listAvailable()
	{
		if(Videos.size()==0)
			System.out.println("Ainda não foram adicionados filmes\n");
		else {
			for(int i=0;i<Videos.size();i++)
				System.out.println(Videos.get(i).availableString());
		}
	}
	
	
	
	//----------------------//
	//--------OPÇÃO8--------//
	//Requisição------------//
	//----------------------//
	public static void checkout()
	{
		System.out.print("Insira o ID do filme: ");
		int id = ler.nextInt();
		System.out.print("Insira o NºSócio: ");
		int ns = ler.nextInt();	
		
		if(validId(id)==-1)
			System.out.println("ERRO: ID inválido!\n");
		else {
			if(validNum(ns)==-1)
				System.out.println("ERRO: NºSócio inválido!\n");
			else {
				if(Videos.get(validId(id)).available !=-1)
					System.out.println("ERRO: Filme indisponível!\n");
				else {
					if((Users.get(validNum(ns)).currentVideos+1)>max)
						System.out.println("Valor máximo de empréstimos atingido\n");
					else {
						Videos.get(validId(id)).available = Users.get(validNum(ns)).numsoc();
						Users.get(validNum(ns)).currentVideos++;
						System.out.println("Filme requisitado com sucesso!\n");
					}
				}
			}
		}
	}
	//Funções auxiliares para verificar se um id ou numsócio existem - devolvem o index ou -1 caso inválido
	public static int validId(int id)
	{
		int check=-1;
		for(int i=0;i<Videos.size();i++) {
			if(Videos.get(i).id() == id) {
				check = i;
				break;
			}
		}
		return check;
	}
	public static int validNum(int numsoc)
	{
		int check=-1;
		for(int i=0;i<Users.size();i++) {
			if(Users.get(i).numsoc() == numsoc) {
				check = i;
				break;
			}
		}
		return check;
	}
	
	
	
	//----------------------//
	//--------OPÇÃO9--------//
	//Devolução-------------//
	//----------------------//
	public static void checkin()
	{
		int i=0, r, index;
		boolean check = false;
		System.out.print("Insira o ID do filme a devolver: ");
		int id = ler.nextInt();
		for(i=0;i<Videos.size();i++) {
			if(Videos.get(i).id()==id) {
				check = true;
				break;
			}
		}
		
		if(check) {
			index = validNum(Videos.get(i).available);  //index do nºsócio
			if(Videos.get(i).available()!=-1) {
				Videos.get(i).available = -1;
				do {
					System.out.print("Insira a classificação [0,10]: ");
					r = ler.nextInt();
				}
				while(r<0 || r>10);
				Videos.get(i).ratings().add(r);
				Users.get(index).videosVistos.add(Videos.get(i));
				Users.get(index).currentVideos--;
				System.out.println("Filme devolvido com sucesso!\n");
			}
			else
				System.out.println("ERRO: O filme especificado não foi requesitado!\n");
		}
		else
			System.out.println("ERRO: ID inválido!\n");
	}
	
	
	
	
	//----------------------//
	//--------OPÇÃO10-------//
	//Ratings---------------//
	//----------------------//
	public static void ratingsVideos()
	{
		
		LinkedList<Video> cln = new LinkedList<Video>();
		for(int z=0;z<Videos.size();z++)
			cln.add(Videos.get(z));

		int i=0, n=0, z=0;
		for (i=0;i<cln.size();i++) {
			for(n=i+1;n<cln.size();n++)	{
				Video mv1 = cln.get(i);
				Video mv2 = cln.get(n);
				if(mv2.averageRating()>mv1.averageRating())	{
					cln.set(i, mv2);
					cln.set(n, mv1);
				}
			}
		}
		
		for(z=0;z<cln.size();z++) {
			System.out.println(cln.get(z).ratingString());
		}	
	}
	
	
	
	//----------------------//
	//--------OPÇÃO11-------//
	//Histórico-------------//
	//----------------------//
	public static void printHistory()
	{
		System.out.print("Insira o NºSócio: ");
		int n = ler.nextInt();
		int index = validNum(n); 
		if(index==-1)
			System.out.println("ERRO: NºSócio Inválido!\n");
		else {
			if(Users.get(index).videosVistos.size() == 0)
				System.out.println("O utilizador ainnda não efectuou requisições\n");
			else {
				System.out.println("NºSócio: "+Users.get(index).numsoc+" | Nome: "+Users.get(index).nome);
				for(int i=0;i<Users.get(index).videosVistos.size();i++) {
					System.out.println(Users.get(index).videosVistos.get(i).toString());
				}
			}
		}
	}
}
