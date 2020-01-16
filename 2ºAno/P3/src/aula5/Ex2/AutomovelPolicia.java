//João Gameiro  Nº93097
//P3-ECT-UA

package aula5.Ex2;

public class AutomovelPolicia extends Automovel implements Policia{
	
	private Tipo tipo;
	private String id;
	
	public AutomovelPolicia(int ano, Cor cor, int numRodas, int cilindrada, int Vmax, String matricula, double consumo, double combustivel, boolean atrelado, Tipo tipo, String id)
	{
		super(ano, cor, numRodas, cilindrada, Vmax, matricula, consumo, combustivel, atrelado);
		this.tipo = tipo;
		this.id = id;
	}
	
	public Tipo getTipo() { return tipo; }
	public String getId() { return id; }
	
	@Override public String toString()
	{
		return tipo+" | "+super.toString(); 
	}
}
