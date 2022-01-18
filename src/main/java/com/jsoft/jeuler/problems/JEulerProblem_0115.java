package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0115 extends EulerSolver {

    public JEulerProblem_0115(int problemNumber) {
        super(problemNumber);
    }

    long[][] memory;

    @Override
    public String solve() {
        int maxN = 0;
        int m = 50;

        //By trail and error, found that n has to be between 150 and 200.
        for(int n=150; n<=200; n++) {
            memory = new long[n + 1][m + 1];
            long ans = noOfWays(n, m);
            if(ans > NumericHelper.ONE_MILLION_INT) {
                maxN = n;
                break;
            }
        }
        return Integer.toString(maxN);
    }

    private long noOfWays(int n, int m) {
        if (n >= 0 && n < m) {
            return 1;
        }

        if(memory[n][m] != 0) {
            return memory[n][m];
        }

        long ans = noOfWays(n-1, m);
        for(int x=m; x<n; x++) {
            ans += noOfWays(n-x-1, m);
        }

        ans += 1;

        memory[n][m] = ans;

        return ans;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=115";
    }
}
