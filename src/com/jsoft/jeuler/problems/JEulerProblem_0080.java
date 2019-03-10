package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class JEulerProblem_0080 extends EulerSolver {

    public JEulerProblem_0080(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long total = 0;
        //int i =2;
        for(int i=1; i<=100; i++) {
            int sqrt = (int) Math.sqrt(i);
            if (sqrt * sqrt == i) continue;
            BigDecimal d = getSquareRootByNewtonMethod(i);
            int integralPart = d.intValue();
            int integralLength = Integer.toString(integralPart).length();
            total += NumericHelper.sumOfDigits(Integer.toString(integralPart));
            String decimalPart = d.toString().substring(2);
            String first100DecimalPart = decimalPart.substring(0, 100 - integralLength);
            total += NumericHelper.sumOfDigits(first100DecimalPart);
        }
        return Long.toString(total);
    }

    private BigDecimal getSquareRootByNewtonMethod(int n) {
        int guessSquareRoot = (int) Math.sqrt(n);
        BigDecimal d = BigDecimal.valueOf(guessSquareRoot);
        //Guess : Make 10 iterate
        for(int i=0; i<1000; i++) {
            BigDecimal num = d.pow(2).subtract(BigDecimal.valueOf(n));
            BigDecimal denom = BigDecimal.valueOf(2).multiply(d);
            d = d.subtract(num.divide(denom, 200, RoundingMode.FLOOR));
        }

        return d;
    }

    //http://www.afjarvis.staff.shef.ac.uk/maths/jarvisspec02.pdf
//    private BigInteger Squareroot(int n, int digits) {
//        BigInteger limit = BigInteger.Pow(10, digits + 1);
//        BigInteger a = 5 * n;
//        BigInteger b = 5;
//
//        while (b < limit) {
//            if (a >= b) {
//                a -= b;
//                b += 10;
//            } else {
//                a *= 100;
//                b = (b/10) * 100 + 5;
//            }
//        }
//
//        return b/100;
//    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=80";
    }
}
