package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0668 extends EulerSolver {

    public JEulerProblem_0668(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * A number is NOT square-root-smooth, if and only if n=xp for some prime p and x<=p.
         * Therefore, the number of square-root-smooth not exceeding n is given by
         *
         * a(n) = n - Sum_{i=1..floor(sqrt(n))} (pi(floor(n/i)) - pi(i)).
         * where pi() is a prime counting function.
         *
         * Refer : https://oeis.org/A064775
         *
         */
        long LIMIT = 10_000_000_000L;
        long sqrtLimit = (long)Math.sqrt(LIMIT);
        long total = 0;
        for(int i=1; i<sqrtLimit; i++) {
            total += PrimeNumberHelper.getPrimeCount(LIMIT/i) - PrimeNumberHelper.getPrimeCount(i-1);
        }
        return String.valueOf(LIMIT - total);
    }

    @Override
    public String getProblemStatement() {
        return "A positive integer is called square root smooth if all of its prime factors are strictly less than its square root.\n" +
                "Including the number 1,there are 29 square root smooth numbers not exceeding 100.\n" +
                "\n" +
                "How many square root smooth numbers are there not exceeding 10 000 000 000?";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("oeis", "A064775", "prime", "counting");
    }
}
