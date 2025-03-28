package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0872 extends EulerSolver {

    public JEulerProblem_0872(int problemNumber) {
        super(problemNumber);
    }

    private final BigInteger TWO = BigInteger.valueOf(2);

    /**
     * Given a rootNode r and currNode c, we have to find parent node p of c and traverse till rootNode.
     *
     * Note that:
     * p - c = 2^k where k is the largest such that c + 2^k <= r
     * so to find p, first we have to find k
     * i.e., 2^k <= r - c (root minus current node)
     * i.e., log(r-c) base 2 will give largest k.
     *
     * then p can be found by
     * p = c + 2^k
     *
     */
    @Override
    public String solve() {
        //Test case #1
        if (!solve(BigInteger.valueOf(6), BigInteger.ONE).equals("12")) {
            throw new IllegalStateException("Test case #1 failed");
        }

        //Test case #2
        if (!solve(BigInteger.TEN, BigInteger.valueOf(3)).equals("29")) {
            throw new IllegalStateException("Test case #2 failed");
        }

        BigInteger rootNode = BigInteger.TEN.pow(17);
        BigInteger currNode = BigInteger.valueOf(9).pow(17);
        return solve(rootNode, currNode);
    }

    private String solve(BigInteger rootNode, BigInteger currNode) {
        BigInteger answer = currNode;
        //Traverse from current node to parent node.
        while (currNode.compareTo(rootNode) < 0) {
            currNode = getParentNode(rootNode, currNode);
            answer = answer.add(currNode);
        }
        return answer.toString();
    }

    private BigInteger getParentNode(BigInteger rootNode, BigInteger node) {
        BigInteger diff = rootNode.subtract(node);
        int maxExponent = log2(diff.longValue());
        return node.add(TWO.pow(maxExponent));
    }

    private int log2(long n) {
        return (int)(Math.log(n) / Math.log(2.0));
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=872";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("traversal", "log2", "tree");
    }
}
