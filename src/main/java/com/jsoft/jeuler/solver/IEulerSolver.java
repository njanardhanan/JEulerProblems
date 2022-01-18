package com.jsoft.jeuler.solver;

import com.jsoft.jeuler.utils.Logger;

public interface IEulerSolver {

    void solve(Logger log);

    String solve();

    String getProblemStatement();

    int getProblemNumber();
}
