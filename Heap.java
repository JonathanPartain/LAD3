// De andra, deadline fredag
public class Heap<E extends Comparable<E>>{
    
    private E[] arr;
    
    private int size = 0;
    
    /** Creates a heap with an initial capacity of 8 **/
    @SuppressWarnings("unchecked")
    public Heap(){
        arr = (E[]) new Comparable[8];
    }
    
    /** Finds the minimal element in the heap, should be O(1). 
     * @return The minimal element
     * **/
    public E findMin(){
        // This works but is way too slow!
        E min = null;
        for(int i = 0; i < size; i++)
            if (min == null || arr[i].compareTo(min)<0)
                min = arr[i];
        
        return min;        
    }
    
    /** Finds and removes the minimal element from the heap, should be O(log N). 
     * 
     * @return The minimal element
     * **/
    public E removeMin(){
        // This works but is way too slow!
        int minix = 0;
        for(int i = 1;i<size;i++)
            if (arr[i].compareTo(arr[minix]) < 0)
                minix = i;
        
        E min = arr[minix];
        
        int i;
        for(i = minix;i<size-1;i++)
            arr[i]=arr[i+1];
        arr[size-1]=null;
        
        size--;
        
        return min;   
        
    }
    
    
    /** Adds an element to the heap, should be O(log N). 
     * @param elem The element to be added.
     * **/
    public void add(E elem){
        if (size >= arr.length){
            @SuppressWarnings("unchecked")
            E[] newArr = (E[]) new Comparable[size*2];
            for (int i = 0;i<arr.length;i++) newArr[i] = arr[i];
            arr = newArr;
        }

        arr[size] = elem;
        
        // Something more should probably be done here?
        
        size++;
    }
    
    public int size(){
        return size;
    }
    
    public boolean isEmpty(){
        return size <= 0;
    }
    
}