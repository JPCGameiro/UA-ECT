//João Gameiro  Nº93097
//P3-ECT-UA

package aula5.Ex2;

public class Moto extends Motorizado{
	
	private boolean sidecar;
	
	//Construtor
	public Moto(int ano, Cor cor, int numRodas, int cilindrada, int Vmax, String matricula, double consumo, double combustivel, boolean sidecar)
	{
		super(ano, cor, numRodas, cilindrada, Vmax, matricula, consumo, combustivel);
		this.sidecar = sidecar;
	}
	
	public boolean sidecar() { return sidecar; }
	
	@Override public String toString()
	{
		return "Moto-> Ano: "+ano+" - Matrícula: "+matricula+" - Cor: "+cor+" - Cilindrada: "+cilindrada+" - Consumo: "+consumo+" - Combustivel: "+combustivel+ " - Sidecar: "+sidecar;
	}

}
