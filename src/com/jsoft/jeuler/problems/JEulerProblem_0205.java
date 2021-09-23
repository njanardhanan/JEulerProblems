package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class JEulerProblem_0205 extends EulerSolver {

    public JEulerProblem_0205(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        Map<Integer, Double> peter = diceProbabilityDistribution(9,4);
        Map<Integer, Double> colin = diceProbabilityDistribution(6,6);

        /**
         * P(peter wins) = SumOf(i=9 to 36) [ P(peter rolls i) * P(colin rolls less than i) ]
         */
        double ans = 0.0;
        for(Map.Entry<Integer, Double> p : peter.entrySet()) {
            ans += (p.getValue() * diceProbabilityLessThan(colin, p.getKey()));
        }
        DecimalFormat df = new DecimalFormat("#.#######");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(ans);
    }

    private double diceProbabilityLessThan(Map<Integer, Double> diceProbability, int val) {
        double d = 0.0;
        for(int i=0; i<val; i++) {
            d += diceProbability.getOrDefault(i, 0.0);
        }
        return d;
    }

    //https://www.geeksforgeeks.org/probability-of-getting-all-possible-values-on-throwing-n-dices/
    private Map<Integer, Double> diceProbabilityDistribution(int noOfDice, int diceSide) {

        // Store the probabilities
        double[][] dp = new double[noOfDice + 1][diceSide * noOfDice + 1];

        // Precompute the probabilities
        // for values possible using 1 dice
        for(int i = 1; i <= diceSide; i++)
            dp[1][i] = 1 / (diceSide * 1.0);

        // Compute the probabilities
        // for all values from 2 to N
        for(int i = 2; i <= noOfDice; i++) {
            for (int j = i - 1; j <= diceSide * (i - 1); j++) {
                for (int k = 1; k <= diceSide; k++) {
                    dp[i][j + k] += (dp[i - 1][j] *
                            dp[1][k]);
                }
            }
        }

        Map<Integer, Double> diceProbability = new HashMap<>();
        for(int i = noOfDice; i <= diceSide * noOfDice; i++) {
            diceProbability.put(i, dp[noOfDice][i]);
            //System.out.println(i + " " + Math.round(dp[noOfDice][i] * 1000.0) / 1000.0);
        }
        return diceProbability;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=205\n" +
                "Peter has nine four-sided (pyramidal) dice, each with faces numbered 1, 2, 3, 4.\n" +
                "Colin has six six-sided (cubic) dice, each with faces numbered 1, 2, 3, 4, 5, 6.\n" +
                "\n" +
                "Peter and Colin roll their dice and compare totals: the highest total wins. The result is a draw if the totals are equal.\n" +
                "\n" +
                "What is the probability that Pyramidal Pete beats Cubic Colin? Give your answer rounded to seven decimal places in the form 0.abcdefg";
    }
}
