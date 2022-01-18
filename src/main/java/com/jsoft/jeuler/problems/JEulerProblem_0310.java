package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.*;

public class JEulerProblem_0310 extends EulerSolver {

    public JEulerProblem_0310(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * Refer:
         * Problem 301 - https://projecteuler.net/thread=301
         * Problem 306 - https://projecteuler.net/thread=306
         *
         * https://www.geeksforgeeks.org/combinatorial-game-theory-set-2-game-nim/
         * https://mathstrek.blog/2012/08/05/combinatorial-game-theory-iii/
         *
         * Nim square game can be solved by using Nim Value.
         *
         * The number 0 has Nim value *0 (also written *0 = 0).
         * For each n, we look at all possible moves from n,
         * and label the results m_1, m_2 .... Suppose these
         * numbers have Nim values *r_1, *r_2 ... respectively,
         * then the Nim value of n is defined to be *r,
         * where r is the smallest non-negative integer which does
         * not occur among r_1, r_2 ...
         *
         **/

        int LIMIT = 29;
        LIMIT = 100000;

        int[] nimValue = new int[LIMIT+1];
        Set<Integer> allNimValue = new HashSet();

        for (int size = 0; size <= LIMIT; size++) {
            int NimLimit = 80;
            BitSet bitSet = new BitSet(NimLimit);
            for (int i = 1; i*i <= size; i++) {
                // take i^2 stones from the heap
                bitSet.set(nimValue[size - i*i]);
            }
            int available = 0;
            while (bitSet.get(available)) {
                available++;
            }
            nimValue[size] = available;
        }

        //Init
        int maxNimValue = Arrays.stream(nimValue).max().getAsInt();
        int maxNimValueInPowerTwo = 1;
        while(maxNimValueInPowerTwo <= maxNimValue) {
            maxNimValueInPowerTwo *= 2;
        }

        int[] frequency = new int[maxNimValueInPowerTwo];

        long firstLostPlayerCount = 0;
        BigInteger bigInteger = BigInteger.ZERO;

        for(int a=LIMIT; a>=0; a--) {
            //int b = a;
            for (int b = a; b == a; b++) {
                for (int c = b; c <= LIMIT; c++) {
                    frequency[nimValue[b] ^ nimValue[c]]++;
                }
            }
            firstLostPlayerCount += frequency[nimValue[a]];
            bigInteger = bigInteger.add(BigInteger.valueOf(frequency[nimValue[a]]));
        }

        //2586528661783
        //2544781364419
        System.out.println(bigInteger.toString());

        return Long.toString(firstLostPlayerCount);
    }

    public String solveSlow() {
        /**
         * Refer:
         * Problem 301 - https://projecteuler.net/thread=301
         * Problem 306 - https://projecteuler.net/thread=306
         *
         * https://www.geeksforgeeks.org/combinatorial-game-theory-set-2-game-nim/
         * https://mathstrek.blog/2012/08/05/combinatorial-game-theory-iii/
         *
         * Nim square game can be solved by using Nim Value.
         *
         * The number 0 has Nim value *0 (also written *0 = 0).
         * For each n, we look at all possible moves from n,
         * and label the results m_1, m_2 .... Suppose these
         * numbers have Nim values *r_1, *r_2 ... respectively,
         * then the Nim value of n is defined to be *r,
         * where r is the smallest non-negative integer which does
         * not occur among r_1, r_2 ...
         *
         **/

        int LIMIT = 29;
        LIMIT = 100000;

        int[] nimValue = new int[LIMIT+1];
        Set<Integer> allNimValue = new HashSet();

        //Init
        nimValue[0] = 0;
        allNimValue.add(0);

        for(int i=1; i<=LIMIT; i++) {
            Set<Integer> allPossibleNimValues = new HashSet();
            int squareSteps = 1;
            int currentStep = 0;
            while(currentStep >= 0) {
                currentStep = i - (squareSteps * squareSteps);
                if(currentStep >= 0) {
                    allPossibleNimValues.add(nimValue[currentStep]);
                    squareSteps++;
                }
            }
            int currentNimValue = 0;
            boolean found = false;
            for (int x : allPossibleNimValues) {
                if (x != currentNimValue) {
                    nimValue[i] = currentNimValue;
                    allNimValue.add(currentNimValue);
                    found = true;
                    break;
                } else {
                    currentNimValue++;
                }
            }

            if(!found) {
                nimValue[i] = currentNimValue;
                allNimValue.add(currentNimValue);
            }
        }

        System.out.println("allNimValue size : " + allNimValue.size());

//        for(int i=0; i<=LIMIT; i++) {
//            System.out.printf("%d - %d\n", i, nimValue[i]);
//        }

        long firstPlayerCount = 0;
        //long secondPlayerCount = 0;
        int optimization = 0;
        for(int a=0; a<=LIMIT; a++) {
            for(int b=a; b<=LIMIT; b++) {
                if(!allNimValue.contains(nimValue[a] ^ nimValue[b])) {
                    optimization++;
                    continue;
                }
                for(int c=b; c<=LIMIT; c++) {
                    int nimSum = nimValue[a] ^ nimValue[b] ^ nimValue[c];
                    if(nimSum == 0) {
                        firstPlayerCount++;
                    }
//                    else {
//                        secondPlayerCount++;
//                    }
                }
            }
        }

        System.out.println("First player : " + firstPlayerCount);
        //System.out.println("Second player : " + secondPlayerCount);
        System.out.println("Optimization : " + optimization);


        return Long.toString(firstPlayerCount);
    }

    @Override
    public String getProblemStatement() {
        String problem = "\n" +
                "\n" +
                "Alice and Bob play the game Nim Square.\n" +
                "Nim Square is just like ordinary three-heap normal play Nim, but the players may only remove a square number of stones from a heap.\n" +
                "The number of stones in the three heaps is represented by the ordered triple (a,b,c).\n" +
                "If 0≤a≤b≤c≤29 then the number of losing positions for the next player is 1160.\n" +
                "\n" +
                "Find the number of losing positions for the next player if 0≤a≤b≤c≤100000.\n" +
                "\n";
        return problem + "\nhttps://projecteuler.net/thread=310";
    }
}
