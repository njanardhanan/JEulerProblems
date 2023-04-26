package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0800 extends EulerSolver {

    public JEulerProblem_0800(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * The number we are looking for is p^q * q^p < n^m
         *
         * by taking logs both side, the equation will become
         *
         *   p log q + q log p < m log n
         *
         * The maximum prime q for prime p=2 will be around 15 million.
         * we can get this by
         *
         *   2*log(q) + q * log(2) < 800800 * log (800800)
         *   by solving this equation, you will get q as roughly 15 million.
         *   hence, we have to generate primes till 16 million.
         *
         **/
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList( 16 * NumericHelper.ONE_MILLION_INT);

        //800^1
        //double target = Math.log10(800);
        //800^800
        //double target = 800 * Math.log10(800);

        //800800 ^ 800800
        double TARGET = 800800 * Math.log10(800800);

        //last prime
        int qStartIndex = primes.size() - 1;
        int grandTotal = 0;
        for (int i=0; i<primes.size(); i++) {
            int p = primes.get(i);
            int total = 0;
            if (i >= qStartIndex) break;
            for (int j=qStartIndex; j>i; j--) {
                int q = primes.get(j);

                double pPowerq = p * Math.log10(q);
                double qPowerp = q * Math.log10(p);
                double val = pPowerq + qPowerp;

                if (val <= TARGET) {
                    qStartIndex = j;
                    //Total number of primes for p.
                    total = j - i;
                    break;
                }
            }
            grandTotal += total;
        }

        //1412403576
        return Integer.toString(grandTotal);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=800";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("prime", "log");
    }
}
