package com.jsoft.jeuler.helper;

public class FibonacciHelper {
    public static int getPisanoPeriod(int m) {
        int a = 0;
        int b = 1;
        int c = a + b;
        for (int i = 0; i < 121; i++) {
            c = (a + b) % m;
            a = b;
            b = c;
            if (a == 0 && b == 1)  {
                return i + 1;
            }
        }
        return 0;
    }
}
