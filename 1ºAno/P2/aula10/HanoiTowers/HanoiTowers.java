import static java.lang.System.*;
import java.util.Scanner;
import p2utils.Stack;

public class HanoiTowers
{
	private Stack <Integer> a = new Stack<>();
	private Stack <Integer> b = new Stack<>();
	private Stack <Integer> c = new Stack<>();
	
	public HanoiTowers(int n)
	{
		for(int i=1;i<=n;i++)
		{
			a.push(i);
		}
	}
	
	static void moveDiscs(int n, String origem, String destino, String auxiliar)
	{
		assert n>=0;
		
		if(n>0)
		{
			moveDiscs(n-1, origem, auxiliar, destino);
			out.println("Move disco " + n + " da torre " + origem + " para a torre " + destino);
			moveDiscs(n-1, auxiliar, destino, origem); 
		}
	}
	
	

}

