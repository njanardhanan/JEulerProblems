package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.*;

public class JEulerProblem_0095 extends EulerSolver {

    public JEulerProblem_0095(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int LIMIT = NumericHelper.ONE_MILLION_INT;
        Map<Integer, Integer> amicableChain = new HashMap();

        for(int i=2; i<=LIMIT; i++) {

            int currentIndex = i;
            while (!amicableChain.containsKey(currentIndex)) {
                int nextPair = NumericHelper.getAmicablePair(currentIndex);

                if (nextPair >= LIMIT) {
                    amicableChain.put(currentIndex, 1);
                    break;
                } else {
                    amicableChain.put(currentIndex, nextPair);
                }
                currentIndex = nextPair;
            }
        }

        //Get index with maximum chain length.
        Set<Integer> validIndex = new HashSet();
        int maxChainLength = 0;
        for(int i=2; i<=LIMIT; i++) {
            List<Integer> chain = getChain(amicableChain, i);
            if(maxChainLength <= chain.size()) {
                maxChainLength = chain.size();
                validIndex.add(i);
            }
        }


        //Get the smallest number in maximum chain
        int maxIndex = 0;
        int smallestNumber = Integer.MAX_VALUE;
        for(int x : validIndex) {
            List<Integer> chain = getChain(amicableChain, x);
            if(chain.size() == maxChainLength) {
                int s = chain.stream().mapToInt(y -> y).min().getAsInt();
                if(smallestNumber > s) {
                    smallestNumber = s;
                    maxIndex = x;
                }
            }
        }

        System.out.printf("Starting index : %d\nLength : %d\nSmallest Number : %d\n", maxIndex, maxChainLength, smallestNumber);

        return Integer.toString(smallestNumber);
    }

    private List<Integer> getChain(Map<Integer, Integer> amicableChain, int index) {
        List<Integer> chain = new ArrayList();
        chain.add(index);
        int nextPair = amicableChain.get(index);
        while(!chain.contains(nextPair)) {
            chain.add(nextPair);
            nextPair = amicableChain.get(nextPair);
        }
        if(nextPair == index) {
            return chain;
        }
        return new ArrayList();

    }




    @Override
    public String getProblemStatement() {
        return "Find the smallest member of the longest amicable chain with no element exceeding one million";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
