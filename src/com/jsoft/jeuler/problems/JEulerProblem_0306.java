package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.*;

public class JEulerProblem_0306 extends EulerSolver {

    public JEulerProblem_0306(int problemNumber) {
        super(problemNumber);
    }

    private static final boolean LOGGING = false;

    @Override
    public String solve() {
        /**
         * Refer:
         * Problem 301 - https://projecteuler.net/thread=301
         * https://oeis.org/A215721
         * https://www.geeksforgeeks.org/combinatorial-game-theory-set-2-game-nim/
         * https://mathstrek.blog/2012/08/02/combinatorial-game-theory-i/
         *
         * Grundy numbers
         *
         **/

        int LIMIT = 1000000;
        int SAMPLE_STRIP_SIZE = 75;
        //You can also hard-code first 14 numbers from https://oeis.org/A215721
        List<Integer> firstPlayerLossList = getFirstPlayerLossList(SAMPLE_STRIP_SIZE);

        int answer = countFirstPlayerLoss(firstPlayerLossList, LIMIT);

        return Integer.toString(answer);
    }

    private int countFirstPlayerLoss(List<Integer> lostPosition, int limit) {
        //Algorithm as given in https://oeis.org/A215721
        // nextLostPosition(n) = nextLostPosition(n - 5) + 34
        int count = lostPosition.size();
        Queue<Integer> lastFiveEntry = new LinkedList();
        lastFiveEntry.addAll(lostPosition.subList(count-5, count));

        while(true) {
            int nextLostPosition = lastFiveEntry.remove() + 34;
            if(nextLostPosition <= limit) {
                lastFiveEntry.add(nextLostPosition);
                count++;
            } else {
                break;
            }
        }

        return limit - count;
    }

    private List<Integer> getFirstPlayerLossList(int stripSize) {

        //Algorithm as given in https://oeis.org/A215721

        List<Integer> lossList = new ArrayList();
        lossList.add(1);

        if (stripSize<=1) return lossList;

        Map<Integer, Integer> nimValues = new HashMap();
        nimValues.put(0, 0);
        nimValues.put(1, 0);


        for(int n=2; n<=stripSize; n++) {
            if (LOGGING) System.out.println("Checking : " + n);
            if (LOGGING) System.out.print("Min of : {");
            Map<Integer, Boolean> isInSet = new HashMap();
            for (int k = 0; k <= (n - 2); k++) {
                int nv = nimValues.get(k) ^ nimValues.get(n-2-k);
                if (LOGGING) System.out.print(nv + ", ");
                isInSet.put(nv, true);
            }
            if (LOGGING) System.out.println("}");
            for (int k = 0; ; k++) {
                if(!isInSet.containsKey(k)) {
                    nimValues.put(n, k);
                    if (LOGGING) System.out.printf("Nim Value for %d is %d\n", n, k);
                    break;
                }
            }
        }

        for(Map.Entry<Integer, Integer> e : nimValues.entrySet()) {
            //Don't add stripSize 0 and 1.
            // 0 - We are not interested in.
            // 1 - Already added while initializing the list.
            if(e.getKey() == 0 || e.getKey() == 1) continue;
            if(e.getValue() == 0) {
                if (LOGGING) System.out.printf("%d, ", e.getKey());
                lossList.add(e.getKey());
            }
        }
        if (LOGGING) System.out.println();

        return lossList;
    }

    @Override
    public String getProblemStatement() {
        String s = "The following game is a classic example of Combinatorial Game Theory:\n" +
                "\n" +
                "Two players start with a strip of n white squares and they take alternate turns.\n" +
                "On each turn, a player picks two contiguous white squares and paints them black.\n" +
                "The first player who cannot make a move loses.\n" +
                "\n" +
                "    If n = 1, there are no valid moves, so the first player loses automatically.\n" +
                "    If n = 2, there is only one valid move, after which the second player loses.\n" +
                "    If n = 3, there are two valid moves, but both leave a situation where the second player loses.\n" +
                "    If n = 4, there are three valid moves for the first player; she can win the game by painting the two middle squares.\n" +
                "    If n = 5, there are four valid moves for the first player (shown below in red); but no matter what she does, the second player (blue) wins.\n" +
                "    So, for 1 ≤ n ≤ 5, there are 3 values of n for which the first player can force a win. " +
                "    Similarly, for 1 ≤ n ≤ 50, there are 40 values of n for which the first player can force a win. " +
                "\n" +
                "    For 1 ≤ n ≤ 1 000 000, how many values of n are there for which the first player can force a win?  " +
                "\nhttps://projecteuler.net/thread=301 \n";
        return s;
    }
}
