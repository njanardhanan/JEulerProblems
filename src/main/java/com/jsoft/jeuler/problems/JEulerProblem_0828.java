package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.combinatorics.Generator;
import com.jsoft.jeuler.solver.EulerSolver;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class JEulerProblem_0828 extends EulerSolver {

    public JEulerProblem_0828(int problemNumber) {
        super(problemNumber);
    }

    class PE828Data {
        private int target;
        private List<Integer> values;

        public PE828Data(int target, List<Integer> values) {
            this.target = target;
            this.values = values;
        }

        public int getTarget() {
            return target;
        }

        public List<Integer> getValues() {
            return values;
        }

        @Override
        public String toString() {
            return target + " : " + values;
        }
    }

    @Override
    public String solve() {
        BigInteger ans = BigInteger.ZERO;
        BigInteger MOD = BigInteger.valueOf(1005075251L);
        BigInteger THREE = BigInteger.valueOf(3);
        String FILE_NAME = "data/problem828/p828_number_challenges.txt";

        List<PE828Data> dataList = null;
        try {
            dataList = readPE828Data(FILE_NAME);
        } catch (IOException e) {
            System.out.println("Unable to load the file : " + FILE_NAME);
            return "0";
        }

        int counter = 1;
        for (PE828Data data : dataList) {
            int minScore = Integer.MAX_VALUE;
            for (int combinationLength = 2; combinationLength <= 6; combinationLength++) {
                //System.out.println("Combination Length : " + combinationLength);
                Iterator<List<Integer>> comb = Generator.combination(data.getValues()).simple(combinationLength).iterator();
                while (comb.hasNext()) {
                    List<Integer> currentCombination = comb.next();
                    //System.out.println("Checking : " + currentCombination);
                    if (checkIfResultReached(currentCombination, data.getTarget())) {
                        int score = currentCombination.stream().mapToInt(Integer::intValue).sum();
                        if (minScore > score) {
                            minScore = score;
                            //System.out.println("Mins : " + minScore);
                        }
                    }
                }
            }
            if (minScore < Integer.MAX_VALUE) {
                System.out.printf("Min Score #%d : %d\n", counter, minScore);
                ans = ans.add(THREE.pow(counter).multiply(BigInteger.valueOf(minScore))).mod(MOD);
            } else {
                System.out.printf("Min Score #%d : %d\n", counter, 0);
            }
            counter++;
        }

        //148693670
        return ans.mod(MOD).toString();
    }

    private List<Integer> generatePossibleResults(int a, int b) {
        List<Integer> res = new ArrayList<>();
        res.add(a + b);
        res.add(a - b);
        res.add(b - a);
        res.add(a * b);
        if (b != 0 && a>=b && a%b == 0) {
            res.add(a / b);
        }
        if (a != 0 && b>=a && b%a == 0) {
            res.add(b / a);
        }
        return res;
    }

    // Check if using current list reaches the target.
    private boolean checkIfResultReached(List<Integer> list, int target) {
        if (list.size() == 1) {
            // Base Case: We have only one number left, check if it is equal to target.
            return list.get(0) == target;
        }

        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                // Create a new list with the remaining numbers and the new result.
                List<Integer> newList = new ArrayList<>();
                for (int k = 0; k < list.size(); k++) {
                    if (k != j && k != i) {
                        newList.add(list.get(k));
                    }
                }

                // For any two numbers in our list,
                // we perform every operation one by one.
                for (int res : generatePossibleResults(list.get(i), list.get(j))) {
                    // Push the new result in the list
                    newList.add(res);

                    // Check if using this new list we can obtain the result 24.
                    if (checkIfResultReached(newList, target)) {
                        return true;
                    }

                    // Backtrack: remove the result from the list.
                    newList.remove(newList.size() - 1);
                }
            }
        }
        return false;
    }

    class Data {
        public int score;
        public int total;
        public int minimumScore;

        public Data(Data d) {
            score = d.score;
            total = d.total;
            minimumScore = d.minimumScore;
        }

        public Data(int s, int t, int m) {
            score = s;
            total = t;
            minimumScore = m;
        }

        public Data(int s, int t) {
            score = s;
            total = t;
            minimumScore = Integer.MAX_VALUE;
        }

        @Override
        public String toString() {
            return "[" + score + "," + total + "," + minimumScore + "]";
        }
    }

    class DataLine {
        private int target;
        private List<Data> values;

        public DataLine(int id, List<Data> values) {
            this.target = id;
            this.values = values;
        }

        public int getTarget() {
            return target;
        }

        public List<Data> getValues() {
            return values;
        }

        @Override
        public String toString() {
            return target + " : " + values;
        }
    }

    private String solveSlow() {
        BigInteger MOD = BigInteger.valueOf(1005075251L);
        BigInteger THREE = BigInteger.valueOf(3);
        String FILE_NAME = "data/problem828/p828_number_challenges.txt";

        List<DataLine> dataList = null;
        try {
            dataList = readData(FILE_NAME);
        } catch (IOException e) {
            System.out.println("Unable to load the file : " + FILE_NAME);
            return "0";
        }

        int i = 1;
        BigInteger ans = BigInteger.ZERO;

        List<Integer> scoreList = new ArrayList<>();
        for (DataLine data : dataList) {
            List<Data> resultA = checkIfResultReachedWithData(data.getValues(), data.getTarget());
            int minScore = Integer.MAX_VALUE;
            for (Data r : resultA) {
                if (minScore > r.minimumScore) {
                    minScore = r.minimumScore;
                }
            }
            if (minScore < Integer.MAX_VALUE) {
                System.out.printf("Min Score #%d : %d\n", i, minScore);
                scoreList.add(minScore);
                ans = ans.add(THREE.pow(i).multiply(BigInteger.valueOf(minScore))).mod(MOD);
            } else {
                scoreList.add(0);
            }

            i++;
        }

        System.out.println(scoreList);

        //148693670

        return ans.mod(MOD).toString();
    }

    private int min(int x, int y) {
        return (x<y) ? x : y;
    }

    // All possible operations we can perform on two numbers.
    private List<Data> generatePossibleResults(Data x, Data y) {

        int scoreA = x.score;
        int scoreB = y.score;

        int totalA = x.total;
        int totalB = y.total;

        int minScore = min(x.minimumScore, y.minimumScore);

        List<Data> res = new ArrayList<>();
        res.add(new Data(scoreA + scoreB, totalA + totalB, minScore ));
        res.add(new Data(scoreA + scoreB, totalA - totalB, minScore ));
        res.add(new Data(scoreA + scoreB, totalB - totalA, minScore ));
        res.add(new Data(scoreA + scoreB, totalA * totalB, minScore ));

        if (totalB != 0 && totalA%totalB == 0) {
            res.add(new Data(scoreA + scoreB, totalA / totalB, minScore ));
        }
        if (totalA != 0 && totalB%totalA == 0) {
            res.add(new Data(scoreA + scoreB, totalB / totalA, minScore ));
        }
        return res;
    }

    // Check if using current list we can react result 24.
    private List<Data> checkIfResultReachedWithData(List<Data> list, int target) {
        if (list.size() == 1) {
            // Base Case: We have only one number left, check if it is approximately 24.
            return list;
        }

        List<Data> returnList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                // Create a new list with the remaining numbers and the new result.
                List<Data> newList = new ArrayList<>();
                for (int k = 0; k < list.size(); k++) {
                    if (k != j && k != i) {
                        newList.add(new Data(list.get(k)));
                    }
                }

                // For any two numbers in our list,
                // we perform every operation one by one.
                for (Data res : generatePossibleResults(list.get(i), list.get(j))) {
                    if (res.total == target && res.minimumScore > res.score) {
                        res.minimumScore = res.score;
                    }
                    // Push the new result in the list
                    newList.add(res);

                    // Check if using this new list we can obtain the result 24.
                    returnList.addAll(checkIfResultReachedWithData(newList, target));

                    // Backtrack: remove the result from the list.
                    newList.remove(newList.size() - 1);
                }
            }
        }
        return returnList;
    }

    public List<DataLine> readData(String filename) throws IOException {
        List<DataLine> dataList = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filename))) {
            scanner.useDelimiter(":|\r\n");

            while (scanner.hasNext()) {
                int target = scanner.nextInt();
                String[] valuesStr = scanner.next().split(",");
                List<Data> values = Arrays.stream(valuesStr)
                        .map(Integer::parseInt)
                        .map(x -> new Data(x, x))
                        .collect(Collectors.toList());
                dataList.add(new DataLine(target, values));
            }
        }

        return dataList;
    }

    public List<PE828Data> readPE828Data(String filename) throws IOException {
        List<PE828Data> dataList = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filename))) {
            scanner.useDelimiter(":|\r\n");

            while (scanner.hasNext()) {
                int target = scanner.nextInt();
                String[] valuesStr = scanner.next().split(",");
                List<Integer> values = Arrays.stream(valuesStr)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                dataList.add(new PE828Data(target, values));
            }
        }

        return dataList;
    }


    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=828";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("recursive", "combination", "countdown game");
    }
}
