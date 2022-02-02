package com.jsoft.jeuler.solver;

import com.jsoft.jeuler.utils.Logger;

import java.util.List;

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
        log.logTags(getTags());
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

    @Override
    public abstract List<String> getTags();
}
