package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.*;
import java.util.stream.Collectors;


//Refer Problem 31 and 76
public class JEulerProblem_0079 extends EulerSolver {

    public JEulerProblem_0079(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //Ans found by hand : 73162890
        //Assuming no repeating keys in the passcode.

        int[] keylog = {319,680,180,690,129,620,762,689,762,318,368,710,720,710,629,
                168,160,689,716,731,736,729,316,729,729,710,769,290,719,680,318,389,
                162,289,162,718,729,319,790,680,890,362,319,760,316,729,380,319,728,716};

        Set<Integer> allNoInKey = new HashSet();
        Map<Integer, Set<Integer>> map = new HashMap();

        for(int n : keylog) {
            int f = n / 100;
            int s = (n / 10) % 10;
            int t = n % 10;

            allNoInKey.add(f);
            allNoInKey.add(s);
            allNoInKey.add(t);


            addToMap(f, s, map);
            addToMap(f, t, map);
            addToMap(s, t, map);
        }

        Map<Integer, Integer> keyMap = new HashMap();

        for(int k : map.keySet()) {
            keyMap.put(k, map.get(k).size());
            //System.out.println(k + " : " + map.get(k));
        }

        Map<Integer, Integer> sortedMap = keyMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

        String keyCode = "";
        int lastNumber = -1;

        for(int k : sortedMap.keySet()) {
            keyCode = keyCode + k;
            lastNumber = k;
        }
        Set<Integer> s = map.get(lastNumber);
        for(int i : s) {
            keyCode += i;
        }

        return keyCode;
    }


    private void addToMap(int key, int val, Map<Integer, Set<Integer>> map ) {
        if(map.containsKey(key)) {
            map.get(key).add(val);
        } else {
            Set<Integer> s = new HashSet();
            s.add(val);
            map.put(key, s);
        }
    }



    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=79";
    }
}
