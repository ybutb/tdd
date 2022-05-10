package com.tdd.isbntools;

public class ValidateISBN
{
    public boolean checkISBN(String isbn) throws NumberFormatException {
        if (isbn.length() != 10 && isbn.length() != 13) {throw new NumberFormatException("ISBN should be 10 digits long.");}

        if (isbn.length() == 13) {
            return validate13DigitsISBN(isbn);
        }

        int total = 0;

        for (int i = 0; i < isbn.length(); i++) {
            char currentChar = isbn.charAt(i);

            if (!Character.isDigit(currentChar)) {
                if (!(i != isbn.length() - 1 && currentChar != 'X')) {
                    throw new NumberFormatException("ISBN should contain only digits.");
                }
            }

            total += Character.getNumericValue(isbn.charAt(i)) * (10 - i);
        }

        return total % 11 == 0;
    }

    private boolean validate13DigitsISBN(String isbn)
    {
        int total = 0;

        for (int i = 0; i < isbn.length(); i++) {
            char currentChar = isbn.charAt(i);

            if (!Character.isDigit(currentChar)) {
                if (!(i != isbn.length() - 1 && currentChar != 'X')) {
                    throw new NumberFormatException("ISBN should contain only digits.");
                }
            }

            int multiplier = 1;

            if ((i + 1) % 2 == 0) {
                multiplier = 3;
            }

            total += Character.getNumericValue(isbn.charAt(i)) * multiplier;
        }

        return total % 10 == 0;
    }
}
