package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0387 extends EulerSolver {

    public JEulerProblem_0387(int problemNumber) {
        super(problemNumber);
    }

    private static final long LIMIT = 14;

    @Override
    public String solve() {
        //Collect all two digits harshad numbers.
        //Two digit harshad numbers are always right truncatable
        List<Long> twoDigitRightTruncHarshadNumber = new ArrayList<>();
        for (long i=10; i<100; i++) {
            if (NumericHelper.isHarshadNumber(i)) {
                twoDigitRightTruncHarshadNumber.add(i);
            }
        }
        //System.out.println(twoDigitRightTruncHarshadNumber);

        List<Long> strongRightTruncHarshadNumbers = new ArrayList<>();
        for (long n : twoDigitRightTruncHarshadNumber) {
            long s = NumericHelper.sumOfDigits(n);
            strongRightTruncHarshadNumbers.addAll(generateRightTruncStrongHarshadNumbers(n, s, 2));
        }
        //System.out.println(strongRightTruncHarshadNumbers);
        long ans = strongRightTruncHarshadNumbers.stream().mapToLong(Long::longValue).sum();
        return Long.toString(ans);
    }

    private List<Long> generateRightTruncStrongHarshadNumbers(long currNum, long sumOfDigits, int digitLen) {
        if (digitLen >= LIMIT) {
            return new ArrayList<>();
        }

        List<Long> strongRightTruncHarshadNumbers = new ArrayList<>();

        //The currNum will always be a RightTrunc harshad number
        //so check if it strong and prime and add it to the list.
        List<Long> tmpList = getStrongRightTruncHarshadNumbersPrimes(currNum, sumOfDigits);
        if (!tmpList.isEmpty()) {
            strongRightTruncHarshadNumbers.addAll(tmpList);
        }

        //Generate other strongRightTruncHarshadNumbers with currNum as prefix
        for (long i=0; i<=9; i++) {
            //If it is right truc harshad number
            if ((currNum * 10 + i) % (sumOfDigits + i) == 0) {
                strongRightTruncHarshadNumbers.addAll(generateRightTruncStrongHarshadNumbers(currNum * 10 + i, sumOfDigits + i, digitLen + 1));
            }
        }

        return strongRightTruncHarshadNumbers;
    }

    private List<Long> getStrongRightTruncHarshadNumbersPrimes(long num, long sumOfDigits) {
        List<Long> strongRightTruncHarshadNumbers = new ArrayList<>();
        if (PrimeNumberHelper.isPrime(num/sumOfDigits)) {
            List<Integer> possibleLastDigitsForPrimeNumber = Arrays.asList(1, 3, 5, 7, 9);
            for (int lastDigit : possibleLastDigitsForPrimeNumber) {
                if (PrimeNumberHelper.isPrime(num * 10 + lastDigit)) {
                    strongRightTruncHarshadNumbers.add(num * 10 + lastDigit);
                }
            }
        }
        return strongRightTruncHarshadNumbers;
    }

    public String solveBF() {
        System.out.println("NOT SOLVED YET");
        long LIMIT = (long)Math.pow(10, 4);
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
                    System.out.print(currPrime + ", ");
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

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=387\n\n" +
                "<p>A <b>Harshad or Niven number</b> is a number that is divisible by the sum of its digits.\n" +
                "<br />201 is a Harshad number because it is divisible by 3 (the sum of its digits.)\n" +
                "<br />When we truncate the last digit from 201, we get 20, which is a Harshad number.\n" +
                "<br />When we truncate the last digit from 20, we get 2, which is also a Harshad number.\n" +
                "<br />Let's call a Harshad number that, while recursively truncating the last digit, always results in a Harshad number a <i>right truncatable Harshad number.</i></p>  \n" +
                "<p>Also:\n" +
                "<br />201/3=67 which is prime.\n" +
                "<br />Let's call a Harshad number that, when divided by the sum of its digits, results in a prime a <i>strong Harshad number</i>.</p>\n" +
                "<p>Now take the number 2011 which is prime.\n" +
                "<br />When we truncate the last digit from it we get 201, a strong Harshad number that is also right truncatable.\n" +
                "<br />Let's call such primes <i>strong, right truncatable Harshad primes</i>.</p>\n" +
                "<p>You are given that the sum of the strong, right truncatable Harshad primes less than 10000 is 90619.</p>\n" +
                "<p>Find the sum of the strong, right truncatable Harshad primes less than 10<sup>14</sup>.</p>";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("Harshad", "Niven", "generation", "primes");
    }
}
