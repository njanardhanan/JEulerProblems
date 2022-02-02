package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JEulerProblem_0031 extends EulerSolver {

    public JEulerProblem_0031(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int[] coins = {1, 2, 5, 10, 20, 50, 100, 200};
        int target = 200;

        return Integer.toString(countWays(coins, coins.length, target));
    }

    public int countWays( int S[], int m, int n ) {
        // table[i] will be storing the number of solutions for
        // value i. We need n+1 rows as the table is constructed
        // in bottom up manner using the base case (n = 0)
        int table[]=new int[n+1];

        // Base case (If given value is 0)
        table[0] = 1;

        // Pick all coins one by one and update the table[] values
        // after the index greater than or equal to the value of the
        // picked coin
        for(int i=0; i<m; i++)
            for(int j=S[i]; j<=n; j++)
                table[j] += table[j-S[i]];

        return table[n];
    }

    @Override
    public String getProblemStatement() {
        return "How many different ways can £2 be made using any number of coins?";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("dp", "dynamic programming", "coin", "counting");
    }
}
