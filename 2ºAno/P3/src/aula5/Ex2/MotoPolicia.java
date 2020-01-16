//João Gameiro  Nº93097
//P3-

package aula5.Ex2;

public class MotoPolicia extends Moto implements Policia{
	
	private Tipo tipo;
	private String id;
	
	//Construtor
	public MotoPolicia(int ano, Cor cor, int numRodas, int cilindrada, int Vmax, String matricula, double consumo, double combustivel, boolean sidecar, Tipo tipo, String id)
	{
		super(ano, cor, numRodas, cilindrada, Vmax, matricula, consumo, combustivel, sidecar);
		this.tipo = tipo;
		this.id = id;
	}
	
	public Tipo getTipo() { return tipo; }
	public String getId() { return id; }
	
	@Override public String toString()
	{
		return tipo+" | "+ super.toString();
	}
}
