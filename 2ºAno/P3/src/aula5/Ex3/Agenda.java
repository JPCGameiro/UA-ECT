//João Gameiro  Nº93097
//P3-ECT-UA

package aula5.Ex3;
import java.util.LinkedList;

public class Agenda {

	private LinkedList<Pessoa> agenda;
	
	//Construtor
	public Agenda(LinkedList<Pessoa> agenda)
	{
		this.agenda = agenda;
	}
	
	public LinkedList<Pessoa> agenda() { return agenda; }
	
	//Funções Adicionais
	
	//adicionar um contacto
	public void addContacto(Pessoa p)
	{
		agenda.add(p);
	}
	//remover um contacto
	public void removeContacto(Pessoa p)
	{
		if(agenda.contains(p))
			agenda.remove(p);
		else
			System.out.println("ERRO: Contacto inválido");
	}
	//Número de contactos
	public int agendaSize()
	{
		return agenda.size();
	}
	//String com os contactos da agenda
	@Override public String toString()
	{
		String result = "";
		for(Pessoa p : agenda)
		{
			result = result+p.toString()+"\n";
		}
		return result;
	}
	
}
