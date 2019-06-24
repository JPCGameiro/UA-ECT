
/**
 * Implementa uma linha de atraso com array "circular".
 * Funciona como fila, mas de tamanho fixo e com acesso direto a
 * qualquer dos elementos (amostras), através do get(pos).
 * Não tem out(). Apenas tem in(x), que acrescenta uma nova amostra
 * e simultaneamente descarta a mais antiga.
 */
public class DelayArray<E> {
  /** Este é o array onde as amostras vão ser armazenadas. */
  private E[] buffer;

  /** Este é o índice onde se vai inserir a próxima amostra
   * (e onde está a amostra mais antiga).
   */
  private int next;

  /** Cria uma linha de atraso com size amostras inicializadas. */
  @SuppressWarnings(value = "unchecked")  // sem isto, dá warning!
  public DelayArray(int size, E init) {
    assert size >= 0;
    // FALTA criar array de size elementos de tipo E (tem truque!)
    //...

    // FALTA encher buffer com size amostras com o valor "init"
    //...

    // Invariante:
    assert size()==size: String.format("Delay line size should be %d", size);
    // Pós-condição:
    assert get(-size()).equals(init) && get(-1).equals(init): "All samples should have the initial value";
  }

  /** Devolve a tamanho da linha de atraso. */
  public int size() { return buffer.length; }

  /** Acrescenta uma nova amostra e descarta a mais antiga.
   * @param x  A amostra atual que é acrescentada no fim da linha.
   */
  public void in(E x) {
    // FALTA colocar x na posição next e incrementar next,
    // mas "dar a volta" se next == length!
    //...

  }

  /** Valor da amostra do instante t.
   * get(-1) devolve a última amostra armazenada na linha,
   * get(-2) devolve a penúltima e assim sucessivamente.
   * Requer que -size() <= t < 0.
   */
  public E get(int t) {
    assert -size() <= t && t < 0;
    // FALTA devolver amostra da posição (next+t), mas "dar a volta" se < 0!
    //...

  }
}
