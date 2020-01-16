//João Gameiro  Nº93097
//P3-ECT-UA

package aula13.Ex1;

public class Provincia extends Regiao{

	private String governador;
	
	//Construtor
	public Provincia(String nome, int populacao, String governador)
	{
		super(nome, populacao);
		this.governador = governador;
	}
	
	public String governador() { return governador; }

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((governador == null) ? 0 : governador.hashCode());
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
		Provincia other = (Provincia) obj;
		if (governador == null) {
			if (other.governador != null)
				return false;
		} else if (!governador.equals(other.governador))
			return false;
		return true;
	}	
}
