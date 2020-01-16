package aula3.Ex3;

public class Ligeiro extends Veiculo{
	
	private boolean atrelado;
	
	//Construtor
	public Ligeiro(Condutor condutor, Carta tipo, int lotacao, double cilindrada, double potencia, double pesoBruto, boolean atrelado)
	{
		super(condutor, tipo, lotacao, cilindrada, potencia, pesoBruto);
		this.atrelado = atrelado;
	}
	
	public boolean atrelado() { return atrelado; }
}
