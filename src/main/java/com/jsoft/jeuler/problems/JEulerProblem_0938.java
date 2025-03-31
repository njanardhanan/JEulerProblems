package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0938 extends EulerSolver {

    public JEulerProblem_0938(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        if (!calculate(2, 2 ).equals("0.4666666667")) {
            throw new IllegalStateException("Test case #1 failed");
        }

        if (!calculate(10,9 ).equals("0.4118903397")) {
            throw new IllegalStateException("Test case #2 failed");
        }

        if (!calculate(34,25 ).equals("0.3665688069")) {
            throw new IllegalStateException("Test case #3 failed");
        }

        return calculate(24690, 12345);
    }

    private String calculate(int R, int B) {
        double p = computeP(R, B);
        String ans = String.format("%.10f", p);
        return ans;
    }

    private double computeP(int R, int B) {
        // Create DP table (add 1 to indices to include 0)
        // Size is R+1 x B+1, but we ensure we can handle (0,2) from (2,2)
        double[][] dp = new double[R + 1][B + 1];

        // Fill table iteratively
        for (int r = 0; r <= R; r++) {
            for (int b = 0; b <= B; b++) {
                // Base cases
                if (r == 0 && b >= 1) {
                    dp[r][b] = 1.0; // All black
                    continue;
                }
                if (b == 0 && r >= 1) {
                    dp[r][b] = 0.0; // All red
                    continue;
                }
                if (r + b < 2) {
                    dp[r][b] = 0.0; // Not enough cards
                    continue;
                }

                // Probabilities of drawing pairs
                int total = r + b;

                //Case 1 : If both cards are red, they are discarded.
                double pRR = 0.0;
                if (r >= 2) {
                    double firstCard = (double) r / total;
                    double secondCard = (double) (r - 1) / (total - 1);
                    pRR = firstCard * secondCard;
                }

                //Case 2 : If both cards are black, they are both put back in the deck.
                double pBB = 0.0;
                if (b >= 2) {
                    double firstCard = (double) b / total;
                    double secondCard = (double) (b - 1) / (total - 1);
                    pBB = firstCard * secondCard;
                }

                //Case 3 : If they are different colours, the red card is put back in the deck and the black card is discarded.
                //Case 3.1 - If red card is picked first
                double pRB = 0.0;
                if (r >= 1 && b >= 1) {
                    double firstCard = (double) r / total;
                    double secondCard = (double) b / (total - 1);
                    pRB = firstCard * secondCard;
                }
                //Case 3.2 - If black card is picked first
                double pBR = 0.0;
                if (r >= 1 && b >= 1) {
                    double firstCard = (double) b / total;
                    double secondCard = (double) r / (total - 1);
                    pBR = firstCard * secondCard;
                }

                double result = 0.0;
                //Handle Case 1 - RR
                if (pRR > 0) {
                    result += pRR * dp[Math.max(0, r - 2)][b]; // RR: discard both
                }

                // Handle Case 3 - RB & BR
                if (pRB > 0 || pBR > 0)  {
                    result += (pRB + pBR) * dp[r][b - 1]; // RB or BR
                }

                // Handle Case 2 - BB
                if (pBB > 0) {
                    dp[r][b] = result / (1.0 - pBB);
                } else {
                    dp[r][b] = result;
                }
            }
        }

        return dp[R][B];
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=938\n" +
                "A deck of cards contains $R$ red cards and $B$ black cards.<br>\n" +
                "A card is chosen uniformly randomly from the deck and removed. A second card is then chosen uniformly randomly from the cards remaining and removed.</p>\n" +
                "If both cards are red, they are discarded.</li>\n" +
                "If both cards are black, they are both put back in the deck.</li>\n" +
                "If they are different colours, the red card is put back in the deck and the black card is discarded.</li></ul>\n" +
                "Play ends when all the remaining cards in the deck are the same colour and let $P(R,B)$ be the probability that this colour is black. </p>\n" +
                "You are given $P(2,2) = 0.4666666667$, $P(10,9) = 0.4118903397$ and $P(34,25) = 0.3665688069$.</p>\n" +
                "Find $P(24690,12345)$. Give your answer with 10 digits after the decimal point.</p>";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("Deck", "Card", "probability");
    }
}
