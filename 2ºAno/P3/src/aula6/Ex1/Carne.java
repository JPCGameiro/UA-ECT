//João Gameiro  Nº93097
//P3-ECT-UA

package aula6.Ex1;

public class Carne extends Alimento implements Comparable<Alimento>{

	private VariedadeCarne variedade;
	
	//Construtor
	public Carne(VariedadeCarne variedade, double proteinas, double calorias, double peso)
	{
		super(proteinas, calorias, peso);
		this.variedade = variedade;
	}
	
	public VariedadeCarne variedade() { return variedade; }
	
	//Funções Adicionais
	@Override public String toString()
	{
		return "Carne: "+variedade+" -> "+super.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((variedade == null) ? 0 : variedade.hashCode());
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
		Carne other = (Carne) obj;
		if (variedade != other.variedade)
			return false;
		return true;
	}
	
}
