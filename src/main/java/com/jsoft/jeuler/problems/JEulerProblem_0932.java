package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class JEulerProblem_0932 extends EulerSolver {

    public JEulerProblem_0932(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //https://projecteuler.net/problem=heegner
        //https://oeis.org/A102766
        //solveUsingOEIS();
        return p932();
    }

    private String p932() {
        long LIMIT = (long)Math.sqrt(1e16);
        long ans = 0;
        for (long i=1; i<=LIMIT; i++) {
            long v = i*i;
            if (splitAndCheck(Long.toString(v))) {
                System.out.println(v);
                ans += v;
            }
        }
        return Long.toString(ans);
    }


    private void solveUsingOEIS() {
        BigInteger ans = BigInteger.ZERO;
        try {
            List<String> data = loadData("data/problem932/euler_932.txt");
            for (String line : data) {
                if (line.length() > 16) break;
                if (splitAndCheck(line)) {
                    System.out.println(line + " : " + line.length());
                    ans = ans.add(BigInteger.valueOf(Long.parseLong(line)));
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Ans : " + ans);
    }

    private boolean splitAndCheck(String s) {
        if (s.length() < 2) return false;
        String firstHalf = s.substring(0, s.length()/2);
        String secondHalf = s.substring(s.length()/2).trim();
        long x = Long.parseLong(firstHalf);
        long y = Long.parseLong(secondHalf);
        long z = x+y;
        BigInteger sq = BigInteger.valueOf(z).pow(2);
        if (!sq.toString().equals(s)) {
            return false;
        }
        if (!Long.toString(y).equals(secondHalf)) {
            //System.out.println(s + " - " + firstHalf + " - " + secondHalf + " vs " + y);
            return false;
        }
        return true;
    }

    private List<String> loadData(String file) throws FileNotFoundException {

        List<String> loadedData = new ArrayList<>();

        Scanner input = new Scanner(new File(file));
        while (input.hasNextLine()) {
            String s = input.nextLine();
            String[] data = s.split(" ");
            loadedData.add(data[1].trim());
            //System.out.println(Arrays.toString(data));
        }
        return loadedData;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=932" +
                "<p>For the year $2025$</p>\n" +
                "$$2025 = (20 + 25)^2$$\n" +
                "<p>Given positive integers $a$ and $b$, the concatenation $ab$ we call a $2025$-number if $ab = (a+b)^2$.<br>\n" +
                "Other examples are $3025$ and $81$.<br>\n" +
                "Note $9801$ is not a $2025$-number because the concatenation of $98$ and $1$ is $981$.</p>\n" +
                "\n" +
                "<p>\n" +
                "Let $T(n)$ be the sum of all $2025$-numbers with $n$ digits or less. You are given $T(4) = 5131$.</p>\n" +
                "\n" +
                "<p>\n" +
                "Find $T(16)$.</p>\n";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("OEIS", "A102766", "brute-force");
    }
}
