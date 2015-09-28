import java.util.Comparator;

public class PointForTest implements Comparable<PointForTest> {
    public final Comparator<PointForTest> SLOPE_ORDER = new BySlope();
    
    private final int x;
    private final int y;
    
    public PointForTest(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void draw() {
        StdDraw.point(x, y);
    }
    
    public void drawTo(PointForTest that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    @Override
    public int compareTo(PointForTest that) {
        if (this.y > that.y) return +1;
        if (this.y < that.y) return -1;
        if (this.x > that.x) return +1;
        if (this.x < that.x) return -1;
        return 0;
    }
    
    public double slopeTo(PointForTest that) {
        if (this.x == that.x) {
            if (this.y == that.y) return Double.NEGATIVE_INFINITY;
            return Double.POSITIVE_INFINITY;
        }
        if (this.y == that.y) return 0.0;
        return (double) (that.y - this.y) / (that.x - this.x);
    }
    
    private class BySlope implements Comparator<PointForTest> {
        @Override
        public int compare(PointForTest p1, PointForTest p2) {
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);
            if (slope1 == slope2) return 0;
            if (slope1 > slope2) return +1;
            return -1;
        }
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    public static void main(String[] args) {
        String dir = "D:/Algs/W3/collinear-testing";
        String file = args[0];
        file = dir + '/' + file;
        In inFile = new In(file);
        int N = inFile.readInt();
        PointForTest[] points = new PointForTest[N];
        StdOut.println("number is " + N);
        int count = 0;
        while (count < N) {
            int x = inFile.readInt();
            int y = inFile.readInt();
            points[count] = new PointForTest(x, y);
            count++;
        }
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (PointForTest point : points) {
            point.draw();
        }
        double a = Double.POSITIVE_INFINITY;
        double b = Double.NEGATIVE_INFINITY;
        double c = a - b;
        if (a == a) {
            StdOut.println("infinity equal");
        }
        if (c > 0) {
            StdOut.println("> 0");
        }
        else if (c == 0) {
            StdOut.println("= 0");
        }
        else if (c < 0) {
            StdOut.println("< 0");
        }
    }
    
}
