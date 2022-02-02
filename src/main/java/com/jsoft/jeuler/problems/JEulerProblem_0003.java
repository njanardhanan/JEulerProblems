package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class JEulerProblem_0003 extends EulerSolver {

    public JEulerProblem_0003(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long max = 600851475143l;
        int n = (int)Math.sqrt(max);
        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(n);
        OptionalInt ans = IntStream.range(1, n+1)
                .filter(x -> primes[x] && max%x == 0)
                .max();
        return Integer.toString(ans.orElse(0));
    }

    @Override
    public String getProblemStatement() {
        return "The prime factors of 13195 are 5, 7, 13 and 29.\n" +
                "\n" +
                "What is the largest prime factor of the number 600851475143 ?";
    }

    @Override
    public List<String> getTags() {
        return null;
    }

}
