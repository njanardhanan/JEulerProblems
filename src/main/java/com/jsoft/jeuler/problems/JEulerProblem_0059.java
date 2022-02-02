package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class JEulerProblem_0059 extends EulerSolver {

    public JEulerProblem_0059(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int answer = 0;

        try {
            List<Integer> data = loadData("data/problem59/p059_cipher.txt");

            int[] masterKey = {};
            int maxCount = 0;
            for(int a=97; a<=122; a++) {
                for(int b=97; b<=122; b++) {
                    for(int c=97; c<=122; c++) {
                        int[] key = {a, b, c};
                        int count = 0;
                        for(int n=0; n<data.size(); n++) {
                            int val = data.get(n) ^ key[n%3];
                            if(Character.isLetterOrDigit(val) || val == 32) {
                                count++;
                            }
                        }
                        if(count > maxCount) {
                            maxCount = count;
                            masterKey = key;
                        }
                    }
                }
            }

            //Arrays.stream(masterKey).forEach(x -> System.out.printf("%c",x));
            for(int n=0; n<data.size(); n++) {
                int val = data.get(n) ^ masterKey[n % 3];
                answer += val;
                System.out.printf("%c", val);
            }
            System.out.println();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        return Integer.toString(answer);
    }

    private List<Integer> loadData(String file) throws FileNotFoundException {

        List<Integer> data;

        Scanner input = new Scanner(new File(file));
        String s = input.nextLine();
        data = Arrays.stream(s.split(","))
                .mapToInt(x -> Integer.parseInt(x))
                .boxed()
                .collect(Collectors.toList());
        //System.out.println(s);
        return data;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=59";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
