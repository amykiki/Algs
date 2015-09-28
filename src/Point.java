import java.util.Comparator;

public class Point implements Comparable<Point> {
    public final Comparator<Point> SLOPE_ORDER = new BySlope();
    
    private final int x;
    private final int y;
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void draw() {
        StdDraw.point(x, y);
    }
    
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    @Override
    public int compareTo(Point that) {
        if (this.y > that.y) return +1;
        if (this.y < that.y) return -1;
        if (this.x > that.x) return +1;
        if (this.x < that.x) return -1;
        return 0;
    }
    
    public double slopeTo(Point that) {
        if (this.x == that.x) {
            if (this.y == that.y) return Double.NEGATIVE_INFINITY;
            return Double.POSITIVE_INFINITY;
        }
        if (this.y == that.y) return 0.0;
        return (double) (that.y - this.y) / (that.x - this.x);
    }
    
    private class BySlope implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
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
    
}
