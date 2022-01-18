package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0117 extends EulerSolver {

    public JEulerProblem_0117(int problemNumber) {
        super(problemNumber);
    }

    private long[][] memoization;

    @Override
    public String solve() {
        /**
         * Refer Problem 116
         */
        int NO_OF_GREY_TILES = 50;

        memoization = new long[NO_OF_GREY_TILES+1][5];
        long ans = noOfWays(NO_OF_GREY_TILES);

        //Add one for include all grey tile.
        return Long.toString(ans + 1);
    }

    private long noOfWays(int n) {
        long ans = noOfWays(n, 2);
        ans += noOfWays(n, 3);
        ans += noOfWays(n, 4);

        return ans;
    }

    /**
     * Find no. of ways.
     * @param n - Grey tiles.
     * @param m - Red/Green/Blue tiles.
     * @return - No. of ways.
     */
    private long noOfWays(int n, int m) {
        if(n<m) {
            return 0L;
        }
        if(n==m) {
            return 1L;
        }

        if(memoization[n][m] != 0) {
            return memoization[n][m];
        }

        long ans = n-m+1;
        for(int x=m; x<=n; x++) {
            ans += noOfWays(n-x);
        }
        memoization[n][m] = ans;
        return ans;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=116";
    }
}
