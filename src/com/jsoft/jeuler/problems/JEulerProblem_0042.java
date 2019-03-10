package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;
import com.jsoft.jeuler.utils.FileUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JEulerProblem_0042 extends EulerSolver {

    public JEulerProblem_0042(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        //Generate triangle numbers.
        Set<Integer> triangleNumbers = new HashSet();
        //Upper limit for triangle no. generation is a guesstimate.
        for(int i=1; i<=250; i++) {
            triangleNumbers.add(i * (i+1) / 2);
        }

        List<String> words = FileUtils.getCommaSeparatedWords("data/problem42/p042_words.txt");
        int count = 0;
        for(String w : words) {
            int tmp = w.chars().map(x -> x - 64).sum();
            if (triangleNumbers.contains(tmp)) {
                count++;
            }
        }



//        for(String s : lines) {
//            String lineWithoutDoubleQuotes = s.replace("\"", "");
//            System.out.println(lineWithoutDoubleQuotes);
//            String[] words = lineWithoutDoubleQuotes.split(",");
//            for(String w : words) {
//                long wordValue = 0;
//                for(int i : w.toCharArray()) {
//                    wordValue += (i-64);
//                    //System.out.println(i-64);
//                }
//                if (wordValue > 0) {
//                    long tmp = (long)Math.floor(Math.sqrt(wordValue * 2));
//                    if ((tmp * (tmp+1)) == wordValue * 2) {
//                        //System.out.print(w + ", ");
//                        count++;
//                    }
//                }
//            }
//        }
        return Integer.toString(count);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=42";
    }
}
