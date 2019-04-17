package p1;
import java.util.Scanner;
import static java.lang.System.*;
public class matricula
{
public class Ex804 {
	
	final Scanner sc = new Scanner (System.in);

	public static void main(String[] args) {
		String data;
		boolean a, b, c;
		
		out.println("Insira uma matrícula: ");
		data = sc.nextInt();
		
		a=matchPattern(data, "AA-00-00");
		b=matchPattern(data, "00-AA-00");
		c=matchPattern(data, "00-00-AA");
		
		if((a==true && b==false && c==false) || (b==true && a==false && c==false) || (c==true && a==false && b==false)){
			out.print("A sua matricula é válida em Portugal.");
		}
		else{
			out.print("A sua matricula não é válida em Portugal.");
		}
		
	}
	
	public static boolean matchPattern(String data, String patt){
		
		boolean check=true;
		
		for(int i=0; i<data.length(); i++){
			
			if (patt.charAt(i)=='A'){
				if(Character.isDigit((data.charAt(i)))) check=false;
			}
			else if (patt.charAt(i)=='0'){
				if(Character.isDigit((data.charAt(i)))==false) check=false;
			}
			else if (patt.charAt(i)=='-'){
				if(data.charAt(i)!='-') check=false;
			}
			else{
				check=false;
				break;
			}
		if(patt.length()!=8) check=false;
		if(patt.length()!=data.length()) check=false;
			
		}
		
		return check;
	}
}
}
		
