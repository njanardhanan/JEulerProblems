package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.combinatorics.Generator;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class JEulerProblem_0090 extends EulerSolver {

    public JEulerProblem_0090(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int DICE_SIDE = 6;
        List<List<Integer>> squares = new ArrayList<>();
        squares.add(Arrays.asList(0, 1));
        squares.add(Arrays.asList(0, 4));
        squares.add(Arrays.asList(0, 6)); //9 as 6
        squares.add(Arrays.asList(1, 6));
        squares.add(Arrays.asList(2, 5));
        squares.add(Arrays.asList(3, 6));
        squares.add(Arrays.asList(4, 6)); //9 as 6
        squares.add(Arrays.asList(6, 4));
        squares.add(Arrays.asList(8, 1));

        List<Integer> nums = Arrays.asList(0,1,2,3,4,5,6,7,8,6); ////9 as 6
        Iterator<List<Integer>> comb = Generator.combination(nums).simple(DICE_SIDE).iterator();
        List<List<Integer>> combinationOfDices = new ArrayList<>();
        while(comb.hasNext()) {
            combinationOfDices.add(new ArrayList<>(comb.next()));
        }
        int ans = 0;
        for (int i=0; i<combinationOfDices.size(); i++) {
            for (int j=i+1; j<combinationOfDices.size(); j++) {
                if (isValid(combinationOfDices.get(i), combinationOfDices.get(j), squares)) {
                    ans++;
                }
            }
        }
        System.out.println(ans);
        return "0";
    }

    private boolean isValid(List<Integer> dice1, List<Integer> dice2, List<List<Integer>> sq) {
        for (int i = 0; i < sq.size(); i++) {
            if (!((dice1.contains(sq.get(i).get(0)) && dice2.contains(sq.get(i).get(1))) ||
                    (dice2.contains(sq.get(i).get(0)) && dice1.contains(sq.get(i).get(1))))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=90";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("combination", "dice");
    }
}
