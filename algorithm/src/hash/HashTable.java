package hash;

import java.util.Arrays;
import java.util.Set;

public class HashTable<K, V> implements Hash<K, V> {

	private int currentSize;

	private Node<K, V>[] table;

	@SuppressWarnings("unchecked")
	public HashTable() {
		this.currentSize = 15;
		table = new Node[currentSize];
	}

	public static class Node<K, V> extends Entry<K, V> {
		Node<K, V> next;

		public Node(K key, V value, Node<K, V> next) {
			super(key, value);
			this.next = next;
		}

		@Override
		public String toString() {
			return "Node [next=" + next + ", key=" + key + ", value=" + value + "]";
		}
	}

	private int hash(String key, int tableSize) {
		int hashValue = 0;
		for (int i = 0; i < key.length(); i++) {
			hashValue = 37 * hashValue + key.charAt(i);
		}
		hashValue %= tableSize;
		if (hashValue < 0) {
			hashValue += tableSize;
		}
		return hashValue;

	}

	private int index(K key) {
		return hash(key.toString(), table.length);
	}

	private Node<K, V> getNode(K key) {
		if (currentSize < 1 || index(key) > currentSize) {
			return null;
		}
		int index = index(key);
		Node<K, V> front = table[index];
		if (front != null) {
			Node<K, V> node = front;
			if (node.key == key) {
				return node;
			}
			while (node != null) {
				if (node.key == key || (node.key.equals(key))) {
					return node;
				}
				node = node.next;
			}
			return node;
		}
		return null;
	}

	@Override
	public boolean containsKey(K key) {
		return getNode(key) != null;
	}

	@Override
	public Entry<K, V> remove(K key) {
		if (currentSize < 1 || index(key) > currentSize) {
			return null;
		}
		int index = index(key);
		Node<K, V> front = table[index];
		Node<K, V> removedNode = null;
		Node<K, V> node = new Node<K, V>(null, null, front);
		if (front != null) {
			Node<K, V> e ;
			while ((e = node) != null) {
				if (e.key == key || (e.key.equals(key))) {
					removedNode = e;
					front.next=e.next;
					e.next=null;
					currentSize--;
					break;

				} 
				node=node.next;
			}
		}
		table[index] = node.next;
		return removedNode;
	}

	@Override
	public V get(K key) {
		Node<K, V> resultNode = getNode(key);
		if (resultNode != null) {
			return resultNode.getValue();
		}
		return null;
	}

	@Override
	public V put(K key, V value) {
		int index = index(key);
		Node<K, V> node = table[index];
		if (node == null) {
			table[index] = new Node<K, V>(key, value, null);
			currentSize++;
			return null;
		} else {
			Node<K, V> e = node;
			V oldValue;
			while (e != null) {
				if (e.key == key || e.key.equals(key)) {
					oldValue = e.getValue();
					e.setValue(value);
					return oldValue;
				}
				e = node.next;
				if (e != null) {
					node = e;
				}
			}
			node.next = new Node<K, V>(key, value, null);
			currentSize++;

		}
		return value;
	}

	@Override
	public void clear() {

	}

	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return null;
	}

	@Override
	public String toString() {
		return "HashTable [currentSize=" + currentSize + ", table=" + Arrays.toString(table) + "]";
	}

}
