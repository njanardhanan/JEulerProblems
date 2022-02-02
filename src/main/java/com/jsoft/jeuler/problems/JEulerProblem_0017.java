package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumberToWords;
import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.List;

public class JEulerProblem_0017 extends EulerSolver {

    public JEulerProblem_0017(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        NumberToWords numberToWords = new NumberToWords();
        System.out.println(numberToWords.convert(127));
        int count = 0;
        for(long i=1; i<=1000; i++) {
            String words = numberToWords.convert(i);
            words = words.replace(" ", "");
            words = words.replace("-", "");
            count += words.length();
        }
        return Integer.toString(count);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=17";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
