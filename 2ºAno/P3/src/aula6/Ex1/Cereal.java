//João Gameiro  Nº93097
//P3-ECT-UA

package aula6.Ex1;

public class Cereal extends Alimento implements Comparable<Alimento>, AlimentoVegetariano{
	
	private String nome;
	
	//Construtor
	public Cereal(String nome, double proteinas, double calorias, double peso)
	{
		super(proteinas, calorias, peso);
		this.nome = nome;
	}
	
	public String nome() { return nome; }

	@Override public String toString()
	{
		return "Cereal: "+nome+" -> "+super.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cereal other = (Cereal) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	
}
