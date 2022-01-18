package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.List;

public class JEulerProblem_0134 extends EulerSolver {

    public JEulerProblem_0134(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        List<Long> primes = PrimeNumberHelper.sieveOfEratosthenesAsLongList(2 * NumericHelper.ONE_MILLION_INT);

        //Remove 2, 3 and 5. Start the logic from 7.
        primes.remove(0);
        primes.remove(0);
        primes.remove(0);

        long sum = 0;
        long prevPrime = 5;

        for(long p : primes) {

            if(prevPrime > NumericHelper.ONE_MILLION_INT) break;

            //x = r mod n
            long v = getDigitPower(prevPrime);
            long[] n = {v, p};
            long[] a = {prevPrime, 0};
            long ans =  NumericHelper.chineseRemainder(n, a);

            sum += ans;

            prevPrime = p;
        }

        return Long.toString(sum);
    }

    private long getDigitPower(long p) {
        int n = NumericHelper.noOfDigits(p);
        return (long)Math.pow(10, n);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=134";
    }
}
