package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class JEulerProblem_0023 extends EulerSolver {

    public JEulerProblem_0023(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long LIMIT = 28123l;
        Set<Long> abundantNumbers = getAbundantNumbers(LIMIT);
        boolean[] sumOfAbundantNumbers = new boolean[(int)LIMIT + 1];
        Arrays.fill(sumOfAbundantNumbers, true);
        for(long x : abundantNumbers) {
            for(long y : abundantNumbers) {
                if(x + y > LIMIT) continue;
                sumOfAbundantNumbers[(int)(x+y)] = false;
            }
        }
        int sum = IntStream.range(1, (int)LIMIT)
                .map(x -> sumOfAbundantNumbers[x] ? x : 0)
                .sum();
        return Integer.toString(sum);
    }

    private Set<Long> getAbundantNumbers(long n) {
        Set<Long> abundantNumbers = new HashSet();
        for(long i=12; i<=n; i++) {
            Set<Long> divisor = NumericHelper.getDivisors(i);
            long sum = divisor.stream().mapToLong(x -> x).sum() - i;
            if(sum > i) {
                abundantNumbers.add(i);
            }
        }
        return abundantNumbers;
    }

    @Override
    public String getProblemStatement() {
        return "Find the sum of all the positive integers which cannot be written as the sum of two abundant numbers";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("abundant");
    }
}
