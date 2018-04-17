package hash;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		HashTable<String, Integer> map = new HashTable<>();
		int nums = 100;
		for (int i = 0; i < nums; i++) {
			map.put("" + i, i);
		}
		for (int i = 0; i < nums; i++) {
			System.out.print(i + ":" + map.get(i + "") + "\t");
		}
		System.out.println();
		System.out.println(map.remove("2"));
		for (int i = 0; i < nums; i++) {
			System.out.print(i + ":" + map.get(i + "") + "\t");
		}

		// Node<String,Integer> a = new Node<>("a",1,null);
		// Node<String,Integer> b = new Node<>("b",2,null);
		// Node<String,Integer> c = new Node<>("c",3,null);
		// a.next=b;
		// b.next=c;
		// a.next=a.next.next;
		//// b.next=null;
		// System.out.println(a);
		// System.out.println(b);
		// System.out.println(c);
	}
}
