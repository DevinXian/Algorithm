package net.wind.algorithm;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<T> implements Iterable<T> {

	private int theSize;
	private int modCount = 0;
	private Node<T> beginMarker;
	private Node<T> endMarker;

	public MyLinkedList() {
		clear();
	}

	public void clear() {
		beginMarker = new Node<T>(null, null, null);
		endMarker = new Node<T>(null, beginMarker, null);
		beginMarker.next = endMarker;
		theSize = 0;
		modCount++;// modify times
	}

	public int size() {
		return theSize;
	}

	public boolean isEmpty() {
		return theSize == 0;
	}

	public boolean add(T x) {
		add(size(), x);
		return true;
	}

	public void add(int idx, T x) {
		addBefore(idx == theSize ? endMarker : getNode(idx), x);
	}

	private void addBefore(Node<T> p, T x) {
		System.out.println();
		Node<T> newNode = new Node<T>(x, p.prev, p);
		newNode.prev.next = newNode;
		p.prev = newNode;
		theSize++;
		modCount++;
	}

	public T set(int idx, T newVal) {
		Node<T> p = getNode(idx);
		T oldVal = p.data;
		p.data = newVal;
		return oldVal;
	}

	public T get(int idx) {
		return getNode(idx).data;
	}

	private T remove(Node<T> p) {
		p.prev.next = p.next;
		p.next.prev = p.prev;
		theSize--;
		modCount++;
		return p.data;
	}

	private Node<T> getNode(int idx) {
		Node<T> p;
		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}
		if (idx < size() / 2) {// binarysearch like
			p = beginMarker.next;
			for (int i = 0; i < idx; i++) {
				p = p.next;
			}
		} else {
			p = endMarker.prev;
			for (int i = size(); i > idx; i--) {
				p = p.prev;
			}
		}
		return p;
	}

	public Iterator<T> iterator() {
		return new LinkedListIterator();
	}

	private class LinkedListIterator implements Iterator<T> {

		private Node<T> current = beginMarker.next;
		private int expectedModeCount = modCount;
		private boolean okToRemove = false;

		public boolean hasNext() {
			return current != endMarker;
		}

		public T next() {
			if (modCount != expectedModeCount) {
				throw new ConcurrentModificationException();
			}
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T nextItem = current.data;
			current = current.next;
			okToRemove = true;
			return nextItem;
		}

		public void remove() {
			if (modCount != expectedModeCount) {
				throw new ConcurrentModificationException();
			}
			if (!okToRemove) {
				throw new IllegalStateException();
			}
			MyLinkedList.this.remove(current.prev);
			okToRemove = false;
			expectedModeCount++;
		}

	}

	private static class Node<T> {
		public Node(T d, Node<T> p, Node<T> n) {
			data = d;
			prev = p;
			next = n;
		}

		public T data;
		public Node<T> prev;
		public Node<T> next;
	}

	public String toString() {
		Iterator<T> i = iterator();
		if (!i.hasNext())
			return "[]";

		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (;;) {
			T e = i.next();
			sb.append(e == this ? "(this Collection)" : e);
			if (!i.hasNext())
				return sb.append(']').toString();
			sb.append(", ");
		}
	}

	public static void main(String[] args) {

		MyLinkedList<String> sl = new MyLinkedList<String>();
		sl.add("x");
		sl.add("y");
		sl.add("z");
		Iterator<String> its = sl.iterator();
		if (its.hasNext()) {
			its.next();
			its.remove();
		}
		System.out.println(sl);

	}
}
