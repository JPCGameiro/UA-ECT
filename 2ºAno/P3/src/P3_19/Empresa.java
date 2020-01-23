package P3_19;

public class Empresa extends Cliente{
	
	private Cliente gerente;
	
	//Construtor
	public Empresa(String nome, String numC, TipoCliente tipo, Cliente gerente)
	{
		super(nome, numC, tipo);
		this.gerente = gerente;
	}
	
	public Cliente getGerente() { return gerente; }

}
