//João Gameiro  Nº93097
//P3-ECT-UA

package aula5.Ex2;

public class BicicletaPolicia extends Bicicleta implements Policia{
	
	private Tipo tipo;
	private String ID;
	
	//Construtor
	public BicicletaPolicia(int ano, Cor cor, int numRodas, int pneuSize, Tipo tipo, String ID)
	{
		super(ano, cor, numRodas, pneuSize);
		this.tipo = tipo;
		this.ID = ID;
	}
	
	public Tipo getTipo() { return tipo; }
	public String getId() { return ID; }
	
	@Override public String toString()
	{
		return tipo+ " | "+super.toString();
	}

}
