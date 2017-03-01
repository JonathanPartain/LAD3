import java.util.Arrays;

public class SortedListImpl<E extends Comparable<E>> implements SortedList<E> {
    
    private int arraysize = 0;
    private int size = 0;
    private E[] arr = (E[]) new Comparable[size];
    public void add(E elem) {
        if (size <= 0) {
            size++;
        } else if(arraysize>=arr.length){
            size = arraysize * 2;
        }
        arr = Arrays.copyOf(arr, size);
        
        arr[arraysize] = elem;
        arraysize++;
        swap(elem, searchPos(elem, true));
    }
    
    public void addSortedArray(E[] arr) {
        for (E elem : arr)
            add(elem);
    }
    
    public E get(int ix) {
        if (ix < 0 || ix >= arraysize)
            return null;
        return arr[ix];
    }
    
    public int firstIndex(E elem) {
        return searchPos(elem, true);
    }
    
    public int lastIndex(E elem) {
        return searchPos(elem, false);
    }
    
    public boolean contains(E elem) {
        return elem.compareTo(arr[searchPos(elem, true)]) == 0;
    }
    
    public int countBetween(E lo, E hi) {
        return lastIndex(hi) - firstIndex(lo) + 1;
    }
    
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
