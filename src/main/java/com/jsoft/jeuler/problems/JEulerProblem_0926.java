package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0926 extends EulerSolver {

    public JEulerProblem_0926(int problemNumber) {
        super(problemNumber);
    }

    private static final long MOD = 1_000_000_007;

    /**
     * The prime factors for a factorial can be found using
     * <a href="https://en.wikipedia.org/wiki/Legendre%27s_formula#Example">Legendre's formula</a>
     *
     * The number of zeros for a number n in base b is equal to maximum value of k
     * when b^k divides n.
     *
     * For example:
     * The prime factors of 10! = 2^8 * 3^4 * 5^2 * 7^1
     * if you see 2^8 divides 10!, and 8 is the highest such exponent for prime 2
     * so there will be 8 zeros when 10! is represented in base 2
     * Similarly, there will be 4 zeros when 10! is represented in base 3
     * and there will be 2 zeros when 10! is represented in base 5, and so on.
     *
     * Also, we can note that all divisors of 10! will have atleast 1 zeros in its base.
     * Also note that, 8 (highest exponent in prime factor) is the maximum number of zeros, and 1 (lowest exponent) being the least.
     *
     * Now we need to calculate how many base b will have 8 zeros, 7 zeros, and upto 1 zeros, and the sum will give the answer.
     *
     * 8 zeros - There is only one number that will have 8 zeros which is base 2.
     * 7 zeros - None because, there is no number below 10! with exponent 7 divides 10!
     * 6 zeros - None
     * 5 zeros - None
     * 4 zeros - 4 => (2^4, 3^4, (2^2 * 3)^4, (2*3)^4
     * 3 zeros - None
     * 2 zeros - I will leave this to your exercise.
     * 1 zeros - Equal to number of divisors minus the above calculated numbers.
     *
     * The number of divisors can be calculated using <a href="https://en.wikipedia.org/wiki/Divisor_function">Divisor_function</a>
     * i.e., for 10! = (8+1) * (4+1) * (2+1) * (1+1) = 270
     *
     * Similarly, to find number of bases with 8 zeros, take only exponents with 8
     * {2^8} = (1+1) => This will include number 1 (divisors) along with base 2 which we need to remove.
     *
     * to find number of bases with  4 zeros, take only exponents with 4 or above
     * [{2^8, 2^4}, {3^4}] = (2+1) * (1+1) = 6 => This will include number 1 (divisors) along with
     * number of bases with 8 zeros, number of bases with 4 zeros and we need to remove 1 and number of bases with 8 zeros.
     *
     * Therefore:
     * If R(n) is number of zeros for base n, then
     * R(8) = 1
     * R(7) = R(8) + 0 + 1 => 0 is number of bases with 7 zeros.
     * R(6) = R(8) + R(7) + 0 + 1 => 0 is number of bases with 6 zeros.
     *
     * @return
     */
    @Override
    public String solve() {
        int testInputValue=10;
        if (calculateRoundness(testInputValue) != 312L) {
            throw new IllegalArgumentException("constructor argument is null");
        }
        int inputValue = 10000000;
        long roundness = calculateRoundness(inputValue);
        return Long.toString(roundness);
    }

    public long calculateRoundness(int n) {
        List<Long> l = getPrimeFactorsForFactorialAsList(n);
        long ans = 0;
        //for (int k = 1; k <= l.get(0); k++) {
        for (long k = l.get(0); k >=1 ; k--) {
            long x = 1;
            for (long e : l) {
                if (e < k) {
                    break;
                }
                x *= (e / k + 1);
                x %= MOD;
            }
            ans += x - 1;
            ans %= MOD;
        }

        return ans;
    }

    private List<Long> getPrimeFactorsForFactorialAsList(int n) {
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(n);
        List<Long> primeFactors = new ArrayList<>();
        for (long prime : primes) {
            long x = n;
            long freq = 0;

            while ( x/prime > 0 ) {
                freq += x / prime;
                x = x / prime;
            }
            primeFactors.add(freq);
            //System.out.printf("%d^%d\n", prime, freq );
        }
        return primeFactors;
    }
    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=926" +
                "A <strong>round number</strong> is a number that ends with one or more zeros in a given base.</p>\n" +
                "Let us define the <dfn>roundness</dfn> of a number $n$ in base $b$ as the number of zeros at the end of the base $b$ representation of $n$.<br>\n" +
                "For example, $20$ has roundness $2$ in base $2$, because the base $2$ representation of $20$ is $10100$, which ends with $2$ zeros.</p>\n" +
                "\n" +
                "Also define $R(n)$, the <dfn>total roundness</dfn> of a number $n$, as the sum of the roundness of $n$ in base $b$ for all $b &gt; 1$.<br>\n" +
                "For example, $20$ has roundness $2$ in base $2$ and roundness $1$ in base $4$, $5$, $10$, $20$, hence we get $R(20)=6$.<br>\n" +
                "You are also given $R(10!) = 312$.</p>\n" +
                "Find $R(10\\,000\\,000!)$. Give your answer modulo $10^9 + 7$.</p>";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("divisors", "Divisor function", "Legendre's formula");
    }
}
