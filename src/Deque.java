import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int N;
    
    public Deque() {
        N     = 0;
        first = null;
        last  = null;
    }
    private class Node {
        private Item item;
        private Node forward;
        private Node next;
    }
    public boolean isEmpty() {
        return N == 0;
    }
    public int size() {
        return N;
    }
    
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException("the add item is NULL");
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.forward = null;
        first.next = oldFirst;
        if (isEmpty()) {
            last = first;
        }
        else {
            oldFirst.forward = first;
        }
        N++;
    }
    
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException("the add item is NULL");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.forward = oldLast;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldLast.next = last;
        }
        N++;
    }
    
    public Item removeFirst() {
        Item item = null;
        try {
            if (isEmpty()) {
                throw new java.util.NoSuchElementException("Deque is empty");
            }
            else {
                item = first.item;
                if (first == last) {
                    first = null;
                    last  = null;
                }
                else {
                    first = first.next;
                    first.forward = null;
                }
                N--;
            }
        }
        catch (java.util.NoSuchElementException e) {
            throw e;
        }
        return item;
    }
    public Item removeLast() {
        Item item = null;
        try {
            if (isEmpty()) {
                throw new java.util.NoSuchElementException("Deque is empty");
            }
            else {
                item = last.item;
                if (last == first) {
                    first = null;
                    last  = null;
                }
                else {
                    last = last.forward;
                    last.next = null;
                }
                N--;
            }
        }
        catch (java.util.NoSuchElementException e) {
            throw e;
        }
        return item;
    }
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        @Override
        public boolean hasNext() {
            return current != null;
        }
        @Override
        public Item next() {
            Item item = null;
            try {
                if (current == null) {
                    throw new java.util.NoSuchElementException("Deque is empty");
                }
                else {
                    item = current.item;
                    current = current.next;
                }
            }
            catch (java.util.NoSuchElementException e) {
                throw e;
            }
            return item;
        }
    }
    
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        d.addFirst(5);
        d.addLast(6);
        d.addFirst(7);
        int rc = d.removeFirst();
        StdOut.println(rc);
        rc = d.removeLast();
        StdOut.println(rc);
        rc = d.removeFirst();
        StdOut.println(rc);
        d.addFirst(10);
        d.addFirst(11);
        d.addLast(9);
        d.addLast(8);
        Iterator<Integer> it = d.iterator();
        while (it.hasNext()) {
            StdOut.print(it.next() + " ");
        }
        StdOut.println();
    }
}
