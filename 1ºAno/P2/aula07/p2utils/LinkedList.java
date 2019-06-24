package p2utils;

/** This class implements a List abstract data type using a linked list.
 * Please note that this is p2utils.LinkedList.
 * It is NOT the java.util.LinkedList class in the standard Java library!
 */
public class LinkedList<E> {

  private Node<E> first = null;
  private Node<E> last = null;
  private int size = 0;

  /** {@code LinkedList} constructor, empty so far.
   */
  public LinkedList() { }

  /** Returns the number of elements in the list.
   * @return Number of elements in the list
   */
  public int size() { return size; }

  /** Checks if the list is empty.
   * @return  {@code true} if list empty, otherwise {@code false}.
   */
  public boolean isEmpty() { return size == 0; }

  /** Returns the first element in the list.
   * @return  First element in the list
   */
  public E first() 
  {
    assert !isEmpty(): "empty!";

    return first.elem;
  }

  /** Returns the last element in the list.
   * @return Last element in the list
   */
  public E last() 
  {
    assert !isEmpty(): "empty!";

    return last.elem;
  }

  /** Adds the given element to the start of the list.
   * @param e the element to add
   */
  public void addFirst(E e) 
  {
    first = new Node<>(e, first);
    if (isEmpty())
      last = first;
    size++;

    assert !isEmpty(): "empty!";
    assert first().equals(e) : "wrong element";
  }

  /** Adds the given element to the end of the list.
   * @param e the element to add
   */
  public void addLast(E e) 
  {
    Node<E> newest = new Node<>(e);
    if (isEmpty())
      first = newest;
    else
      last.next = newest;
    last = newest;
    size++;

    assert !isEmpty(): "empty!";
    assert last().equals(e) : "wrong element";
  }

  /** Removes the first element in the list.
   */
  public void removeFirst() 
  {
    assert !isEmpty(): "empty!";
    first = first.next;
    size--;
    if (isEmpty())
      last = null;
  }

  /** Removes all elements.
   */
  public void clear() {
    first = last = null;
    size = 0;
  }

  /** Checks if the given element exists in the list.
   * @param e an element
   * @return {@code true} if the element exists and {@code false} otherwise
   */
  public boolean contains(E e) 
  { 
    return contains(first, e); 
  }
  private boolean contains(Node<E> n, E e) 
  {
    if (n == null) return false;
    if (n.elem==null) return e==null; //dispensável, se impedirmos elem==null
    if (n.elem.equals(e)) return true; 
    return contains(n.next, e);
  }

  /** Prints all elements, one per line. */
  public void print() 
  {
    print(first);
  }
  private void print(Node<E> n) 
  {
    if (n != null) {
      System.out.println(n.elem);
      print(n.next);
    }
  }

  // funções adicionais pedidas no guião...
  //lista clonada
  public LinkedList<E> clone()
  {
	  return clone(first);
  }
  private LinkedList<E> clone(Node<E> n)
  {
	  if(n == null)
	  {
		  return new LinkedList();
	  }
	  else
	  {
		  LinkedList cloned = clone(n.next);
		  cloned.addFirst(n.elem);
		  return cloned;
	  }
  }
  //lista por ordem inversa
  public LinkedList<E> reverse()
  {
	  return reverse(first);
  }
  private LinkedList<E> reverse(Node<E> n)
  {
	  if(n == null)
	  {
		  return new LinkedList();
	  }
	  else
	  {
		  LinkedList rev = reverse(n.next);
		  rev.addLast(n.elem);
		  return rev;
	  }
  }
  
  //devolve a posição
  public E get(int pos)
  {
	  assert pos >= 0;
	  assert pos < size;
	  
	  return get(first, pos);
  }
  private E get(Node<E> n, int pos)
  {
	  if(pos == 0)
	  {
		  return n.elem;
	  }
	  else
	  {
		  return get(n.next, pos-1);
	  }
  }
  
  //devolve uma lista concatenada
  public LinkedList<E> concatenate(LinkedList l)
  {
	  return concatenate(first, l);
  }
  private LinkedList<E> concatenate(Node<E> n, LinkedList l)
  {
	  if(n==null)
	  {
		  return l.clone();
	  }
	  else
	  {
		  LinkedList conc = this.concatenate(n.next, l);
		  conc.addFirst(n.elem);
		  return conc;
	  }
  }
  
  //remove um elemento
  public void remove(E elemento)
  {
	  assert contains(elemento) : "Elemento não existe";
	  first = remove(first, elemento);
	  size --;
	  
	   if (isEmpty())
	   {
            last = null;
       }
  }
  private Node<E> remove(Node<E> no, E elemento)
  { 
        // temos de utilizar o metodo equals() pois o tipo pode ser uma string
        if (no.elem.equals(elemento)) 
        {
            return no.next;
        } else 
        {
            no.next = remove(no.next, elemento);
 
            //se removermos o ultimo membro da lista
            if (no.next == null) 
            {
                last = no;
            }
 
            return no;
        }
   }  
}
