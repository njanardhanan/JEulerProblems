package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class JEulerProblem_0191 extends EulerSolver {

    public JEulerProblem_0191(int problemNumber) {
        super(problemNumber);
    }

    class PatternData {
        int noOfL;
        int noOfAatTheEnd;

        PatternData(int l, int a) {
            noOfL = l;
            noOfAatTheEnd = a;
        }

        int getNoOfL() {
            return noOfL;
        }

        int getNoOfAatTheEnd() {
            return noOfAatTheEnd;
        }
    }

    @Override
    public String solve() {
        int TARGET = 30;

        /**
         * Pattern on day 1 is O L A
         * Pattern on day 2 is OO OL OA LO LL LA AO AL AA
         * Initialize with day #1 pattern.
         */
        int total = 3;
        int onlyOneL = 1;
        int endsWithOneA = 1;
        int endsWithTwoA = 0;
        int oneLAndEndsWithOneA = 0;
        int oneLAndEndsWithTwoA = 0;
        int noLAndNotEndsWithA = 1;


        for(int day=2; day<=TARGET; day++) {
            /**
             *
             * WHAT WILL only-One-L string BECOME?
             *
             * When you add O in only-One-L string (Ex : OOLAO-O):
             * It will remain only-One-L string, so we have to add amount of
             * only-One-L string to next generation (day #2) only-One-L string.
             *
             * When you add L in only-One-L string (Ex : OOLAO-L):
             * It will become invalid string, so no need to add it to next generation.
             *
             * When you add A in only-One-L string (Ex : OOLAO-A):
             * It will become ends-With-One-A string, so we have to add amount of
             * only-One-L string to next generation (day #2) ends-With-One-A string.
             *
             *
             * WHAT OTHER BUCKETs CONTRIBUTE TO only-One-L string?
             *
             * only-One-L                : When you add O in only-One-L "OOLAO-O", it will remain only-One-L.
             * ends-With-One-A           : When you add L in ends-With-One-A "OOA-L", it will became only-One-L.
             * ends-With-Two-A           : When you add L in ends-With-Two-A "OOAA-L", it will became only-One-L.
             * one-L-And-Ends-With-One-A : When you add O in one-L-And-Ends-With-One-A "OOLA-O", it will became only-One-L.
             * one-L-And-Ends-With-Two-A : When you add O in one-L-And-Ends-With-Two-A "OOLAA-O", it will became only-One-L.
             * no-L-And-Not-Ends-With-A  : When you add L in no-L-And-Not-Ends-With-A "OOAAO-L", it will became only-One-L.
             *
             */
            int currOnlyOneL = onlyOneL + endsWithOneA + endsWithTwoA + oneLAndEndsWithOneA + oneLAndEndsWithTwoA + noLAndNotEndsWithA;

            /**
             *
             * WHAT WILL ends-With-One-A string BECOME?
             *
             * When you add O in ends-With-One-A string (Ex : OOA-O):
             * It will become no-L-And-Not-Ends-With-A string, so we have to add amount of
             * ends-With-One-A string to next generation (day #2) no-L-And-Not-Ends-With-A string.
             *
             * When you add L in ends-With-One-A string (Ex : OOA-L):
             * It will become only-One-L string, so we have to add amount of
             * ends-With-One-A string to next generation (day #2) only-One-L string.
             *
             * When you add A in ends-With-One-A string (Ex : OOA-A):
             * It will become ends-With-Two-A string, so we have to add amount of
             * ends-With-One-A string to next generation (day #2) ends-With-Two-A string.
             *
             *
             * WHAT OTHER BUCKETs CONTRIBUTE TO ends-With-One-A string?
             *
             * only-One-L                : No matter what you add, it will not become ends-With-One-A.
             * ends-With-One-A           : No matter what you add, it will not become ends-With-One-A.
             * ends-With-Two-A           : No matter what you add, it will not become ends-With-One-A.
             * one-L-And-Ends-With-One-A : No matter what you add, it will not become ends-With-One-A.
             * one-L-And-Ends-With-Two-A : No matter what you add, it will not become ends-With-One-A.
             * no-L-And-Not-Ends-With-A  : When you add A in no-L-And-Not-Ends-With-A "OOAAO-A", it will became ends-With-One-A.
             *
             */
            int currEndsWithOneA = noLAndNotEndsWithA;

            /**
             *
             * WHAT WILL ends-With-Two-A string BECOME?
             *
             * When you add O in ends-With-Two-A string (Ex : OOAA-O):
             * It will become no-L-And-Not-Ends-With-A string, so we have to add amount of
             * ends-With-Two-A string to next generation (day #2) no-L-And-Not-Ends-With-A string.
             *
             * When you add L in ends-With-Two-A string (Ex : OOAA-L):
             * It will become only-One-L string, so we have to add amount of
             * ends-With-Two-A string to next generation (day #2) only-One-L string.
             *
             * When you add A in ends-With-Two-A string (Ex : OOAA-A):
             * It will become invalid string, so no need to add it to next generation.
             *
             *
             * WHAT OTHER BUCKETs CONTRIBUTE TO ends-With-Two-A string?
             *
             * only-One-L                : No matter what you add, it will not become ends-With-Two-A.
             * ends-With-One-A           : When you add A in ends-With-One-A "OOA-A", it will become ends-With-Two-A.
             * ends-With-Two-A           : No matter what you add, it will not become ends-With-Two-A.
             * one-L-And-Ends-With-One-A : No matter what you add, it will not become ends-With-Two-A.
             * one-L-And-Ends-With-Two-A : No matter what you add, it will not become ends-With-Two-A.
             * no-L-And-Not-Ends-With-A  : No matter what you add, it will not become ends-With-Two-A.
             *
             */
            int currEndsWithTwoA = endsWithOneA;

            /**
             *
             * WHAT WILL one-L-And-Ends-With-One-A string BECOME?
             *
             * When you add O in one-L-And-Ends-With-One-A string (Ex : OOLA-O):
             * It will become only-One-L string, so we have to add amount of
             * one-L-And-Ends-With-One-A string to next generation (day #2) only-One-L string.
             *
             * When you add L in one-L-And-Ends-With-One-A string (Ex : OOLA-L):
             * It will become invalid string, so no need to add it to next generation.
             *
             * When you add A in one-L-And-Ends-With-One-A string (Ex : OOLA-A):
             * It will become one-L-And-Ends-With-Two-A string, so we have to add amount of
             * one-L-And-Ends-With-One-A string to next generation (day #2) one-L-And-Ends-With-Two-A string.
             *
             *
             * WHAT OTHER BUCKETs CONTRIBUTE TO one-L-And-Ends-With-One-A string?
             *
             * only-One-L                : When you add A in only-One-L "OOAALO-A", it will became one-L-And-Ends-With-One-A.
             * ends-With-One-A           : No matter what you add, it will not become one-L-And-Ends-With-One-A.
             * ends-With-Two-A           : No matter what you add, it will not become one-L-And-Ends-With-One-A.
             * one-L-And-Ends-With-One-A : No matter what you add, it will not become one-L-And-Ends-With-One-A.
             * one-L-And-Ends-With-Two-A : No matter what you add, it will not become one-L-And-Ends-With-One-A.
             * no-L-And-Not-Ends-With-A  : No matter what you add, it will not become one-L-And-Ends-With-One-A.
             *
             */
            int currOneLAndEndsWithOneA = onlyOneL;

            /**
             *
             * WHAT WILL one-L-And-Ends-With-Two-A string BECOME?
             *
             * When you add O in one-L-And-Ends-With-Two-A string (Ex : OOLAA-O):
             * It will become only-One-L string, so we have to add amount of
             * one-L-And-Ends-With-Two-A string to next generation (day #2) only-One-L string.
             *
             * When you add L in one-L-And-Ends-With-Two-A string (Ex : OOLAA-L):
             * It will become invalid string, so no need to add it to next generation.
             *
             * When you add A in one-L-And-Ends-With-Two-A string (Ex : OOLAA-A):
             * It will become invalid string, so no need to add it to next generation.

             *
             * WHAT OTHER BUCKETs CONTRIBUTE TO one-L-And-Ends-With-Two-A string?
             *
             * only-One-L                : No matter what you add, it will not become one-L-And-Ends-With-Two-A.
             * ends-With-One-A           : No matter what you add, it will not become one-L-And-Ends-With-Two-A.
             * ends-With-Two-A           : No matter what you add, it will not become one-L-And-Ends-With-Two-A.
             * one-L-And-Ends-With-One-A : When you add A in one-L-And-Ends-With-One-A "OOLA-A", it will become one-L-And-Ends-With-Two-A.
             * one-L-And-Ends-With-Two-A : No matter what you add, it will not become one-L-And-Ends-With-Two-A.
             * no-L-And-Not-Ends-With-A  : No matter what you add, it will not become one-L-And-Ends-With-Two-A.
             *
             */
            int currOneLAndEndsWithTwoA = oneLAndEndsWithOneA;

            /**
             *
             * WHAT WILL no-L-And-Not-Ends-With-A string BECOME?
             *
             * When you add O in no-L-And-Not-Ends-With-A string (Ex : OOAAO-O):
             * It will remain no-L-And-Not-Ends-With-A string, so we have to add amount of
             * no-L-And-Not-Ends-With-A string to next generation (day #2) no-L-And-Not-Ends-With-A string.
             *
             * When you add L in no-L-And-Not-Ends-With-A string (Ex : OOAAO-L):
             * It will become only-One-L string, so we have to add amount of
             * no-L-And-Not-Ends-With-A string to next generation (day #2) only-One-L string.
             *
             * When you add A in no-L-And-Not-Ends-With-A string (Ex : OOAAO-A):
             * It will become ends-With-One-A string, so we have to add amount of
             * no-L-And-Not-Ends-With-A string to next generation (day #2) ends-With-One-A string.
             *
             * WHAT OTHER BUCKETs CONTRIBUTE TO no-L-And-Not-Ends-With-A string?
             *
             * only-One-L                : No matter what you add, it will not become no-L-And-Not-Ends-With-A.
             * ends-With-One-A           : When you add O in ends-With-One-A "OOA-O", it will become no-L-And-Not-Ends-With-A.
             * ends-With-Two-A           : When you add O in ends-With-Two-A "OOAA-O", it will become no-L-And-Not-Ends-With-A.
             * one-L-And-Ends-With-One-A : No matter what you add, it will not become no-L-And-Not-Ends-With-A.
             * one-L-And-Ends-With-Two-A : No matter what you add, it will not become no-L-And-Not-Ends-With-A.
             * no-L-And-Not-Ends-With-A  : When you add O in ends-With-Two-A "OOAAO-O", it will become no-L-And-Not-Ends-With-A.
             */
            int currNoLAndNotEndsWithA = endsWithOneA + endsWithTwoA + noLAndNotEndsWithA;

            /**
             * Total valid strings in new generation.
             */
            int currTotal = currOnlyOneL + currEndsWithOneA + currEndsWithTwoA + currOneLAndEndsWithOneA + currOneLAndEndsWithTwoA + currNoLAndNotEndsWithA;

            //System.out.printf("N : %d, Total : %d\n", day, currTotal);

            onlyOneL = currOnlyOneL;
            endsWithOneA = currEndsWithOneA;
            endsWithTwoA = currEndsWithTwoA;
            oneLAndEndsWithOneA = currOneLAndEndsWithOneA;
            oneLAndEndsWithTwoA = currOneLAndEndsWithTwoA;
            noLAndNotEndsWithA = currNoLAndNotEndsWithA;
            total = currTotal;
        }

        return Integer.toString(total);
    }
    private String bruteForce1() {
        int TARGET = 10;

        Queue<PatternData> patternDataQueue = new LinkedList<>();
        // O
        patternDataQueue.add(new PatternData(0, 0));
        // L
        patternDataQueue.add(new PatternData(1, 0));
        // A
        patternDataQueue.add(new PatternData(0, 1));


        for(int n=2; n<=TARGET; n++) {
            int size = patternDataQueue.size();
            for(int x=0; x<size; x++) {
                PatternData currentPattern = patternDataQueue.remove();

                //Append O
                //Appending O will not make the pattern ineligible.
                //Appending O will not affect the L count.
                //Appending O will reset the A count at the end.
                PatternData pO = new PatternData(currentPattern.getNoOfL(), 0);
                patternDataQueue.add(pO);

                //Append L
                //Appending L may make the pattern ineligible.
                //Appending L will affect the L count.
                //Appending L will reset the A count at the end.
                if(currentPattern.getNoOfL() == 0) {
                    PatternData pL = new PatternData(currentPattern.getNoOfL() + 1, 0);
                    patternDataQueue.add(pL);
                }

                //Append A
                //Appending A may make the pattern ineligible.
                //Appending A will not affect the L count.
                //Appending A will not reset the A count at the end.
                if(currentPattern.getNoOfAatTheEnd() < 2) {
                    PatternData pL = new PatternData(currentPattern.getNoOfL(), currentPattern.getNoOfAatTheEnd() + 1);
                    patternDataQueue.add(pL);
                }
            }
            System.out.printf("N = %d, size = %d, actual = %d\n", n, patternDataQueue.size(), (int)Math.pow(3, n));
        }

        return Integer.toString(patternDataQueue.size());
    }

    private String bruteForce() {
        List<String> trinary = new ArrayList();
        trinary.add("O");
        trinary.add("L");
        trinary.add("A");

        List<String> pattern = new ArrayList(trinary);

        List<String> nextPattern = new ArrayList();
        int TARGET = 6;
        System.out.printf("N = %d - [", 1);
        for(String s : pattern) {
            System.out.printf("%s, ", s);
        }
        System.out.printf("] - %d\n", pattern.size());
        for(int n=1; n<TARGET; n++) {
            for (String p : pattern) {
                for (String t : trinary) {
                    String s = p + t;
                    if (s.contains("AAA")) continue;

                    int firstL = s.indexOf("L");
                    int nextL = s.indexOf("L", firstL + 1);

                    if (nextL > 0) continue;

                    nextPattern.add(s);
                }
            }
            pattern = nextPattern;
            System.out.printf("N = %d - [", n+1);
            for(String s : pattern) {
                System.out.printf("%s, ", s);
            }
            System.out.printf("] - %d\n", pattern.size());

            int onlyOneL = 0;
            int endsWithOneA = 0;
            int endsWithTwoA = 0;
            int lAndendsWithOneA = 0;
            int lAndendsWithTwoA = 0;
            int others = 0;
            String validString= "";

            for(String s : pattern) {
                if(s.contains("L") && !s.endsWith("A")) {
                    onlyOneL++;
                } else if(!s.contains("L") && s.endsWith("A") && !s.endsWith("AA")) {
                    endsWithOneA++;
                } else if(!s.contains("L") && s.endsWith("AA")) {
                    endsWithTwoA++;
                } else if(s.contains("L") && s.endsWith("AA")) {
                    lAndendsWithTwoA++;
                } else if(s.contains("L") && s.endsWith("A")) {
                    lAndendsWithOneA++;
                } else{
                    validString = validString + s + ", ";
                    others++;
                }
            }
            System.out.println("t : " + pattern.size());
            System.out.println("1L : " + onlyOneL);
            System.out.println("1A : " + endsWithOneA);
            System.out.println("2A : " + endsWithTwoA);
            System.out.println("1L + 1A : " + lAndendsWithOneA);
            System.out.println("1L + 2A : " + lAndendsWithTwoA);
            System.out.println("valid : " + others);
            System.out.println(validString);

            nextPattern = new ArrayList();
        }

        System.out.println("\n");

        return Integer.toString(pattern.size());
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=357";
    }
}
