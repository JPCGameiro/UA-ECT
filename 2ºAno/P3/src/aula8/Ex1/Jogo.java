//João Gameiro Nº93097
//P3-ECT-UA

package aula8.Ex1;

public class Jogo {
	
	private String JogadorX;
	private String JogadorO;
	private String[][] board;
	private int NumJogada;
	private String first;
	
	//Construtor
	public Jogo(String first)
	{
		this.JogadorX = "X";
		this.JogadorO = "O";
		this.board = new String[3][3];
		for(int i=0;i<3;i++) {
			for(int z=0;z<3;z++) {
				board[i][z]=" ";
			}
		}
		this.first = first;
		NumJogada = 0;
	}
	
	public String JogadorX() { return JogadorX; }
	public String JogadorO() { return JogadorO; }
	public String[][] board() { return board; }
	public int NumJogada() { return NumJogada; }
	public String first() { return first; }
	
	//Efectua uma jogada
	public int fazerJogada(int l, int c)
	{
		if(first.equals(JogadorX)) {							//Se o Jogador X 
			if(board[l][c].equals(" ")) {						//for o primeiro
				if(NumJogada%2==0)
					board[l][c] = JogadorX();
				else
					board[l][c] = JogadorO();
				NumJogada++;
			}
			return NumJogada;
		}		
		else {													//Se o  Jogador O
			if(board[l][c].equals(" ")) {						//for o primeiro
				if(NumJogada%2==0)
					board[l][c] = JogadorO();
				else
					board[l][c] = JogadorX();
				NumJogada++;
			}
			return NumJogada;
		}
	}
	
	//Devolve o jogador que efectuou a última jogada
	public String ultimoJogador()
	{
		if(first.equals(JogadorX)) {						//Se o Jogador X
			if(NumJogada%2==0)								//for o primeiro
				return JogadorO();
			else
				return JogadorX();
		}
		else {												//Se o Jogador O
			if(NumJogada%2==0)								//for o primeiro
				return JogadorX();
			else
				return JogadorO();
		}
	}
	
	//Verifica se o jogador identificado pela String v venceu o jogo
	public boolean vencedor(String v)
	{
		if(board[0][0].equals(v) && board[0][1].equals(v) && board[0][2].equals(v))
			return true;
		else if(board[1][0].equals(v) && board[1][1].equals(v) && board[1][2].equals(v))
			return true;
		else if(board[2][0].equals(v) && board[2][1].equals(v) && board[2][2].equals(v))
			return true;
		else if(board[0][0].equals(v) && board[1][0].equals(v) && board[2][0].equals(v))
			return true;
		else if(board[0][1].equals(v) && board[1][1].equals(v) && board[2][1].equals(v))
			return true;
		else if(board[0][2].equals(v) && board[1][2].equals(v) && board[2][2].equals(v))
			return true;
		else if(board[0][2].equals(v) && board[1][1].equals(v) && board[2][0].equals(v))
			return true;
		else if(board[0][0].equals(v) && board[1][1].equals(v) && board[2][2].equals(v))
			return true;
		else
			return false;
	}
	
	//Verifica se o jogo terminou
	public String jogoTerminou()
	{
		if(vencedor(JogadorX))
				return JogadorX;
		else if(vencedor(JogadorO))
			return JogadorO;
		else if(NumJogada() == 9)
			return "Empate";
		else
			return " ";
	}

}
