/**
 * The <tt>PercolationStats</tt> class provides methods for compute the an
 * estimation of the percolation threshold, the sample standard deviation and
 * the 95% confidence interval.
 * <p>
 * @author Amy
 */
public class PercolationStats {
    private int N = 0;              // N*N grid
    private int T = 0;               // repeat time
    private double[] data;          // data[i] save the threshold for one time
    
    /**
     * Initializes repeat time
     * @throws java.lang.IllegalArgumentException if T < 0
     * @param aT the repeat test time
     */
    public PercolationStats(int aN, int aT) {
        if (aN <= 0 || aT <= 0) {
            throw new IllegalArgumentException("Arugument N and T Must "
                    + "                        Greater than 0");
        }
        N = aN;
        T = aT;
        data = new double[T];
        int repeat = 0;
        while (repeat < T) {
            Percolation pla = new Percolation(N);
            int x = 0;
            int y = 0;
            double i = 0.0;
            while (!pla.percolates()) {
                x = StdRandom.uniform(1, N + 1);
                y = StdRandom.uniform(1, N + 1);
                if (!pla.isOpen(x, y)) {
                    pla.open(x, y);
                    i++;
                }
                else {
                    continue;
                }
            }
            data[repeat] = i / (N*N);
            repeat++;
        }
        
    }
    
    /**
     * @return estimation of T times mean threshold value
     */
    public double mean() {
        double sum = 0.0;
        for (double x : data) {
            sum += x;
        }
        double meanThres = sum /T;
        return meanThres;
    }
    
    /**
     * @return the sample standard deviation, measures the
     * sharpness of the threshold.
     */
    public double stddev() {
        double sum = 0.0;
        double product = 0.0;
        for (double x : data) {
            sum += x;
            product += x*x;
        }
        double mean = sum / T;
        double std = product - 2*sum*mean + T*mean*mean;
        if (T == 1) return 0;
        std = Math.sqrt(std / (T - 1));
        return std;
    }
    
    /**
     * @return  95% confidence interval for the percolation threshold of lower bound
     */
    public double confidenceLo() {
        return (mean() - 1.96*stddev()/Math.sqrt(T));
    }
    
    /**
     * @return 95% confidence interval for the percolation threshold of upper bound
     */
    public double confidenceHi() {
        return (mean() + 1.96*stddev()/Math.sqrt(T));
    }
    
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        
        PercolationStats plaStat = new PercolationStats(N, T);
        StdOut.println("Input Size N is " + N + ", Repeat T is " + T);
        StdOut.printf("%-23s = %.16f\n", "mean", plaStat.mean());
        StdOut.printf("%-23s = %.16f\n", "stddev", plaStat.stddev());
        StdOut.printf("95%% confidence interval = %.16f, %.16f\n",
                      plaStat.confidenceLo(), plaStat.confidenceHi());
    }
}

