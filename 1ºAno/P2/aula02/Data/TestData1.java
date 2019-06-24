import static java.lang.System.*;
import java.util.Scanner;

public class TestData1 
{
  static final Scanner in = new Scanner(System.in);

  public static void main(String[] args) 
  {
    
    out.printf("Data.bissexto(2018) = %b\n", Data.bissexto(2018));

    Data d1 = new Data();
    out.printf("d1.toString() = %s\n", d1.toString());
    out.printf("d1 = %s\n", d1);  // toString() is called implicitly!

	//Mova esta linha para descomentar linhas progressivamente...
    out.printf("d1.ano() = %d\n", d1.ano());
    out.printf("d1.mes() = %d\n", d1.mes());
    out.printf("d1.dia() = %d\n", d1.dia());

    for (int ano = 2015; ano < 2020; ano++)
      out.printf("Data.diasDoMes(2, %d) = %d\n", ano, Data.diasDoMes(2, ano));

    out.printf("d1.mesExtenso() = %s\n", d1.mesExtenso());

    out.printf("d1.extenso() = %s\n", d1.extenso());

    out.printf("Data.dataValida(29, 2, 2016) = %b\n",
                Data.dataValida(29, 2, 2016)); // true
    out.printf("Data.dataValida(31, 4, 2018) = %b\n",
                Data.dataValida(31, 4, 2018)); // false
    out.printf("Data.dataValida(0, 4, 2018) = %b\n",
                Data.dataValida(0, 4, 2018)); // false

    Data d2 = new Data(25, 1, 2019);
    out.printf("d2 = %s = %s\n", d2, d2.extenso());    
    d2.seguinte();
    out.printf("d2 = %s = %s\n", d2, d2.extenso());
    d2.seguinte();
    out.printf("d2 = %s = %s\n", d2, d2.extenso());
  }

}

