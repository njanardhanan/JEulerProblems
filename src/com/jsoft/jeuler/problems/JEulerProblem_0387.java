package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0387 extends EulerSolver {

    public JEulerProblem_0387(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        System.out.println("NOT SOLVED YET");
        long LIMIT = (long)Math.pow(10, 8);
        long currPrime = 181;
        long ans = 0;
        while(currPrime <= LIMIT) {
            long n = currPrime/10;
            if(NumericHelper.isHarshadNumber(n) &&
                    PrimeNumberHelper.isPrime(n/NumericHelper.sumOfDigits(n))) {
                while(n > 0) {
                    if(NumericHelper.isHarshadNumber(n/10)) {
                        n = n/10;
                    } else {
                        break;
                    }
                }
                if(n > 0 && n <=9) {
                    System.out.println(currPrime);
                    ans += currPrime;
                }
            }
            //do {
                currPrime = PrimeNumberHelper.nextPrime(currPrime);
            //} while(currPrime > 99 && !isAllDigitsEven(currPrime/100));
        }
        return Long.toString(ans);
        //10^8 => 130459097  => time taken 426211 ms.
    }

    private boolean isAllDigitsEven(long n) {
        while(n>0) {
            long r = n%10;
            if(r%2 != 0) return false;
            n = n/10;
        }
        return true;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=387\n\n" +
                "<p>A <b>Harshad or Niven number</b> is a number that is divisible by the sum of its digits.\n" +
                "<br />201 is a Harshad number because it is divisible by 3 (the sum of its digits.)\n" +
                "<br />When we truncate the last digit from 201, we get 20, which is a Harshad number.\n" +
                "<br />When we truncate the last digit from 20, we get 2, which is also a Harshad number.\n" +
                "<br />Let's call a Harshad number that, while recursively truncating the last digit, always results in a Harshad number a <i>right truncatable Harshad number.</i></p>  \n" +
                "\n" +
                "<p>Also:\n" +
                "<br />201/3=67 which is prime.\n" +
                "<br />Let's call a Harshad number that, when divided by the sum of its digits, results in a prime a <i>strong Harshad number</i>.</p>\n" +
                "\n" +
                "<p>Now take the number 2011 which is prime.\n" +
                "<br />When we truncate the last digit from it we get 201, a strong Harshad number that is also right truncatable.\n" +
                "<br />Let's call such primes <i>strong, right truncatable Harshad primes</i>.</p>\n" +
                "\n" +
                "<p>You are given that the sum of the strong, right truncatable Harshad primes less than 10000 is 90619.</p>\n" +
                "\n" +
                "<p>Find the sum of the strong, right truncatable Harshad primes less than 10<sup>14</sup>.</p>";
    }
}
