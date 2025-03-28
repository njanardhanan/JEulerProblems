package com.jsoft.jeuler.solver;

import com.jsoft.jeuler.utils.Constants;


public class EulerSolverFactory {
    public IEulerSolver getSolver(int problemNo) {

        String problemClassName = Constants.SOLVER_CLASS_PREFIX;
        problemClassName += String.format(Constants.PROBLEM_NUMBER_FORMAT, problemNo);

        try {
            return (IEulerSolver)Class.forName(problemClassName).getConstructor(int.class).newInstance(problemNo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UnsupportedOperationException("Problem " + problemNo + " not implemented yet...");
        }
    }

    public IEulerSolver getSolver(int problemNo, boolean inProgress) {

        String problemClassName = inProgress ? Constants.INPROGRESS_CLASS_PREFIX : Constants.SOLVER_CLASS_PREFIX;
        problemClassName += String.format(Constants.PROBLEM_NUMBER_FORMAT, problemNo);

        try {
            return (IEulerSolver)Class.forName(problemClassName).getConstructor(int.class).newInstance(problemNo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new UnsupportedOperationException("Problem " + problemNo + " not implemented yet...");
        }
    }
}
