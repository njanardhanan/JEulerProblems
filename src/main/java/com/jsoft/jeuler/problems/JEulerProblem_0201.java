package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JEulerProblem_0201 extends EulerSolver {

    public JEulerProblem_0201(int problemNumber) {
        super(problemNumber);
    }
    public Map<String, Integer> memo = new HashMap<>();
    private final int TARGET = 50;

    @Override
    public String solve() {
        int[] num = new int[100];
        for (int i=1; i<=num.length; i++) {
            num[i-1] = i*i;
        }

        //int[] num = {1,3,6,8,10,11};

        //Map to store subsetSize, subsetSum and Count
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();

        for (int i=0; i<num.length; i++) {
            System.out.println("Processing : " + (i+1));
            Map<Integer, Map<Integer, Integer>> tmpMap = new HashMap<>();

            populateMaps(tmpMap, map, num[i]);

            tmpMap.put(1, new HashMap<>());
            tmpMap.get(1).put(num[i], 1);

            //Merge tmpMap to map
            mergeMaps(map, tmpMap);

            //System.out.println(num[i] + " : " + map);
        }

        Map<Integer, Integer> targetSet = map.get(TARGET);
        int ans = 0;
        for (Map.Entry<Integer, Integer> r : targetSet.entrySet()) {
            if (r.getValue() == 1) {
                ans += r.getKey();
            }
        }

        return Integer.toString(ans);
    }

    private void populateMaps(Map<Integer, Map<Integer, Integer>> tmpMap,
                              Map<Integer, Map<Integer, Integer>> map,
                              int num) {
        for (Map.Entry<Integer, Map<Integer, Integer>> e : map.entrySet()) {
            int setSize = e.getKey() + 1;
            //We are not interested in sets with size greater than TARGET.
            if (setSize > TARGET) {
                continue;
            }
            tmpMap.putIfAbsent(setSize, new HashMap<>());
            for (Map.Entry<Integer, Integer> f : e.getValue().entrySet()) {
                int setSum = f.getKey() + num;
                tmpMap.get(setSize).put(setSum, f.getValue());  
            }
        }
    }

    private void mergeMaps(Map<Integer, Map<Integer, Integer>> map,
                           Map<Integer, Map<Integer, Integer>> tmpMap) {
        for (Map.Entry<Integer, Map<Integer, Integer>> e : tmpMap.entrySet()) {
            int setSize = e.getKey();
            map.putIfAbsent(setSize, new HashMap<>());
            for (Map.Entry<Integer, Integer> f : e.getValue().entrySet()) {
                int setSum = f.getKey();
                int count = tmpMap.get(setSize).getOrDefault(setSum, 1);
                if (map.get(setSize).containsKey(setSum)) {
                    map.get(setSize).put(setSum, count + 1);
                } else {
                    map.get(setSize).put(setSum, count);
                }
            }
        }
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=201\n";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("subset sum", "generation");
    }
}
