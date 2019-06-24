import java.util.Scanner;
public class alberto
{
	public static void main (String[] args)
	{
		Scanner sc = new Scanner(System.in);
		String sent;
		
		System.out.printf("Frase: ");
		sent = sc.nextLine();
		
		Convert(sent);
	}
	
	public static void Convert(String sent)
	{
		String result="";
		int pos=0;
		for(pos=0;pos<sent.length();pos++)
		{
			char a = sent.charAt(pos);
			if(a=='l')
			{
				result=result+"u";
			}
			else if(a=='L')
			{
				result=result+"U";
			}
			else if(a=='r')
			{
				result=result;
			}
			else if(a=='R')
			{
				result=result;
			}
			else
			{
				result=result+a;
			}
		}
		System.out.println(result);
		
	}
}
