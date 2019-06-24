import static java.lang.System.*;
class ReverseString
{
	public static void main(String[] args)
	{
		printString(args, args.length);
	}	
	//imprime a String e a string invertida
	public static void printString(String[] a, int n)
	{
		int i = a.length;
		
		if(i-n<a.length)
		{
			out.print(a[i-n] + " -> " + invertString(a[i-n]) + "\n");
			printString(a, n-1);
		}
		
	}
	//inverte a string
	public static String invertString(String fr)
	{
		int i=0;
		String inverted="";
		
		for(i=fr.length()-1;i>=0;i--)
		{
			char a = fr.charAt(i);
			inverted=inverted + a;
		}
		return inverted;
	}
}
