package P3_19;

public class Individual extends Cliente{

	private String nTelefone;
	
	//Construtores
	public Individual(String nome, String numC, TipoCliente tipo, String nTelefone)
	{
		super(nome, numC, tipo);
		this.nTelefone = nTelefone;
	}
	public Individual(String nome, String numC, TipoCliente tipo)
	{
		super(nome, numC, tipo);
		this.nTelefone = "";
	}
	
	public String getTelefone() { return nTelefone; }
}
