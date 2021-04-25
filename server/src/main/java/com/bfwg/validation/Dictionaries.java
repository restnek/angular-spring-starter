package com.bfwg.validation;

public final class Dictionaries {
    private Dictionaries() {
        throw new AssertionError("Suppress instantiate utility class");
    }

    public static final String DIGITS = "0123456789";
    public static final String LATIN_UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LATIN_LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    public static final String LATIN_LETTERS = LATIN_UPPER_CASE + LATIN_LOWER_CASE;
    public static final String LATIN_ALPHANUMERIC = LATIN_LETTERS + DIGITS;

    public static boolean isLatinLetter(char ch) {
        return LATIN_LETTERS.indexOf(ch) != -1;
    }

    public static boolean isLatinLetter(int ch) {
        return LATIN_LETTERS.indexOf(ch) != -1;
    }

    public static boolean isLatinAlphanumeric(char ch) {
        return LATIN_ALPHANUMERIC.indexOf(ch) != -1;
    }

    public static boolean isLatinAlphanumeric(int ch) {
        return LATIN_ALPHANUMERIC.indexOf(ch) != -1;
    }
}
