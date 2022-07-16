package com.tdd.isbntools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class StockManagementTest
{
    @Test
    public void testCanGetACorrectLocatorCode() {
        ExternalISBNDataService testWebService = new ExternalISBNDataService() {

            @Override
             public Book lookup(String isbn) {
                return new Book(isbn, "Of Mice And Men", "J. Steinbeck");
            }
        };

        ExternalISBNDataService testDatabaseService = new ExternalISBNDataService() {

            @Override
             public Book lookup(String isbn) {
                return null;
            }
        };

        String isbn = "0140177396";
        StockManager stockManager = new StockManager();
        stockManager.setWebService(testWebService);
        stockManager.setDatabaseService(testDatabaseService);

        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4", locatorCode);
    }

    @Test
    public void testDatabaseIsUsedWhenDataIsPresent() {
        ExternalISBNDataService dbService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(dbService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(dbService);

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        verify(dbService, times(1)).lookup(isbn);
        verify(webService, never()).lookup(anyString());
    }

    @Test
    public void testWebServiceIsNotUsedWhenDataIsAbsentInDatabase() {
        ExternalISBNDataService dbService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(dbService.lookup("0140177396")).thenReturn(null);
        when(webService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));

        StockManager stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(dbService);

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        verify(dbService).lookup(isbn);
        verify(webService).lookup(isbn);
    }
}
