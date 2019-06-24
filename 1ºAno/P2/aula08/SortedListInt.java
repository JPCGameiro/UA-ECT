
public class SortedListInt
{
  // private attributes
  private NodeInt first = null;
  private int size = 0;

  public SortedListInt() { }

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
  public int first() {
    assert !isEmpty(): "empty!";
      return first.elem;
  }

  /** Inserts a new element in the list.
   * @param e the element to be inserted
   */
  public void insert(int e) {
    first = insert(first,e);
    size++;
  }
  private NodeInt insert(NodeInt n,int e) {
    if (n==null || e<n.elem)
      return new NodeInt(e,n);
    n.next = insert(n.next,e);
    return n;
  }
  
  //imprime os eleme
  public void print()
  {
	  print(first);
  }
  private void print(NodeInt n)
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
  private boolean isSorted(NodeInt prev,NodeInt n) {
    if (n == null) return true;
    if (n.elem < prev.elem) return false;
    return isSorted(n,n.next);
  }

}
