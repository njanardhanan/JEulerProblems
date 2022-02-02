package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.Permutations;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0041 extends EulerSolver {

    public JEulerProblem_0041(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long output = 0;
        // 9 digits and 8 digits are divisible by 3
        // since 9+8+7+6+5+4+3+2+1 = 45 which is divisible by 3
        // and 8+7+6+5+4+3+2+1 = 36 which is divisible by 3
        List<Integer> items = Arrays.asList(7, 6, 5, 4, 3, 2, 1);
        long totalPermutation = NumericHelper.fatorial(items.size());
        for(long i=0; i<totalPermutation; i++) {
            List<Integer> nextPermutation = Permutations.permutation(i, items);
            int lastDigit = nextPermutation.get(items.size()-1);
            if(lastDigit % 2 == 0 || lastDigit % 5 == 0) {
                continue;
            }

            if(PrimeNumberHelper.checkPrime(getInt(nextPermutation))) {
                output =  getInt(nextPermutation);
                break;
            }

        }
        return Long.toString(output);
    }

    public long getInt(List<Integer> digits) {
        long result = 0;
        long x = 0;
        for(int i=digits.size()-1; i>=0; i--) {
            result = result + (long)( digits.get(i) * Math.pow(10, x++));
        }
        return result;
    }

    @Override
    public String getProblemStatement() {
        return "What is the largest n-digit pandigital prime that exists?";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
