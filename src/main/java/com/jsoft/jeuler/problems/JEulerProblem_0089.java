package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class JEulerProblem_0089 extends EulerSolver {

    public JEulerProblem_0089(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        Map<Character, Integer> romanLibrary = new HashMap();
        romanLibrary.put('I', 1);
        romanLibrary.put('V', 5);
        romanLibrary.put('X', 10);
        romanLibrary.put('L', 50);
        romanLibrary.put('C', 100);
        romanLibrary.put('D', 500);
        romanLibrary.put('M', 1000);

        List<String> romanData = loadData("data/problem89/p089_roman.txt");

        int answer = 0;
        for(String roman : romanData) {
            int v = romanToInt(roman, romanLibrary);
            String s = intToRomain(v);
            if(roman.length() < s.length()) {
                System.out.println(roman + " : " + v + " : " + s + " " + ((roman.length() < s.length()) ? "----> yes" : ""));
            }
            answer += (roman.length() - s.length());
        }

        return Integer.toString(answer);
    }

    private int romanToInt(String roman, Map<Character, Integer> romanLibrary) {

        int value = 0;
        char prevC = '@';
        for(char c : roman.toCharArray()) {
            if(prevC == 'I' && c == 'V') {
                //It's four, but we already added 1 so remove it
                value -= 1;
                value += 4;
            } else if(prevC == 'I' && c == 'X') {
                //It's nine, but we already added 1 so remove it
                value -= 1;
                value += 9;
            } else if(prevC == 'X' && c == 'L') {
                //It's 40, but we already added 10 so remove it
                value -= 10;
                value += 40;
            } else if(prevC == 'X' && c == 'C') {
                //It's 90, but we already added 10 so remove it
                value -= 10;
                value += 90;
            } else if(prevC == 'C' && c == 'D') {
                //It's 400, but we already added 100 so remove it
                value -= 100;
                value += 400;
            } else if(prevC == 'C' && c == 'M') {
                //It's 900, but we already added 100 so remove it
                value -= 100;
                value += 900;
            } else {
                value += romanLibrary.get(c);;
            }
            prevC = c;
        }

        return value;

    }

    private String intToRomain(int value) {

        StringBuilder roman = new StringBuilder();
        int n = value;
        while(n > 0) {
            if(n >= 1000) {
                roman.append('M');
                n -= 1000;
            } else if(n/100 == 9) {
                roman.append("CM");
                n -= 900;
            } else if(n >= 500) {
                roman.append("D");
                n -= 500;
            } else if(n/100 == 4) {
                roman.append("CD");
                n -= 400;
            } else if(n >= 100) {
                roman.append("C");
                n -= 100;
            } else if(n/10 == 9) {
                roman.append("XC");
                n -= 90;
            } else if(n >= 50) {
                roman.append("L");
                n -= 50;
            } else if(n/10 == 4) {
                roman.append("XL");
                n -= 40;
            } else if(n >= 10) {
                roman.append("X");
                n -= 10;
            } else if(n == 9) {
                roman.append("IX");
                n -= 9;
            } else if(n >= 5) {
                roman.append("V");
                n -= 5;
            } else if(n == 4) {
                roman.append("IV");
                n -= 9;
            } else {
                roman.append("I");
                n -= 1;
            }
        }

        return roman.toString();

    }

    private List<String> loadData(String fileName) {
        List<String> data = new ArrayList();
        try {
            Scanner input = new Scanner(new File(fileName));
            while(input.hasNext()) {
                data.add(input.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Exception while reading from file " + fileName + " : " + e.getMessage());
        }

        return data;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=89";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
