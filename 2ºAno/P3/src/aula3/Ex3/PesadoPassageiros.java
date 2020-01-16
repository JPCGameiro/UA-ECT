//João Gameiro  Nº93097
//P3-ECT-UA

package aula3.Ex3;

import java.util.LinkedList;

public class PesadoPassageiros extends Pesado {
	
	private LinkedList<Pessoa> passageiros;
	
	//Construtor 
	public PesadoPassageiros(Condutor condutor, Carta tipo, int lotacao, double cilindrada, double potencia, double pesoBruto, double tara, LinkedList<Pessoa> passageiros)
	{
		super(condutor, tipo, lotacao, cilindrada, pesoBruto, potencia, tara);
		this.passageiros = passageiros;
	}
	
	public LinkedList<Pessoa> passageiros() { return passageiros; }
}
