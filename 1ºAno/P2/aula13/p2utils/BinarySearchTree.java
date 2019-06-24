package p2utils;

import static java.lang.System.*;

public class BinarySearchTree<E>
{

  public BinarySearchTree() { }

  public void set(String key, E elem) {
    assert key != null;

    // ........

    assert contains(key);
    assert get(key) == elem;
  }

  public E get(String key) {
    assert key != null;
    assert contains(key);

    // ........

  }

  public void remove(String key) {
    assert key != null;
    assert contains(key);

    // ........

    assert !contains(key);
  }

  public boolean contains(String key) {
    assert key != null;
    // ........

    return root != null && root.contains(key);
  }

  public boolean isEmpty() {
    // ........
  }

  public int size() {
    // ........
  }

  public void clear() {
    // ........
  }

  public String[] keys() {
    // ........
  }

  public String toString() {
    // ........
  }

}

