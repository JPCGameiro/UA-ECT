//João Gameiro  Nº93097
//P3 - ECT - UA

package aula3.Ex3;

public class Condutor extends Pessoa{
		
	private Carta carta;
	
	//Construtor
	public Condutor(String nome, int cc, Data dataNasc, Carta carta)
	{
		super(nome, cc, dataNasc);
		this.carta = carta;
	}
	
	public Carta carta() { return carta; }
	
	//Função para devolver uma String com as caracteristicas do condutor
	@Override public String toString()
	{
		return super.toString()+" | Carta: "+carta.tipo();
	}	
}
