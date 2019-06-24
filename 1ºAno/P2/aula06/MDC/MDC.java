import static java.lang.System.*;
public class MDC
{
	public static void main(String[] args)
	{
		assert(args.length == 2) : "Número de argumentos inválido";
		assert(Integer.parseInt(args[0])>=0 && Integer.parseInt(args[1])>=0) : "Números inválidos";
		
		out.printf("mdc(%d, %d) = %d\n", Integer.parseInt(args[0]), Integer.parseInt(args[1]), mdc(Integer.parseInt(args[0]), Integer.parseInt(args[1])));
	}
	
	public static int mdc(int a, int b)
	{
		int result = -3;
		if(b==0)
		{
			result = a;
		}
		else if(b!=0)
		{
			result = mdc(a, a%b);
		}
		return result;
	}
}
