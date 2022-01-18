package com.jsoft.jeuler.poker;

import java.util.*;

public abstract class Hand {

    private static final int FALSE = -1;

    public static final int PLAYER_ONE = 1;
    public static final int PLAYER_TWO = -1;
    public static final int EQUAL = 0;

    public static int ROYAL_FLUSH = 1000;
    public static int STRAIGHT_FLUSH = 900;
    public static int FOUR_OF_A_KIND = 800;
    public static int FULL_HOUSE = 700;
    public static int FLUSH = 600;
    public static int STRAIGHT = 500;
    public static int THREE_OF_A_KIND = 400;
    public static int TWO_PAIRS = 300;
    public static int ONE_PAIR = 200;

    /**
     * Private constructor to disable instantiation of an abstract class.
     */
    private Hand() {

    }

    public static int evaluate(Card[] cards) {
        // Only 5-card hands are supported
        if (cards == null || cards.length != 5) {
            throw new IllegalArgumentException("Exactly 5 cards are required.");
        }

        // Binary representations of each card
        final int c1 = cards[0].getValue();
        final int c2 = cards[1].getValue();
        final int c3 = cards[2].getValue();
        final int c4 = cards[3].getValue();
        final int c5 = cards[4].getValue();

        // No duplicate cards allowed
        if (hasDuplicates(new int[]{c1, c2, c3, c4, c5})) {
            throw new IllegalArgumentException("Illegal hand.");
        }

        if(isRoyalFlush(cards)) {
            return ROYAL_FLUSH;
        }

        int hand = isStraightFlush(cards);
        if(hand != FALSE) {
            return STRAIGHT_FLUSH + hand;
        }

        hand = isFourOfAKind(cards);
        if(hand != FALSE) {
            return FOUR_OF_A_KIND + hand;
        }

        hand = isFullHouse(cards);
        if(hand != FALSE) {
            return FULL_HOUSE + hand;
        }

        hand = isFlush(cards);
        if(hand != FALSE) {
            return FLUSH + hand;
        }

        hand = isStraight(cards);
        if(hand != FALSE) {
            return STRAIGHT + hand;
        }

        hand = isThreeOfAKind(cards);
        if(hand != FALSE) {
            return THREE_OF_A_KIND + hand;
        }

        hand = isTwoPairs(cards);
        if(hand != FALSE) {
            return TWO_PAIRS + hand;
        }

        hand = isPair(cards);
        if(hand != FALSE) {
            return ONE_PAIR + hand;
        }

        return getHighestCardValue(cards);
    }

    public static int evaluateHighestCard(Card[] cards1, Card[] cards2) {
        final int c1 = cards1[0].getRank();
        final int c2 = cards1[1].getRank();
        final int c3 = cards1[2].getRank();
        final int c4 = cards1[3].getRank();
        final int c5 = cards1[4].getRank();

        int[] ranks1 = new int[]{c1, c2, c3, c4, c5};
        Arrays.sort(ranks1);

        final int x1 = cards2[0].getRank();
        final int x2 = cards2[1].getRank();
        final int x3 = cards2[2].getRank();
        final int x4 = cards2[3].getRank();
        final int x5 = cards2[4].getRank();

        int[] ranks2 = new int[]{x1, x2, x3, x4, x5};
        Arrays.sort(ranks2);

        for(int i=4; i>=0; i--) {
            if(ranks1[i] == ranks2[i]) continue;
            if(ranks1[i] > ranks2[i]) return PLAYER_ONE;
            if(ranks1[i] < ranks2[i]) return PLAYER_TWO;
        }

        return EQUAL;
    }

    //From "5H 5C 6S 7S KD"
    public static Card[] fromString(String string) {
        final String[] parts = string.split(" ");
        final Card[] cards = new Card[parts.length];

        if (parts.length != 5)
            throw new IllegalArgumentException("Exactly 5 cards are required.");

        int index = 0;
        for (String part : parts)
            cards[index++] = Card.fromString(part);

        return cards;
    }

    public static String toString(Card[] cards) {
        final StringBuilder builder = new StringBuilder();

        for (int i = 0; i < cards.length; i++) {
            builder.append(cards[i]);
            if (i < cards.length - 1)
                builder.append(" ");
        }

        return builder.toString();
    }

    private static boolean hasDuplicates(int[] values) {
        Arrays.sort(values);
        for (int i = 1; i < values.length; i++) {
            if (values[i] == values[i - 1])
                return true;
        }
        return false;
    }

    private static boolean isSameSuite(Card[] cards) {
        //Cards must be in same suite.
        for (int i = 1; i < cards.length; i++) {
            if (cards[i].getSuit() != cards[i-1].getSuit()) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSequence(Card[] cards) {
        final int c1 = cards[0].getRank();
        final int c2 = cards[1].getRank();
        final int c3 = cards[2].getRank();
        final int c4 = cards[3].getRank();
        final int c5 = cards[4].getRank();

        int[] ranks = new int[]{c1, c2, c3, c4, c5};
        Arrays.sort(ranks);
        for (int i = 1; i < ranks.length; i++) {
            if (ranks[i] != ranks[i - 1] + 1)
                return false;
        }
        return true;
    }

    public static int getHighestCardValue(Card[] cards) {
        final int c1 = cards[0].getRank();
        final int c2 = cards[1].getRank();
        final int c3 = cards[2].getRank();
        final int c4 = cards[3].getRank();
        final int c5 = cards[4].getRank();

        int[] ranks = new int[]{c1, c2, c3, c4, c5};
        Arrays.sort(ranks);
        return ranks[cards.length-1];
    }

    private static boolean isRoyalFlush(Card[] cards) {

        if (!isSameSuite(cards)) {
            return false;
        }

        if(!isSequence(cards)) {
            return false;
        }

        if(getHighestCardValue(cards) != Card.ACE) {
            return false;
        }

        return true;
    }

    private static int isStraightFlush(Card[] cards) {

        if (!isSameSuite(cards)) {
            return FALSE;
        }

        if(!isSequence(cards)) {
            return FALSE;
        }

        return getHighestCardValue(cards);
    }

    private static int isFourOfAKind(Card[] cards) {
        //Value of any four card should be equal.

        //Count the cards using hash map
        Map<Integer, Integer> map = new HashMap();
        for(Card c : cards) {
            int i = c.getRank();
            if(map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }

        if (map.size() != 2) {
            return FALSE;
        }

        for(int v : map.keySet()) {
            if (map.get(v) == 4) {
                return v;
            }
        }

        return FALSE;
    }

    private static int isFullHouse(Card[] cards) {
        //Count the cards using hash map
        Map<Integer, Integer> map = new HashMap();
        for(Card c : cards) {
            int i = c.getRank();
            if(map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }

        if (map.size() != 2) {
            return FALSE;
        }

        List<Integer> v =   new ArrayList<>(map.values());
        Collections.sort(v);
        if(v.get(0) == 2 && v.get(1) == 3) {
            for(int k : map.keySet()) {
                if(map.get(k) == 3) {
                    return k;
                }
            }
        }
        return FALSE;
    }

    private static int isFlush(Card[] cards) {
        if(!isSameSuite(cards)) {
            return FALSE;
        }

        return getHighestCardValue(cards);
    }

    private static int isStraight(Card[] cards) {
        if(isSameSuite(cards)) {
            return FALSE;
        }

        if(isSequence(cards)) {
            return getHighestCardValue(cards);
        }

        return FALSE;
    }

    private static int isThreeOfAKind(Card[] cards) {
        //Value of any three card should be equal.

        //Count the cards using hash map
        Map<Integer, Integer> map = new HashMap();
        for(Card c : cards) {
            int i = c.getRank();
            if(map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }

        if (map.size() != 3) {
            return FALSE;
        }

        List<Integer> v =   new ArrayList<>(map.values());
        Collections.sort(v);
        if(v.get(0) == 1 && v.get(1) == 1 && v.get(2) == 3) {
            for(int k : map.keySet()) {
                if(map.get(k) == 3) {
                    return k;
                }
            }
        }
        return FALSE;
    }

    private static int isTwoPairs(Card[] cards) {
        //Value of any three card should be equal.

        //Count the cards using hash map
        Map<Integer, Integer> map = new HashMap();
        for(Card c : cards) {
            int i = c.getRank();
            if(map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }

        if (map.size() != 3) {
            return FALSE;
        }

        List<Integer> v =   new ArrayList<>(map.values());
        Collections.sort(v);
        if(v.get(0) == 1 && v.get(1) == 2 && v.get(2) == 2) {
            int highest = -1;
            for(int k : map.keySet()) {
                if(map.get(k) == 2) {
                    if(highest < k) {
                        highest = k;
                    }
                }
            }
            return highest;
        }
        return FALSE;
    }

    private static int isPair(Card[] cards) {
        //Value of any three card should be equal.

        //Count the cards using hash map
        Map<Integer, Integer> map = new HashMap();
        for(Card c : cards) {
            int i = c.getRank();
            if(map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }

        if (map.size() != 4) {
            return FALSE;
        }

        List<Integer> v =   new ArrayList<>(map.values());
        Collections.sort(v);
        if(v.get(0) == 1 && v.get(1) == 1 && v.get(2) == 1 && v.get(3) == 2) {
            for(int k : map.keySet()) {
                if(map.get(k) == 2) {
                    return k;
                }
            }
        }
        return FALSE;
    }

}
