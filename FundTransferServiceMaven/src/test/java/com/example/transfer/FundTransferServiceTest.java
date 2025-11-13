package com.example.transfer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;



public class FundTransferServiceTest {

   
    private AccountService accountService;

   
    private FundTransferService fundTransferService;

    @Test
    public void testTransfer_Successful() {
        when(accountService.getBalance("A1")).thenReturn(200.0);
        when(accountService.exists("B1")).thenReturn(true);
        when(accountService.debit("A1", 100.0)).thenReturn(true);
        when(accountService.credit("B1", 100.0)).thenReturn(true);

        String result = fundTransferService.transfer("A1", "B1", 100.0);
        assertEquals("SUCCESS: Transfer completed", result);

        InOrder order = inOrder(accountService);
        order.verify(accountService).getBalance("A1");
        order.verify(accountService).exists("B1");
        order.verify(accountService).debit("A1", 100.0);
        order.verify(accountService).credit("B1", 100.0);
    }

    @Test
    public void testTransfer_InvalidAmount() {
        assertEquals("FAILURE: Invalid amount",
                fundTransferService.transfer("A1", "B1", 0.0));
        assertEquals("FAILURE: Invalid amount",
                fundTransferService.transfer("A1", "B1", -10.0));
        verifyNoInteractions(accountService);
    }

    @Test
    public void testTransfer_SameAccount() {
        assertEquals("FAILURE: Source and destination cannot be same",
                fundTransferService.transfer("A1", "A1", 50.0));
        verifyNoInteractions(accountService);
    }

    @Test
    public void testTransfer_InsufficientFunds() {
        when(accountService.getBalance("A1")).thenReturn(50.0);
        String result = fundTransferService.transfer("A1", "B1", 100.0);
        assertEquals("FAILURE: Insufficient funds", result);
        verify(accountService).getBalance("A1");
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testTransfer_DestinationNotFound() {
        when(accountService.getBalance("A1")).thenReturn(200.0);
        when(accountService.exists("B1")).thenReturn(false);
        String result = fundTransferService.transfer("A1", "B1", 50.0);
        assertEquals("FAILURE: Destination account not found", result);

        InOrder inOrder = inOrder(accountService);
        inOrder.verify(accountService).getBalance("A1");
        inOrder.verify(accountService).exists("B1");
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testTransfer_DebitFails() {
        when(accountService.getBalance("A1")).thenReturn(200.0);
        when(accountService.exists("B1")).thenReturn(true);
        when(accountService.debit("A1", 100.0)).thenReturn(false);
        String result = fundTransferService.transfer("A1", "B1", 100.0);
        assertEquals("FAILURE: Transaction error", result);

        InOrder inOrder = inOrder(accountService);
        inOrder.verify(accountService).getBalance("A1");
        inOrder.verify(accountService).exists("B1");
        inOrder.verify(accountService).debit("A1", 100.0);
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testTransfer_CreditFails() {
        when(accountService.getBalance("A1")).thenReturn(200.0);
        when(accountService.exists("B1")).thenReturn(true);
        when(accountService.debit("A1", 100.0)).thenReturn(true);
        when(accountService.credit("B1", 100.0)).thenReturn(false);

        String result = fundTransferService.transfer("A1", "B1", 100.0);
        assertEquals("FAILURE: Transaction error", result);

        InOrder inOrder = inOrder(accountService);
        inOrder.verify(accountService).getBalance("A1");
        inOrder.verify(accountService).exists("B1");
        inOrder.verify(accountService).debit("A1", 100.0);
        inOrder.verify(accountService).credit("B1", 100.0);
    }

    @Test
    public void testTransfer_FromAccountNull() {
        assertThrows(NullPointerException.class, () ->
            fundTransferService.transfer(null, "B1", 100.0));
    }

    @Test
    public void testTransfer_AmountAtLimit_ShouldPass() {
        when(accountService.getBalance("A1")).thenReturn(1000000.0);
        when(accountService.exists("B1")).thenReturn(true);
        when(accountService.debit("A1", 500000.0)).thenReturn(true);
        when(accountService.credit("B1", 500000.0)).thenReturn(true);

        String result = fundTransferService.transfer("A1", "B1", 500000.0);
        assertEquals("SUCCESS: Transfer completed", result);
    }
    
    
    
    
    
    
    @Test
    public void testTransfer_AmountExceedsLimit() {
        String from = "A1";
        String to = "B1";
        double amount = 600000.0; 

        String result = fundTransferService.transfer(from, to, amount);
        assertEquals("FAILURE: Amount exceeds transfer limit", result);
        verifyNoInteractions(accountService);
    }
}
