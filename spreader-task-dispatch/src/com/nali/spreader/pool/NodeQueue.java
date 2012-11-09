package com.nali.spreader.pool;

import com.nali.spreader.pool.NodeQueue.Node;

public class NodeQueue<T extends Node<T>> {
	private T head;
	private T last;

	public void remove(T node) {
		if (node == head) {
			head = node.getNext();
		}
		if (node == last) {
			last = node.getPrev();
		}
		node.leave();
	}

	public void addFirst(T node) {
		if (head == null) {
			head = last = node;
		} else {
			node.append(head);
			head = node;
		}
	}

	public T poll() {
		if (head == null) {
			return null;
		} else if (last == head) {
			T quiter = last;
			last = head = null;
			return quiter;
		} else {
			T quiter = head;
			head = head.getNext();
			quiter.leave();
			return quiter;
		}
	}

	public T pollLast() {
		if (last == null) {
			return null;
		} else if (last == head) {
			T quiter = last;
			last = head = null;
			return quiter;
		} else {
			T quiter = last;
			last = last.getPrev();
			quiter.leave();
			return quiter;
		}
	}

	public boolean isEmpty() {
		return head == null;
	}

	public T getHead() {
		return head;
	}

	public T getLast() {
		return last;
	}

	public static class Node<T extends Node<T>> {
		private T next;
		private T prev;

		@SuppressWarnings("unchecked")
		private void append(T next) {
			this.next = next;
			next.prev = (T) this;
		}

		private void leave() {
			if (prev != null) {
				prev.next = next;
			}
			if (next != null) {
				next.prev = prev;
			}
			next = null;
			prev = null;
		}

		public T getNext() {
			return next;
		}

		public T getPrev() {
			return prev;
		}
	}
}