package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.StringHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JEulerProblem_0125 extends EulerSolver {

    public JEulerProblem_0125(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int TARGET = (int)Math.pow(10, 8);
        int LIMIT = (int)Math.sqrt(TARGET)/2;

        long[] squares = new long[LIMIT];
        int index = 0;
        for(long i=1; i<LIMIT; i++) {
            squares[index++] = i*i;
        }

        Set<Long> list = new HashSet();
        for(int i=2; i<LIMIT; i++) {
            list.addAll(palindromicSums(squares, LIMIT, i, TARGET));
        }

        long sum = list.stream().mapToLong(x->x).sum();

        return Long.toString(sum);
    }

    //Sliding window algo
    static Set<Long> palindromicSums(long arr[], int n, int k, int target)
    {
        Set<Long> s = new HashSet();

        // Consider all blocks starting with i.
        for (int i = 0; i < n - k + 1; i++) {
            long current_sum = 0;
            for (int j = 0; j < k; j++)
                current_sum = current_sum + arr[i + j];

            if(current_sum < target && StringHelper.isPalindrome(Long.toString(current_sum))) {
                s.add(current_sum);
            }
        }

        return s;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=125";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
