package com.jsoft.jeuler.inprogress;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0474 extends EulerSolver {

    public JEulerProblem_0474(int problemNumber) {
        super(problemNumber);
    }

    private final long GLOBAL_MOD = (long)1e16 + 61;

    @Override
    public String solve() {

        solveProblem(1000000, 65432, true);
        return "0";
    }

    public String solveProblem(int n, int lastDigits, boolean isFactorial) {
        System.out.println(GLOBAL_MOD);
        Map<Integer, Integer> primeFactors;
        if (isFactorial) {
            primeFactors = PrimeNumberHelper.getPrimeFactorsForFactorial(n);
        } else {
            primeFactors = NumericHelper.getPrimeFactors(n);
        }
        //
        int MOD = (int)Math.pow(10, Integer.toString(lastDigits).length());
        System.out.println("Mod : " + MOD);
        List<Integer> primes = new ArrayList<>(primeFactors.keySet());
        primes.sort(Comparator.naturalOrder());
        System.out.println(primeFactors);
        System.out.println("Size : " + primeFactors.size());
        return "0";
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
