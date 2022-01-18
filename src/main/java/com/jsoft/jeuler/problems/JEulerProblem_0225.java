package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0225 extends EulerSolver {

    public JEulerProblem_0225(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        /**
         * https://oeis.org/A000213
         * https://oeis.org/A215782
         */

        long x = 1;
        long y = 1;
        long z = 1;

        long n = 27;

        int TARGET  = 124;
        int count = 0;

        while(true) {
            long nextTermMod = (x + y + z) % n;
            if (nextTermMod == 0) {

                //Reset the x, y and z
                x = 1;
                y = 1;
                z = 1;

                //Check next odd no.
                n += 2;
                continue;
            }

            x = y;
            y = z;
            z = nextTermMod;

            if (x == 1 && y == 1 && z == 1) {
                //Increase the count.
                count++;

                //For debug only
                System.out.printf("%d, ", n);
                if(count % 10 == 0) System.out.println();

                //Check next odd no.
                n += 2;
            }

            if(count == TARGET){
                break;
            }
        }

        //-2 because we have already moved to next odd
        return Long.toString(n-2);
    }


    @Override
    public String getProblemStatement() {
        String s = "The sequence 1, 1, 1, 3, 5, 9, 17, 31, 57, 105, 193, 355, 653, 1201 ...\n" +
                "is defined by T1 = T2 = T3 = 1 and Tn = Tn-1 + Tn-2 + Tn-3.\n" +
                "\n" +
                "It can be shown that 27 does not divide any terms of this sequence.\n" +
                "In fact, 27 is the first odd number with this property.\n" +
                "\n" +
                "Find the 124th odd number that does not divide any terms of the above sequence.";

        return  s + "https://projecteuler.net/problem=225";
    }
}
