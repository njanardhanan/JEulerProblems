package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class JEulerProblem_0463 extends EulerSolver {

    public JEulerProblem_0463(int problemNumber) {
        super(problemNumber);
    }

    Map<BigInteger, BigInteger> memoBig = new HashMap<>();
    private final BigInteger BIG_MOD = BigInteger.TEN.pow(9);
    private final BigInteger ZERO = BigInteger.ZERO;
    private final BigInteger ONE = BigInteger.ONE;
    private final BigInteger TWO = BigInteger.valueOf(2);
    private final BigInteger THREE = BigInteger.valueOf(3);
    private final BigInteger FOUR = BigInteger.valueOf(4);
    private final BigInteger FIVE = BigInteger.valueOf(5);
    private final BigInteger SIX = BigInteger.valueOf(6);
    private final BigInteger EIGHT = BigInteger.valueOf(8);

    @Override
    public String solve() {
        /**
         * https://oeis.org/A239447
         *
         */

        BigInteger ans = S(BigInteger.valueOf(3).pow(37)).mod(BIG_MOD);

        //808981553
        return ans.toString();
    }

    private BigInteger S(BigInteger n) {
        /**
         * a(4n) = 6 a(2n) - 5 a(n) - 3 a(n-1) - 1.
         *
         * a(4n + 1) = 2 a(2n+1) + 4 a(2n) - 6 a(n) - 2 a(n-1) - 1.
         *
         * a(4n + 2) = 3 a(2n+1) + 3 a(2n) - 6 a(n) - 2 a(n-1) - 1.
         *
         * a(4n + 3) = 6 a(2n+1) - 8 a(n) - 1.
         */

        if (isEqual(n, ZERO)) {
            return ZERO;
        }

        if (isEqual(n, ONE)) {
            return ONE;
        }

        if (isEqual(n, TWO)) {
            return TWO;
        }

        if (isEqual(n, THREE)) {
            return FIVE;
        }

        if (memoBig.containsKey(n)) {
            return memoBig.get(n);
        }

        BigInteger mod4 = n.mod(FOUR);

        if (isEqual(mod4, ZERO)) {
            BigInteger m = n.divide(FOUR);

            //a(4n) = 6 a(2n) - 5 a(n) - 3 a(n-1) - 1.
            BigInteger x1 = SIX.multiply(S(TWO.multiply(m)));
            BigInteger x2 = FIVE.multiply(S(m));
            BigInteger x3 = THREE.multiply(S(m.subtract(ONE)));
            BigInteger r = x1.subtract(x2).subtract(x3).subtract(ONE);
            memoBig.put(n, r);
            return r;
        }

        if (isEqual(mod4, ONE)) {
            BigInteger m = n.subtract(ONE).divide(FOUR);

            //a(4n + 1) = 2 a(2n+1) + 4 a(2n) - 6 a(n) - 2 a(n-1) - 1.
            BigInteger x1 = TWO.multiply(S(TWO.multiply(m).add(ONE)));
            BigInteger x2 = FOUR.multiply(S(TWO.multiply(m)));
            BigInteger x3 = SIX.multiply(S(m));
            BigInteger x4 = TWO.multiply(S(m.subtract(ONE)));
            BigInteger r = x1.add(x2).subtract(x3).subtract(x4).subtract(ONE);
            memoBig.put(n, r);
            return r;
        }

        if (isEqual(mod4, TWO)) {
            BigInteger m = n.subtract(TWO).divide(FOUR);

            //a(4n + 2) = 3 a(2n+1) + 3 a(2n) - 6 a(n) - 2 a(n-1) - 1.
            BigInteger x1 = THREE.multiply(S(TWO.multiply(m).add(ONE)));
            BigInteger x2 = THREE.multiply(S(TWO.multiply(m)));
            BigInteger x3 = SIX.multiply(S(m));
            BigInteger x4 = TWO.multiply(S(m.subtract(ONE)));
            BigInteger r = x1.add(x2).subtract(x3).subtract(x4).subtract(ONE);

            memoBig.put(n, r);
            return r;
        }

        if (isEqual(mod4, THREE)) {
            BigInteger m = n.subtract(THREE).divide(FOUR);

            //a(4n + 3) = 6 a(2n+1) - 8 a(n) - 1.
            BigInteger x1 = SIX.multiply(S(TWO.multiply(m).add(ONE)));
            BigInteger x2 = EIGHT.multiply(S(m));
            BigInteger r = x1.subtract(x2).subtract(ONE);
            memoBig.put(n, r);
            return r;
        }

        return BigInteger.ZERO;

    }

    private boolean isEqual(BigInteger x, BigInteger y) {
        return x.compareTo(y) == 0;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=463";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("bigInteger", "oeis", "A239447");
    }
}
