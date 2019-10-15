package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;

public class JEulerProblem_0121 extends EulerSolver {

    public JEulerProblem_0121(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * The problem can be solved in many different ways.
         *
         * Probability:
         * ===========
         *
         * Draw #1 - 1 blue and 1 red - Total 2 disk
         * so the probability of winning (taking blue disk) is 1/2.
         * i.e., there is only one way of taking blue disk out of two possible ways.
         * Total disk : B, R
         * possible ways : B or R
         * Winning way : B
         * Losing way : R
         *
         * Draw #2 - 1 blue and 2 red - Total 3 disk
         * so the probability of winning (taking blue disk again) is 1/6
         * i.e., 1/2 from draw 1 and 1/3 now, so total (1/2) * (1/3) = 1/6
         * i.e., there is only one way of taking two blue disk out of six possible ways.
         * Total Disk : B, R1, R2
         * possible ways : BB, BR1, BR2, R1B, R1R1, R1R2
         * Winning way : BB
         * Losing way : R1R1, R1R2
         * Draw the match : BR1, BR2, R1B
         *
         * and so on.. continue till draw 15.
         *
         *
         * Combinatorial:
         * =============
         * Draw #1 - 1 blue and 1 red - Total 2 disk
         * So out of two disk and two possible ways of taking it,
         * there is 1 way of taking blue disk and 1 way of taking red disk.
         *
         * Draw #2 - 1 blue and 2 red - Total 3 disk
         * So out of three disk and three possible ways of taking it,
         * there is 1 way of taking blue disk and 2 way of taking red disk.
         *
         * Combining draw #1 and #2, total combination is
         * Total disk = 2 * 3 = 6
         * total possible ways of taking it = 6
         * No. of ways taking two blue disk = 1
         * No. of ways taking one blue disk = 3
         * No. of ways taking zero blue disk = 2
         *
         * and so on.. continue till draw 15.
         *
         * Linear equation:
         * ===============
         * From Combinatorial method, we can see that
         * draw #1 = b + r
         * draw #2 = b + 2r
         * Combining draw #1 and #2
         * = (b + r) * (b + 2r)
         * = (b^2 + 3br + 2r^2) ....(2)
         *
         * from the equation (2) we can clearly see that
         * No. of ways taking two blue disk = 1 (i.e., 1 b^2)
         * No. of ways taking one blue disk = 3 (i.e., 3 br)
         * No. of ways taking zero blue disk = 2 (i.e., 2 r^2)
         *
         * and so on.. continue till draw 15.
         * Formula : (b + r) * (b + 2r) * (b + 3r) * ...... * (b + 15r)
         *
         */
        long NO_OF_DRAWS = 15L;
        long TOTAL_POSSIBLE_WAYS = NumericHelper.fatorial(NO_OF_DRAWS + 1);

        //init combination for draw one,
        //No of possible ways
        // index 0 (0 blue) - 1 way to have 0 blue (or 1 red)
        // index 1 (1 blue) - 1 way to have 1 blue
        long[] drawCombination = {1L, 1L};

        for(int draw=2; draw<=NO_OF_DRAWS; draw++) {
            long noOfBlueDisk = 1L;
            long noOfRedDisk = (long)draw; //No. of red disk, or zero blue disk

            long[] nextCombination = new long[draw+1];
            for(int x=drawCombination.length-1; x>=0; x--) {
                nextCombination[x+1] += drawCombination[x] * noOfBlueDisk;
                nextCombination[x] += drawCombination[x] * noOfRedDisk;
            }
            System.out.printf("Draw #%d - %s\n", draw, Arrays.toString(nextCombination));
            drawCombination = nextCombination;
        }

        //In n draw match, if the no. of blue disk is greater than n/2 then it is a win
        long totalPossibleWaysToWin = 0;
        for(int blueDiskCount=(int)(NO_OF_DRAWS/2)+1; blueDiskCount<drawCombination.length; blueDiskCount++) {
            totalPossibleWaysToWin += drawCombination[blueDiskCount];
        }

        long answer = TOTAL_POSSIBLE_WAYS /totalPossibleWaysToWin;

        return Long.toString(answer);
    }



    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=121";
    }
}
