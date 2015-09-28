import java.util.Arrays;

public class Fast {
    public static void main(String[] args) {
        String file = args[0];
//        String dir = "D:/Algs/W3/collinear-testing";
//        file = dir + '/' + file;
        In inFile = new In(file);
        int N = inFile.readInt();
        Point[] points = new Point[N];
        int count = 0;
        while (count < N) {
            int x = inFile.readInt();
            int y = inFile.readInt();
            points[count] = new Point(x, y);
            count++;
        }
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point point : points) {
            point.draw();
        }
        Arrays.sort(points);
//        for (Point p : points) {
//            StdOut.print(p + " ");
//        }
//        StdOut.println();
        for (int i = 0; i < N - 3; i++) {
            Point[] pPoints = new Point[N];
            for (int j = 0; j < N; j++) {
                pPoints[j] = points[j];
            }
            Arrays.sort(pPoints, points[i].SLOPE_ORDER);
//            for (Point p : pPoints) {
//                StdOut.print(p + " ");
//            }
//            StdOut.println();
            int m = 0;
            Point v = pPoints[0];
            int n = 2;
            int num = 2;
            while (n < N) {
                while (n < N && (v.slopeTo(pPoints[n]) == v.slopeTo(pPoints[n-1]))) {
                    num++;
                    n++;
                }
                if (num >= 4) {
                    Point minpt = pPoints[m];
                    int nextpti = n - num + 1;
                    if (pPoints[m].compareTo(pPoints[nextpti]) > 0) {
                        minpt = pPoints[nextpti];
                        pPoints[nextpti] = pPoints[m];
                    }
                    int index = Arrays.binarySearch(points, 0, i, minpt);
                    if (index < 0) {
                        StdOut.print(minpt.toString());
                        for (int z = nextpti; z < n; z++) {
                            StdOut.print(" -> " + pPoints[z]);
                        }
                        StdOut.println();
                        pPoints[n-1].drawTo(minpt);
                    }
                }
                n++;
                num = 2;
            }
        }
    }
}
