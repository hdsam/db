package search;

/**
 * @author green
 * 二分查找法
 */
public class BinarySearch {

	/**
	 * non-reclusive
	 */
	public static int binarySearch(int[] arr, int key) {
		int low = 0;
		int high = arr.length;
		int mid;
		while (low <= high) {
			mid = low + (high - low) / 2;
			if (arr[mid] < key) {
				low = mid + 1;
			} else if (arr[mid] > key) {
				high = mid - 1;
			} else {
				return mid;
			}
		}
		return -1;
	}

	/**
	 * reclusive
	 */
	public static int binarySearch(int[] arr, int key, int low, int high) {
		if (low > high) {
			return -1;
		}
		int mid = low + (high - low) / 2;
		if (arr[mid] == key) {
			return mid;
		} else if (arr[mid] < key) {
			return binarySearch(arr, key, mid + 1, high);
		} else {
			return binarySearch(arr, key, low, mid - 1);
		}
	}

	public static void main(String[] args) {
		int[] arr = { -1, 2, 3, 4, 5, 7, 9, 10 };
		System.out.println(binarySearch(arr, 9));
		System.out.println(binarySearch(arr, 9, 0, arr.length-1));
	}
}
