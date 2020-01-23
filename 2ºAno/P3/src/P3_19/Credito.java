package P3_19;

public class Credito extends Movimento{

	
	//Contrutor
	public Credito(double montante, String data)
	{
		super(montante, data);
		if(montante <= 0) { throw new IllegalArgumentException("Montante tem de ser positivo"); }
	}
	
	@Override public String toString()
	{
		return "[Credito > "+super.toString()+"]";
	}
}
