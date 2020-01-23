package P3_19;

public abstract class Movimento {
	
	private double montante;
	private String data;
	
	//Contrutor
	public Movimento(double montante, String data)
	{
		this.montante=montante;
		this.data=data;
	}
	
	public double getMontante() { return montante; }
	public String getData() { return data; }
	
	@Override
	public String toString() {
		return "[amount="+montante+", data="+data+"]";
	}
}
