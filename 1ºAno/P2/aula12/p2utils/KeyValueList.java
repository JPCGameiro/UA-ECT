package p2utils;

public class KeyValueList<E> {

  private KeyValueNode<E> first = null;
  private int size = 0;

  /** {@code KeyValueList} constructor.
   */
  public KeyValueList() { }

  /** Returns the number of elements in the list.
   * @return Number of elements in the list
   */
  public int size() { return size; }

  /** Checks if the list is empty.
   * @return  {@code true} if list empty, otherwise {@code false}.
   */
  public boolean isEmpty() { return size == 0; }

  /** Updates the element associated to the given key, if the
   * key already exists; otherwise, inserts a new pair with
   * the given key and element.
   * @param  k a key
   * @param  e an element
   * @return  {@code true} if the key was added (size increased), 
   *          otherwise {@code false}.
   */
  public boolean set(String k, E e) {
    int prev_size = size;
    first = set(first, k, e);
    assert contains(k) && get(k).equals(e);
    return size>prev_size;
  }
  private KeyValueNode<E> set(KeyValueNode<E> n, String k, E e) {
    if (n==null) {
      n = new KeyValueNode<E>(k, e);
      size++;
    }
    else if (n.key.equals(k)) {
      n.elem = e;
    }
    else n.next = set(n.next, k, e);
    return n;
  }

  /** Returns the element associated to the given key.
   * @param  k a key
   * @return The associated element
   */
  public E get(String k) {
    assert contains(k) : "Key does not exist";
    return get(first, k);
  }
  private E get(KeyValueNode<E> n, String k) {
    if (n.key.equals(k)) return n.elem;
    return get(n.next, k);
  }

  /** Removes the given key and associated element.
   * @param  k a key
   */
  public void remove(String k) {
    assert contains(k) : "Key does not exist";
    first = remove(first, k);
    size--;
    assert !contains(k) : "Key still exists";
  }
  private KeyValueNode<E> remove(KeyValueNode<E> n, String k) {
    if (n.key.equals(k)) return n.next;
    n.next = remove(n.next, k);
    return n;
  }

  /** Checks if the given key exists in the list.
   * @param k a key
   * @return {@code true} if the key exists and {@code false} otherwise
   */
  public boolean contains(String k) { 
    return contains(first, k);
  }
  private boolean contains(KeyValueNode<E> n, String k) {
    if (n == null) return false;
    if (n.key.equals(k)) return true; 
    return contains(n.next, k);
  }

  /** Removes all entries from the list.
   */
  public void clear() {
    first = null;
    size = 0;
  }

  /** Returns an array with all keys in the list.
   * @return An array of keys
   */
  public String[] keys() {
    String[] keys = new String[size];
    keys(first, 0, keys);
    return keys;
  }
  private void keys(KeyValueNode<E> n, int i, String[] keys) {
    if (n==null) return;
    keys[i] = n.key;
    keys(n.next, i+1, keys);
    return;      
  }

  /** Returns a string representing the contents of the key-value list.
   * The result is formatted as a comma-separated sequence of pairs inside
   * braces.  Something like "{(k1, e1), (k2, e2), (k3, e3)}".
   * @return A string representing the contents of the key-value list
   */
  public String toString() {
    // Call a more versatile version with the proper delimiters and separator:
    return toString("{", ", ", "}");
  }

  /** Extended version of {@link #toString()}.
   * The parameters allow configuring the separator and delimiters.
   * @param left the left delimitor
   * @param sep the item separator
   * @param right the right delimitor
   * @return A formatted string representing the contents of the key-value list
   */
  public String toString(String left, String sep, String right) {
    // Complete this function (11.2)
    //...

  }

}


