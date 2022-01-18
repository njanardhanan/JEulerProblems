package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;

public class JEulerProblem_0401 extends EulerSolver {

    public JEulerProblem_0401(int problemNumber) {
        super(problemNumber);
    }

    private final long LIMIT = (long)Math.pow(10, 15);
    private final BigInteger MOD = BigInteger.valueOf(10).pow(9);
    @Override
    public String solve() {
        /**
         * https://oeis.org/A064602
         * Ans : 281632621
         * As per OEIS the formula for sigma2 is a(n) = Sum_{i=1..n} i^2 * floor(n/i)
         * so SIGMA2 will be summatory of sigma2.
         * For example, n=9 will be as follows.
         *
         * 1^2 * 9 = 9
         * 2^2 * 4 = 16
         * 3^2 * 3 = 27
         * 4^2 * 2 = 32
         * 5^2 * 1 = 25
         * 6^2 * 1 = 36
         * 7^2 * 1 = 49
         * 8^2 * 1 = 64
         * 9^2 * 1 = 81
         *
         * Even with the direct formula, iterating upto 10^15 will be too much.
         * Observe the above list where 50% of squares are multiples of 1,  33% are multiples of 2 etc.
         * So instead of iterating upto 10^15, we can use sum of Square formula and iterate less.
         */
        //Iterate upto square root of LIMIT.
        BigInteger ans = SIGMA2BigInteger(LIMIT, MOD);

        //Find cut-off such that the multiples start after sqrt root.
        long cutOff = LIMIT/(long)Math.sqrt(LIMIT);

        //Use sum of square formula
        //Find sum of squares of lower 50% and multiply it by 1
        //then Find sum of squares of next 33% and multiply it by 2,
        //and so on and so forth.
        for(long i=1; i<cutOff; i++) {
            BigInteger sumOfSq = NumericHelper.sumOfSquare((LIMIT/(i+1))+1, LIMIT/i);
            sumOfSq = sumOfSq.multiply(BigInteger.valueOf(i));
            ans = ans.add(sumOfSq).mod(MOD);
        }

        return ans.toString();
    }

    private long SIGMA2(long n) {
        long ans = 0;
        for(long i=1; i<=n; i++) {
            long s = (i*i) * (long)Math.floor(n/i);
            ans += s;
            System.out.println((i*i) + " * " + (long)Math.floor(n/i) + " = " + s + "(" + ans + ")");
        }
        return ans;
    }

    private BigInteger SIGMA2BigInteger(long n, BigInteger mod) {
        BigInteger ans = BigInteger.ZERO;
        long sqrtN = (long)Math.sqrt(n);
        long status = 10;
        for(long i=1; i<=sqrtN; i++) {
            if(i%status == 0) {
                System.out.println("Reached : " + status + "(" + Long.toString(status).length() + ") = " + ans);
                status *= 10;
            }
            BigInteger iSq = BigInteger.valueOf(i).pow(2);
            BigInteger sigma2 = iSq.multiply(BigInteger.valueOf(n).divide(BigInteger.valueOf(i)));
            //System.out.println(i + "^2 * " + BigInteger.valueOf(n).divide(BigInteger.valueOf(i)).toString() + " = " + sigma2);
            ans = ans.add(sigma2);
        }
        return ans.mod(mod);
    }



    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=401\n\n" +
                "The divisors of 6 are 1,2,3 and 6.\n" +
                "The sum of the squares of these numbers is 1+4+9+36=50.\n" +
                "\n" +
                "Let sigma2(n) represent the sum of the squares of the divisors of n. Thus sigma2(6)=50.\n" +
                "\n" +
                "Let SIGMA2 represent the summatory function of sigma2, that is SIGMA2(n)=∑sigma2(i) for i=1 to n.\n" +
                "The first 6 values of SIGMA2 are: 1,6,16,37,63 and 113.\n" +
                "Find SIGMA2(10^15) modulo 10^9.";
    }
}
