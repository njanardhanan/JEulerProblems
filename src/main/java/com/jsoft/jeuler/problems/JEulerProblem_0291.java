package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0291 extends EulerSolver {

    public JEulerProblem_0291(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         *  Ran bruteforce and found https://oeis.org/A027862
         */
        long LIMIT = 5 * (long)Math.pow(10, 15);
        long ACTUAL_LIMIT = (long)Math.sqrt(LIMIT) + 1;
        System.out.println(ACTUAL_LIMIT);
        long ans = 0;

        for (long i=1; i<=ACTUAL_LIMIT; i++) {
            if (i % 10000000 == 0) {
                System.out.println(i);
            }
            long n = i*i + ((i+1) * (i+1));
            if (n>LIMIT) break;
            if (n!=5 && n%5 == 0) continue;
            if (PrimeNumberHelper.checkPrime(n,5)) {
                //System.out.println(n);
                ans++;
            }
        }
        return Long.toString(ans);
    }

    public String solveBruteForce() {
        //High limit will be very slow.
        for (long x=1; x<=200; x++) {
            if (isPanaitopol(x)) {
                System.out.println(x);
            }
        }
        return "0";
    }

    private boolean isPanaitopol(long n) {
        if (!PrimeNumberHelper.checkPrime(n)) {
            return false;
        }

        for (long x=1; x<20*n; x++) {
            for (long y=1; y<20*n; y++) {
                long num = x*x*x*x - y*y*y*y;
                long denum = x*x*x + y*y*y;

                if (denum * n == num) {
                    //System.out.println(num + "/" + denum + "=" + n);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getProblemStatement() {
        return "A prime number p is called a Panaitopol prime if \n" +
                "p = (x^4 - y^4) / (x^3 + y^3)" +
                " \n" +
                " for some positive integers x and y.\n" +
                "\n" +
                "Find how many Panaitopol primes are less than 5×10^15.";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList();
    }
}
