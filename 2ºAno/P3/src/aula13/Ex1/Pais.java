//João Gameiro  Nº93097
//P3-ECT-UA

package aula13.Ex1;

import java.util.LinkedList;

public class Pais implements Comparable<Pais>{

	private String nome;
	private Localidade capital;
	private LinkedList<Regiao> regioes;
	
	//Construtor
	public Pais(String nome, Localidade capital, LinkedList<Regiao> regioes)
	{
		if(capital.tipo().equals((TipoLocalidade.Cidade)))
			this.capital = capital;
		else
			throw new IllegalArgumentException();
		this.nome = nome;
		this.regioes = regioes;
	}
	public Pais(String nome, Localidade capital)
	{
		if(capital.tipo().equals((TipoLocalidade.Cidade)))
			this.capital = capital;
		else
			throw new IllegalArgumentException("ERRO: Capital Inválida");
		this.nome = nome;
		this.regioes = new LinkedList<Regiao>();
	}
	public Pais(String nome)
	{
		this.nome = nome;
		this.capital = null;
		this.regioes = new LinkedList<Regiao>();
	}
	public Pais(Pais p)
	{
		this.nome = p.nome;
		this.capital = p.capital;
		this.regioes = p.regioes;
	}
	
	public String nome() { return nome; }
	public Localidade capital() { return capital; }
	public LinkedList<Regiao> regioes() { return regioes; }

	
	//Adicionar uma regiao
	public void addRegiao(Regiao r)
	{
		regioes.add(r);
	}
	//Devolver número de habitantes
	public int getPopulacao()
	{
		int i=0;
		for(Regiao r : regioes)
			i += r.populacao();
		
		if(capital!=null && regioes.contains(capital)) return i;
		else if(capital!=null && !regioes.contains(capital)) return i+capital.populacao();
		else return i;
	}
	
	@Override
	public String toString() {
		if(capital!=null)
			return "Pais: "+nome+", População: "+getPopulacao()+capital.toString();
		else
			return "Pais: "+nome+", População: "+getPopulacao()+" (Capital: *Indefinida*)";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((capital == null) ? 0 : capital.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((regioes == null) ? 0 : regioes.hashCode());
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
		Pais other = (Pais) obj;
		if (capital == null) {
			if (other.capital != null)
				return false;
		} else if (!capital.equals(other.capital))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (regioes == null) {
			if (other.regioes != null)
				return false;
		} else if (!regioes.equals(other.regioes))
			return false;
		return true;
	}
	
	@Override
	public int compareTo(Pais p0) {
		if(getPopulacao()==p0.getPopulacao())
			return 0;
		else if(getPopulacao()<p0.getPopulacao())
			return -1;
		else
			return 1;
	}
	
	


}
