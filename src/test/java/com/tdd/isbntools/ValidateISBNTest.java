package com.tdd.isbntools;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidateISBNTest {

    @Test
    public void check10DigitsISBNIsValid()
    {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0140449116");
        assertTrue("first value", result);

        result = validator.checkISBN("0140177396");
        assertTrue("second value", result);
    }

    @Test
    public void check13DigitsISBNIsValid()
    {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9781853260087");
        assertTrue("first value", result);
        result = validator.checkISBN("9781853267338");
        assertTrue("second value", result);
    }

    @Test
    public void check10DigitsInvalidISBN()
    {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0140449117");
        assertFalse(result);
    }

    @Test
    public void check13DigitsInvalidISBN()
    {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("1231131231231");
        assertFalse(result);
    }

    @Test(expected = NumberFormatException.class)
    public void nineDigitsISBNAreNotAllowed()
    {
        ValidateISBN validator = new ValidateISBN();
        validator.checkISBN("123456789");
    }

    @Test(expected = NumberFormatException.class)
    public void nonDigitsISBNAreNotAllowed()
    {
        ValidateISBN validator = new ValidateISBN();
        validator.checkISBN("helloworld");
    }

    @Test
    public void checkISBNWithXIsValid()
    {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("012000030X");
        assertTrue(result);
    }

    @Test(expected = NumberFormatException.class)
    public void checkLongISBNWithXIsNotValid()
    {
        ValidateISBN validator = new ValidateISBN();
        validator.checkISBN("978185326008X");
    }
}
