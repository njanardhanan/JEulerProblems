package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;
import com.jsoft.jeuler.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class JEulerProblem_0022 extends EulerSolver {

    public JEulerProblem_0022(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        List<String> names = FileUtils.getCommaSeparatedWords("data/problem22/p022_names.txt");

        List<String> nameSorted = names.stream().sorted().collect(Collectors.toList());
        List<Integer> nameValue = new ArrayList();
        for(String s : nameSorted) {
            nameValue.add(s.chars().map(x -> x - 64).sum());
        }

        int count = nameValue.size() + 1;
        long ans = IntStream
                .range(1, count)
                .map(x -> x * nameValue.get(x-1))
                .sum();
        //System.out.println(nameSorted.get(937));

        return Long.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=22";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
