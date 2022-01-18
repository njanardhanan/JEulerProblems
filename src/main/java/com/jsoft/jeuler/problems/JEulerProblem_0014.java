package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashMap;
import java.util.Map;

public class JEulerProblem_0014 extends EulerSolver {

    public JEulerProblem_0014(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long MAX_LIMIT = 1000000L;
        //long MAX_LIMIT = 20L;
        Map<Long, Long> collatzChainLength = new HashMap();
        collatzChainLength.put(1l, 1l);

        for(long n=2; n<MAX_LIMIT; n++) {
            getCollatzChainLength(n, collatzChainLength);
        }

        long maxLength = 0;
        long ans = 1l;
        for(long n=2; n<MAX_LIMIT; n++) {
            if (collatzChainLength.containsKey(n) && maxLength < collatzChainLength.get(n)) {
                maxLength = collatzChainLength.get(n);
                ans = n;
                //System.out.format("%d - %d\n", ans, maxLength);
            }
        }

        System.out.format("%d - %d\n", ans, maxLength);

        return Long.toString(ans);
    }

    private long getCollatzChainLength(long n, Map<Long, Long> memory) {
        //n → n/2 (n is even)
        //n → 3n + 1 (n is odd)

        //System.out.print(n + ", ");

        if (memory.containsKey(n)) {
            return memory.get(n);
        }
        long ans;
        if (n%2 == 0) {
            ans = 1 + getCollatzChainLength(n / 2, memory);
        } else {
            ans = 1 + getCollatzChainLength(3 * n + 1, memory);
        }
        memory.put(n, ans);
        return ans;
    }

    @Override
    public String getProblemStatement() {
        return "Which starting number, under one million, produces the longest Collatz sequence chain?";
    }
}
