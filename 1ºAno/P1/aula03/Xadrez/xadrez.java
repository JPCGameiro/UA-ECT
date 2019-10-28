import java.util.*;
public class xadrez
{
	public static void main(String[] args)
	{
		for(int i=8;i>=1;i--)
		{
			for(int j=0;j<8;j++)
			{
				char letra = (char)('a'+j);
				System.out.print(letra);
				System.out.print(i+" ");
			}
			System.out.printf("\n");
		}
	}
}
