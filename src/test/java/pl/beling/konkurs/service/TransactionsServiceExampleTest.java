package pl.beling.konkurs.service;

import org.junit.jupiter.api.Test;
import pl.beling.konkurs.dtos.input.TransactionDto;
import pl.beling.konkurs.dtos.output.AccountDto;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransactionsServiceExampleTest {
    private final TransactionsService transactionsService = new TransactionsServiceImpl();

    @Test
    void sampleTest() {
        List<TransactionDto> input = List.of(
                new TransactionDto().amount(new BigDecimal("10.90")).creditAccount("06105023389842834748547303").debitAccount("32309111922661937852684864"),
                new TransactionDto().amount(new BigDecimal("200.90")).creditAccount("66105036543749403346524547").debitAccount("31074318698137062235845814"),
                new TransactionDto().amount(new BigDecimal("50.10")).creditAccount("32309111922661937852684864").debitAccount("66105036543749403346524547")
        );
        List<AccountDto> output = transactionsService.report(input);

        assertNotNull(output);
        assertEquals(4, output.size());

        var account = output.get(0);
        assertEquals("06105023389842834748547303", account.getAccount());
        assertEquals("10.90", account.getBalance().toString());
        assertEquals(1, account.getCreditCount());
        assertEquals(0, account.getDebitCount());

        account = output.get(1);
        assertEquals("31074318698137062235845814", account.getAccount());
        assertEquals("-200.90", account.getBalance().toString());
        assertEquals(0, account.getCreditCount());
        assertEquals(1, account.getDebitCount());

        account = output.get(2);
        assertEquals("32309111922661937852684864", account.getAccount());
        assertEquals("39.20", account.getBalance().toString());
        assertEquals(1, account.getCreditCount());
        assertEquals(1, account.getDebitCount());

        account = output.get(3);
        assertEquals("66105036543749403346524547", account.getAccount());
        assertEquals("150.80", account.getBalance().toString());
        assertEquals(1, account.getCreditCount());
        assertEquals(1, account.getDebitCount());
    }

}