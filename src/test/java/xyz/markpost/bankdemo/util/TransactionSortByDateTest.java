package xyz.markpost.bankdemo.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import xyz.markpost.bankdemo.dto.TransactionResponseDTO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

class TransactionSortByDateTest {

    @Test
    void sortTransactionsByDateAscending() {
        List<TransactionResponseDTO> transactions = new ArrayList<>();
        TransactionResponseDTO t1 = new TransactionResponseDTO();
        t1.setDate(new Date(1000000000L));
        TransactionResponseDTO t2 = new TransactionResponseDTO();
        t2.setDate(new Date(2000000000L));
        transactions.add(t2);
        transactions.add(t1);

        transactions.sort(new TransactionSortByDate());

        assertEquals(1000000000L, transactions.get(0).getDate().getTime());
        assertEquals(2000000000L, transactions.get(1).getDate().getTime());
    }

    @Test
    void sortTransactionsByDateDescending() {
        List<TransactionResponseDTO> transactions = new ArrayList<>();
        TransactionResponseDTO t1 = new TransactionResponseDTO();
        t1.setDate(new Date(1000000000L));
        TransactionResponseDTO t2 = new TransactionResponseDTO();
        t2.setDate(new Date(2000000000L));
        transactions.add(t1);
        transactions.add(t2);

        transactions.sort(new TransactionSortByDate().reversed());

        assertEquals(2000000000L, transactions.get(0).getDate().getTime());
        assertEquals(1000000000L, transactions.get(1).getDate().getTime());
    }

    @Test
    void sortEmptyTransactionList() {
        List<TransactionResponseDTO> transactions = new ArrayList<>();

        transactions.sort(new TransactionSortByDate());

        assertTrue(transactions.isEmpty());
    }

    @Test
    void sortSingleTransaction() {
        List<TransactionResponseDTO> transactions = new ArrayList<>();
        TransactionResponseDTO t1 = new TransactionResponseDTO();
        t1.setDate(new Date(1000000000L));
        transactions.add(t1);

        transactions.sort(new TransactionSortByDate());

        assertEquals(1000000000L, transactions.get(0).getDate().getTime());
    }
}