package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.List;

public class JEulerProblem_0053 extends EulerSolver {

    public JEulerProblem_0053(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int count = 0;
        for(int n=2; n<=100; n++) {
            List<Integer> row = getPascalRowLessThanOneMillion(n);
            //System.out.print(n + " : " + row.size() + " - ");
            //row.stream().forEachOrdered(x -> System.out.printf("%d ", x));

            int s = row.size();
            //multiple by 2 because C(n,r) = C(n, n-r)
            if(2*s < n) {
                count += ((n+1) - 2*s);
            }

            //System.out.println();
        }
        return Integer.toString(count);
    }

    //Refer NumericHelper::getPascalRow(
    private List<Integer> getPascalRowLessThanOneMillion(int n) {
        List<Integer> row = new ArrayList();
        row.add(1);

        for(int k=0; k<n; k++) {
            int val = row.get(k) * (n-k) / (k+1);
            if(val > NumericHelper.ONE_MILLION_INT) {
                return row;
            }
            row.add(row.get(k) * (n-k) / (k+1));
        }

        return row;

    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=53";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
