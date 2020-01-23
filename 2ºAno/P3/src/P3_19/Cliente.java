package P3_19;

public class Cliente {
	
	private String nome;
	private String numC;
	private TipoCliente tipo;
	
	//Construtor
	public Cliente(String nome, String numC, TipoCliente tipo)
	{
		this.nome=nome;
		this.numC=numC;
		this.tipo=tipo;
	}
	
	public String getNome() { return nome; }
	public String getNumC() { return numC; }
	public TipoCliente getTipo() { return tipo; }
	
	@Override
	public String toString() {
		return "Cliente: "+nome;
	}
	
}
