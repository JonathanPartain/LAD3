import java.util.Arrays;

public class SortedList<E extends Comparable<E>> {


    private int arraysize = 0;


    @SuppressWarnings("unchecked")

    private E[] arr = (E[]) new Comparable[arraysize];



    public void add(E elem) {


        if (arraysize <= arr.length) {

            arraysize++;

            arr = Arrays.copyOf(arr, arraysize);

        }


        arr[arraysize - 1] = elem;


        int lo = 0;

        int hi = arraysize - 1;


        while (lo <= hi) {


            int mid = (lo + hi) / 2;


            if (elem.compareTo(arr[mid]) > 0) {

                lo = mid + 1;

            } else if (elem.compareTo(arr[mid]) < 0) {

                hi = mid - 1;

            } else {

                lo = mid;

                break;

            }


        }


        E tmpE = arr[lo];

        arr[lo] = elem;


        for (int i = lo + 1; i <= arraysize - 1; i++) {

            arr[arraysize - 1] = arr[i];

            arr[i] = tmpE;

            tmpE = arr[arraysize - 1];

        }

    }



    public void addSortedArray(E[] arr) {

        for (E elem : arr) {

            add(elem);

        }

    }



    public E get(int ix) {

        return arr[ix];

    }


    public int firstIndex(E elem) {

        int firstIndex = 0;

        for (int i = 0; i < arraysize; i++) {

            if (elem.compareTo(get(i)) <= 0) {

                firstIndex = i;

                break;

            } else {

                firstIndex++;

            }

        }

        return firstIndex;

    }




    public int lastIndex(E elem) {

        int lastIndex = 0;

        for (int i = 0; i < arraysize; i++) {

            if (elem.compareTo(get(i)) < 0) {

                lastIndex = i - 1;

                break;

            } else {

                lastIndex = i;

            }

        }

        return lastIndex;

    }


    public boolean contains(E elem) {

        for (int i = 0; i < arraysize - 1; i++) {

            if (elem.compareTo(get(i)) == 0) {

                return true;

            }

        }

        return false;

    }

    public int countBetween(E lo, E hi) {

        return lastIndex(hi) - firstIndex(lo) + 1;

    }

    public int size() {

        return arraysize;

    }


}
