package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class JEulerProblem_0138 extends EulerSolver {

    public JEulerProblem_0138(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         *
         * Played with brute force and searched the no.,
         * and end up with https://oeis.org/A226205
         *
         * The formula for generating the series is
         * a(n) = 2*a(n-1) + 2*a(n-2) - a(n-3)
         *
         */

        long bMinusOne   = 3;
        long bMinusTwo   = 0;
        long bMinusThree = 1;

        int count = 0;
        int TARGET = 12;

        int currentOpteration = -1;
        BigInteger sumOfL = BigInteger.ZERO;

        while(count < TARGET) {
            long b = 2 * bMinusOne + 2 * bMinusTwo - bMinusThree;

            //swap
            bMinusThree = bMinusTwo;
            bMinusTwo = bMinusOne;
            bMinusOne = b;

            if(b%2 != 0) continue;

            //Calculate L
            BigInteger x = BigInteger.valueOf(b).divide(BigInteger.valueOf(2));
            BigInteger y = BigInteger.valueOf(b + currentOpteration);
            BigInteger lSquare = x.pow(2).add(y.pow(2));
            BigInteger L = sqrt(lSquare);
            sumOfL = sumOfL.add(L);

            count++;
            System.out.printf("%d - %d (%s)\n", count, b, (currentOpteration == 1) ? "+1" : "-1");

            if(currentOpteration == 1) {
                currentOpteration = -1;
            } else {
                currentOpteration = 1;
            }

        }

        return sumOfL.toString();
    }

    public BigInteger sqrt(BigInteger x) {
        BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
        BigInteger div2 = div;
        // Loop until we hit the same value twice in a row, or wind
        // up alternating.
        for(;;) {
            BigInteger y = div.add(x.divide(div)).shiftRight(1);
            if (y.equals(div) || y.equals(div2))
                return y;
            div2 = div;
            div = y;
        }
    }

    private void bruteForce() {
        /**
         * Given:
         * l^2 = h^2 + b^2
         *
         * where: h = b+1 or b-1
         *        b = B/2
         *
         * Therefore:
         *
         * l^2 = 5*B^2/4 - 2*B + 1
         * or
         * * l^2 = 5*B^2/4 + 2*B + 1
         *
         */
        int count = 0;

        for(long b=10; count<10; b++) {

            long bSq = b * b;
            if(bSq%4 != 0) continue;

            long x = (5 * b * b) / 4;
            long y = x - (2 * b) + 1;
            long z = x + (2 * b) + 1;

            long ySqrt = (long)Math.sqrt(y);
            long zSqrt = (long)Math.sqrt(z);

            //Perfect square
            if(ySqrt * ySqrt == y) {
                System.out.println("b-1 - " + b);
                count++;
            } else if(zSqrt * zSqrt == z) {
                System.out.println("b+1 - " + b);
                count++;
            }
        }
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=138";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
