// Implementação parcial de uma Data.
// jmr@ua.pt
// 2018
package p2utils;
import static java.lang.System.*;

public class Data implements Comparable<Data>
{
  private int dia, mes, ano;

  public Data(int dia, int mes, int ano) {
    assert dataValida(dia, mes, ano): "Data inválida";
    this.dia = dia;
    this.mes = mes;
    this.ano = ano;
    assert valida(): "Invariant";
  }

  public int dia() { return dia; }
  public int mes() { return mes; }
  public int ano() { return ano; }

  /** Indica se ano é bissexto. */
  public static boolean bissexto(int ano) {
    return ano%4 == 0 && ano%100 != 0 || ano%400 == 0;
  }

  /** Dimensões dos meses num ano comum. */
  private static final
  int[] diasMesComum = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

  /** Devolve o número de dias do mês dado. */
  public static int diasDoMes(int mes, int ano) {
    assert mesValido(mes): "Mês inválido";  // aula04
    int result = diasMesComum[mes-1];
    if (mes == 2 && bissexto(ano))
      result++;
    return result;
  }

  public static boolean mesValido(int mes) {
    return 1 <= mes && mes <= 12;
  }

  /** Indica se um terno (dia, mes, ano) forma uma data válida. */
  public static boolean dataValida(int dia, int mes, int ano) {
    return mesValido(mes) && (dia >= 1 && dia <= diasDoMes(mes, ano));
  }

  /** Indica se esta data é válida. (Invariante de objeto.) */
  public boolean valida() {
    return dataValida(dia, mes, ano);
  }

  /** Devolve esta data segundo a norma ISO 8601. */
  public String toString() {
    return String.format("%04d-%02d-%02d", ano, mes, dia);
  }

  /** Converte uma string no formato ISO 8691 numa Data.
   * @param str string com data no formato "YYYY-MM-DD".
   * @return data correspondente à string, se válida ou null, se inválida.
   */
  public static Data parseData(String str) {
    try {
      String[] parts = str.split("-");
      if (parts.length != 3) return null;
      int a = Integer.parseInt(parts[0]);
      int m = Integer.parseInt(parts[1]);
      int d = Integer.parseInt(parts[2]);
      if (!dataValida(d, m, a)) return null;
      return new Data(d, m, a);
    }
    catch (NumberFormatException e) {
      return null;
    }
  }
  
  /** Compares this date to another.
   *  @return 0 if dates are equal, positive if this>that, negative otherwise.
   */
  public int compareTo(Data that) {
    int d = ano - that.ano;
    if (d != 0) return d;
    d = mes - that.mes;
    if (d != 0) return d;
    d = dia - that.dia;
    return d;
  }

  public boolean equals(Data that) {
    return this.compareTo(that) == 0;
  }
}
