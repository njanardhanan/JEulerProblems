package com.jsoft.jeuler.helper;

import java.util.HashMap;
import java.util.Map;

public class StringHelper {

    public static String reverse(String s) {
        String rev = new StringBuffer(s).reverse().toString();
        return rev;
    }

    public static boolean isPalindrome(String s) {
        if ( s == null ) return false;
        return s.equals(reverse(s));
    }

    //O(n^2) - Need to improve this
    public static boolean isUniqueCharacters(String str) {
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String cycleString(String str, int len) {
        StringBuilder sb = new StringBuilder();
        char[] c = str.toCharArray();
        int subStringLen = str.length();
        int i = 0;
        while(i < len) {
            sb.append(c[i%subStringLen]);
            i++;
        }

        return sb.toString();
    }

    public static String getRepeatingSubString(String str) {
        int len = str.length();
        int i=1;
        while(i<len) {
            String sub = str.substring(0, i);
            if(str.equals(cycleString(sub, len))) {
                return sub;
            }
            i++;
        }
        return str;
    }

    public static boolean isAnagram(String x, String y) {

        if (x.length() != y.length()) {
            return false;
        }

        return getCharCount(x).equals(getCharCount(y));
    }

    private static Map<Character, Integer> getCharCount(String s) {
        Map<Character, Integer> charCount = new HashMap();
        for(char c : s.toCharArray()) {
            if (charCount.containsKey(c)) {
                charCount.put(c, charCount.get(c) + 1);
            } else {
                charCount.put(c, 1);
            }
        }
        return charCount;
    }
}
