package com.tdd.isbntools;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class StockManagementTest
{
    private ExternalISBNDataService dbService;
    private ExternalISBNDataService webService;
    private StockManager stockManager;

    @Before
    public void setup() {
        System.out.println("Setting up mocks");

        dbService = mock(ExternalISBNDataService.class);
        webService = mock(ExternalISBNDataService.class);

        stockManager = new StockManager();
        stockManager.setWebService(webService);
        stockManager.setDatabaseService(dbService);
    }

    @Test
    public void testCanGetACorrectLocatorCode() {
        when(dbService.lookup(anyString())).thenReturn(null);
        when(webService.lookup(anyString())).thenReturn(new Book("0140177396", "Of Mice And Men", "J. Steinbeck"));

        String isbn = "0140177396";

        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4", locatorCode);
    }

    @Test
    public void testDatabaseIsUsedWhenDataIsPresent() {
        when(dbService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        verify(dbService, times(1)).lookup(isbn);
        verify(webService, never()).lookup(anyString());
    }

    @Test
    public void testWebServiceIsNotUsedWhenDataIsAbsentInDatabase() {
        when(dbService.lookup("0140177396")).thenReturn(null);
        when(webService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));

        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        verify(dbService).lookup(isbn);
        verify(webService).lookup(isbn);
    }
}
