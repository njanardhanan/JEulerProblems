package com.jsoft.jeuler.helper;

import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Permutations {
    private Permutations() {
    }


    public static boolean isPermutation(int x, int y) {
        return isPermutation((long)x, (long)y);
    }

    public static boolean isPermutation(long x, long y) {
        int[] val = new int[10];
        Arrays.fill(val, 0);
        while(x>0) {
            val[(int)x%10] += 1;
            x = x/10;
        }
        while(y>0) {
            val[(int)y%10] -= 1;
            y = y/10;
        }
        for(int i=0; i<10; i++) {
            if(val[i] > 0) return false;
        }
        return true;
    }

    public static long factorial(int n) {
        if (n > 20 || n < 0) throw new IllegalArgumentException(n + " is out of range");
        return LongStream.rangeClosed(2, n).reduce(1, (a, b) -> a * b);
    }
    public static <T> List<T> permutation(long no, List<T> items) {
        return permutationHelper(no,
                new LinkedList<>(Objects.requireNonNull(items)),
                new ArrayList<>());
    }
    private static <T> List<T> permutationHelper(long no, LinkedList<T> in, List<T> out) {
        if (in.isEmpty()) return out;
        long subFactorial = factorial(in.size() - 1);
        out.add(in.remove((int) (no / subFactorial)));
        return permutationHelper((int) (no % subFactorial), in, out);
    }
    @SafeVarargs
    @SuppressWarnings("varargs") // Creating a List from an array is safe
    public static <T> Stream<Stream<T>> of(T... items) {
        List<T> itemList = Arrays.asList(items);
        return LongStream.range(0, factorial(items.length))
                .mapToObj(no -> permutation(no, itemList).stream());
    }

    /**
     * This method will split a number into all possible permutation
     * Ex: 12345
     * ['12345', '1234-5', '123-45', '123-4-5', '12-345', '12-34-5', '12-3-45', '12-3-4-5', '1-2345', '1-234-5',
     * '1-23-45', '1-23-4-5', '1-2-345', '1-2-34-5', '1-2-3-45', '1-2-3-4-5']
     *
     * @param n - the number to split
     * @return - List of list
     */
    public static List<List<Long>> splitIntoAllPossiblePermutation(long n) {
        return splitIntoAllPossiblePermutation(n, 0, Long.toString(n).length() ,new ArrayList<>(), new ArrayList<>());
    }

    private static List<List<Long>> splitIntoAllPossiblePermutation(long n, int i, int len, List<Long> out, List<List<Long>> result) {
        if (i == len) {
            result.add(new ArrayList<>(out));
        }

        for(int j=len-1; j>=i; j--) {
            long m = Long.parseLong(Long.toString(n).substring(i, j+1));
            out.add(m);
            result = splitIntoAllPossiblePermutation(n, j+1, len, out, result);
            out.remove(out.size() - 1);
        }
        return result;
    }
}
