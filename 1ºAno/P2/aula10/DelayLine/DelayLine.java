import p2utils.LinkedList;

/**
 * Implementa uma linha de atraso.
 * Trata-se de uma fila, mas de tamanho fixo e com acesso direto a
 * qualquer dos elementos (amostras), através do get(pos).
 * Não tem out(). Apenas tem in(x), que acrescenta uma nova amostra
 * e simultaneamente descarta a mais antiga.
 */
public class DelayLine<E> 
{
  /** Este é a lista onde as amostras vão ser armazenadas. */
  private LinkedList<E> buffer;

  /** Cria uma linha de atraso com size amostras inicializadas. */
  public DelayLine(int size, E init) 
  {
    assert size >= 0;
    buffer = new LinkedList<>();
    // FALTA encher a lista com size amostras com o valor "init"
    for(int i=0;i<size;i++)
    {
		buffer.addLast(init);
	}

    // Invariante:
    assert size()==size: String.format("Delay line size should be %d", size);
    // Pós-condição:
    assert get(-size()).equals(init) && get(-1).equals(init): "All samples should have the initial value";
  }

  /** Devolve a tamanho da linha de atraso. */
  public int size() { return buffer.size(); }

  /** Acrescenta uma nova amostra e descarta a mais antiga.
   * @param x  A amostra atual que é acrescentada no fim da linha.
   */
  public void in(E x) 
  {
    // FALTA completar!
    buffer.removeFirst();
    buffer.addLast(x);
  }

  /** Valor da amostra do instante t.
   * get(-1) devolve a última amostra armazenada na linha,
   * get(-2) devolve a penúltima e assim sucessivamente.
   * Requer que -size() <= t < 0.
   */
  public E get(int t) 
  {
    assert -size() <= t && t < 0;
    // O método get na LinkedList tem de estar implementado (Problema 7.1)
    return buffer.get(size()+t);
  }
}
