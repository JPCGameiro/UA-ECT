package p2utils;
import static java.lang.System.*;

public class Pessoa implements Comparable<Pessoa>
{
	private Data birth;
	private String name;
	
	public Pessoa (Data birth, String name)
	{
		this.birth = birth;
		this.name = name;
	}
	
	
	public Data birth()
	{
		return birth;
	}
	public String name()
	{
		return name;
	}
	
	public String toString()
	{
		return(birth.dia() + " - " + birth.mes() + " - " + birth.ano() + " : " + name);
	}
	
	
	public int compareTo(Pessoa p)
	{
		if(p.birth.mes() > birth.mes())
		{
			return -1;
		}
		else if(p.birth.mes() < birth.mes())
		{
			return 1;
		}
		else
		{
			if(p.birth.dia() > birth.dia())
			{
				return -1;
			}
			else if(p.birth.dia() < birth.dia())
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
	}
}
