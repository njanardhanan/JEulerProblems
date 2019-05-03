package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_9999 extends EulerSolver {

    public JEulerProblem_9999(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int target = 10000;
        boolean[] isopen = new boolean[target+1];
        for(int student=1; student<=target; student++) {
            isopen[student] = true;
        }
        for(int student=2; student<=target; student++) {
            for(int s=student; s<=target; s += student) {
                isopen[s] = !isopen[s];
            }
        }

        int count = 0;
        for(int student=1; student<=target; student++) {
            if(isopen[student]) {
                System.out.print(student + ", ");
                count++;
            }
        }
        System.out.println();
        System.out.println("No of open : " + count);
        System.out.println("No of close : " + (target-count));
        return Integer.toString(count);
    }

    @Override
    public String getProblemStatement() {
        return "just like that";
    }
}
