package com.jsoft.jeuler.inprogress;

import com.jsoft.jeuler.solver.EulerSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class JEulerProblem_0936 extends EulerSolver {

    public JEulerProblem_0936(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        List<Long> data = new ArrayList<>();
        try {
            data = loadData("data/problem936/data.txt");
            System.out.println(data.get(0));
            BigInteger bigInt = BigInteger.ZERO;
            for (Long l : data) {
                bigInt = bigInt.add(BigInteger.valueOf(l));
            }
            System.out.println(bigInt);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "0";
    }

    private List<Long> loadData(String filename) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filename));
        List<Long> data = new ArrayList<>();
        while(input.hasNextLine()) {
            String d = input.nextLine();
            String[] sd = d.split(" ");
            data.add(Long.parseLong(sd[1]));
        }
        return data;
    }

    @Override
    public String getProblemStatement() {
        return "This is a template file";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList();
    }
}
