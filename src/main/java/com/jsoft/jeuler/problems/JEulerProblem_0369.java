package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.combinatorics.Generator;
import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0369 extends EulerSolver {

    public JEulerProblem_0369(int problemNumber) {
        super(problemNumber);
    }

    class Card {
        char suit; //C, D, H, S
        int rank; //1-A, 2-2 .... 13-K

        public Card(char s, int r) {
            this.suit = s;
            this.rank = r;
        }

        @Override
        public String toString() {
            return "" + rank + suit;
        }
    }

    private int count = 0;
    private Map<String, Integer> map = new HashMap<>();

    @Override
    public String solve() {
        long fiveCard = NumericHelper.choose(13, 2) *
                NumericHelper.choose(11, 1) *
                NumericHelper.choose(10, 1) *
                NumericHelper.choose(9, 1);
                //NumericHelper.choose(5, 4);
        System.out.println("fiveCard : " + fiveCard);

        long sixCard = NumericHelper.choose(13, 3) *
                NumericHelper.choose(12, 1) *
                NumericHelper.choose(11, 1) *
                NumericHelper.choose(10, 1) *
                NumericHelper.choose(6, 1);
        System.out.println(7464600);
        System.out.println("sixCard : " + sixCard);

//        List<Card> pack = getAPack();
//        Iterator<List<Card>> iterator = Generator.combination(pack).simple(6).iterator();
//        long result = 0;
//        long total = 0;
//        while(iterator.hasNext()) {
//            if (isBadugiTest(iterator.next())) {
//                result++;
//            }
//            total++;
//        }
//        System.out.printf("%d / %d = %f \n", result, total, (result * 1.0/total * 1.0)*100.0);
//
//        Map<Integer, Integer> mmap = new HashMap<>();
//        for(int v : map.values()) {
//            mmap.put(v, mmap.getOrDefault(v, 0) + 1);
//        }

        //System.out.println(mmap);

        /**
         * 4 - 17160 -
         * 5 - 514800 - 30 times
         * 514800 / 2598960 = 19.807923
         * {12=24, 13=24, 14=48, 15=72, 16=120, 17=144, 18=216, 19=264, 20=360, 21=432, 22=528, 23=600, 24=720, 25=768, 26=864, 27=912, 28=984, 29=984, 30=1032, 31=984, 32=984, 33=912, 34=864, 35=768, 36=720, 37=600, 38=528, 39=432, 40=360, 41=264, 42=216, 43=144, 44=120, 45=72, 46=48, 47=24, 48=24}
         * 6 - 7464600 - 435
         * 7 - 70579080 - 4113
         */

        //30, 435, 4113
        return "0";
    }

    private boolean isBadugiTest(List<Card> cards) {
        Iterator<List<Card>> iterator = Generator.combination(cards).simple(4).iterator();
        while(iterator.hasNext()) {
            if (isBadugi(iterator.next())) {
                return true;
            }
        }
        return false;
    }

    private boolean isBadugi(List<Card> cards) {
        for (int i=0; i<cards.size(); i++) {
            for (int j=i+1; j<cards.size(); j++) {
                if (cards.get(i).rank == cards.get(j).rank) {
                    return false;
                }

                if (cards.get(i).suit == cards.get(j).suit) {
                    return false;
                }
            }
        }

        map.put(cards.toString(), map.getOrDefault(cards.toString(), 0) + 1);

//        if (cards.get(0).toString().equals("2C") &&
//                cards.get(1).toString().equals("3D") &&
//                cards.get(2).toString().equals("4H") &&
//                cards.get(3).toString().equals("5S")) {
//            count++;
//            System.out.println(cards + " : " + count);
//        }

        return true;
    }

    private List<Card> getAPack() {
        List<Card> pack = new ArrayList<>();
        char[] suits = {'C', 'D', 'H', 'S'};
        for(char s : suits) {
            for(int r=1; r<=13; r++) {
                pack.add(new Card(s, r));
            }
        }
        return pack;
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
