package pl.beling.konkurs.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.beling.konkurs.dtos.AccountDto;
import pl.beling.konkurs.dtos.TransactionDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Few tests based on random values, most to check how fast implementation is
 */
class TransactionsServiceRandomTest {
    private final TransactionsService transactionsService = new TransactionsServiceImpl();

    // Things to remember:
    // no more than 100k transactions
    // account number - always string containing 26 numbers

    @Test
    void testOneTransaction1() {
        List<TransactionDto> input = List.of(
                new TransactionDto().amount(new BigDecimal(123)).creditAccount("B").debitAccount("A")
        );
        List<AccountDto> output = transactionsService.report(input);

        assertNotNull(output);
        assertEquals(2, output.size());

        var account1 = output.get(0);
        assertEquals("A", account1.getAccount());
        assertEquals(-123, account1.getBalance().intValue());
        assertEquals(0, account1.getCreditCount());
        assertEquals(1, account1.getDebitCount());

        var account2 = output.get(1);
        assertEquals("B", account2.getAccount());
        assertEquals(123, account2.getBalance().intValue());
        assertEquals(1, account2.getCreditCount());
        assertEquals(0, account2.getDebitCount());
    }

    @Test
    void testThreeTransactions() {
        List<TransactionDto> input = List.of(
                new TransactionDto().amount(new BigDecimal(123)).creditAccount("B").debitAccount("A"),
                new TransactionDto().amount(new BigDecimal(123)).creditAccount("A").debitAccount("B"),
                new TransactionDto().amount(new BigDecimal(123)).creditAccount("B").debitAccount("A")
        );
        List<AccountDto> output = transactionsService.report(input);

        assertNotNull(output);
        assertEquals(2, output.size());

        var account1 = output.get(0);
        assertEquals("A", account1.getAccount());
        assertEquals(-123, account1.getBalance().intValue());
        assertEquals(1, account1.getCreditCount());
        assertEquals(2, account1.getDebitCount());

        var account2 = output.get(1);
        assertEquals("B", account2.getAccount());
        assertEquals(123, account2.getBalance().intValue());
        assertEquals(2, account2.getCreditCount());
        assertEquals(1, account2.getDebitCount());
    }

    @Test
    void testOneTransaction2() {
        List<TransactionDto> input = List.of(
                new TransactionDto().amount(new BigDecimal(123)).creditAccount("A").debitAccount("B")
        );
        List<AccountDto> output = transactionsService.report(input);

        assertNotNull(output);
        assertEquals(2, output.size());

        var account1 = output.get(1);
        assertEquals("B", account1.getAccount());
        assertEquals(-123, account1.getBalance().intValue());
        assertEquals(0, account1.getCreditCount());
        assertEquals(1, account1.getDebitCount());

        var account2 = output.get(0);
        assertEquals("A", account2.getAccount());
        assertEquals(123, account2.getBalance().intValue());
        assertEquals(1, account2.getCreditCount());
        assertEquals(0, account2.getDebitCount());
    }

    @ParameterizedTest
    @CsvSource({
            "100",
            "1000",
            "10000",
            "100000"
    })
    void testOneAmountUniqueAccounts(int max) {
        List<TransactionDto> input = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            input.add(new TransactionDto()
                    .amount(new BigDecimal(1))
                    .creditAccount("A" + i)
                    .debitAccount("B" + i));
        }
        List<AccountDto> output = transactionsService.report(input);

        assertNotNull(output);
        assertEquals(max * 2, output.size());

        var account1 = output.get(0);
        assertEquals("A", account1.getAccount().substring(0, 1));
        assertEquals(1, account1.getBalance().intValue());
        assertEquals(1, account1.getCreditCount());
        assertEquals(0, account1.getDebitCount());

        var account2 = output.get(output.size() - 1);
        assertEquals("B", account2.getAccount().substring(0, 1));
        assertEquals(-1, account2.getBalance().intValue());
        assertEquals(0, account2.getCreditCount());
        assertEquals(1, account2.getDebitCount());
    }

    @ParameterizedTest
    @CsvSource({
            "100",
            "1000",
            "10000",
            "50000",
            "100000"
    })
    void testOneAmountAndCreditADebitB(int max) {
        List<TransactionDto> input = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            input.add(new TransactionDto()
                    .amount(new BigDecimal(1))
                    .creditAccount("A" + i)
                    .debitAccount("B" + i));
        }
        List<AccountDto> output = transactionsService.report(input);

        assertNotNull(output);

        var account1 = output.get(0);
        assertEquals("A", account1.getAccount().substring(0, 1));
        assertEquals(1, account1.getCreditCount());

        var account2 = output.get(output.size() - 1);
        assertEquals("B", account2.getAccount().substring(0, 1));
        assertEquals(1, account2.getDebitCount());
    }

    @ParameterizedTest
    @CsvSource({
            "100",
            "1000",
            "10000",
            "50000",
            "100000"
    })
    void testWithOneAmountAndRandomLimitedAccounts(int max) {
        List<TransactionDto> input = new ArrayList<>();
        var r = new Random();
        for (int i = 0; i < max; i++) {
            input.add(new TransactionDto()
                    .amount(new BigDecimal(1))
                    .creditAccount(String.valueOf(r.nextInt(max)))
                    .debitAccount(String.valueOf(r.nextInt(max))));
        }
        List<AccountDto> output = transactionsService.report(input);

        assertNotNull(output);
    }

    @ParameterizedTest
    @CsvSource({
            "50",
            "500",
            "5000",
            "50000"
    })
    void testRandomAmount(int max) {
        List<TransactionDto> input = new ArrayList<>();
        var r = new Random();
        for (int i = 0; i < max; i++) {
            int accountTo = r.nextInt();
            int accountForm = r.nextInt();

            String creditAccount = String.format("%1$26s", accountTo).replace(" ", "0");
            String debitAccount = String.format("%1$26s", accountForm).replace(" ", "0");
            input.add(new TransactionDto()
                    .amount(BigDecimal.valueOf(r.nextDouble()))
                    .creditAccount(creditAccount)
                    .debitAccount(debitAccount));

            input.add(new TransactionDto()
                    .amount(BigDecimal.valueOf(r.nextDouble()))
                    .creditAccount(String.valueOf(r.nextInt(max)))
                    .debitAccount(String.valueOf(r.nextInt(max))));
        }

        List<AccountDto> output = transactionsService.report(input);

        assertNotNull(output);
    }

    @ParameterizedTest
    @CsvSource({
            "100,10",
            "100,100",
            "1000,10",
            "1000,100",
            "1000,1000",
            "10000,10",
            "10000,100",
            "10000,1000",
            "10000,10000",
            "100000,10",
            "100000,100",
            "100000,1000",
            "100000,10000",
            "100000,100000",
            "100000,10",
            "100000,100",
            "100000,1000",
            "100000,10000",
            "100000,100000" // max is 100k
    })
    void testTenAccountsWithRandomAmount(int max, int maxAccounts) {
        List<String> accountNumbers = new ArrayList<>();
        for(int i = 0; i < maxAccounts; i++) {
            accountNumbers.add(generateRandomAccountNumber());
        }

        List<TransactionDto> input = new ArrayList<>();
        var r = new Random();
        for (int i = 0; i < max; i++) {
            int accountTo = r.nextInt(maxAccounts);
            int accountForm = r.nextInt(maxAccounts);
            while(accountForm == accountTo) {
                accountForm = r.nextInt(maxAccounts);
            }

            String creditAccount = accountNumbers.get(accountTo);
            String debitAccount = accountNumbers.get(accountForm);
            input.add(new TransactionDto()
                    .amount(BigDecimal.valueOf(r.nextDouble()))
                    .creditAccount(creditAccount)
                    .debitAccount(debitAccount));
        }
        List<AccountDto> output = transactionsService.report(input);
        assertNotNull(output);
    }

    @Test
    void testTransactionToMyself() {
        List<TransactionDto> input = new ArrayList<>();
        input.add(
                new TransactionDto().amount(BigDecimal.valueOf(1.23)).debitAccount("1").creditAccount("1")
        );
        List<AccountDto> output = transactionsService.report(input);

        assertNotNull(output);
        assertEquals(1, output.size());
    }

    private final Random random = new Random();

    // it is always 26 characters (numbers 0-9)
    private String generateRandomAccountNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    @ParameterizedTest
    @CsvSource({
            "10",
            "100",
            "1000",
            "10000",
            "100000"
    })
    void testTenAccountsWithRandomAmountAndFull26digitsNumbers(int max) {
        List<TransactionDto> input = new ArrayList<>();
        var r = new Random();
        for (int i = 0; i < max; i++) {
            String creditAccount = generateRandomAccountNumber();
            String debitAccount = generateRandomAccountNumber();
            input.add(new TransactionDto()
                    .amount(BigDecimal.valueOf(r.nextDouble()))
                    .creditAccount(creditAccount)
                    .debitAccount(debitAccount));
        }
        List<AccountDto> output = transactionsService.report(input);
        assertNotNull(output);
        assertEquals(input.size() * 2, output.size());
    }

    @Test
    void testTenAccountsWithRandomAmountAndFull26digitsNumbers() {
        int iterations = 10;
        int max = 100000;
        for(int j = 0; j < iterations; j++) {
            List<TransactionDto> input = new ArrayList<>();
            var r = new Random();

            for (int i = 0; i < max; i++) {
                String creditAccount = generateRandomAccountNumber();
                String debitAccount = generateRandomAccountNumber();
                input.add(new TransactionDto()
                        .amount(BigDecimal.valueOf(r.nextDouble()))
                        .creditAccount(creditAccount)
                        .debitAccount(debitAccount));
            }
            List<AccountDto> output = transactionsService.report(input);
            assertNotNull(output);
            assertEquals(input.size() * 2, output.size());
        }
    }
}