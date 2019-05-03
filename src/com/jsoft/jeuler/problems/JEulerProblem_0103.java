package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.*;
import java.util.stream.Collectors;

public class JEulerProblem_0103 extends EulerSolver {

    public JEulerProblem_0103(int problemNumber) {
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
                //Condition #2 in problem-103
                if(count > s.count) {
                    if(sum <= s.sum) {
                        return false;
                    }
                    //Condition #2 in problem-103
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
        //Given near optimum for n=6
        int[] a = {11, 18, 19, 20, 22, 25};
        List<Integer> nOptimumSix = Arrays.stream(a).boxed().collect(Collectors.toList());
        List<Integer> nNearOptimumSeven = getNearOptimumSpecialSumSet(nOptimumSix);
        System.out.println("NearOptimumSpecialSumSet for n=7 is " +  nNearOptimumSeven);
        int inputSum = nNearOptimumSeven.stream().mapToInt(x->x).sum();
        List<Integer> nOptimumSeven = new ArrayList();

        List<List<Integer>> allPossibleOptimumSpecialSumSet = getAllPossibleOptimumSpecialSumSet(nNearOptimumSeven, inputSum);

        for(List<Integer> curSet : allPossibleOptimumSpecialSumSet) {
            Collections.sort(curSet);
            List<Data> subsets = getAllSubSet(curSet);
            boolean optimumSpecialSumSet = true;
            for (Data data : subsets) {
                if (!data.isOptimumSpecialSumSet(subsets)) {
                    optimumSpecialSumSet = false;
                    break;
                }
            }
            if (optimumSpecialSumSet) {
                int curSum = curSet.stream().mapToInt(x->x).sum();
                if(curSum <= inputSum) {
                    nOptimumSeven = curSet;
                }
            }
        }


        List<Integer> answerList;
        if(nOptimumSeven.size() == 0) {
            answerList = nNearOptimumSeven;
        } else {
            answerList = nOptimumSeven;
        }
        System.out.println("OptimumSpecialSumSet for n=7 is " +  answerList);

        String answer = answerList.stream().map(n -> n.toString()).collect(Collectors.joining(""));


        return answer;
    }

    private List<List<Integer>> getAllPossibleOptimumSpecialSumSet(List<Integer> inputList, int inputSum) {
        int maxIndex = inputList.size() - 1;

        Queue<List<Integer>> queue = new LinkedList();
        queue.add(inputList);

        for(int i=0; i<=maxIndex; i++) {
            int queueSize = queue.size();
            for(int j=0; j<queueSize; j++) {
                List<Integer> currList = queue.remove();
                for(int k=-3; k<=3; k++) {
                    List<Integer> copyList = new ArrayList(currList);
                    if(currList.get(i) + k > 0) {
                        copyList.set(i, currList.get(i) + k);
                        queue.add(copyList);
                    }
                }
            }
        }

        List<List<Integer>> allPossibleOptimumSpecialSumSet = new ArrayList<>();
        int qSize = queue.size();
        for(int j=0; j<qSize; j++) {
            List<Integer> currList = queue.remove();
            int sum = currList.stream().mapToInt(x->x).sum();
            if(inputSum > sum) {
                allPossibleOptimumSpecialSumSet.add(currList);
            }
        }

        return allPossibleOptimumSpecialSumSet;
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

    private List<Integer> getNearOptimumSpecialSumSet(List<Integer> prevSet) {
        List<Integer> newSet = new ArrayList();
        int middleValue = prevSet.get(prevSet.size() / 2);
        newSet.add(middleValue);
        for(int x : prevSet) {
            newSet.add(middleValue + x);
        }

        return newSet;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=103";
    }
}
