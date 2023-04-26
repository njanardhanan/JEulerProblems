package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JEulerProblem_0833 extends EulerSolver {

    public JEulerProblem_0833(int problemNumber) {
        super(problemNumber);
    }

    BigInteger LIMIT = BigInteger.TEN.pow(35);
    BigInteger ZERO = BigInteger.ZERO;
    BigInteger ONE = BigInteger.ONE;
    BigInteger TWO = BigInteger.valueOf(2);
    BigInteger FOUR = BigInteger.valueOf(4);
    BigInteger MOD = BigInteger.valueOf(136101521);

    @Override
    public String solve() {
        /**
         *   https://oeis.org/A322699 - this gives the formula to generate b for a given a.
         *   i.e., T(a).T(b) will be a perfect square if the numbers are from the same row.
         *   Refer the table in https://oeis.org/A322699.
         *
         *   //A(n,0) = 0, A(n,1) = n and A(n,k) = (4*n+2) * A(n,k-1) - A(n,k-2) + 2*n for k > 1.
         *
         *   The above formula is sufficient, but the limit 10^35 is too large to iterate.
         *   so we need a smarter way.
         *
         *   https://oeis.org/A083374 - This gives the summation of c = SQRT( T(a) * T(b) )
         *   but for the first column and second column in https://oeis.org/A322699 table.
         *   i.e., if a=first column (1,2,3....) and b=second column(8,24,48...)
         *
         *   So to optimize for large limit, we can use two binary search.
         *   Binary search #1 - To find the Max a, such that SQRT( T(a+1) * T(b) ) exceed the limit.
         *   Binary search #2 - To find the Max a with two solution, such that there exist only two 'b' solution.
         *
         *   What this mean is, the number between Max a, and Max a with two solution will have only one solution
         *   which can be calculated quickly using the summation of c.
         *
         */
        Set<Long> processedSet = new HashSet<>();

        BigInteger sum = BigInteger.ZERO;

        //Use binary search to find Max a, and fine tune it.
        long upperBound = fineTuneMax(findMaxA(LIMIT)).longValue();

        //Use binary search to find Max a with two solution.
        long lowerBound = findMaxAWithTwoSolution(LIMIT, upperBound).longValue();
        System.out.println("Found MaxA : " + upperBound);
        System.out.println("Found MaxA with two solution : " + lowerBound);

        BigInteger uBound = BigInteger.valueOf(upperBound);
        for (long i=1; i<=upperBound; i++) {
            if (i % 10000000 == 0) {
                System.out.printf("Reached %d - remaining %d\n", i, lowerBound-i);
            }

            BigInteger n = BigInteger.valueOf(i);
            BigInteger prevPrev = ZERO;
            BigInteger prev = BigInteger.valueOf(i);
            //Optimization.
            if (processedSet.contains(i)) {
                processedSet.remove(i);
                continue;
            }

            List<BigInteger> list = new ArrayList<>();
            boolean didAddedSum = true;
            for (int j = 1; j <=50; j++) {
                BigInteger curr = getNextValue(n, prev, prevPrev);
                list.add(curr);
                prevPrev = prev;
                prev = curr;

                BigInteger c = isSquareRoot(n, prev);
                if (c.compareTo(LIMIT) <= 0) {
                    if (list.size() >= 2) {
                        //System.out.printf("%d(%d), ", c, prev);
                        sum = sum.add(c.mod(MOD));
                    }
                    sum = sum.mod(MOD);
                    didAddedSum = false;
                } else {
                    break;
                }
            }

            //No need to process if the list size 2
            //we will add these value using summation logic (read comment again).
            if (list.size() == 2) {
                break;
            }


            for (int k=0; k<list.size()-1; k++) {
                List<BigInteger> newList = list.subList(k + 1, list.size());
                BigInteger newVal = list.get(k);
                if (uBound.compareTo(newVal) > 0) {
                    processedSet.add(newVal.longValue());
                }

                boolean flagAdded = false;
                for (BigInteger b : newList) {
                    BigInteger c = isSquareRoot(newVal, b);
                    if (c.compareTo(LIMIT) <= 0) {
                        sum = sum.add(c.mod(MOD));
                        flagAdded = true;
                        sum = sum.mod(MOD);
                    } else {
                        break;
                    }
                }
                //This is to remove the duplicate values that overlap summation calculation.
                if (flagAdded && newVal.compareTo(uBound) <= 0) {
                    BigInteger curr = getNextValue(newVal, newVal, BigInteger.ZERO);
                    BigInteger c = isSquareRoot(newVal, curr);
                    sum = sum.subtract(c.mod(MOD));
                    sum = sum.mod(MOD);
                }
            }

            if (didAddedSum) {
                break;
            }

        }

        BigInteger offSetValue = getSummationValue(upperBound + 1);
        System.out.println("Sum so far " +  sum);
        System.out.println("offset so far " +  offSetValue);
        sum = sum.add(offSetValue.mod(MOD));
        sum = sum.mod(MOD);


        //43884302

        return sum.toString();
    }


    //https://oeis.org/A083374
    private BigInteger getSummationValue(long max) {
        BigInteger maxSquare = BigInteger.valueOf(max).pow(2);
        BigInteger maxValue = maxSquare.multiply(maxSquare.subtract(ONE)).divide(TWO);
        System.out.println("Total MaxValue : " + maxValue);
        return maxValue;
    }

    private BigInteger findMaxA(BigInteger limit) {
        BigInteger currMin = BigInteger.ONE;
        BigInteger currMax = limit.add(BigInteger.ZERO);
        BigInteger currMid;
        while (currMax.subtract(currMin).compareTo(BigInteger.valueOf(1)) > 0) {
            currMid = currMin.add(currMax).divide(TWO);

            BigInteger minB = getNextValue(currMin, currMin, BigInteger.ZERO);
            BigInteger minC = isSquareRoot(currMin, minB);

            BigInteger midB = getNextValue(currMid, currMid, BigInteger.ZERO);
            BigInteger midC = isSquareRoot(currMid, midB);

            if (limit.compareTo(minC) > 0 && limit.compareTo(midC) < 0) {
                currMax = currMid;
            } else if (limit.compareTo(minC) > 0 && limit.compareTo(midC) > 0) {
                currMin = currMid;
            }
        }

        return currMax;

    }

    private BigInteger fineTuneMax(BigInteger n) {
        do {
            BigInteger x = getNextValue(n, n, BigInteger.ZERO);
            BigInteger val = isSquareRoot(n, x);

            if (LIMIT.compareTo(val) >= 0) {
                break;
            } else {
                n = n.subtract(ONE);
            }

        } while(true);
        return  n;
    }

    private BigInteger findMaxAWithTwoSolution(BigInteger limit, long upperBound) {
        BigInteger currMin = BigInteger.ONE;
        BigInteger currMax = BigInteger.valueOf(upperBound);
        BigInteger currMid;
        while (currMax.subtract(currMin).compareTo(BigInteger.valueOf(2)) >= 0) {
            currMid = currMin.add(currMax).divide(TWO);

            BigInteger minB1 = getNextValue(currMin, currMin, BigInteger.ZERO);
            BigInteger minB2 = getNextValue(currMin, minB1, currMin);

            BigInteger minC = isSquareRoot(currMin, minB2);

            BigInteger midB1 = getNextValue(currMid, currMid, BigInteger.ZERO);
            BigInteger midB2 = getNextValue(currMid, midB1, currMid);
            BigInteger midC = isSquareRoot(currMid, midB2);

            if (limit.compareTo(minC) > 0 && limit.compareTo(midC) < 0) {
                currMax = currMid;
            } else if (limit.compareTo(minC) > 0 && limit.compareTo(midC) > 0) {
                currMin = currMid;
            }
        }

        return currMax;

    }

    private BigInteger isSquareRoot(BigInteger n, BigInteger m) {
        BigInteger f = n.multiply(n.add(ONE)).divide(TWO);
        BigInteger s = m.multiply(m.add(ONE)).divide(TWO);
        BigInteger v = f.multiply(s);
        return sqrt(v);
    }

    //https://oeis.org/A322699
    private BigInteger getNextValue(BigInteger n, BigInteger prev, BigInteger prevPrev) {
        //A(n,0) = 0, A(n,1) = n and A(n,k) = (4*n+2) * A(n,k-1) - A(n,k-2) + 2*n for k > 1.
        return FOUR.multiply(n).add(TWO).multiply(prev).subtract(prevPrev).add(TWO.multiply(n));
    }

    public BigInteger sqrt(BigInteger x) {
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

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=833";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("triangle number", "OEIS", "summation", "top100");
    }
}
