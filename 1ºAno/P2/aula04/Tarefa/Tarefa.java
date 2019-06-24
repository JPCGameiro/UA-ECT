import static java.lang.System.*;
public class Tarefa
{
	private Data inicio, fim;
	private String texto;
	
	public Tarefa (Data inicio, Data fim, String texto)
	{
		this.inicio=inicio;
		this.fim=fim;
		this.texto=texto;
		//assert inicio.compareTo(fim)==1 : "Inicio tem de ser menor que Fim";
		assert texto!="" : "a task text must not be empty";
	}
	
	public Data inicio()
	{
		return inicio;
	}
	public Data fim()
	{
		return fim;
	}
	public String texto()
	{
		return texto;
	}
	
	public String toString()
	{
		return String.format("ínico: %04d-%02d-%02d    fim: %04d-%02d-%02d    Tarefa: %s",inicio.ano(), inicio.mes(), inicio.dia(), fim.ano(), fim.mes(), fim.dia(), texto);  
	}
}
