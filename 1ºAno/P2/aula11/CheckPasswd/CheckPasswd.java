import static java.lang.System.*;
import java.util.Scanner;
import java.io.*;
import p2utils.*;
public class CheckPasswd
{
	public static void main(String[] args) throws IOException
	{
		Scanner ler = new Scanner(in);
		String username = "", password = "";
		KeyValueList<String> k = new KeyValueList<String>();
		
		if(args.length != 1)
		{
			out.println("Usage Method: java -ea CheckPasswd <passwords file>");
			exit(0);
		}
		
		File f = new File(args[0]);
		Scanner readfile = new Scanner(f);
		
		while(readfile.hasNext())
		{
			k.set(readfile.next(), readfile.next());
		}
		
		do
		{
			out.printf("Username: ");
			username = ler.next();
			out.printf("PassWord: ");
			password = ler.next();
			if(k.contains(username) && k.get(username).equals(password))
				out.println("Authentication Sucessfull");
			else
				out.println("Authentication Failed");			
			 
		}
		while(true);
	}
}
