package com.jsoft.jeuler.poker;

public class Card {
    //rank
    public static final int TWO = 0;
    public static final int ACE = 12;

    // Suits
    public static final int CLUBS = 0x8000;
    public static final int DIAMONDS = 0x4000;
    public static final int HEARTS = 0x2000;
    public static final int SPADES = 0x1000;

    private static final String RANKS = "23456789TJQKA";
    private static final String SUITS = "SHDC";

    private int rank;
    private int suit;

    public Card(int rank, int suit) {
        if (!isValidRank(rank)) {
            throw new IllegalArgumentException("Invalid rank.");
        }

        if (!isValidSuit(suit)) {
            throw new IllegalArgumentException("Invalid suit.");
        }

        this.rank = rank;
        this.suit = suit;
    }

    public static Card fromString(String string) {
        if (string == null || string.length() != 2) {
            throw new IllegalArgumentException("Card string must be non-null with length of exactly 2.");
        }

        final int rank = RANKS.indexOf(string.charAt(0));
        final int suit = SPADES << SUITS.indexOf(string.charAt(1));

        return new Card(rank, suit);
    }

    public int getRank() {
        return this.rank;
    }

    public int getSuit() {
        return this.suit;
    }

    public int getValue() {
        return this.suit + this.rank;
    }

    public String toString() {
        char rank = RANKS.charAt(getRank());
        char suit = SUITS.charAt(getSuit());
        return "" + rank + suit;
    }

    private static boolean isValidRank(int rank) {
        return rank >= TWO && rank <= ACE;
    }

    private static boolean isValidSuit(int suit) {
        return suit == CLUBS || suit == DIAMONDS || suit == HEARTS || suit == SPADES;
    }
}
