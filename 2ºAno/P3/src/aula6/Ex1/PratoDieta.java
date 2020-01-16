//João Gameiro  Nº93097
//P3-ECT-UA

package aula6.Ex1;

public class PratoDieta extends Prato implements Comparable<Prato>{
	
	private double maxCaloria;
	
	//Construtor
	public PratoDieta(String nome, double maxCaloria)
	{
		super(nome);
		this.maxCaloria = maxCaloria;
	}
	
	public double maxCaloria() { return maxCaloria; }
	
	@Override public String toString()
	{
		return "Dieta -> ("+maxCaloria+") "+ super.toString();
	}
	public boolean addIngrediente(Alimento a)
	{
		if(super.composicao().contains(a) || a==null || ((a.calorias()+super.maxCalorias()) >= maxCaloria))
			return false;
		else {
			super.addIngrediente(a);
			return true;
		}		
	}
	

}
