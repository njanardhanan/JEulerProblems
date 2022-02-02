package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class JEulerProblem_0358 extends EulerSolver {

    public JEulerProblem_0358(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //https://en.wikipedia.org/wiki/Cyclic_number
        int suffix = 56789;
        long ans = 0;

        //Found the range by trail and error.
        for(int p = 700000001; p < 750000000; p+=2) {
            //Skip the trivial non prime candidates.
            if (p%3 == 0 || p%5 == 0 || p%7 == 0) continue;
            if (!BigInteger.valueOf(p).isProbablePrime(1)) continue;
            if(!doesStartWith137(p)) continue;
            if(!doesEndWith56789(p)) continue;
            long sum = constructCyclicNumber(p, suffix);
            if (sum != 0) ans = sum;
        }
        return Long.toString(ans);
    }

    private boolean doesStartWith137(int p) {
        /**
         *  Alternate approach, but this will be very slow.
         *  BigDecimal.ONE.divide(BigDecimal.valueOf(p)).toPlainString().startsWith("0.00000000137")
         */

        double d = 1.0/p;
        return (d > 0.00000000137) && (d < 0.00000000138);
    }

    private boolean doesEndWith56789(int p) {
        /**
         *   As per https://en.wikipedia.org/wiki/Cyclic_number
         *
         *   (10^(p-1) - 1) / p = cyclicNumber
         *   We need to check only last 5 digits, so take mod 10^5 on both sides
         *
         *   ((10^(p-1) - 1) / p) % 10^5 = cyclicNumber  % 10^5
         *   i.e. (10^(p-1) - 1) % 10^5 = (cyclicNumber * p) % 10^5
         *   i.e. 10^(p-1) % 10^5 = (cyclicNumber * p + 1) % 10^5
         *   i.e. 0 = (cyclicNumber * p + 1) % 10^5
         *
         *   last 5 digits are 56789
         *   therefore
         *   ((56789 * p) + 1) % 10^5 = 0
         *   if the above condition statisfy then the cyclic number ends with 56789
         */

        BigInteger suffix = BigInteger.valueOf(56789);
        int v = suffix.multiply(BigInteger.valueOf(p)).add(BigInteger.ONE).mod(BigInteger.TEN.pow(5)).intValue();
        return v == 0;
    }

    private long constructCyclicNumber(int p, long suffix) {
        long t = 0;
        long r = 1;
        long sum = 0;
        long actualSuffix = 0;
        while(true) {
            t++;
            long x = r * 10;
            long d = x/p;
            r = x % p;
            sum += d;
            actualSuffix = actualSuffix * 10 + d;
            actualSuffix %= 100000;
            if (r == 1) break;
            if (t == p-1) break;
        }
        if (t == p-1) {
            System.out.println("Prime " + p + " ends with " + actualSuffix);
            if (actualSuffix == suffix) {
                return sum;
            }
        }

        return 0;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=358\n";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
