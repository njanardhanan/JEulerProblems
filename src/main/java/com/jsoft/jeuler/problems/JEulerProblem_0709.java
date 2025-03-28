package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0709 extends EulerSolver {

    public JEulerProblem_0709(int problemNumber) {
        super(problemNumber);
    }

    private static final Map<Integer, List<Integer>> memo = new HashMap<>();
    private static final long MOD = 1_020_202_009L;


    @Override
    public String solve() {
        List<Long> list = A008281rowLoop(24680-1);
        //List<Long> list = A008281rowLoop(8-1);
        long ans = 0;
        for (long l : list) {
            ans = (ans + l) % MOD;
        }
        //System.out.println(list);
        return Long.toString(ans);
    }

    /**
     * https://oeis.org/A000111
     * https://oeis.org/A008281
     * https://mathworld.wolfram.com/EntringerNumber.html
     */
    public static List<Long> A008281rowLoop(int n) {
        // Base case: For n = 0, the row is [1].
        List<Long> currentRow = new ArrayList<>();
        currentRow.add(1L);

        // Iteratively build rows up to n.
        for (int i = 1; i <= n; i++) {
            List<Long> previousRow = new ArrayList<>(currentRow); // Copy the previous row
            currentRow = new ArrayList<>();
            currentRow.add(0L); // Add leading 0

            // Fill the row using the Seidel sequence formula.
            for (int k = 1; k <= i; k++) {
                if (k == 1) {
                    currentRow.add(previousRow.get(i - 1));
                } else {
                    long newValue = (currentRow.get(k - 1) + previousRow.get(i - k)) % MOD;
                    currentRow.add(newValue);
                }
            }
        }

        return currentRow;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=709" +
                "<p>Every day for the past $n$ days Even Stevens brings home his groceries in a plastic bag. He stores these plastic bags in a cupboard. He either puts the plastic bag into the cupboard with the rest, or else he takes an <b>even</b> number of the existing bags (which may either be empty or previously filled with other bags themselves) and places these into the current bag.</p>\n" +
                "<p>After 4 days there are 5 possible packings and if the bags are numbered 1 (oldest), 2, 3, 4, they are:</p>\n" +
                "<ul><li>Four empty bags,</li>\n" +
                "<li>1 and 2 inside 3, 4 empty,</li>\n" +
                "<li>1 and 3 inside 4, 2 empty,</li>\n" +
                "<li>1 and 2 inside 4, 3 empty,</li>\n" +
                "<li>2 and 3 inside 4, 1 empty.</li>\n" +
                "</ul><p>Note that 1, 2, 3 inside 4 is invalid because every bag must contain an even number of bags.</p>\n" +
                "<p>Define $f(n)$ to be the number of possible packings of $n$ bags. Hence $f(4)=5$. You are also given $f(8)=1\\,385$.</p>\n" +
                "<p>Find $f(24\\,680)$ giving your answer modulo $1\\,020\\,202\\,009$.</p>";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("OEIS", "A000111", "A008281", "EntringerNumber");
    }
}
