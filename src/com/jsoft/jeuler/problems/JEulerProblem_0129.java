package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.List;

public class JEulerProblem_0129 extends EulerSolver {

    public JEulerProblem_0129(int problemNumber) {
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
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(NumericHelper.ONE_MILLION_INT);
        BigInteger TEN = BigInteger.valueOf(10);

        int TARGET = NumericHelper.ONE_MILLION_INT;

        int ans = 0;
        for(int n = TARGET+1; ;n+=2) {
            if(NumericHelper.gcd(n, 10) != 1) {
                continue;
            }
            for (int k = 1; ; k++) {
                int remainder = TEN.modPow(BigInteger.valueOf(k), BigInteger.valueOf(n * 9)).intValue();
                if (remainder == 1) {
                    if(k > TARGET) {
                        System.out.println("n = " + n);
                        System.out.println("A(n) = " + k);
                        ans = n;
                    }
                    break;
                }

//                if(k%100000 == 0) {
//                    System.out.printf("n = %d, k=%d\n", n,k);
//                }
            }

            if(ans > 0) break;
        }

        return Integer.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=129";
    }
}
