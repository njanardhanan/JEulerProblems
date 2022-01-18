package com.jsoft.jeuler.helper;

import java.util.HashMap;
import java.util.Map;

public class NumberToWords {
    private final long OneThousand = 1000l;
    private final long OneMillion = 1000000l;
    private final long OneBillion = 1000000000l;
    private final long OneTrillion = 1000000000000l;
    private final long OneQuadrillion = 1000000000000000l;
    private final long OneQuintillion = 1000000000000000000l;

    private Map<Long, String> wordsDict = new HashMap();

    public NumberToWords() {
        wordsDict.put(0l, "zero");
        wordsDict.put(1l, "one");
        wordsDict.put(2l, "two");
        wordsDict.put(3l, "three");
        wordsDict.put(4l, "four");
        wordsDict.put(5l, "five");
        wordsDict.put(6l, "six");
        wordsDict.put(7l, "seven");
        wordsDict.put(8l, "eight");
        wordsDict.put(9l, "nine");
        wordsDict.put(10l, "ten");
        wordsDict.put(11l, "eleven");
        wordsDict.put(12l, "twelve");
        wordsDict.put(13l, "thirteen");
        wordsDict.put(14l, "fourteen");
        wordsDict.put(15l, "fifteen");
        wordsDict.put(16l, "sixteen");
        wordsDict.put(17l, "seventeen");
        wordsDict.put(18l, "eighteen");
        wordsDict.put(19l, "nineteen");
        wordsDict.put(20l, "twenty");
        wordsDict.put(30l, "thirty");
        wordsDict.put(40l, "forty");
        wordsDict.put(50l, "fifty");
        wordsDict.put(60l, "sixty");
        wordsDict.put(70l, "seventy");
        wordsDict.put(80l, "eighty");
        wordsDict.put(90l, "ninety");
        wordsDict.put(100l, "hundred");
        wordsDict.put(OneThousand, "thousand");
        wordsDict.put(OneMillion, "million");
        wordsDict.put(OneBillion, "billion");
        wordsDict.put(OneTrillion, "trillion");
        wordsDict.put(OneQuadrillion, "quadrillion");
        wordsDict.put(OneQuintillion, "quintillion");
    }

    private String ProcessTens(long number) {
        // split the number into the number of tens and the number of units,
        // so that words for both can be looked up independently
        long tens = (number / 10) * 10;
        long units = number % 10;
        String conversion = wordsDict.get(tens);
        conversion += units > 0 ? "-" + wordsDict.get(units) : "";
        return conversion;
    }

    private String ProcessHundreds(long number) {
        long hundreds = number / 100;
        long remainder = number % 100;
        String conversion = wordsDict.get(hundreds) + " " + wordsDict.get(100l);
        conversion += remainder > 0 ? " and " + convert(remainder) : "";
        return conversion;
    }

    private String ProcessLargeNumber(long number, long baseUnit) {
        // split the number into number of baseUnits (thousands, millions, etc.)
        // and the remainder
        long numberOfBaseUnits = number / baseUnit;
        long remainder = number % baseUnit;
        // apply ConvertToWordsCore to represent the number of baseUnits as words
        String conversion = convert(numberOfBaseUnits) + " " + wordsDict.get(baseUnit);
        // recurse for any remainder
        conversion += remainder <= 0 ? ""
                : (remainder < 100 ? " and " : ", ") + convert(remainder);
        return conversion;
    }

    public String convert(long number) {
        // if between 1 and 19, convert to word
        if (0 <= number && number < 20) {
            return wordsDict.get(number);
        }

        if (20 <= number && number < 100) {
            return ProcessTens(number);
        }

        // if between 100 and 999, convert hundreds to word then recurse for tens
        if (100 <= number && number < OneThousand) {
            return ProcessHundreds(number);
        }

        if (OneThousand <= number && number < OneMillion) {
            return ProcessLargeNumber(number, OneThousand);
        }

        if (OneMillion <= number && number < OneBillion) {
            return ProcessLargeNumber(number, OneMillion);
        }

        if (OneBillion <= number && number < OneTrillion) {
            return ProcessLargeNumber(number, OneBillion);
        }

        if (OneTrillion <= number && number < OneQuadrillion) {
            return ProcessLargeNumber(number, OneTrillion);
        }

        if (OneQuadrillion <= number && number < OneQuintillion) {
            return ProcessLargeNumber(number, OneQuadrillion);
        }
        else {
            return ProcessLargeNumber(number, OneQuintillion);
        }
    }
}
