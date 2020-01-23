package P3_19;

public class eConta extends Conta{
	
	//Construtor
	public eConta(Cliente cliente) 
	{
		super(cliente);
		if(cliente.getTipo()!=TipoCliente.Online) { throw new IllegalArgumentException("Cliente Inválido"); }
	}

}
