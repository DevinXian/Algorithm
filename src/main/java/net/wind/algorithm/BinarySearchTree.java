package net.wind.algorithm;

import java.util.Comparator;

public class BinarySearchTree<T extends Comparable<? super T>> {

	/**
	 * 内部类-二度节点
	 * 
	 * @author netwind
	 *
	 * @param <T>
	 */
	private static class BinaryNode<T> {

		T element;
		BinaryNode<T> left;
		BinaryNode<T> right;

		BinaryNode(T element, BinaryNode<T> lt, BinaryNode<T> rt) {
			this.element = element;
			this.left = lt;
			this.right = rt;
		}
	}

	private BinaryNode<T> root;
	private Comparator<? super T> cmp;// 比较器

	public BinarySearchTree() {
		this(null);
	}

	public BinarySearchTree(Comparator<? super T> c) {
		root = null;
		cmp = c;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int myCompare(T lhs, T rhs) {
		if (cmp != null) {
			return cmp.compare(lhs, rhs);
		} else {
			return ((Comparable) lhs).compareTo(rhs);
		}
	}

	public void makeEmpty() {
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public boolean contains(T x) {
		return contains(x, root);
	}

	public T findMin() throws UnderFlowException {
		if (isEmpty()) {
			throw new UnderFlowException();
		}
		return findMin(root).element;
	}

	public T findMax() throws UnderFlowException {
		if (isEmpty())
			throw new UnderFlowException();
		return findMax(root).element;
	}

	public void insert(T x) {
		root = insert(x, root);
	}

	public void remove(T x) {
		root = remove(x, root);
	}

	private boolean contains(T x, BinaryNode<T> t) {
		if (t == null)
			return false;
		int compareResult = myCompare(x, t.element);
		if (compareResult < 0) {
			return contains(x, t.left);
		} else if (compareResult > 0) {
			return contains(x, t.right);
		} else {
			return true;
		}
	}

	private BinaryNode<T> findMin(BinaryNode<T> t) {
		if (t == null) {
			return null;
		} else if (t.left == null) {// 没有左子树，则本节点最小
			return t;
		} else {
			return findMin(t.left);// 递归左子树
		}

	}

	private BinaryNode<T> findMax(BinaryNode<T> t) {
		if (t != null) {
			while (t.right != null) {// 右子树大
				t = t.right;
			}
		}
		return t;
	}

	private BinaryNode<T> insert(T x, BinaryNode<T> t) {
		if (t == null) {
			return new BinaryNode<T>(x, null, null);// 一个节点的新树
		}
		int compareResult = x.compareTo(t.element);
		if (compareResult < 0) {
			t.left = insert(x, t.left);// 小于本节点，进左子树
		} else if (compareResult > 0) {
			t.left = insert(x, t.right);// 大于本节点，进右子树
		} else {
			;// 等于本节点，已存在，do nothing
		}
		return t;
	}

	private BinaryNode<T> remove(T x, BinaryNode<T> t) {
		if (t == null) {
			return t;
		}
		int compareResult = x.compareTo(t.element);
		if (compareResult < 0) {
			t.left = remove(x, t.left);// 小从左子树移除
		} else if (compareResult > 0) {
			t.right = remove(x, t.right);// 大从右子树移除
		} else if (t.left != null && t.right != null) {
			// 与本节点相同，则用右子树的最小值替代本节点
			// 同时从右子树中递归删除这个值
			t.element = findMin(t.right).element;
			t.right = remove(t.element, t.right);
		} else {
			t = (t.left != null) ? t.left : t.right;// 不为空的子树提升一层
		}
		return t;
	}

	public void printTree() {
		if (isEmpty()) {
			System.out.println("Empty tree");
		} else {
			printTree(root);
		}
	}

	public void printTree(BinaryNode<T> t) {
		if (t != null) {
			printTree(t.left);
			System.out.println(t.element);
			printTree(t.right);
		}
	}
}

class UnderFlowException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8162114561636269840L;

	public UnderFlowException() {
	}

	public UnderFlowException(String s) {
		super(s);
	}

	public UnderFlowException(String s, Throwable cause) {
		super(s, cause);
	}
}
