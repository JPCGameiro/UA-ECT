//João Gameiro  Nº93097
//P3 - ECT - UA

package aula3.Ex3;

public class Motociclo extends Veiculo{
	
	private boolean sidecar;
	
	//Construtor
	public Motociclo (Condutor condutor, Carta tipo, int lotacao, double cilindrada, double potencia, double pesoBruto, boolean sidecar)
	{
		super(condutor, tipo, lotacao, cilindrada, potencia, pesoBruto);
		this.sidecar = sidecar;
	}
	
	public boolean sidecar() { return sidecar; }
}
