package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.List;

public class JEulerProblem_0132 extends EulerSolver {

    public JEulerProblem_0132(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * https://en.wikipedia.org/wiki/Repunit#Definition
         *
         * R(n) = (10^n - 1) / 9
         *
         * Example : R(10) = (10^10 - 1) / 9
         *
         * => (10000000000 - 1) / 9
         * => 9999999999 / 9
         * => 1111111111
         *
         * We want
         *
         * (10^n - 1) / 9 mod p = 0
         *
         * (10^n - 1) mod 9p = 0
         *
         * 10^n mod 9p = 1
         *
         */
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(NumericHelper.ONE_MILLION_INT);
        BigInteger TEN_POWER_NINE = BigInteger.valueOf(10).pow(9);
        BigInteger TEN = BigInteger.valueOf(10);

        long sum = 0;
        int counter = 0;
        for(int p : primes) {
            int remainder = TEN.modPow(TEN_POWER_NINE, BigInteger.valueOf(p * 9)).intValue();
            if(remainder == 1) {
                sum += p;
                //System.out.println(p);
                counter++;
            }
            if(counter == 40) {
                break;
            }
        }

        return Long.toString(sum);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=132";
    }
}
