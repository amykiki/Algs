import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N;
    private Item[] a = (Item []) new Object[1];
    public RandomizedQueue() {
        N = 0;
    }
    public boolean isEmpty() {
        return N == 0;
    }
    public int size() {
        return N;
    }
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException("the add item is NULL");
        }
        if (N == a.length) {
            resize(a.length * 2);
        }
        a[N++] = item;
    }
    public Item dequeue() {
        Item item = null;
        try {
            if (isEmpty()) {
                throw new java.util.NoSuchElementException(
                           "RandomizedQueue is empty");
            }
            else {
                int index = getRandom();
                item = a[index];
                Item temp = a[N-1];
                a[index] = temp;
                a[N-1] = null;
                N--;
                
                if (N > 0 && N == a.length/4) {
                    resize(a.length/2);
                }
            }
        }
        catch (java.util.NoSuchElementException e) {
            throw e;
        }
        return item;
    }
    private int getRandom() {
        int index = StdRandom.uniform(a.length);
        while (a[index] == null) {
            index = StdRandom.uniform(a.length);
        }
        return index;
        
    }
    public Item sample() {
        Item item = null;
        try {
            if (isEmpty()) {
                throw new java.util.NoSuchElementException(
                           "RandomizedQueue is empty");
            }
            else {
                int index = getRandom();
                item = a[index];
            }
        }
        catch (java.util.NoSuchElementException e) {
            throw e;
        }
        return item;
    }
    @Override
    public Iterator<Item> iterator() {
        return new RandomizeIterator();
    }
    private class RandomizeIterator implements Iterator<Item> {
        private int current;
        private int[] data = new int[a.length];
        public RandomizeIterator() {
            current = N;
            for (int i = 0; i < a.length; i++) {
                if (a[i] == null) {
                    data[i] = 0;
                }
                else {
                    data[i] = 1;
                }
            }
        }
  
        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        @Override
        public Item next() {
            Item item = null;
            try {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException(
                               "RandomizedQueue is empty");
                }
                else {
                    int index = getRandom();
                    while (data[index] == 0) {
                        index = getRandom();
                    }
                    item = a[index];
                    data[index] = 0;
                    current--;
                }
            }
            catch (java.util.NoSuchElementException e) {
                throw e;
            }
            return item;
        }
        @Override
        public boolean hasNext() {
            return current > 0;
        }
    }
//    private void prindQueue() {
//        for (int k = 0; k < a.length; k++) {
//            StdOut.print(a[k] + " ");
//            if ((k+1) % 5 == 0) StdOut.println();
//        }
//        StdOut.println();
//    }
    public static void main(String[] args) {
        RandomizedQueue<Integer> r = new RandomizedQueue<>();
        r.enqueue(0);
        r.enqueue(1);
        r.dequeue();
        r.enqueue(3);
//        r.enqueue(4);
//        r.prindQueue();
//        Iterator<Integer> it = r.iterator();
//        while (it.hasNext()) {
//            StdOut.print(it.next() + " ");
//        }
//        StdOut.println();
    }
}
