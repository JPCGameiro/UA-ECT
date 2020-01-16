//João Gameiro  Nº93097
//P3-ECT-UA

package aula6.Ex1;

public abstract class Alimento implements Comparable<Alimento> {
	
	private double proteinas;
	private double calorias;
	private double peso;
	
	//Construtor
	public Alimento(double proteinas, double calorias, double peso)
	{
		this.proteinas = proteinas;
		this.calorias = calorias;
		this.peso = peso;
	}
	
	public double proteinas() { return proteinas; }
	public double calorias() { return calorias; }
	public double peso() { return peso; }
	
	@Override public String toString()
	{
		return "Proteínas: "+proteinas+" - Calorias: "+calorias+" - Peso: "+peso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(calorias);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(peso);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(proteinas);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Alimento other = (Alimento) obj;
		if (Double.doubleToLongBits(calorias) != Double.doubleToLongBits(other.calorias))
			return false;
		if (Double.doubleToLongBits(peso) != Double.doubleToLongBits(other.peso))
			return false;
		if (Double.doubleToLongBits(proteinas) != Double.doubleToLongBits(other.proteinas))
			return false;
		return true;
	}
	
	public int compareTo(Alimento a)
	{
		if(a.calorias == calorias)
			return 0;
		else if(a.calorias > calorias)
			return -1;
		else
			return 1;
	}
	
}
