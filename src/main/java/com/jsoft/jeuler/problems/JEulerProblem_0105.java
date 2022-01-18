package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class JEulerProblem_0105 extends EulerSolver {

    public JEulerProblem_0105(int problemNumber) {
        super(problemNumber);
    }

    class Data {
        public List<Integer> subset;
        private int disjointValue;
        private int count;
        private int sum;

        public Data(List<Integer> subset, int disjointValue) {
            this.subset = new ArrayList(subset);
            this.disjointValue = disjointValue;
            this.count = (int)this.subset.stream().count();
            this.sum = this.subset.stream().mapToInt(Integer::intValue).sum();
        }

        public String toString() {
            return subset.stream().map(n -> String.valueOf(n)).collect(Collectors.joining(", ", "{", "}")) +
                    " - " + sum;
        }

        private List<Data> getDisjointSet(List<Data> subsets) {
            List<Data> disjointSet = new ArrayList();
            for(Data s : subsets) {
                if ((disjointValue & s.disjointValue) == 0) {
                    disjointSet.add(s);
                }
            }
            return disjointSet;
        }

        public boolean isOptimumSpecialSumSet(List<Data> subsets) {
            List<Data> disjointSubSet = getDisjointSet(subsets);
            for(Data s : disjointSubSet) {
                //Condition #2 in problem-105
                if(count > s.count) {
                    if(sum <= s.sum) {
                        return false;
                    }
                    //Condition #2 in problem-105
                } else if(count < s.count) {
                    if(s.sum <= sum) {
                        return false;
                    }
                } else {
                    //Condition #1
                    if(s.sum == sum) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    @Override
    public String solve() {
        /**
         * Refer Problem 103.
         */

        int answer = 0;
        try {
            Scanner input = new Scanner(new File("data/problem105/p105_sets.txt"));

            for(int i=0; i<100; i++) {
                String line = input.nextLine();
                List<Integer> inputList = Arrays
                        .stream(line.split(","))
                        .mapToInt(Integer::parseInt)
                        .boxed()
                        .collect(Collectors.toList());

                Collections.sort(inputList);
                List<Data> subsets = getAllSubSet(inputList);
                boolean optimumSpecialSumSet = true;
                for (Data data : subsets) {
                    if (!data.isOptimumSpecialSumSet(subsets)) {
                        optimumSpecialSumSet = false;
                        break;
                    }
                }
                if (optimumSpecialSumSet) {
                    int curSum = inputList.stream().mapToInt(x -> x).sum();
                    answer += curSum;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return Integer.toString(answer);
    }

    private List<Data> getAllSubSet(List<Integer> a) {
        int n = a.size();
        List<Data> subsets = new ArrayList();
        for(int i=0; i < (1<<n); i++) {
            List<Integer> subset = new ArrayList();
            for(int j=0; j < n; j++) {
                if((i & (1<<j)) > 0) {
                    subset.add(a.get(j));
                }
            }
            if(subset.size() > 0) {
                subsets.add(new Data(subset, i));
            }
        }

        return subsets;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=103";
    }
}
