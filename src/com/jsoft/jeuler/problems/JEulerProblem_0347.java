package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JEulerProblem_0347 extends EulerSolver {

    public JEulerProblem_0347(int problemNumber) {
        super(problemNumber);
    }


    @Override
    public String solve() {
        final int LIMIT = 10 * NumericHelper.ONE_MILLION_INT;
        long sum = 0;
        Map<Long, Map<Long, Integer>> data = new HashMap<>();
        for(int i=LIMIT; i>6; i--) {
            Map<Long, Integer> map = NumericHelper.getPrimeFactors(i);
            if (map.size() == 2) {
                long primeOne = (long)map.keySet().toArray()[0];
                long primeTwo = (long)map.keySet().toArray()[1];
                if (primeOne < primeTwo) {
                    putData(primeOne, primeTwo, i, data);
                } else {
                    putData(primeTwo, primeOne, i, data);
                }
            }
        }
        for(Map.Entry<Long, Map<Long, Integer>> e : data.entrySet()) {
            for(Map.Entry<Long, Integer> ee : e.getValue().entrySet()) {
                sum += ee.getValue();
            }
        }
        return Long.toString(sum);
    }

    private void putData(long x, long y, int v, Map<Long, Map<Long, Integer>> map) {
        if (!map.containsKey(x)) {
            map.put(x, new HashMap<>());
        }
        if (!map.get(x).containsKey(y)) {
            map.get(x).put(y, v);
        }
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=347\n";
    }
}
