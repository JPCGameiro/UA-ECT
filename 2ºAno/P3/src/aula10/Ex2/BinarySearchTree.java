//João Gameiro  Nº93097
//P3-ECT-UA

package aula10.Ex2;
import java.util.Iterator;
import java.lang.Comparable;
import java.util.Stack;

public class BinarySearchTree <T extends Comparable<? super T>> implements Iterable <T> {
	
	private static class BSTNode<T> {
		T element;
		BSTNode<T> left;
		BSTNode<T> right;
		BSTNode(T theElement) {
		element = theElement;
		left = right = null;
		}
	}
	
	private BSTNode<T> root;
	private int numNodes;
	
	//Insert
	public void insert(T value) {
		root = insert(value, root);
	}
	private BSTNode<T> insert(T value, BSTNode<T> root) {
		if(root == null) {
			numNodes++;
			return new BSTNode<T>(value);
		}
		else if (root.element.compareTo(value) > 0) {
				return insert(value, root.left);
		}
		else {
				return insert(value, root.right);
		}		
	}
	
	//Remove
	public void remove(T value) {
		root = remove(value, root);
	}
	private BSTNode<T> remove(T value, BSTNode<T> root) {
		if(root.element.compareTo(value)==0) {
			if(root.left.element.compareTo(value)==0)
				root = root.right;
			else if(root.right.element.compareTo(value)==0)
				root = root.left;
			else
				root = insert(root.left.element, root.right);
			return root;
		}
		else if (root.element.compareTo(value) > 0)
			return remove(value, root.left);
		else
			return remove(value, root.right);		
	}
	
	//Contains
	public boolean contains(T value) {
		return (find(value, root)) != null;
	}
	private BSTNode<T> find(T value, BSTNode<T> root) {
		
		try {
			if(root.element.compareTo(value) > 0)
				return find(value, root.left);
			else if(root.element.compareTo(value) < 0)
				return find(value, root.right);
			else
				return root;	
		}
		catch(NullPointerException e) {
			return null;
		}
	}

	
	public Iterator<T> iterator() {
		return (this).new BSTIterator(root);
	}
	
	
	private class BSTIterator implements Iterator<T>{
		
		Stack<BSTNode> stack;
		BSTNode<T> root;
		
		public BSTIterator(BSTNode<T> root) {
			stack = new Stack<BSTNode>();
			while (root != null) {
				stack.push(root);
				root = root.left;
			}
		}
		@Override
		public boolean hasNext() {
			return stack.isEmpty();
		}

		@Override
		public T next() {
			BSTNode nd = stack.pop();
			T result = (T) nd.element;
			if(nd.right != null) {
				nd = nd.right;
				while(nd != null) {
					stack.push(nd);
					nd = nd.left;
				}
			}
			return result;
		}
		
	}

}
