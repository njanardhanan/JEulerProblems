package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.combinatorics.Generator;
import com.jsoft.jeuler.solver.EulerSolver;
import org.apache.commons.numbers.fraction.Fraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class JEulerProblem_0093 extends EulerSolver {

    public JEulerProblem_0093(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int longestConsecutiveSeqCount = 0;
        List<Integer> longestConsecutiveSeq = new ArrayList<>();
        List<Integer> nums = Arrays.asList(1,2,3,4,5,6,7,8,9);
        Iterator<List<Integer>> combinationIterator = Generator.combination(nums).simple(4).iterator();
        while(combinationIterator.hasNext()) {
            List<Integer> combi = combinationIterator.next();
            int[] result = execute(combi);
            int i = 1;
            while(result[i] > 0) {
                i++;
            }
            if (longestConsecutiveSeqCount < i) {
                longestConsecutiveSeqCount = i;
                longestConsecutiveSeq = new ArrayList<>(combi);
            }
        }
        //System.out.println(longestConsecutiveSeqCount);
        //System.out.println(longestConsecutiveSeq);
        StringBuilder sb = new StringBuilder();
        for(int i : longestConsecutiveSeq) {
            sb.append(i);
        }
        return sb.toString();
    }

    private int[] execute(List<Integer> combi) {
        int[] result = new int[9 * 8 * 7 * 6];
        Iterator<List<Integer>> permutationIterator = Generator.permutation(combi).simple().iterator();
        while(permutationIterator.hasNext()) {
            List<Integer> perm = permutationIterator.next();
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 4; k++) {
                        Fraction f0 = Fraction.of(perm.get(0), 1);
                        Fraction f1 = Fraction.of(perm.get(1), 1);
                        Fraction f2 = Fraction.of(perm.get(2), 1);
                        Fraction f3 = Fraction.of(perm.get(3), 1);

                        Fraction x = ope(ope(ope(f0, f1, i), f2, j), f3, k);
                        setResult(result, x);
                        x = ope(ope(f0, ope(f1, f2, j), i), f3, k);
                        setResult(result, x);
                        x = ope(f0, ope(ope(f1, f2, j), f3, k), i);
                        setResult(result, x);
                        x = ope(f0, ope(f1, ope(f2, f3, k), j), i);
                        setResult(result, x);
                        x = ope(ope(f0, f1, i), ope(f2, f3, k), j);
                        setResult(result, x);
                    }
                }
            }
        }
        return result;
    }

    private void setResult(int[] result, Fraction d) {
        if (d.getNumerator() < 0) return;
        if (d.getNumerator() >= result.length) return;
        if (d.getDenominator() != 1) return;

        result[d.getNumerator()] = d.getNumerator();
    }

    private Fraction ope(Fraction a, Fraction b, int op) {
        switch (op){
            case 0:
                return a.add(b);
            case 1:
                return a.subtract(b);
            case 2:
                return a.multiply(b);
            case 3:
                if (b.getNumerator() == 0) return Fraction.ZERO;
                return a.divide(b);
        }
        return Fraction.ZERO;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=93\n";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("combination", "permutation", "fraction");
    }
}
