package bitmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//位图排序
public class BitMap {
	
	/**
	 * 适用于已知数据的范围
	 * 构建位图索引
	 * @param arr
	 * @param max
	 * @return
	 */
	public static int[] buildIndex(int[] arr, int max) {
		int[] res = new int[max+1];
		for (int a : arr) {
			res[a]++;
		}
		return res;
	}
	
	/**
	 * 获取数据
	 * @param arr
	 * @return
	 */
	public static int[] toArray(int[] arr) {
		List<Integer> list=new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			for(int j=0;j<arr[i];j++) {  	//not bit
				list.add(i);
			}
		}
		int[] res= new int[list.size()];
		for (int i=0;i<res.length;i++) {
			res[i]=list.get(i);
		}
		return res;
	}
	
	public static void main(String[] args) {
		int[] arr= {0,1,1,2,6,3,9,10};
		System.out.println("before sort: "+Arrays.toString(arr));

		int[] indexes = buildIndex(arr, 10);
		int[] res = toArray(indexes);
		System.out.println("after sort: "+Arrays.toString(res));
		
		
	}

}
