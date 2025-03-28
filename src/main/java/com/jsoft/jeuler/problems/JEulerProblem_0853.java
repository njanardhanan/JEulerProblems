package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.FibonacciHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0853 extends EulerSolver {

    public JEulerProblem_0853(int problemNumber) {
        super(problemNumber);
    }

    List<Integer> primes = Arrays.asList(2, 3, 5, 11, 31, 41, 61, 2521);

    @Override
    public String solve() {
        int LIMIT = 1_000_000_000;
        int TARGET = 120;
        long ans = 0;
        int count = 0;
        boolean done = false;
        boolean isFactor = false;
        for (int i = 30; i <= LIMIT; i++) {

            //Optimization
            //Check if prime p is factor of i
            for (int p : primes) {
                if (i % p == 0) {
                    isFactor = true;
                    break;
                }
            }
            if (!isFactor) {
                continue;
            }
            isFactor = false;

            long ps = FibonacciHelper.getPisanoPeriod(i);
            if (ps == TARGET) {
                //System.out.printf("%d, ", i);
                ans += i;
                //count++;
                //done = true;
            }
            //if (done && count % 10 == 0) {
            //    done = false;
            //    System.out.println();
            //}
        }

        return Long.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=853 \n" +
                "For every positive integer $n$ the Fibonacci sequence modulo \n" +
                "n is periodic. The period depends on the value of n.\n" +
                "This period is called the Pisano period for n, often shortened to $\\pi(n)$.</p>\n" +
                "There are three values of n for which $\\pi(n)$ equals 18: 19, 38 and 76. The sum of those smaller than 50$ is 57.\n" +
                "Find the sum of the values of $n$ smaller than 1_000_000_000 for which $\\pi(n)$ equals 120.\n";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList();
    }
}
