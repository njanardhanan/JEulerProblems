package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class JEulerProblem_0823 extends EulerSolver {

    public JEulerProblem_0823(int problemNumber) {
        super(problemNumber);
    }

    private BigInteger MOD = BigInteger.valueOf(1234567891L);

    @Override
    public String solve() {
        /**
         * Method:
         * 1. convert each number to list of prime factors with multiplicity and sort it in descending order.
         * Ex : [2, 3, 4, 5, 6, 7, 8, 9, 10] - convert this as follows
         *      [
         *          [2],
         *          [3],
         *          [2,2],
         *          [5],
         *          [3,2],
         *          [7],
         *          [2,2,2],
         *          [3,3],
         *          [5,2]
         *       ]
         *
         * 2. Sort the list of list by its length in descending order.
         *  Ex : [
         *          [2,2,2],
         *          [2,2],
         *          [3,2],
         *          [3,3],
         *          [5,2],
         *          [2],
         *          [3],
         *          [5],
         *          [7]
         *       ]
         *
         *  3. Now, to calculate the next round, just pick the last item from each list
         *  and form a new list.
         *  Ex : [
         *          [7,5,3,3,2,2,2,2,2]
         *          [2,2],
         *          [2],
         *          [3],
         *          [3],
         *          [5]
         *       ]
         *
         *  4. If you keep on do this for each round, at some time this will for a triangle.
         *     you may need to append some dummy value (I used n+1 in this problem) to visualize the triangle.
         *
         *     Ex: for n=10 - the triangle will be formed after 9 rounds.
         *     Round : 9
         *     5 2 2 2 2
         *     5 3 2 2
         *     3 3 2
         *     3 2
         *     7
         *
         *  5. Now, once the triangle is formed, the next round will be simply rotating the column.
         *  6. Fast forward to the desired round by rotating the column. I rotated by transposing the
         *     rows to columns for easy understanding and visualization.
         *  7. Calculate the sum, remember to skip the dummy number.
         *
         */
        int n = 10000;
        long m = 10_000_000_000_000_000L;
        //int n = 10;
        //long m = 100;
        LinkedList<LinkedList<Integer>> factorList = new LinkedList<>();

        int k =0;
        int count = 0;
        for(int i=2; i<=n; i++) {

            LinkedList<Integer> factors = new LinkedList<>();
            int t = i;
            while(t % 2 == 0) {
                t /= 2;
                factors.add(2);
            }
            for (int x=3; x<=n; x+=2) {
                while(t % x == 0) {
                    t /= x;
                    factors.add(x);
                }
            }
            if (t > 2) {
                factors.add(t);
            }
            count += factors.size();

            //Triangle number -  to calculate no. of rows in triangle.
            //This will help us to calculate how many dummy numbers to add.
            while (k*(k+1)/2 < count) {
                k++;
            }

            factors = sort(factors);

            factorList.add(factors);
        }
        System.out.printf("Count : %d, K : %d, Triangle : %d\n", count, k, k*(k+1)/2);

        LinkedList<Integer> factors = new LinkedList<>();
        for(int i=count; i<(k*(k+1)/2); i++) {
            factors.add(n+1);
        }
        if (factors.size() > 0) {
            factorList.add(factors);
        }

        factorList = factorList.stream().sorted((x,y) -> y.size() - x.size()).collect(Collectors.toCollection(LinkedList::new));

        //printData(factorList);

        int triangleFormedAtRound = 0;
        for (int i=1; i<=m; i++) {
            nextRound(factorList);
            //System.out.println("Round : " + i);
            //printData(factorList);
            if (isTriangle(factorList)) {
                triangleFormedAtRound = i;
                break;
            }
        }
        System.out.printf("Triangle formed at %d, remaining rounds %d\n", triangleFormedAtRound, m-triangleFormedAtRound);
        factorList = transpose(factorList);
        //printData(factorList);
        rotate(factorList, m-triangleFormedAtRound);
        factorList = transpose(factorList);
        //printData(factorList);
        long ans = sumItUp(factorList, n+1);

        return Long.toString(ans);
    }

    private long sumItUp(LinkedList<LinkedList<Integer>> factorList, int ignoreNumber) {
        BigInteger ans = BigInteger.ZERO;
        for(LinkedList<Integer> list : factorList) {
            BigInteger tmp = BigInteger.ONE;
            boolean hasValue = false;
            for(int n : list) {
                if (n == ignoreNumber) continue;
                hasValue = true;
                tmp = tmp.multiply(BigInteger.valueOf(n)).mod(MOD);
            }
            if (!hasValue) {
                continue;
            }
            ans = ans.add(tmp);
        }
        return ans.mod(MOD).longValue();
    }

    private void nextRound(LinkedList<LinkedList<Integer>> factorList) {
        ListIterator<LinkedList<Integer>> iterator = factorList.listIterator();
        LinkedList<Integer> product = new LinkedList<>();
        while(iterator.hasNext()) {
            LinkedList<Integer> list = iterator.next();
            int p = list.pollLast();
            product.add(p);
            if (list.size() == 0) {
                iterator.remove();
            }
        }
        product = sort(product);
        factorList.addFirst(product);
    }

    private void rotate(LinkedList<LinkedList<Integer>> factorList, long m) {
        for(LinkedList<Integer> list : factorList) {
            long times;
            if (m < list.size()) {
                times = m;
            } else {
                times = (m% list.size());
            }
            for (long i=0; i<times; i++) {
                list.addFirst(list.pollLast());
            }
        }
    }

    private boolean isTriangle(LinkedList<LinkedList<Integer>> factorList) {
        int size = factorList.getFirst().size();
        for(int i=1; i<factorList.size(); i++) {
            int s = factorList.get(i).size();
            if (size != s+1) {
                return false;
            }
            size = s;
        }
        return true;
    }

    /**
     * Transpose rows into columns and vice versa
     */
    private LinkedList<LinkedList<Integer>> transpose(LinkedList<LinkedList<Integer>> factorList) {
        LinkedList<LinkedList<Integer>> ret = new LinkedList<>();
        int size = factorList.getFirst().size();
        for (int x=0; x<size; x++) {
            LinkedList<Integer> col = new LinkedList<>();
            for(LinkedList<Integer> list : factorList) {
                if (x < list.size()) {
                    col.add(list.get(x));
                }
            }
            ret.add(col);
        }
        return ret;
    }

    private LinkedList<Integer> sort(LinkedList<Integer> list) {
        return list.stream().sorted((x,y) -> y - x).collect(Collectors.toCollection(LinkedList::new));
    }

    private void printData(LinkedList<LinkedList<Integer>> factorList) {
        for(LinkedList<Integer> list : factorList) {
            for(int x : list) {
                System.out.printf("%d ", x);
            }
            System.out.println();
        }
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=823" +
                "<p>A list initially contains the numbers $2, 3, \\dots, n$.<br />\n" +
                "At each round, every number in the list is divided by its smallest prime factor. Then the product of these smallest prime factors is added to the list as a new number. Finally, all numbers that become $1$ are removed from the list.</p>\n" +
                "\n" +
                "<p>For example, below are the first three rounds for $n = 5$:\n" +
                "$$[2, 3, 4, 5] \\xrightarrow{(1)} [2, 60] \\xrightarrow{(2)} [30, 4] \\xrightarrow{(3)} [15, 2, 4].$$\n" +
                "Let $S(n, m)$ be the sum of all numbers in the list after $m$ rounds.<br />\n" +
                "For example, $S(5, 3) = 15 + 2 + 4 = 21$. Also $S(10, 100) = 257$.</p>\n" +
                "\n" +
                "<p>Find $S(10^4, 10^{16})$. Give your answer modulo $1234567891$.</p>";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("linked-list", "prime", "factors");
    }
}
