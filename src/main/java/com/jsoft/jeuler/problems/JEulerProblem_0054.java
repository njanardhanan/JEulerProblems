package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.poker.Card;
import com.jsoft.jeuler.poker.Hand;
import com.jsoft.jeuler.solver.EulerSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class JEulerProblem_0054 extends EulerSolver {

    public JEulerProblem_0054(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        //Test cases:
        if(Hand.evaluate(Hand.fromString("5H 5C 6S 7S KD")) >=
                Hand.evaluate(Hand.fromString("2C 3S 8S 8D TD"))) {
            throw new IllegalStateException("Test case #1 failed");
        }

        if(Hand.evaluate(Hand.fromString("5D 8C 9S JS AC")) <=
                Hand.evaluate(Hand.fromString("2C 5C 7D 8S QH"))) {
            throw new IllegalStateException("Test case #2 failed");
        }

        if(Hand.evaluate(Hand.fromString("2D 9C AS AH AC")) >=
                Hand.evaluate(Hand.fromString("3D 6D 7D TD QD"))) {
            throw new IllegalStateException("Test case #3 failed");
        }

        if(Hand.evaluate(Hand.fromString("4D 6S 9H QH QC")) ==
                Hand.evaluate(Hand.fromString("3D 6D 7H QD QS"))) {

            if(Hand.evaluateHighestCard(Hand.fromString("4D 6S 9H QH QC"), Hand.fromString("3D 6D 7H QD QS")) !=
                    Hand.PLAYER_ONE) {
                throw new IllegalStateException("Test case #4 failed");
            }
        }

        if(Hand.evaluate(Hand.fromString("2H 2D 4C 4D 4S")) <=
                Hand.evaluate(Hand.fromString("3C 3D 3S 9S 9D"))) {
            throw new IllegalStateException("Test case #5 failed");
        }

        List<String> pokerHands = null;
        int count = 0;

        try {
            pokerHands = loadData("data/problem54/p054_poker.txt");
            for(String p : pokerHands) {
                Card[] cards1 = Hand.fromString(p.substring(0, 14));
                int player1 = Hand.evaluate(cards1);

                Card[] cards2 = Hand.fromString(p.substring(15, 29));
                int player2 = Hand.evaluate(cards2);

                if (player1 == player2) {
                    int player = Hand.evaluateHighestCard(cards1, cards2);
                    if (player == Hand.PLAYER_ONE) {
                        System.out.println(p + " : Player #1");
                        count++;
                    } else if (player == Hand.PLAYER_TWO) {
                        System.out.println(p + " : Player #2");
                    } else {
                        throw new IllegalStateException("Something wrong : " + p);
                    }
                } else if (player1 > player2) {
                    System.out.println(p + " : Player #1");
                    count++;
                } else {
                    System.out.println(p + " : Player #2");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return Integer.toString(count);
    }


    private List<String> loadData(String file) throws FileNotFoundException {
        List<String> list = new ArrayList();
        Scanner input = new Scanner(new File(file));
        for(int i=0; i<1000; i++) {
            String s = input.nextLine();
            list.add(s);
        }

        return list;
    }

        @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=54";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
