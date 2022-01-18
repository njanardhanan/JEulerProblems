package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class JEulerProblem_0203 extends EulerSolver {

    public JEulerProblem_0203(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        int N = 51;
        Set<Long> fullRow = new HashSet();
        for(int i=1; i<N; i++) {
            List<Long> pascalRow = NumericHelper.getPascalRow(i);
//            pascalRow.stream().map(x -> Long.toString(x) + ", ").forEach(System.out::print);
//            System.out.println();

            Set<Long> currentRow = pascalRow.stream().distinct().collect(Collectors.toSet());
            fullRow.addAll(currentRow);
        }

//        fullRow.stream().map(x -> Long.toString(x) + ", ").forEach(System.out::print);
//        System.out.println();

        long ans = fullRow.stream().filter(x -> NumericHelper.isSquareFreeNumber(x)).mapToLong(x-> x).sum();

        return Long.toString(ans);
    }


    @Override
    public String getProblemStatement() {
        return "Find the sum of the distinct squarefree numbers in the first 51 rows of Pascal's triangle";
    }
}
