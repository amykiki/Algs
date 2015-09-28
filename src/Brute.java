import java.util.Arrays;

public class Brute {
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
        Arrays.sort(points);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point point : points) {
            point.draw();
        }
        for (int i = 0; i < N - 3; i++) {
            for (int j = i + 1; j < N - 2; j++) {
                for (int k = j + 1; k < N - 1; k++) {
                    for (int m = k + 1; m < N; m++) {
                        Point v = points[i];
                        double slopeJ = v.slopeTo(points[j]);
                        double slopeK = v.slopeTo(points[k]);
                        double slopeM = v.slopeTo(points[m]);
                        if (slopeJ == slopeK && slopeJ == slopeM) {
                            Point[] result = new Point[4];
                            result[0] = points[i];
                            result[1] = points[j];
                            result[2] = points[k];
                            result[3] = points[m];
                            StdOut.print(result[0].toString());
                            for (int p = 1; p < 4; p++) {
                                StdOut.print(" -> " + result[p]);
                            }
                            result[0].drawTo(result[3]);
                            StdOut.println();
                        }
                    }
                }
            }
        }
    }
}
