package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JEulerProblem_0074 extends EulerSolver {

    public JEulerProblem_0074(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        int[] facts = new int[10];
        for(int i=0; i<10; i++) {
            facts[i] = NumericHelper.fatorial(i);
        }

        Map<Integer, Integer> map = new HashMap();
        for(int i=0; i<NumericHelper.ONE_MILLION_INT * 3; i++) {
            map.put(i, getDigitFactorial(facts, i));
        }

        int answer = 0;
        for(int i=0; i<NumericHelper.ONE_MILLION_INT; i++) {
            if(getDigitFactorialChainCount(map, i) == 60) {
                answer++;
            }
        }

        return Integer.toString(answer);
    }

    private int getDigitFactorialChainCount(Map<Integer, Integer> map, int d) {
        Set<Integer> memory = new HashSet();
        memory.add(d);
        while(true) {
            int x = map.get(d);
            if(memory.contains(x)) {
                break;
            }

            memory.add(x);
            d = x;
        }

        return memory.size();
    }

    private int getDigitFactorial(int[] fact, int n) {
        int count = 0;
        while(n>0) {
            int r = n % 10;
            count = count + fact[r];
            n = n/10;
        }

        return count;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=74";
    }
}
