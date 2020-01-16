//João Gameiro Nº93097
//P3-ECT-UA

package aula3.Ex3;

import java.util.LinkedList;

public class PesadoMercadorias extends Pesado{
	
	private LinkedList<String> mercadorias;
	
	//Construtor
	public PesadoMercadorias(Condutor condutor, Carta tipo, int lotacao, double cilindrada, double potencia, double pesoBruto, double tara, LinkedList<String> mercadorias)
	{
		super(condutor, tipo, lotacao, cilindrada, potencia, pesoBruto, tara);
		this.mercadorias = mercadorias;
	}
	
	public LinkedList<String> mercadorias() { return mercadorias; }
	
}
