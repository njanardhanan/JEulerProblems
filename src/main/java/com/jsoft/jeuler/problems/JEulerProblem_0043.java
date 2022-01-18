package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.StringHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashMap;
import java.util.Map;

public class JEulerProblem_0043 extends EulerSolver {

    public JEulerProblem_0043(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        String n = " ";
        long answer = 0;

        for(int x1=1; x1<10; x1++) {
            for (int x2 = 0; x2 < 10; x2++) {
                for (int x3 = 0; x3 < 10; x3++) {
                    n = itoa(x1) + itoa(x2) + itoa(x3);
                    if (!StringHelper.isUniqueCharacters(n)) continue;

                    for (int x4 = 0; x4 < 10; x4 += 2) {
                        n = itoa(x1) + itoa(x2) + itoa(x3) + itoa(x4);
                        if (!StringHelper.isUniqueCharacters(n)) continue;
                        //Check for TWO
                        if ((x2 * 100 + x3 * 10 + x4) % 2 != 0) continue;

                        for (int x5 = 0; x5 < 10; x5++) {
                            n = itoa(x1) + itoa(x2) + itoa(x3) + itoa(x4) + itoa(x5);
                            if (!StringHelper.isUniqueCharacters(n)) continue;
                            //Check for THREE
                            if ((x3 * 100 + x4 * 10 + x5) % 3 != 0) continue;

                            for (int x6 = 0; x6 < 10; x6 += 5) {
                                n = itoa(x1) + itoa(x2) + itoa(x3) + itoa(x4) + itoa(x5) + itoa(x6);
                                if (!StringHelper.isUniqueCharacters(n)) continue;
                                //Check for FIVE
                                if ((x4 * 100 + x5 * 10 + x6) % 5 != 0) continue;

                                for (int x7 = 0; x7 < 10; x7++) {
                                    n = itoa(x1) + itoa(x2) + itoa(x3) + itoa(x4) + itoa(x5) + itoa(x6) + itoa(x7);
                                    if (!StringHelper.isUniqueCharacters(n)) continue;
                                    //Check for SEVEN
                                    if ((x5 * 100 + x6 * 10 + x7) % 7 != 0) continue;

                                    for (int x8 = 0; x8 < 10; x8++) {
                                        n = itoa(x1) + itoa(x2) + itoa(x3) + itoa(x4) + itoa(x5) + itoa(x6) + itoa(x7) + itoa(x8);
                                        if (!StringHelper.isUniqueCharacters(n)) continue;
                                        //Check for ELEVEN
                                        if ((x6 * 100 + x7 * 10 + x8) % 11 != 0) continue;

                                        for (int x9 = 0; x9 < 10; x9++) {
                                            n = itoa(x1) + itoa(x2) + itoa(x3) + itoa(x4) + itoa(x5) + itoa(x6) + itoa(x7) + itoa(x8) + itoa(x9);
//                                            if (n.equals("140635728")) {
//                                                System.out.println("here");
//                                            }
                                            if (!StringHelper.isUniqueCharacters(n)) continue;
                                            //Check for THIRTEEN
                                            if ((x7 * 100 + x8 * 10 + x9) % 13 != 0) continue;

                                            for (int x10 = 0; x10 < 10; x10 ++) {
                                                n = itoa(x1) + itoa(x2) + itoa(x3) + itoa(x4) + itoa(x5) + itoa(x6) + itoa(x7) + itoa(x8) + itoa(x9) + itoa(x10);
                                                if (!StringHelper.isUniqueCharacters(n)) continue;
                                                //Check for ELEVEN
                                                if ((x8 * 100 + x9 * 10 + x10) % 17 != 0) continue;

                                                //System.out.println(n);
                                                answer += Long.parseLong(n);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        return Long.toString(answer);
    }

    private String itoa(int x) {
        return Integer.toString(x);
    }



    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=43";
    }
}
