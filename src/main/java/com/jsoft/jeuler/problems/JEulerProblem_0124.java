package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.*;

public class JEulerProblem_0124 extends EulerSolver {

    public JEulerProblem_0124(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int LIMIT = 100000;
        int TARGET = 10000;
        Map<Long, List<Long>> map = new HashMap();
        for(long i=1; i<=LIMIT; i++) {
            Map<Long, Integer> primeFactor = NumericHelper.getPrimeFactors(i);
            long radical = 1;
            for(long x : primeFactor.keySet()) {
                radical *= x;
            }
            if(map.containsKey(radical)) {
                List<Long> list = map.get(radical);
                list.add(i);
            } else {
                List<Long> list = new ArrayList();
                list.add(i);
                map.put(radical, list);
            }
        }

        List<Long> radicalList = new ArrayList(map.keySet());
        Collections.sort(radicalList);

        long key = 0;
        for(long x : radicalList) {
            if(TARGET > map.get(x).size()) {
                TARGET -= map.get(x).size();
            } else {
                key = x;
                break;
            }
            //System.out.printf("%d : %d\n", x, map.get(x).size());
        }
        System.out.printf("TARGET %d in Key %d\n", TARGET, key);

        return Long.toString(map.get(key).get(TARGET-1));
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=124";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("prime", "factor");
    }
}
