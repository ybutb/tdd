package com.tdd.isbntools;

public class ValidateISBN
{
    final private int ISBN_SHORT = 10;
    final private int ISBN_LONG = 13;
    final private int MULTIPLIER_ISBN_SHORT = 11;
    final private int MULTIPLIER_ISBN_LONG = 10;

    public boolean checkISBN(String isbn) throws NumberFormatException {
        if (isbn.length() == ISBN_LONG) {
            return validateLongISBN(isbn);
        } else if (isbn.length() == ISBN_SHORT) {
            return validateShortISBN(isbn);
        }

        throw new NumberFormatException("ISBN should be 10 or 13 digits long.");
    }

    private boolean validateShortISBN(String isbn) {
        int total = 0;

        for (int i = 0; i < ISBN_SHORT; i++) {
            char currentChar = isbn.charAt(i);

            if (!Character.isDigit(currentChar)) {
                if ((i != ISBN_SHORT - 1 && currentChar != 'X')) {
                    throw new NumberFormatException("ISBN should contain only digits.");
                }

                total += 10;
            } else {
                total += Character.getNumericValue(isbn.charAt(i)) * (ISBN_SHORT - i);
            }
        }

        return total % MULTIPLIER_ISBN_SHORT == 0;
    }

    private boolean validateLongISBN(String isbn)
    {
        int total = 0;

        for (int i = 0; i < ISBN_LONG; i++) {
            char currentChar = isbn.charAt(i);

            if (!Character.isDigit(currentChar)) {
                throw new NumberFormatException("ISBN should contain only digits.");
            }

            int multiplier = 1;

            if ((i + 1) % 2 == 0) {
                multiplier = 3;
            }

            total += Character.getNumericValue(isbn.charAt(i)) * multiplier;
        }

        return total % MULTIPLIER_ISBN_LONG == 0;
    }
}
