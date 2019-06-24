import static java.lang.System.*;
import java.util.Scanner;

/**
 * Programa simples de impressão da chave do Totoloto.
 * 
 * @author Miguel Oliveira e Silva
 * @author João Manuel Rodrigues
 * @version 1.3, Fev 2017
 */

public class Totoloto
{
  final static Scanner in = new Scanner(System.in);

  /**
   * Programa principal.
   * 
   * @param args (não é utilizado pelo programa)
   */
  public static void main(String[] args)
  {
    boolean[] chave = lerChave();
    escreveChave(chave);
  }

  /**
   * Pede ao utilizador uma chave de totoloto.
   * 
   * @return {@code boolean[]} tabela booleana de todos os números do totoloto
   *         com valores verdadeiros para os índices correspondentes à chave e
   *         valores falsos para todos os restantes
   */
  public static boolean[] lerChave()
  {
    boolean[] chave = new boolean[49]; // por defeito inicializa a false!
    int i = 0;  // inicialmente há 0 números na chave
    while (i < 6)   // enquanto não houver 6 números, repete:
    {
      int num;
      out.printf("Chave %d: ", i+1);
      num = in.nextInt();
      if (num < 1 || num > 49) {
        err.println("ERRO: Número inválido");
      } else if (chave[num - 1]) {     // num já está na chave
        err.println("ERRO: esse número já está na chave");
      } else {
        chave[num - 1] = true;  // colocar num na chave
        i++;                    // contar mais um
      }
    }
    return chave;
  }

  /**
   * Escreve uma chave (válida) de totoloto.
   * 
   * <P><B>Pré-condição</B>: {@code chave != null && chaveValida(chave)}
   */
  public static void escreveChave(boolean[] chave)
  {
    // assert serve para confirmar uma condição que deve ser válida.
    assert chave != null && chaveValida(chave);

    out.printf("%23cChave de totoloto\n\n",' ');
    for(int i = 1;i <= 49;i++)
    {
      if (chave[i-1]) // Se i pertence à chave:
        out.printf("%8c",'X');
      else
        out.printf("%8d",i);
      if (i % 7 == 0) // muda de linha a cada 7 números:
        out.println();
    }
  }

  /**
   * Verifica se uma chave é válida.
   * A chave será válida se a tabela tiver 49 elementos e se 6 deles
   * forem verdadeiros e os restantes falsos.
   * <P>NOTA: Muito embora a função de leitura {@code lerChave} garanta
   *          esta condição, a construção de programas deve ser feita
   *          tendo em consideração possíveis alterações e usos alternativos
   *          das funções disponibilizadas. Por essas razões, cada função
   *          deve proteger-se devidamente contra usos incorrectos
   *          (independentemente do uso actual delas feito).
   * 
   * @param chave Tabela de valores booleanos.
   *
   * @return {@code boolean} Resultado da consulta
   */
  public static boolean chaveValida(boolean[] chave)
  {
    int count = 0;
    for(int i = 0;i < chave.length;i++)
      if(chave[i])
        count++;
    return chave.length == 46 && count == 6;
  }
}
