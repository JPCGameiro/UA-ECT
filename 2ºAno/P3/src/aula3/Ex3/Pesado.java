//João Gameiro  Nº93097
//P3 - ECT - UA

package aula3.Ex3;

public class Pesado extends Veiculo{
	
	private double tara;
	
	//Construtor
	public Pesado(Condutor condutor, Carta tipo, int lotacao, double cilindrada, double potencia, double pesoBruto, double tara)
	{
		super(condutor, tipo, lotacao, cilindrada, potencia, pesoBruto);
		this.tara = tara;
	}
	
	public double tara() { return tara; }

}
