import static java.lang.System.*;
import p2utils.*;
import java.util.Scanner;
public class RPNCalculator
{
	public static void main(String[] args)
	{
		Scanner ler = new Scanner(System.in);
		
		out.println("A inicializar o sistema...\n");
		Stack<Double> stack = new Stack<Double>();
		
		do
		{
			String str = ler.next();
			try
			{
				if(str.equals("+"))
				{
					double a = stack.top();
					stack.pop();
					double b = stack.top();
					stack.pop();
					double result = b+a;
					stack.push(result);					
				}
				else if(str.equals("-"))
				{
					double a = stack.top();
					stack.pop();
					double b = stack.top();
					stack.pop();
					double result = b-a;
					stack.push(result);
				}
				else if(str.equals("*"))
				{
					double a = stack.top();
					stack.pop();
					double b = stack.top();
					stack.pop();
					double result = b*a;
					stack.push(result);
				}
				else if(str.equals("/"))
				{
					double a = stack.top();
					stack.pop();
					double b = stack.top();
					stack.pop();
					double result = b/a;
					stack.push(result);
				}
				else
				{
					stack.push(Double.parseDouble(str));
				}
			}
			catch(NullPointerException e)
			{
				out.println("ERROR: Missing operand");
				System.exit(0);
			}
			catch(NumberFormatException e)
			{
				out.println("ERROR: Invalid operation");
				System.exit(0);
			}
			out.println(stack.inString());
		}
		while(true);
		
	}
}
