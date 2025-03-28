package com.jsoft.jeuler.inprogress;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0735 extends EulerSolver {

    public JEulerProblem_0735(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int INPUT = 1000;
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i=1; i<=INPUT; i++) {
            int n = 2 * i * i;
            //System.out.printf("%2d => ", i);
            for (int j=1; j<=i; j++) {
                if (n % j == 0) {
                    //System.out.printf("%d ", j);
                    if (!map.containsKey(j)) {
                        map.put(j, new ArrayList<>());
                    }
                    map.get(j).add(i);
                }
            }
            //System.out.println();
        }
        int size = 0;
        Map<Integer, List<Integer>> mapSize = new HashMap<>();
        for (int i=1; i<=INPUT; i++) {
            int s = map.get(i).size();
            size += s;
            if (!mapSize.containsKey(s)) {
                mapSize.put(s, new ArrayList<>());
            }
            mapSize.get(s).add(i);
            //if (map.get(i).size() < 2) {
                System.out.printf("%d = %d => %s\n", i, map.get(i).size(), map.get(i));
            //}
        }
//
//        int mmsize = 0;
//        for (int i=1; i<=INPUT; i++) {
//            if (mapSize.containsKey(i)) {
//                System.out.println(i + " : " + mapSize.get(i).size() + " : " + mapSize.get(i));
//                mmsize += (i * mapSize.get(i).size());
//            }
//        }
//
//        System.out.println("mapSize : " + mapSize.size());
//        System.out.println("mmSize : " + mmsize);
        System.out.println("Size : " + size);

        return "0";
    }

    @Override
    public String getProblemStatement() {
        return "This is a template file";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList();
    }
}
