import java.util.Arrays;

public class pe290_java_dp {

    static int sumDigitsOf(long n) {
        int s = 0;
        for (; n > 0; n /= 10) {
            s += n % 10;
        }
        return s;
    }

    static long solveByDPFast(int n) {
        final int maxD = 9 * n;    // max digit sum
        final int maxC = 999;      // max carry
        long[][][][] f = new long[2][maxD+1][maxD+1][maxC+1];
        f[0][0][0][0] = 1;
        for (int i = 0; i < n; ++i) {
            for (int d1 = 0; d1 <= maxD; ++d1) {
                for (int d2 = 0; d2 <= maxD; ++d2) {
                    Arrays.fill(f[(i+1)&1][d1][d2], 0);
                }
            }
            for (int d1 = 0; d1 <= maxD; ++d1) {
                for (int d2 = 0; d2 <= maxD; ++d2) {
                    for (int carry = 0; carry <= maxC; ++carry) {
                        if (f[i&1][d1][d2][carry] > 0) {
                            for (int dig = 0; dig <= 9; ++dig) {
                                int t = carry + 137 * dig;
                                int newcarry = t / 10;
                                int newd1 = d1 + dig;
                                int newd2 = d2 + t % 10;
                                f[(i+1)&1][newd1][newd2][newcarry] += f[i&1][d1][d2][carry];
                            }
                        }
                    }
                }
            }
        }
        long ans = 0;
        for (int d1 = 0; d1 <= maxD; ++d1) {
            for (int d2 = 0; d2 <= d1; ++d2) {
                for (int carry = 0; carry <= maxC; ++carry) {
                    if (d1 == d2 + sumDigitsOf(carry)) {
                        ans += f[n&1][d1][d2][carry];
                    }
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        long ans = solveByDPFast(18);
        System.out.println("ans = " + ans);
    }
}