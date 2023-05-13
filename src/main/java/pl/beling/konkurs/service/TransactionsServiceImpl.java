package pl.beling.konkurs.service;

import pl.beling.konkurs.dtos.input.TransactionDto;
import pl.beling.konkurs.dtos.output.AccountDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionsServiceImpl implements TransactionsService {
    private static final int MAX_TRANSACTIONS = 100_000;
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsServiceImpl.class.getName());

    @Override
    public List<AccountDto> report(List<TransactionDto> transactions) {
        var start = Instant.now().toEpochMilli();
        int size = transactions.size();

        if (size > MAX_TRANSACTIONS) {
            transactions = transactions.subList(0, MAX_TRANSACTIONS);
            size = MAX_TRANSACTIONS;
        }


        Map<String, AccountDto> accountsMapById = new ConcurrentHashMap<>(2 * size);

        transactions.stream().parallel().forEach(transaction -> {
            String creditAccount = transaction.getCreditAccount();
            String debitAccount = transaction.getDebitAccount();

            accountsMapById.putIfAbsent(creditAccount, new AccountDto(creditAccount));
            accountsMapById.putIfAbsent(debitAccount, new AccountDto(debitAccount));

            AccountDto accountDtoCredit = accountsMapById.get(creditAccount);
            AccountDto accountDtoDebit = accountsMapById.get(debitAccount);

            accountDtoCredit.addBalance(transaction.getAmount());
            accountDtoDebit.subtractBalance(transaction.getAmount());

        });

        Comparator<AccountDto> comparator = Comparator.comparing(AccountDto::getAccount);
        List<AccountDto> returnList = new ArrayList<>(accountsMapById.values());
        returnList.sort(comparator);

        if(LOGGER.isDebugEnabled()) {
            var end = Instant.now().toEpochMilli();
            LOGGER.debug(String.format("Time passed: %d. Input transactions: %d, accounts: %d.", end - start, size, returnList.size()));
        }

        return returnList;
    }


}
