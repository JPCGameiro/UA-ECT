//João Gameiro  Nº93097
//Problema 2.1

package aula2.Ex1;
import java.util.*;

public class Ex1 {
	
	//Scanner e LinkedList para armazenar os filmes
	static  Scanner ler = new Scanner(System.in);
	static LinkedList<Video> Movies = new LinkedList<Video>();
	
	//Criação de um Utilizador que possui duas ArrayLists para armazenar Estudantes e Funcionários
	static LinkedList<Estudantes> elist = new LinkedList<Estudantes>();
	static LinkedList<Funcionarios> flist = new LinkedList<Funcionarios>();
	static Utilizador Users = new Utilizador(flist, elist);
	
	//Atribuição dos números de sócios sequenciais
	static int number = 0;
	static int movienumber = 0;
	static int N=0;
	
	
	public static void main(String[] args) {
		
		int option = -1;
		System.out.print("Insira o NºMáximo de empréstimos por utilizador: ");
		N = ler.nextInt();
		
		do
		{
			//Menu
			System.out.println("------------------------------------");
			System.out.println("|            VIDEOCLUBE            |");
			System.out.println("------------------------------------");
			System.out.println("|1 -> Introduzir um novo utilizador|");
			System.out.println("|2 -> Remover um utilizador        |");
			System.out.println("|3- > Listar os utilizadores       |");
			System.out.println("|4 -> Introduzir um filme          |");
			System.out.println("|5 -> Remover um filme             |");
			System.out.println("|6 -> Listar todos os filmes       |");
			System.out.println("|7 -> Pesquisa de um filme         |");
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
					printUsers();
					break;
				
				case 4:
					addMovie();
					break;
				case 5:
					removeMovie();
					break;
					
				case 6:
					printMovies();
					break;
				
				case 7:
					searchMovie();
					break;
				
				case 8:
					checkout();
					break;
				
				case 9:
					checkin();
					break;
				
				case 10:
					listRatings();
					break;
				
				case 11:
					historyMovie();
					break;
					
				case 12:
					System.out.println("Sistema a encerrar. . .");
					System.exit(0);
					break;
					
				default:
					System.out.println("ERRO: Opção inválida\n");
					break;
			}
		}
		while(option!=12);
	}
	
	//--------------------------FUNÇÕES-AUXILIARES-----------------------------------//
	
	
	//-----------------------------------------------
	//Opção 1----------------------------------------
	//Adiconar um utilizador(Estudante ou Funcionário)
	public static void addUser()
	{
		int op = -1;
		
		do
		{
			System.out.print("Estudante(0) ou Funcionario(1): ");
			op = ler.nextInt();
		}
		while(op!=0 && op!=1);

		System.out.print("Nome: ");
		ler.nextLine();
		String nome = ler.nextLine();
		System.out.print("Cartão de Cidadão: ");
		int cc = ler.nextInt();
		System.out.print("Nº de Sócio: ");
		System.out.print(number + "\n");
		System.out.print("Idade: ");
		int age = ler.nextInt();
		System.out.print("Data de inscrição\nDia: ");
		int di = ler.nextInt();
		System.out.print("Mês: ");
		int mi = ler.nextInt();
		System.out.print("Ano: ");
		int ai = ler.nextInt();
		System.out.print("Data de nascimento\nDia: ");
		int dn = ler.nextInt();
		System.out.print("Mês: ");
		int mn = ler.nextInt();
		System.out.print("Ano: ");
		int an = ler.nextInt();
		Data dins = new Data(di, mi, ai);
		Data dnasc = new Data(dn, mn, an);
		
		if(op==0)
		{
			System.out.print("NºMecanográfico: ");
			int nmec = ler.nextInt();
			ler.nextLine();
			System.out.print("Curso: ");
			String curso = ler.nextLine();
			Users.elist().add(new Estudantes(nome, cc, number, age, dins, dnasc, nmec, curso, new LinkedList<Video>(), 0));
			System.out.println("Estudante adicionado com sucesso!\n");
			number++;
		}
		else
		{
			System.out.print("NºFuncionário: ");
			int nfunc = ler.nextInt();
			System.out.print("NºFiscal: ");
			int nfisc =  ler.nextInt();
			Users.flist().add(new Funcionarios(nome, cc, number, age, dins, dnasc, nfunc, nfisc, new LinkedList<Video>(), 0));
			System.out.println("Funcionário adicionado comm sucesso!\n");
			number++;
			ler.nextLine();
		}			
	}
	
	
	//-----------------------------------------------
	//Opção 2----------------------------------------
	//Remover um utilizador estudante ou funcionário
	public static void removeUser()
	{
		boolean done=false;
		int num = number+1;
		
		if(Users.elist().size()==0 && Users.flist().size()==0)
		{
			System.out.println("Lista Vazia\n");
		}
		else
		{		
			System.out.print("Insira o NºSócio: ");
			num = ler.nextInt();
			
			//percorrer a lista dos estudantes
			for(int i=0;i<Users.elist().size();i++)
			{
				if(Users.elist().get(i).numsocio()==num)
				{
					Users.elist().remove(i);
					done = true;
				}
			}
			//percorrer a lista dos funcionários
			if(done==false)
			{
				for(int i=0;i<Users.flist().size();i++)
				{
					if(Users.flist().get(i).numsocio()==num)
					{
						Users.flist().remove(i);
						done = true;
					}
				}
			}
			
			if(done == true)
				System.out.println("Utilizador removido com sucesso!\n");
			else
				System.out.println("ERRO: Número Inválido\n");
		}
	}
	
	
	//-----------------------------------------------
	//Opção 3----------------------------------------
	//Listar todos os utilizadores
	public static void printUsers()
	{
		int sizeE = Users.elist().size();
		int sizeF = Users.flist().size();
		
		if(sizeE==0 && sizeF==0)
			System.out.println("Lista Vazia\n");
		else
		{
			if(sizeE!=0)
			{
				System.out.println("-> Estudantes");
				for(int i=0;i<sizeE;i++)
					System.out.print(Users.elist().get(i).toString() + "\n");
			}
			if(sizeF!=0)
			{
				System.out.println("-> Funcionários");
				for(int i=0;i<sizeF;i++)
					System.out.print(Users.flist().get(i).toString() + "\n");
			}
		}
	}
	
	
	//-----------------------------------------------
	//Opção 4----------------------------------------
	//Introdução de um filme no sistema
	public static void addMovie()
	{
		System.out.print("ID: " + movienumber+"\n");
		ler.nextLine();
		System.out.print("Título: ");
		String title = ler.nextLine();
		int opcat;
		int opage;
		do
		{
			System.out.println("Categoria: 1-Ação, 2-Comédia, 3-Infantil, 4-Drama, 5-Terror");
			opcat = ler.nextInt();
		}
		while(opcat!=1 && opcat!=2 && opcat!=3 && opcat!=4 && opcat!=5);
		do
		{
			System.out.println("Idade: 0-ALL, 6-M6, 12-M12, 16-M16, 18-M18");
			opage = ler.nextInt();
		}
		while(opage!=0 && opage!=6 && opage!=12 && opage!=16 && opage!=18);
		
		Movies.add(new Video(movienumber, title, catSelect(opcat), opage, -1, new LinkedList<Integer>()));
		System.out.println("Filme inserido com sucesso!\n");		
		movienumber++;
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
	
	
	//-----------------------------------------------
	//Opção 5----------------------------------------
	//Impressão de todos os filmes
	public static void removeMovie()
	{
		boolean done = false;
		System.out.print("Insira o ID do filme: ");
		int id = ler.nextInt();
		
		for(int i=0;i<Movies.size();i++)
		{
			if(Movies.get(i).id()==id)
			{
				Movies.remove(id);
				done = true;
			}
		}
		if(done==true)
		{
			System.out.println("Filme removido com sucesso!\n");
		}
		else
			System.out.println("ERRO: Id inválido\n");
	}
	
	
	
	//-----------------------------------------------
	//Opção 6----------------------------------------
	//Impressão de todos os filmes
	public static void printMovies()
	{
		if(Movies.size()==0)
			System.out.println("Lista Vazia\n");
		else
		{
			for(int i=0;i<Movies.size();i++)
				System.out.println(Movies.get(i).toString());
		}
	}
	
	
	//-----------------------------------------------
	//Opção 7----------------------------------------
	//Pesquisa de um filme e apresentação da sua disponibilidade
	public static void searchMovie()
	{
		System.out.print("Insira o ID: ");
		int id = ler.nextInt();
		boolean done = false;
		int i;
		
		for(i=0;i<Movies.size();i++)
		{
			if(Movies.get(i).id()==id) {
				done=true;
				break;
			}
		}
		
		if(done==true) {
			System.out.println(Movies.get(i).toString());
			if(Movies.get(i).available()==-1)
				System.out.println("->Filme disponível\n");
			else
				System.out.println("->Filme indisponível\n");
		}
		else
			System.out.println("ERRO: Número inválido\n");
	}
	
	
	//-----------------------------------------------
	//Opção 8----------------------------------------
	//Requisição de um filme
	public static void checkout()
	{
		int i=0;
		boolean done=false, emp=false;
		
		try {
			System.out.print("Insira o ID: ");
			int n=ler.nextInt();
			if(Movies.get(n).available==-1) {
				System.out.print("Insira o NºSócio: ");
				int num = ler.nextInt();
				for(i=0;i<elist.size();i++) {
					if(elist.get(i).num<N){
						if(elist.get(i).numsocio()==num) {
							Movies.get(n).available = num;
							done = true;
							elist.get(i).history().add(Movies.get(n));
							elist.get(i).num = elist.get(i).num()+1;
						}
					}
					else {
						emp = true;
						System.out.println("Número máximo de empréstimos atingido\n");
					}
				}
				
				if(done == false) {
					for(i=0;i<flist.size();i++) {
						if(flist.get(i).num<N) {
							if(flist.get(i).numsocio()==num) {
								Movies.get(n).available = num;
								done = true;
								flist.get(i).history().add(Movies.get(n));
								flist.get(i).num = flist.get(i).num()+1;
							}
						}
						else {
							emp = true;
							System.out.println("Número máximo de empréstimos atingido\n");
						}
					}
				}
			}
			else {
				System.out.println("ERRO: Filme já foi requisitado!\n");
			}
		}
		catch(Exception e){
			System.out.println("ERRO: Número inválido!\n");
		}
		
		if(emp==false) {
			if(done == true)
				System.out.println("Filme requisitado com sucessso!\n");
			else
				System.out.println("ERRO: NºSócio Inválido!\n");
		}
		
	}
	
	
	
	//-----------------------------------------------
	//Opção 8----------------------------------------
	//Devolução de um filme
	public static void checkin()
	{		
		int r;
		System.out.print("Insira o ID do filme a devolver: ");
		int id = ler.nextInt();
		
		try {
			if(Movies.get(id).available()!=-1) {
				Movies.get(id).available = -1;
				do {
				System.out.print("Insira a classificação[0,10]: ");
				r=ler.nextInt();
				}while(r<0 && r>10);
				Movies.get(id).ratings.add(r);
				System.out.println("Filme devolvido com sucesso!\n");
			}
			else
				System.out.println("ERRO: Filme não foi requisitado\n");
		}
		catch(Exception e){
			System.out.println("ERRO: Número ID inválido!\n");
		}
	}
	
	
	//-----------------------------------------------
	//Opção 10----------------------------------------
	//Listar filmes pelo rating médio
	public static void listRatings()
	{
		LinkedList<Video> cln = new LinkedList<Video>();
		for(int z=0;z<Movies.size();z++)
			cln.add(Movies.get(z));

		int i=0, n=0, z=0;
		for (i=0;i<cln.size();i++)
		{
			for(n=i+1;n<cln.size();n++)
			{
				Video mv1 = cln.get(i);
				Video mv2 = cln.get(n);
				if(mv2.midRating()>mv1.midRating())
				{
					cln.set(i, mv2);
					cln.set(n, mv1);
				}
			}
		}
		
		for(z=0;z<cln.size();z++) {
			System.out.println(cln.get(z).ratingString());
		}		
		
	}
	
	//-----------------------------------------------
	//Opção 11----------------------------------------
	//Ver o histórico de filmes de um utilizador
	public static void historyMovie()
	{
		int op;
		
		do {
			System.out.print("Estudante(0) ou Funcionário(1): ");
			op = ler.nextInt();
		}while(op!=0 && op!=1);
		
		ler.nextLine();
		System.out.print("NºSócio: ");
		int n = ler.nextInt();
		
		try {
			if(op==0) {
				if(elist.get(n).history().size()!=0){
					for(int i=0;i<elist.get(n).history().size();i++) {
						System.out.println(elist.get(n).history().get(i).toString());
					}
				}
				else {
					System.out.println("ERRO: Nenhum empréstimo realizado!\n");
				}
			}
			else {
				if(flist.get(n).history().size()!=0) {
					for(int i=0;i<flist.get(n).history().size();i++) {
						System.out.println(flist.get(n).history().get(i).toString());
					}
				}
				else
					System.out.println("ERRO: Nenhum empréstimo realizado!\n");
			}
		}
		catch(Exception e) {
			System.out.print("ERRO: Número Inválido!\n");
		}
	}
	
}
