//João Gameiro  Nº93097
//P3-ECT-UA

package aula5.Ex2;

public abstract class Motorizado extends Veiculo{
	
	protected int cilindrada;
	protected int Vmax;
	protected String matricula;
	protected double consumo;
	protected double combustivel;
	
	//Construtor
	public Motorizado(int ano, Cor cor, int numRodas, int cilindrada, int Vmax, String matricula, double consumo, double combustivel)
	{
		super(ano, cor, numRodas);
		this.cilindrada = cilindrada;
		this.Vmax = Vmax;
		this.matricula = matricula;
		this.consumo = consumo;
		this.combustivel = combustivel;
	}
	
	public int getPotencia() { return cilindrada; }
	public double getConsumo() { return consumo; }
	public double getCombustivel() { return combustivel; }
	public int Vmax() { return Vmax; }
	public String matricula() { return matricula; }
	
	
}
