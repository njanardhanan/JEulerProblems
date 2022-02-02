package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0755 extends EulerSolver {

    private Map<Long, Map<Long, Map<Long, Long>>> memo = new HashMap<>();

    public JEulerProblem_0755(int problemNumber) {
        super(problemNumber);
    }

    private final long LIMIT = 10000000000000L;
    private final int MAX_FIB_NUM = 65;
    private long[] fib = new long[MAX_FIB_NUM + 1];

    private void generateFibonacci() {
        // First two number of fibonacci sequence
        fib[1] = 1;
        fib[2] = 2;

        for(int i = 3; i <= MAX_FIB_NUM; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
    }

    // Function to find maximum ways to represent num as the sum of fibonacci number
    private long find(long num, int index) {
        long[] dp1 = new long[MAX_FIB_NUM + 1];
        long[] dp2 = new long[MAX_FIB_NUM + 1];
        long[] v = new long[MAX_FIB_NUM + 1];

        int cnt = 0;
        //long nn = num;

        for(int i = index; i > 0 && num > 0; i--) {
            if (num >= fib[i]) {
                v[cnt++] = i;
                num -= fib[i];
            }
        }


        // Reverse the number
        for(int i = 0; i < cnt / 2; i++) {
            long t = v[i];
            v[i] = v[cnt - i - 1];
            v[cnt - i - 1] = t;
        }

        //long[] subArray = IntStream.range(0, cnt).mapToLong(i -> v[i]).toArray();
        //System.out.print(nn + " : " + Arrays.toString(subArray));

        // Base condition of dp1 and dp2
        dp1[0] = 1;
        dp2[0] = (v[0] - 1) / 2;

        // Iterate from 1 to cnt
        for(int i = 1; i < cnt; i++) {

            // Calculate dp1[]
            dp1[i] = dp1[i - 1] + dp2[i - 1];

            // Calculate dp2[]
            dp2[i] = ((v[i] - v[i - 1]) / 2) *
                    dp2[i - 1] +
                    ((v[i] - v[i - 1] - 1) / 2) *
                            dp1[i - 1];
        }

        // Return final ans
        return (dp1[cnt - 1] + dp2[cnt - 1]);
    }


    private long sumZ(long num) {
        List<Integer> Z = new ArrayList<>();
        boolean started = false;
        int cnt = 0;

        for(int i = MAX_FIB_NUM-1; i > 0; i--) {
            if (num >= fib[i]) {
                Z.add(1);
                cnt++;
                num -= fib[i];
                started = true;
            } else if (started) {
                Z.add(0);
            }
        }

        System.out.println(Z);
        System.out.println(Z.size());
        System.out.println(cnt);

        long[] X = new long[cnt];
        int t = 0;

        if (cnt == 1) {
            return (Z.size() + 1) / 2;
        } else {
            for (int i=0; i<Z.size(); i++) {
                if (Z.get(i) == 1) {
                    X[t] = Z.size() - (i+1) + 2;
                    t++;
                }
            }

            System.out.println("X : " + Arrays.toString(X));

            long[] Tp = new long[cnt-1];
            long[] Ep = new long[cnt-1];

            for (int i=0; i<cnt-1; i++) {
                Tp[i] = (X[i] - X[i+1] + 2) / 2;
                Ep[i] = (2 * Tp[i]) - 1 - X[i] + X[i+1];
            }
            System.out.println("T : " + Arrays.toString(Tp));
            System.out.println("Ep : " + Arrays.toString(Ep));

            long[] R = new long[cnt];
            for (int i=0; i<cnt; i++) {
                R[i] = 1;
            }
            R[1] = Tp[0];

            for (int i=2; i<cnt; i++) {
                R[i] = (Tp[i-1] * R[i-1]) - (Ep[i-2] * R[i-2]);
            }
            System.out.println("R : " + Arrays.toString(R));
            return R[cnt-1] * (long)Math.floor(X[cnt-1]/2) - R[cnt-2] * Ep[cnt-2];
        }
    }

    private long sumZZ(long num) {
        List<Integer> Z = new ArrayList<>();
        boolean started = false;
        int cnt = 0;

        for(int i = MAX_FIB_NUM-1; i > 0; i--) {
            if (num >= fib[i]) {
                Z.add(1);
                cnt++;
                num -= fib[i];
                started = true;
            } else if (started) {
                Z.add(0);
            }
        }

        System.out.println(Z);
        System.out.println(Z.size());
        System.out.println(cnt);

        long[] X = new long[cnt];
        int t = 0;

        if (cnt == 1) {
            int m0 = Z.size() + 1;
            return (long)((Math.pow(2, m0) / 6.0) + ((m0+1.0)/2.0));
        } else {
            for (int i=0; i<Z.size(); i++) {
                if (Z.get(i) == 1) {
                    X[t] = Z.size() - (i+1) + 2;
                    t++;
                }
            }

            System.out.println("X : " + Arrays.toString(X));

            long[] Tp = new long[cnt-1];
            long[] Ep = new long[cnt-1];

            for (int i=0; i<cnt-1; i++) {
                Tp[i] = (X[i] - X[i+1] + 2) / 2;
                Ep[i] = (2 * Tp[i]) - 1 - X[i] + X[i+1];
            }
            System.out.println("T : " + Arrays.toString(Tp));
            System.out.println("Ep : " + Arrays.toString(Ep));

            long[] R = new long[cnt];
            for (int i=0; i<cnt; i++) {
                R[i] = 1;
            }
            R[1] = Tp[0];

            for (int i=2; i<cnt; i++) {
                R[i] = (Tp[i-1] * R[i-1]) - (Ep[i-2] * R[i-2]);
            }
            System.out.println("R : " + Arrays.toString(R));
            //return R[cnt-1] * (long)Math.floor(X[cnt-1]/2) - R[cnt-2] * Ep[cnt-2];
            long twoPower = (long)((Math.pow(2, X[cnt-1]) / 6.0) + ((X[cnt-1]+1.0)/2.0));
            long firstPart = R[cnt-1] * twoPower;
            long secondPart = Ep[cnt-2] * R[cnt-2];
            long thridPart = 0;
            for (int i=1; i<cnt; i++) {
                long twoPow = (long)Math.pow(2, X[i-1] - (2*Tp[i-1]));
                thridPart += R[i-1] * getF(Tp[i-1]) * twoPow;
            }
            return firstPart - secondPart + thridPart;
        }
    }

    private long getF(long tp) {
        long fourPower = (long) Math.pow(4, tp-1);
        return 1 + ((2 * (fourPower - 1)) / 3);
    }


    @Override
    public String solve() {
        generateFibonacci();
        long ans = sumZZ(LIMIT);
        return Long.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        return "https://oeis.org/A000119 \n" +
                "Refer problem_755.pdf \n" +
                "https://www.geeksforgeeks.org/count-of-ways-in-which-n-can-be-represented-as-sum-of-fibonacci-numbers-without-repetition/ \n" +
                "https://projecteuler.net/problem=755\n";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
