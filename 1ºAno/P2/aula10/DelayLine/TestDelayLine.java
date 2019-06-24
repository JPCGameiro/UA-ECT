import static java.lang.System.*;
import java.util.Scanner;
import java.io.*;

public class TestDelayLine {
  public static void main(String[] args) throws IOException {
    Scanner sf = new Scanner(new File("temperaturas.txt"));
    printAverage(sf, 24);
    sf.close();
  }

  public static void printAverage(Scanner sf, int N) {
    DelayLine<Double> temp = new DelayLine<Double>(N, 0.0);
    out.printf("Temperaturas hora-a-hora e média das últimas %d horas.\n", N);
    out.printf("(As primeiras %d médias não são exatas!)\n", N);
    out.printf("%3s %4s %7s %7s\n", "dia", "hora", "temp.", "média");
    int t = 0;
    while (sf.hasNextDouble()) 
    {
      double nova = sf.nextDouble();
      // Calcula soma das últimas N horas:
      temp.in(nova);
      double somaN = 0.0;
      for (int hora = -N; hora < 0; hora++) 
      {
        somaN += temp.get(hora);
      }
      out.printf("%3d   %02d %7.1f %7.1f\n", t/24, t%24, nova, somaN/N);
      t++;
    }
  }

}
