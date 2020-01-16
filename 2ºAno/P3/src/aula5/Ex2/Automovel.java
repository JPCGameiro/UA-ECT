//João Gameiro  Nº93097
//P3-ECT-UA

package aula5.Ex2;

public class Automovel extends Motorizado{
	
	private boolean atrelado;
	
	//Construtor
	public Automovel(int ano, Cor cor, int numRodas, int cilindrada, int Vmax, String matricula, double consumo, double combustivel, boolean atrelado)
	{
		super(ano, cor, numRodas, cilindrada, Vmax, matricula, consumo, combustivel);
		this.atrelado = atrelado;
	}
	
	public boolean atrelado() { return atrelado; }
	
	@Override public String toString()
	{
		return "Automóvel-> Ano: "+ano+" - Matrícula: "+matricula+" - Cor: "+cor+" - Cilindrada: "+cilindrada+" - Consumo: "+consumo+" - Combustivel: "+combustivel+ " - Atrelado: "+atrelado;
	}

}
