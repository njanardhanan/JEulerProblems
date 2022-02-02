package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class JEulerProblem_0335 extends EulerSolver {

    public JEulerProblem_0335(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * NOT SOLVED YET
         */
        for(int y=1; y<=100; y++) {
            int TARGET = y;
            Queue<Integer> queue = new LinkedList<>();
            for (int i = 0; i < TARGET; i++)
                queue.add(1);

            int count = 0;
            int head = queue.remove();
            while (head != TARGET) {
                queue.add(0);
                int nextNode = 0;
                for (int x = 0; x < head; x++) {
                    nextNode = queue.remove() + 1;
                    if (head - 1 == x) {
                        break;
                    } else {
                        queue.add(nextNode);
                    }
                }
                count++;
                head = nextNode;

            }
            System.out.printf("%d,",count+1);
        }

        return Integer.toString(0);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=335";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
