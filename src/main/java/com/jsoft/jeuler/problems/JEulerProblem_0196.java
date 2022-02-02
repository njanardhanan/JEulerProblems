package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;
import java.util.Set;

public class JEulerProblem_0196 extends EulerSolver {

    public JEulerProblem_0196(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * 25983272566154 to 25983308610078
         * diff : 36043925
         */
        int LIMIT_1 = 7208785;
        /**
         * 16119981111301 to 16120009501435
         * diff : 28390135
         */
        int LIMIT_2 = 5678027;

        Set<Long> primes = getPrimeSieveByRange(LIMIT_1);
        long ans = sumOfPrimeTriplets(primes, LIMIT_1);

        primes = getPrimeSieveByRange(LIMIT_2);
        ans += sumOfPrimeTriplets(primes, LIMIT_2);

        return Long.toString(ans);
    }

    private Set<Long> getPrimeSieveByRange(int rownum) {
        long low = getValue(rownum - 2, 1);
        long high = getValue(rownum + 2, rownum + 2);

        List<Integer> primeSieves = PrimeNumberHelper.sieveOfEratosthenesAsList((int)Math.sqrt(high));

        boolean[] primesByRange = PrimeNumberHelper.sieveByRange(low, high, primeSieves);
        Set<Long> primes = PrimeNumberHelper.primesFromSieveByRange(low, high, primesByRange);
        //System.out.println(primes.size());
        return primes;
    }

    private long sumOfPrimeTriplets(Set<Long> primes, int rownum) {
        long ans = 0;
        for (int c = 1; c <= rownum; c++) {
            long v = getValue(rownum, c);
            if (primes.contains(v)) {
                if (isPrimeTriplet(primes, rownum, c, -1, -1, 1)) {
                    System.out.println("Ans : " + v);
                    ans += v;
                }
            }
        }

        System.out.println("Returning : " + ans);
        return ans;
    }

    private long getValue(long row, long col) {
        if (col < 1 || col > row) {
            return -1;
        }
        return  (((row-1) * row) / 2) + col;
    }

    private boolean isPrimeTriplet(Set<Long> primes, int row, int col, int pRow, int pCol, int level) {
        if (level > 2) {
            return true;
        }

        int[] rowOff = {-1, 1};
        int[] colOff = {-1, 0, 1};
        int neighbourPrimeCount = 0;

        for (int rOff : rowOff) {
            for (int cOff : colOff) {
                int r = row + rOff;
                int c = col + cOff;
                if (r == pRow && c == pCol) {
                    continue;
                }
                long v = getValue(r, c);
                if (primes.contains(v)) {
                    neighbourPrimeCount++;
                    if (isPrimeTriplet(primes, r, c, row, col, level+1)) {
                        return true;
                    }
                }
                if (neighbourPrimeCount >= 2) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=196";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
