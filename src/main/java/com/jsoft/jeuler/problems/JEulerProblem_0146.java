package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;

public class JEulerProblem_0146 extends EulerSolver {

    public JEulerProblem_0146(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long LIMIT = 150_000_000L;
        long[] offset = {1, 3, 7, 9, 13, 27};
        long[] negativeOffset = {5, 11, 15, 17, 19, 21, 23};
        long sum = 0;
        for (long i=10; i<=LIMIT; i+=10) {

            //Optimization #1
            if (i%3 == 0 || i%7 == 0 || i%13 == 0) {
                continue;
            }

            //Optimization #2
            if ((i+2)%7 < 5) continue;

            boolean flag = true;
            BigInteger b = BigInteger.valueOf(i).pow(2);

            for(long x : offset) {
                BigInteger p = b.add(BigInteger.valueOf(x));
                if (p.isProbablePrime(1)) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }

            if (!flag) continue;

            for(long x : negativeOffset) {
                BigInteger p = b.add(BigInteger.valueOf(x));
                if (!p.isProbablePrime(1)) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                System.out.println(i);
                sum += i;
            }
        }
        //676333270
        return Long.toString(sum);
    }

    @Override
    public String getProblemStatement() {
        return "The smallest positive integer n for which the numbers n2+1, n2+3, n2+7, n2+9, n2+13, and n2+27 are consecutive primes is 10. The sum of all such integers n below one-million is 1242490.\n" +
                "\n" +
                "What is the sum of all such integers n below 150 million?";
    }
}
