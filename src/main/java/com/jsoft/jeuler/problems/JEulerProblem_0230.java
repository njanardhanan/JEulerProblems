package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.List;

public class JEulerProblem_0230 extends EulerSolver {

    public JEulerProblem_0230(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int FIBO_LIMIT = 100;

        String stringA = "1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679";
        String stringB = "8214808651328230664709384460955058223172535940812848111745028410270193852110555964462294895493038196";

        BigInteger[] fibo = new BigInteger[FIBO_LIMIT];
        fibo[0] = BigInteger.valueOf(100);
        fibo[1] = BigInteger.valueOf(100);
        int i = 2;
        for(int j=0; j<FIBO_LIMIT-2; j++) {
            fibo[i] = fibo[i-2].add(fibo[i-1]);
            i++;
        }

        BigInteger ans = BigInteger.ZERO;
        //Find ∑n = 0,1,...,17   10n× DA,B((127+19n)×7n) .
        for(i=17; i>=0; i--) {
            BigInteger currentN = Dn(i);

            int j=0;
            while(fibo[j].compareTo(currentN) < 0) {
                j++;
            }

            System.out.printf("%d - %s - %s - %d\n", i, currentN.toString(), fibo[j].toString(), j+1);

            //Backtrack till either fibo index 0 or 1
            while(j>1) {
                if(fibo[j-2].compareTo(currentN) < 0) {
                    currentN = currentN.subtract(fibo[j-2]);
                    j--;
                } else {
                    j-=2;
                }
            }


            if((j==0 || j==1) && currentN.longValue() <= 100l) {
                int v;
                if(j==0) {
                    v = Character.getNumericValue(stringA.charAt(currentN.intValue()-1));
                } else {
                    v = Character.getNumericValue(stringB.charAt(currentN.intValue()-1));
                }
                ans = ans.multiply(BigInteger.valueOf(10)).add(BigInteger.valueOf(v));
                System.out.printf("%dth char in string %d, which is %s\n",
                        currentN.longValue(),
                        j+1,
                        (j==0) ? stringA.charAt(currentN.intValue()-1) : stringB.charAt(currentN.intValue()-1));
                System.out.println();
            }

        }

        return ans.toString();
    }

    private BigInteger Dn(int n) {
        BigInteger y = BigInteger.valueOf(127).add(BigInteger.valueOf(19).multiply(BigInteger.valueOf(n)));
        BigInteger z = y.multiply(BigInteger.valueOf(7).pow(n));
        return z;
    }


    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=230";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
