
class NodeInt {

  final int elem;
  NodeInt next;

  NodeInt(int e, NodeInt n) {
    elem = e;
    next = n;
  }

  NodeInt(int e) {
    elem = e;
    next = null;
  }
}


