package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.StringHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class JEulerProblem_0062 extends EulerSolver {

    public JEulerProblem_0062(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int LIMIT = 10000;
        BigInteger[] cubes = new BigInteger[LIMIT + 1];
        for(int i=1; i<LIMIT; i++) {
            cubes[i] = BigInteger.valueOf(i).pow(3);
        }

        List<String> list = new ArrayList();
        for(int i=1; i<LIMIT; i++) {
            int count = 0;
            list.add(cubes[i].toString());
            for(int j=i+1; j<LIMIT; j++) {
                if (StringHelper.isAnagram(cubes[i].toString(), cubes[j].toString())) {
                    count++;
                    list.add(cubes[j].toString());
                }
            }

            if(count == 4) {
                System.out.println(i);
                System.out.println(list);
                break;
            } else {
                list.clear();
            }
        }


        return Integer.toString(LIMIT);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=57";
    }
}
