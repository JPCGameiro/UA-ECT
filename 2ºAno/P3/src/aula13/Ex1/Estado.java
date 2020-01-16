//João Gameiro  Nº93097
//P3-ECT-UA

package aula13.Ex1;

public class Estado extends Regiao{
	
	Localidade capital;
	
	//Construtor
	public Estado(String nome, int populacao, Localidade capital)
	{
		super(nome, populacao);
		this.capital = capital;
	}
	
	public Localidade capital() { return capital; }

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((capital == null) ? 0 : capital.hashCode());
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
		Estado other = (Estado) obj;
		if (capital == null) {
			if (other.capital != null)
				return false;
		} else if (!capital.equals(other.capital))
			return false;
		return true;
	}
	
	
}
