package p2utils;
import static java.lang.System.*;
public class SortedList<E extends Comparable<E>>
{
  // private attributes
  private Node<E> first = null;
  private Node<E> last = null;
  private int size = 0;

  public SortedList() { }

  /**
   * @return Number of elements in the list
   */
  public int size() { return size; }

  /** Checks if the list is empty.
   * @return  {@code true} if list empty, otherwise {@code false}.
   */
  public boolean isEmpty() { return size == 0; }

  /**
   * @return  First element in the list
   */
  public E first() {
    assert !isEmpty(): "empty!";
      return first.elem;
  }
  /**
   * @return Last element in the list
   */
  public E last() {
	  assert !isEmpty() : "empty!";
		return last.elem;
   }

  /** Inserts a new element in the list.
   * @param e the element to be inserted
   */
  public void insert(E e) {
    first = insert(first,e);
    size++;
  }
  private Node<E> insert(Node<E> n, E e) {
    if (n==null || e.compareTo(n.elem) < 0)
      return new Node<E>(e, n);
    n.next = insert(n.next,e);
    return n;
  }
  
  //imprime os elementos
  public void print()
  {
	  print(first);
  }
  private void print(Node<E> n)
  {
	  if(n!=null)
	  {
		  System.out.println(n.elem);
		  print(n.next);
	  }
  }
	

  /** Removes the first element in the list.
   */
  public void removeFirst() {
    assert !isEmpty(): "empty!";
    first = first.next;
    size--;
  }

  /** Checks if the list is sorted.
   * @return {@code true} if sorted, {@code false} otherwise
   */
  public boolean isSorted() { 
    if (size < 2)
      return true;
    return isSorted(first,first.next); 
  }
  private boolean isSorted(Node<E> prev,Node<E> n) {
    if (n == null) return true;
    if (n.elem.compareTo(prev.elem) < 0) return false;
    return isSorted(n,n.next);
  }
  
  
  /*verifica se um dado elemento existe na lista*/
  public boolean contains(E e)
  {
	  return contains(first, e);
  }
  private boolean contains(Node<E> n, E e)
  {
	  boolean verify = false;
	  do
	  {
		  if(n==null)
		  {
			  break;
		  }
		  else if(e.compareTo(n.elem) == 0)
		  {
			  verify = true;
			  break;
		  }
		  else
		  {
			  n = n.next;
		  }
	  }
	  while(n!=null);
	  return verify;
  }
  
  /*Devolve uma string com os elementos de uma lista*/
  public String toString()
  {
	  return toString(first);
  }
  private String toString(Node<E> n)
  {
	  String result = "";
	  while(n!=null)
	  {
		  if(n.elem.compareTo(first.elem) == 0)
		  {
			  result = n.elem + ",";
		  }
		  else
		  {
			result = result + " " + n.elem + ",";
		  }
		  n = n.next;
	  }
	  return("[" + result + "]");
  }
  
  /*devolve a junção de duas listas*/
  public SortedList<E> merge(SortedList<E> l)
  {
	  return merge(first, l);
  }
  private SortedList<E> merge(Node<E> n, SortedList l)
  {
	  if(n==null)
	  {
		  return l;
	  }
	  else
	  {
		  while(n!=null)
		  {
			  l.insert(n.elem);
			  n=n.next;
		  }
	  }
	  return l;
  }

}
