package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0130 extends EulerSolver {

    public JEulerProblem_0130(int problemNumber) {
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
         * =>  9999999999 / 9
         * =>  1111111111
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
        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(NumericHelper.ONE_MILLION_INT);

        int TARGET = 25;

        int sum = 91 + 259 + 451 + 481 + 703;
        int counter = 5;
        for(int n = 703+2; ;n+=2) {
            if(primes[n]) {
                continue;
            }
            if(NumericHelper.gcd(n, 10) != 1) {
                continue;
            }

            int An = getA(n);
            if((n-1) % An == 0) {
                //System.out.println(n);
                sum += n;
                counter++;
            }
            if(counter == TARGET) {
                break;
            }

        }
        return Integer.toString(sum);
    }

    private int getA(int n) {
        BigInteger TEN = BigInteger.valueOf(10);
        for (int k = 1; ; k++) {
            int remainder = TEN.modPow(BigInteger.valueOf(k), BigInteger.valueOf(n * 9)).intValue();
            if (remainder == 1) {
                return k;
            }
        }
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=130";
    }
}
