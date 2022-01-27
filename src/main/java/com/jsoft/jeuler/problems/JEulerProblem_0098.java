package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class JEulerProblem_0098 extends EulerSolver {

    class LengthFirstComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            if (o1.length() != o2.length()) {
                return o2.length() - o1.length(); //overflow impossible since lengths are non-negative
            }

            return o1.compareTo(o2);
        }
    }

    public JEulerProblem_0098(int problemNumber) {
        super(problemNumber);
    }

    private boolean isAnagram(String x, String y) {
        if (x.length() != y.length()) return false;
        char[] a1 = x.toCharArray();
        char[] a2 = y.toCharArray();
        Arrays.sort(a1);
        Arrays.sort(a2);
        return Arrays.equals(a1, a2);
    }

    @Override
    public String solve() {
        List<String> data = null;
        try {
            //Load data
            data = loadData("data/problem98/p098_words.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Sort by length and then alphabetically
        Collections.sort(data, new LengthFirstComparator());

        //Create Anagram map.
        Map<String, List<String>> anagramMap = new HashMap<>();
        for (int i=0; i<data.size() - 1; i++) {
            for (int j=i+1; j<data.size(); j++) {
                if (data.get(i).length() != data.get(j).length()) {
                    break;
                }
                if (isAnagram(data.get(i), data.get(j))) {
                    anagramMap.putIfAbsent(data.get(i), new ArrayList<>());
                    anagramMap.get(data.get(i)).add(data.get(j));
                }
            }
        }

        //System.out.println(anagramMap);

        //Take the key from Anagram map, and sort by length.
        List<String> keys = new ArrayList<>(anagramMap.keySet());
        Collections.sort(keys, new LengthFirstComparator());

        //System.out.println(keys);
        //System.out.println(keys.size());

        /**
         *   Now generate a perfect square number such that
         *   1. The number of digits is equal to length of the key.
         *   2. The number of unique digits is equal to number of unique char in the key.
         */

        long ans = 0;
        for (String k : keys) {
            Set<Long> nums = generateAnagramicSquare(k);
            for (long n : nums) {
                for (String v : anagramMap.get(k)) {
                    if (isAnagramicSquare(n, k, v, nums) || isAnagramicSquare(n, v, k, nums)) {
                        //System.out.println("Ans : " + n + ", " + k + ", " + v);
                        if (ans < n) {
                            ans = n;
                        }
                    }
                }
            }
            //System.out.println(k + " " + nums);
        }


        return Long.toString(ans);
    }

    private boolean isAnagramicSquare(long n, String key, String val, Set<Long> nums) {
        Map<Character, Character> map = new HashMap<>();
        String numStr = Long.toString(n);
        for (int i=0; i<key.length(); i++) {
            map.put(key.charAt(i), numStr.charAt(i));
        }

        long newNum = 0;
        for (int i=0; i<val.length(); i++) {
            newNum = newNum * 10 + (long)Character.getNumericValue(map.get(val.charAt(i)));
        }

        return nums.contains(newNum);

    }

    private Set<Long> generateAnagramicSquare(String k) {
        long minValue = (long)Math.sqrt(Math.pow(10, k.length()-1));
        Set<Long> nums = new HashSet<>();
        while (true) {
            long v = minValue * minValue;
            minValue++;
            String numStr = Long.toString(v);
            if (numStr.length() < k.length()) continue;
            if (numStr.length() > k.length()) break;

            if (!areUniqueDigitsEqual(k, numStr)) {
                continue;
            }
            nums.add(v);
        }
        return nums;
    }

    private boolean areUniqueDigitsEqual(String k, String numStr) {
        Map<Character, Character> mapStrToNum = new HashMap<>();
        Map<Character, Character> mapNumToStr = new HashMap<>();
        for (int i=0; i<k.length(); i++) {
            if (!mapStrToNum.containsKey(k.charAt(i))) {
                mapStrToNum.put(k.charAt(i), numStr.charAt(i));
            } else if (mapStrToNum.get(k.charAt(i)) != numStr.charAt(i)) {
                return false;
            }

            if (!mapNumToStr.containsKey(numStr.charAt(i))) {
                mapNumToStr.put(numStr.charAt(i), k.charAt(i));
            } else if (mapNumToStr.get(numStr.charAt(i)) != k.charAt(i)) {
                return false;
            }
        }
        return true;
    }


    private List<String> loadData(String file) throws FileNotFoundException {
        Scanner input = new Scanner(new File(file));
        if (!input.hasNextLine()) {
            return new ArrayList<>();
        }

        String line = input.nextLine();
        String[] d = line.split(",");
        List<String> data = new ArrayList<>();
        for(String s : d) {
            data.add(s.replaceAll("\"", ""));
        }
        return data;
    }

    @Override
    public String getProblemStatement() {
        return "By replacing each of the letters in the word CARE with 1, 2, 9, and 6 respectively, we form a square number: 1296 = 362. What is remarkable is that, by using the same digital substitutions, the anagram, RACE, also forms a square number: 9216 = 962. We shall call CARE (and RACE) a square anagram word pair and specify further that leading zeroes are not permitted, neither may a different letter have the same digital value as another letter.\n" +
                "\n" +
                "Using words.txt (right click and 'Save Link/Target As...'), a 16K text file containing nearly two-thousand common English words, find all the square anagram word pairs (a palindromic word is NOT considered to be an anagram of itself).\n" +
                "\n" +
                "What is the largest square number formed by any member of such a pair?\n" +
                "\n" +
                "NOTE: All anagrams formed must be contained in the given text file.";
    }
}
