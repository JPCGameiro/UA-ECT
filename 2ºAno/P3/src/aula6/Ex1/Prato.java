//João Gameiro  Nº93097
//P3-ECT-UA

package aula6.Ex1;
import java.util.LinkedList;

public class Prato implements Comparable<Prato>{
	
	private String nome;
	private LinkedList<Alimento> composicao;
	private double maxCalorias;
	
	//Construtor
	public Prato(String nome)
	{
		this.nome = nome;
		this.composicao = new LinkedList<Alimento>();
		this.maxCalorias = 0;
	}
	
	public String nome() { return nome; }
	public LinkedList<Alimento> composicao() { return composicao; }
	public double maxCalorias() { return maxCalorias; }
	
	//Funções Adicionais
	@Override public String toString()
	{
		return "Prato -> "+nome+" composto por "+composicao.size()+" Ingredientes";
	}
	
	//Adicionar um ingrediente
	public boolean addIngrediente(Alimento a)
	{
		if(composicao.contains(a) || a==null)
			return false;
		else {
			maxCalorias = maxCalorias+a.calorias();
			composicao.add(a);
			return true;
		}
	}
	
	public int compareTo(Prato p)
	{
		if(p.maxCalorias == maxCalorias)
			return 0;
		else if(p.maxCalorias > maxCalorias)
			return -1;
		else
			return 1;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((composicao == null) ? 0 : composicao.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prato other = (Prato) obj;
		if (composicao == null) {
			if (other.composicao != null)
				return false;
		} else if (!composicao.equals(other.composicao))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
}
