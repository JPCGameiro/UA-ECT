package p2utils;

class KeyValueNode<E> {

  final String key;
  E elem;
  KeyValueNode<E> next;

  KeyValueNode(String k, E e, KeyValueNode<E> n) {
    key  = k;
    elem = e;
    next = n;
  }

  KeyValueNode(String k, E e) {
    key  = k;
    elem = e;
    next = null;
  }

  //...

}
