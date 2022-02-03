package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0164 extends EulerSolver {

    public JEulerProblem_0164(int problemNumber) {
        super(problemNumber);
    }

    private Map<Integer, Map<Integer, Map<Integer, Long>>> memo = new HashMap<>();

    @Override
    public String solve() {
        long ans = solve(0, 0, 20, true);
        return Long.toString(ans);
    }

    private long solve(int prevPrev, int prev, int digits, boolean isFirstDigit) {
        if (digits == 0) {
            return 1L;
        }

        if (memo.containsKey(digits)) {
            if (memo.get(digits).containsKey(prev)) {
                if (memo.get(digits).get(prev).containsKey(prevPrev)) {
                    return memo.get(digits).get(prev).get(prevPrev);
                }
            }
        }

        long result = 0;

        for (int current=0; current<=9; current++) {
            if (isFirstDigit && current == 0) continue;
            if (prevPrev + prev + current > 9) continue;
            result += solve(prev, current, digits - 1, false);
        }

        //Store for memo
        memo.putIfAbsent(digits, new HashMap<>());
        memo.get(digits).putIfAbsent(prev, new HashMap<>());
        memo.get(digits).get(prev).put(prevPrev, result);

        return result;
    }

    public void solveDP() {
        long[][][] v= new long[20][10][10];
        for(int i=1;i<10;i++)
            v[0][0][i] = 1;
        for(int i=1;i<20;i++)
            for(int j=0;j<10;j++)
                for(int k=0;k<10;k++)
                    for(int d=0;d+j+k<10;d++)
                        v[i][k][d]+=v[i-1][j][k];
        long ret = 0;
        for(int j=0;j<10;j++)
            for(int k=0;k<10;k++)
                ret+=v[19][j][k];
        System.out.println(ret);
    }

    @Override
    public String getProblemStatement() {
        return "How many 20 digit numbers n (without any leading zero) exist such that no three consecutive digits of n have a sum greater than 9?";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("problem", "172", "178", "dp", "dynamic programming", "recursion", "digits", "generation", "memoization");
    }
}
