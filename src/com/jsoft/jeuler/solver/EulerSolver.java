package com.jsoft.jeuler.solver;

import com.jsoft.jeuler.utils.Logger;

public abstract class EulerSolver implements IEulerSolver {

    private int problemNumber;

    public EulerSolver(int problemNumber) {
        this.problemNumber = problemNumber;
    }

    @Override
    public void solve(Logger log) {
        log.start(getProblemNumber(), getProblemStatement());
        String answer = solve();
        log.stop();
        log.logAnswer(answer);
    }

    @Override
    public int getProblemNumber() {
        return problemNumber;
    }

    @Override
    public abstract String solve();

    @Override
    public abstract String getProblemStatement();
}
