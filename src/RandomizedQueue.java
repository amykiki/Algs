
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N = 0;
    private Item[] a = (Item []) new Object[1];
    public RandomizedQueue() {
        
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
        if (N == a.length) resize(a.length * 2);
        a[N++] = item;
    }
    public Item dequeue() {
        Item item = null;
        try {
            if (isEmpty()) {
                throw new java.util.NoSuchElementException("RandomizedQueue is empty");
            }
            else {
                N--;
            }
        }
        catch (java.util.NoSuchElementException e) {
            throw e;
        }
        return item;
    }
    private int getRandom() {
        int index = StdRandom.u
    }
}
