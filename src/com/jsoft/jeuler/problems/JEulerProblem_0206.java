package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;

public class JEulerProblem_0206 extends EulerSolver {

    public JEulerProblem_0206(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long ans = 0;
        for(long n=1010101010l; n<1389026623l; n+=10) {
            BigInteger b = BigInteger.valueOf(n);
            BigInteger s = b.pow(2);
            //System.out.println(s.toString());
            if(isInFormat(s.toString())) {
                ans = n;
                break;
            }
        }
        return Long.toString(ans);
    }

    private static BigInteger sqrt(BigInteger x) {
        BigInteger div = BigInteger.ZERO.setBit(x.bitLength()/2);
        BigInteger div2 = div;
        // Loop until we hit the same value twice in a row, or wind
        // up alternating.
        for(;;) {
            BigInteger y = div.add(x.divide(div)).shiftRight(1);
            if (y.equals(div) || y.equals(div2))
                return y;
            div2 = div;
            div = y;
        }
    }

    private boolean isInFormat(String s) {
        char[] ch = s.toCharArray();
        if(ch[0] != '1') return false;
        if(ch[2] != '2') return false;
        if(ch[4] != '3') return false;
        if(ch[6] != '4') return false;
        if(ch[8] != '5') return false;
        if(ch[10] != '6') return false;
        if(ch[12] != '7') return false;
        if(ch[14] != '8') return false;
        if(ch[16] != '9') return false;
        if(ch[18] != '0') return false;
        return true;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=112";
    }
}
