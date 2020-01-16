//João Gameiro  Nº93097
//P3-ECT-UA

package aula5.Ex2;

public class Bicicleta extends Veiculo{
	
	private int pneuSize;
	
	//Construtor
	public Bicicleta(int ano, Cor cor, int numRodas, int pneuSize)
	{
		super(ano, cor, numRodas);
		this.pneuSize = pneuSize;
	}
	
	public int pneuSize() { return pneuSize; }
	
	@Override public String toString()
	{
		return "Bicicleta -> Ano: "+ano+" - Cor: "+cor+" - Tamanho do Pneu: "+pneuSize;
	}

}
