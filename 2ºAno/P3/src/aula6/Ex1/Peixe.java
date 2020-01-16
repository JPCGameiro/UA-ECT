//João Gameiro  Nº93097
//P3-ECT-UA

package aula6.Ex1;

public class Peixe extends Alimento implements Comparable<Alimento>{

	private TipoPeixe tipo;
	
	//Construtor
	public Peixe(TipoPeixe tipo, double proteinas, double calorias, double peso)
	{
		super(proteinas, calorias, peso);
		this.tipo = tipo;
	}
	
	public TipoPeixe tipo() { return tipo; }
	
	@Override public String toString()
	{
		return "Peixe: "+tipo+" -> "+super.toString();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		Peixe other = (Peixe) obj;
		if (tipo != other.tipo)
			return false;
		return true;
	}
	
}
