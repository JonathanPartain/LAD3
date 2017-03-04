import java.util.Arrays;

public class SortedListImpl<E extends Comparable<E>> implements SortedList<E> {

	private int arraysize = 0;
	private int size = 0;

	@SuppressWarnings("unchecked")
	private E[] arr = (E[]) new Comparable[size];
	
	@Override
	public void add(E elem) {
		if(arraysize >= arr.length) {
			if (size <= 0) {
				size++;
			} else if(arraysize>=arr.length){
				size = arraysize * 2;
			}
			arr = Arrays.copyOf(arr, size);
		}
		arr[arraysize] = elem;
		arraysize++;
		swap(elem, searchPos(elem, true));
	}
	
	@Override
	public void addSortedArray(E[] arr) {
		this.arr = Arrays.copyOf(this.arr, arraysize);
		@SuppressWarnings("unchecked")
		E[] tmpArr = (E[]) new Comparable[arr.length + this.arr.length];
		int i = 0;
		int j = 0;
		int k = 0;
		while (i < arr.length && j < this.arr.length) {
			if (arr[i].compareTo(this.arr[j]) < 1) {
				tmpArr[k] = arr[i];
				i++;
			} else {
				tmpArr[k] = this.arr[j];
				j++;
			}
			k++;
		}

		E[] rest;
		int restIndex;
		if (i >= arr.length) {
			rest = this.arr;
			restIndex = j;
		} else {
			rest = arr;
			restIndex = i;
		}

		for (int m = restIndex; m < rest.length; m++) {
			tmpArr[k] = rest[m];
			k++;
		}
		arraysize += arr.length;
		this.arr = Arrays.copyOf(tmpArr, arraysize);
	}

	@Override
	public E get(int ix) {
		if (ix < 0 || ix > arraysize)
			return null;
		return arr[ix];
	}

	@Override
	public int firstIndex(E elem) {
		return searchPos(elem, true);
	}

	@Override
	public int lastIndex(E elem) {
		return searchPos(elem, false);
	}

	@Override
	public boolean contains(E elem) {
		return elem.compareTo(arr[searchPos(elem, true)]) == 0;
	}

	@Override
	public int countBetween(E lo, E hi) {
		return lastIndex(hi) - firstIndex(lo) + 1;
	}

	@Override
	public int size() {
		return arraysize;
	}

	private int searchPos(E elem, boolean lower) {
		int lo = 0;
		int hi = arraysize - 1;

		while (lo <= hi) {
			int mid = (lo + hi) / 2;
			if (lower) {
				if (elem.compareTo(arr[mid]) > 0) {
					lo = mid + 1;
				} else {
					hi = mid - 1;
				}
			} else {
				if (elem.compareTo(arr[mid]) < 0) {
					hi = mid - 1;
				} else {
					lo = mid + 1;
				}
			}
		}		
		return lower?lo:hi;
	}

	private E[] swap(E elem, int lo) {
		E tmpE = arr[lo];
		arr[lo] = elem;

		for (int i = lo + 1; i <= arraysize - 1; i++) {
			arr[arraysize - 1] = arr[i];
			arr[i] = tmpE;
			tmpE = arr[arraysize - 1];
		}
		return arr;
	}
}
