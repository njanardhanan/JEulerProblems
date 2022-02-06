package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JEulerProblem_0216 extends EulerSolver {

    public JEulerProblem_0216(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long ans = 0;
        int TARGET = 50000000;

        long progress = 5;
        for (long i=2; i<=TARGET; i++) {
            if (i % progress == 0) {
                System.out.println("Reached : " + i);
                progress *= 10;
            }
            long v = (2 * i * i)-1;
            if (PrimeNumberHelper.checkPrime(v, 4)) {
                ans++;
            }
        }
        return Long.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        return "This is a template file";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList();
    }
}
