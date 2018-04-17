package hash;

import java.util.Set;

public interface Hash<K, V> {

	/**
	 * @param key
	 * @return 判断是否含有该key。
	 */
	boolean containsKey(K key);

	/**
	 * @param key
	 * @return 若在该hash表包含这个key,则删除的键值对；反之，则返回为空。
	 */
	Entry<K, V> remove(K key);
	
	/**
	 * @param key
	 * @return 该key对应的值，若该key不存在，则返回为空。
	 */
	V get(K key);


	/**
	 * @param key
	 * @param value
	 * @return 返回原始值，或为空，或为旧值
	 */
	V put(K key, V value);

	/**
	 * 清除该hash表中的所有键值对。
	 */
	void clear();

	/**
	 * @return hash表的大小。
	 */
	int size();

	/**
	 * @return 返回hash表中所有键值对
	 */
	Set<Entry<K, V>> entrySet();

	/**
	 *
	 * 存储键值对
	 * 
	 * @param <K>
	 *            key
	 * @param <V>
	 *            value
	 */
	public static class Entry<K, V> {
		K key;
		V value;

		public K getKey() {
			return key;
		}

		public void setKey(K key) {
			this.key = key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}
		
		public Entry() {
		}

		public Entry(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

		@Override
		public String toString() {
			return "Entry [key=" + key + ", value=" + value + "]";
		}

	}
}
