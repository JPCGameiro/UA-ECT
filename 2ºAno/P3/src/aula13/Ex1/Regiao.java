//João Gameiro  Nº93097
//P3-ECT-UA

package aula13.Ex1;

public abstract class Regiao {
	
	private String nome;
	private int populacao;
	
	//Construtor
	public Regiao(String nome, int populacao)
	{
		this.nome = nome;
		this.populacao = populacao;
	}
	
	public String nome() { return nome; }
	public int populacao() { return populacao; }	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + populacao;
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
		Regiao other = (Regiao) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (populacao != other.populacao)
			return false;
		return true;
	}

}
