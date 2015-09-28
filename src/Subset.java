public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> d = new RandomizedQueue<>();
        String s;
        int count = 0;
        while (!StdIn.isEmpty()) {
            s = StdIn.readString();
            if (count < k) {
                d.enqueue(s);
                count++;
            }
            else {
                count++;
                int i = StdRandom.uniform(1, count+1);
                if (i <= k) {
                    d.dequeue();
                    d.enqueue(s);
                }
            }
        }
        while (k > 0) {
            StdOut.println(d.dequeue());
            k--;
        }
                
    }
}
