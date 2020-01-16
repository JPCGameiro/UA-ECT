//João Gameiro  Nº93097
//P3 - ECT - UA

package aula3.Ex3;

public class Veiculo {
	
	private Condutor condutor;
	private Carta tipo;
	private int lotacao;
	private double cilindrada;
	private double potencia;
	private double pesoBruto;
	
	//Construtor
	public Veiculo(Condutor condutor, Carta tipo, int lotacao, double cilindrada, double potencia, double pesoBruto)
	{
		this.condutor = condutor;
		this.tipo = tipo;
		this.lotacao = lotacao;
		this.cilindrada = cilindrada;
		this.potencia = potencia;
		this.pesoBruto = pesoBruto;
	}
	
	public Condutor condutor() { return condutor; }
	public Carta tipo() { return tipo; }
	public int lotacao() { return lotacao; }
	public double cilindrada() { return cilindrada; }
	public double potencia() { return potencia; }
	public double pesoBruto() { return pesoBruto; }
	
	@Override public String toString()
	{
		return "Condutor: "+condutor.nome()+" | Carta: "+tipo.tipo()+" | Lotação: "+lotacao+" | Cilindrada: "+cilindrada+" | Potencia: "+potencia+" | Peso Bruto: "+pesoBruto;
	}

}
