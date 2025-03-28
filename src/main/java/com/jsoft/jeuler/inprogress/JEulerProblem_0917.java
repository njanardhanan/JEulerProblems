package com.jsoft.jeuler.inprogress;

import com.jsoft.jeuler.prime.PrimeIndexGenerator;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class JEulerProblem_0917 extends EulerSolver {

    class Data implements Comparable<Data> {
        long cost;
        int distance;

        Data(int distance, long cost) {
            this.distance = distance;
            this.cost = cost;
        }

        private double averageCost() {
            return (cost * 1.0) / (distance * 1.0);
        }

        @Override
        public int compareTo(Data o) {
            return Double.compare(o.averageCost(), averageCost());
        }
    }

    public JEulerProblem_0917(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
//        System.out.println(Long.MAX_VALUE - 49974662890730L);
//        if (minPathSum(2) != 2388327490L) {
//            throw new IllegalStateException("Test case #1 failed");
//        }
//        if (minPathSum(10) != 13389278727L) {
//            throw new IllegalStateException("Test case #2 failed");
//        }

        //int N = 10000000;
        int N = 10000;
        //500 -  [38R, 103D, 24R, 161D, 223R, 232D, 213R, 3D, 1R]
        //1000 - [38R, 103D, 24R, 161D, 223R, 582D, 682R, 143D, 32R, 10D]
        //2000 - [38R, 103D, 24R, 161D, 223R, 740D, 911R, 537D, 791R, 455D, 10R, 3D, 2R]
        //3000 - [38R, 103D, 24R, 161D, 223R, 740D, 911R, 1973D, 1772R, 18D, 28R, 4D, 3R]
        //4000 - [38R, 103D, 24R, 161D, 223R, 740D, 911R, 537D, 2784R, 2451D, 19R, 7D]
        //5000 - [38R, 103D, 24R, 161D, 223R, 740D, 911R, 537D, 3442R, 2728D, 275R, 681D, 75R, 46D, 10R, 2D, 1R, 1D]
       //10000 - [38R, 103D, 24R, 161D, 223R, 740D, 911R, 537D, 7296R, 7160D, 889R, 873D, 540R, 165D, 30R, 239D, 34R, 13D, 11R, 7D, 3R, 1D]
       //50000 - [38R, 103D, 24R, 161D, 223R, 740D, 911R, 537D, 12490R, 26739D, 34206R, 21490D, 2032R, 210D, 61R, 14D, 11R, 5D, 3R]


        //10^4  time:1141   ans:9995502224219
        //10^5  time:136274 ans:99857804701190

        long ans = minPathSum(N);
        return Long.toString(ans);
    }

    private static final BigInteger MODULO = BigInteger.valueOf(998388889);
    private static final BigInteger TWO = BigInteger.valueOf(2);

    // Memoization map to store already computed values of s_n
    private static Map<Integer, Long> memo = new HashMap<>();

    // Function to generate s_n sequence with memoization
    public long generateS(int n) {
        // If the value of s_n is already computed, return it
        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        long s = 102022661;
        if (n > 1) {
            long prevS = generateS(n - 1);
            s = BigInteger.valueOf(prevS).modPow(TWO, MODULO).longValue();  // s_n = (s_{n-1}^2 mod 998388889)
        }

        // Store the computed value in the memoization map
        memo.put(n, s);
        return s;
    }

    public long getGridValue(int i, int j) {
        return generateS(2 * i + 1) + generateS(2 * j + 2);
    }


    public long minPathSum(int N) {
        long[] dp = new long[N]; // Rolling array for minimal path sums
        char[][] path = new char[N][N];
        for (char[] row : path) Arrays.fill(row, '.');

        for (int i = 0; i < N; i++) {
            if (i%1000 == 0) {
                System.out.printf("Reached : %d\n", i);
            }
            for (int j = 0; j < N; j++) {
                if (i == 0 && j == 0) {
                    dp[j] = getGridValue(i, j); // Starting cell
                    path[i][j] = 'L'; //We came from left to i,j cell.
                } else if (i == 0) {
                    dp[j] = dp[j - 1] + getGridValue(i, j); // First row
                    path[i][j] = 'L'; //We came from left to i,j cell.
                } else if (j == 0) {
                    dp[j] = dp[j] + getGridValue(i, j); // First column
                    path[i][j] = 'U'; //We came from Up to i,j cell.
                } else {
                    if (dp[j] < dp[j - 1]) {
                        path[i][j] = 'U';
                    } else {
                        path[i][j] = 'L';
                    }
                    dp[j] = Math.min(dp[j], dp[j - 1]) + getGridValue(i, j); // General case
                }
            }
        }

        //Backtrack
        int upCount = 0;
        int leftCount = 0;
        List<String> pathCount = new ArrayList<>();
        int x = N - 1, y = N - 1;
        while (x != 0 || y != 0) {
            if (x > 0 && path[x][y] == 'U') {
                path[x][y] = '@';
                x--; // Move up
                if (leftCount != 0) {
                    pathCount.add(leftCount + "R");
                    leftCount = 0;
                }
                upCount++;
            } else if (y > 0 && path[x][y] == 'L') {
                path[x][y] = '@';
                y--; // Move left
                if (upCount != 0) {
                    pathCount.add(upCount + "D");
                    upCount = 0;
                }
                leftCount++;
            }
        }
        path[0][0] = '@';

        if (leftCount != 0) {
            //leftCount++;
            pathCount.add(leftCount + "R");
        }
        if (upCount != 0) {
            //upCount++;
            pathCount.add(upCount + "D");
        }
        Collections.reverse(pathCount);
        System.out.println(pathCount);

//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                if (path[i][j] != '@' ) {
//                    path[i][j] = '_';
//                    //System.out.print('_');
//                }
//                System.out.print(path[i][j]);
//            }
//            System.out.println();
//        }

        return dp[N - 1]; // Minimal path sum to the bottom-right corner
    }

    @Override
    public String getProblemStatement() {
        return "This is a template file";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList();
    }
}
