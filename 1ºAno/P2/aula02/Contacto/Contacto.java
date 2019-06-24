public class Contacto 
{
	private String nome;
	private String telefone;
	private String email;


	public Contacto (String Nome, String Telefone, String eMail)
	{
		nome = Nome;
		telefone = Telefone;
		email = eMail;
	}
	public Contacto (String Nome, String Telefone)
	{
		nome = Nome;
		telefone = Telefone;
	}
	public String Nome()
	{
		return nome.toUpperCase();
	}
	public String Telefone()
	{
		return telefone;
	}
	public String eMail()
	{
		return email;
	}
}


