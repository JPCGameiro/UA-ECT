import static java.lang.System.*;
import java.util.Scanner;
import p2utils.*;     
public class Palindrome
{
	public static void main(String[] args)
	{
		boolean verify = true;
		if(args.length < 1)
		{
			out.println("Usage Method : java -ea Palindrome <word>");
			System.exit(0);
		}
		
		Stack <Character> stack1 = new Stack<>();
		Queue <Character> stack2 = new Queue<>();
		
		String [] a = new String[args.length];
		for(int i=0;i<a.length;i++)
			a[i] = args[i];
		
		for(int i=0;i<a.length;i++)
		{
			for(int z=0;z<a[i].length();z++)
			{
				char b = a[i].charAt(z);
				stack1.push(b);
				stack2.in(b);				
			}
		}	
		
		assert stack1.size() == stack2.size();
		
		for(int f=0;f<stack1.size();f++)
		{
			if(stack1.top() == stack2.peek())
			{
				stack1.pop();
				stack2.out();
				verify = true;
			}
			else
			{
				verify = false;
				break;
			}
		}
		
		if(verify)
			out.println(toString(a) + "is Palindrome");
		else
			out.println(toString(a) + "is Not a Palindrome");
	}
	
	public static String toString(String a[])
	{
		String r = "";
		
		for(int i=0;i<a.length;i++)
		{
			r = r + a[i] + " ";
		}
		return r;
	}
}
