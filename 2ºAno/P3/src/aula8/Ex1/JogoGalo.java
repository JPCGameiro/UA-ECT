//João Gameiro  Nº93097
//P3-ECT-UA

package aula8.Ex1;

public class JogoGalo {

	public static void main(String[] args) {
		
		String f = "";
		
		if(args.length==0)								//Valor por default (X será o primeiro)
			f="X";
		else {
			if(args[0].equals("JogadorO"))				//O será o primeiro	
				f="O";
			else if(args[0].equals("JogadorX")) 		//X será o primeiro
				f="X";	
		}
			
		Jogo game = new Jogo(f);
		JanelaGalo window = new JanelaGalo(game);
		window.criarJanela();
		

	}

}
