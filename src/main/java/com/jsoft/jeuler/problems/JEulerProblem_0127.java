package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.List;


public class JEulerProblem_0127 extends EulerSolver {

    public JEulerProblem_0127(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * Since a, b and c are relatively prime,
         *
         * i.e. gcd(a,b) = gcd(a,c) = gcd(b,c) = 1
         *
         * therefore => rad(abc) = rad(a) * rad(b) * rad(c).
         *
         * Also:
         *
         * gcd(a,c) = gcd(a, a+b) = gcd(a,b)
         * gcd(b,c) = gcd(b, a+b) = gcd(b, a)
         * so there's no need to check all gcd.
         *
         */
        int limit = 120000;

        int[] radicals = NumericHelper.sievePrimeFactorRadical(limit);

        long sum = 0;

        for(int b=2; b<limit; b++) {
            for(int a=1; a<b; a++) {
                int c = a+b;
                if(c>=limit) break;

                if(NumericHelper.gcd(a, b) != 1) continue;
                int r = radicals[a] * radicals[b] * radicals[c];

                if(r < c) {
                    sum += c;
                }
            }
        }

        return Long.toString(sum);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=127";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("prime", "gcd");
    }
}
