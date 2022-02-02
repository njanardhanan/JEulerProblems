package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0116 extends EulerSolver {

    public JEulerProblem_0116(int problemNumber) {
        super(problemNumber);
    }

    long[][] memoization;

    @Override
    public String solve() {
        int NO_OF_GREY_TILES = 50;

        memoization = new long[NO_OF_GREY_TILES+1][5];

        long ans = noOfWays(NO_OF_GREY_TILES, 2);
        ans += noOfWays(NO_OF_GREY_TILES, 3);
        ans += noOfWays(NO_OF_GREY_TILES, 4);

        return Long.toString(ans);
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
            ans += noOfWays(n-x, m);
        }
        memoization[n][m] = ans;
        return ans;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=116";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("dp", "dynamic", "programming", "noofways", "memoization");
    }
}
