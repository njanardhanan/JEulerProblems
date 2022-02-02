package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0348 extends EulerSolver {

    public JEulerProblem_0348(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long LIMIT = (long)Math.pow(10,10);
        Map<Long, Integer> map = new HashMap<>();
        //Found the range by trial and error
        for(int s=2; s<=300000; s++) {
            long sq = s*s;
            if(sq < 0 || sq > LIMIT) {
                break;
            }
            for(int c=2; c<=30000; c++) {
                long v = sq + (c*c*c);
                if(v < 0 || v > LIMIT) break;
                if(v == reverse(v)) {
                    map.put(v, map.getOrDefault(v, 0) + 1);
                }
            }
        }
        long ans = 0;
        for(Map.Entry<Long, Integer> e : map.entrySet()) {
            if(e.getValue() == 4) {
                System.out.println(e.getKey());
                ans += e.getKey();
            }
        }
        return Long.toString(ans);
    }

    private long reverse(long num) {
        long reversedNum = 0;
        while (num != 0) {
            long remainder = num % 10;
            reversedNum = reversedNum * 10 + remainder;
            num /= 10;
        }
        return reversedNum;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=348\n\n" +
                "Many numbers can be expressed as the sum of a square and a cube. Some of them in more than one way.\n" +
                "\n" +
                "Consider the palindromic numbers that can be expressed as the sum of a square and a cube, both greater than 1, in exactly 4 different ways.\n" +
                "For example, 5229225 is a palindromic number and it can be expressed in exactly 4 different ways:\n" +
                "\n" +
                "2285^2 + 20^3\n" +
                "2223^2 + 66^3\n" +
                "1810^2 + 125^3\n" +
                "1197^2 + 156^3\n" +
                "\n" +
                "Find the sum of the five smallest such palindromic numbers.";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
