package com.jsoft.jeuler.solver;

import com.jsoft.jeuler.utils.Logger;

import java.util.List;

public interface IEulerSolver {

    void solve(Logger log);

    String solve();

    String getProblemStatement();

    int getProblemNumber();

    List<String> getTags();
}
