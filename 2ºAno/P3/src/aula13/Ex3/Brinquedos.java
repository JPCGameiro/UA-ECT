//João Gameiro  Nº93097
//P3-ECT-UAs

package aula13.Ex3;

public class Brinquedos {
	
	private String toy;
	private Empregado vencedor;
	
	//Construtor
	public Brinquedos(String toy, Empregado vencedor)
	{
		this.toy = toy;
		this.vencedor = vencedor;
	}
	
	public String toy() { return toy; }
	public Empregado vencedor() { return vencedor; }
	
	@Override
	public String toString() {
		return "Vencedor -> "+vencedor.toString()+" - Brinquedo: "+toy;
	}

}
