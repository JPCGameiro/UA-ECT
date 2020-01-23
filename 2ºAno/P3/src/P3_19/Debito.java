package P3_19;

public class Debito extends Movimento{
	
	//Contrutor
	public Debito(double montante, String data)
	{
		super(montante, data);
		if(montante >= 0) { throw new IllegalArgumentException("Montante tem de ser negativo"); }
	}
	
	@Override public String toString()
	{
		return "[Debito > "+super.toString()+"]";
	}

}
