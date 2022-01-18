package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashSet;
import java.util.Set;

public class JEulerProblem_0346 extends EulerSolver {

    public JEulerProblem_0346(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * Every number x is a repunit in the base b=x-1
         * So we only have to generate all numbers with the form x=1+b^1+b^2+...b^(n-1) = (b^n-1)/(b-1)...
         */
        final long LIMIT = (long)Math.pow(10, 12);
        Set<Long> baseSet = new HashSet<>();
        baseSet.add(1L);
        for (int base = 2; base<=(int)Math.sqrt(LIMIT); base++) {
            int maxPower = (int)Math.ceil(Math.log(LIMIT)/Math.log(base));
            /**
             *  Convert 11(base b) to Decimal
             *  Example:
             *  11(base 2) = 2^1 + 2^0 = 2 + 1
             */
            long value = base + 1;
            /**
             * Convert 111, 1111 etc. This depends on maxPower
             */
            for(int p=2; p<=maxPower; p++) {
                value += (long)Math.pow(base, p);
                if (value < LIMIT) {
                    baseSet.add(value);
                } else {
                    break;
                }
            }
        }
        long sum = baseSet.stream().mapToLong(Long::longValue).sum();
        return Long.toString(sum);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/minimal=346\n\n" +
                "<p>\n" +
                "The number 7 is special, because 7 is 111 written in base 2, and 11 written in base 6 <br />(i.e. 7<sub>10</sub> = 11<sub>6</sub> = 111<sub>2</sub>). In other words, 7 is a repunit in at least two bases b &gt; 1. \n" +
                "</p>\n" +
                "<p>\n" +
                "We shall call a positive integer with this property a strong repunit. It can be verified that there are 8 strong repunits below 50:  {1,7,13,15,21,31,40,43}. <br />Furthermore, the sum of all strong repunits below 1000 equals 15864.\n" +
                "</p>\n" +
                "Find the sum of all strong repunits below 10<sup>12</sup>.\n";
    }
}
