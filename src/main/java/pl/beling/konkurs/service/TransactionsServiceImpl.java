package pl.beling.konkurs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.beling.konkurs.dtos.AccountDto;
import pl.beling.konkurs.dtos.TransactionDto;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TransactionsServiceImpl implements TransactionsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionsServiceImpl.class.getName());

    @Override
    public List<AccountDto> report(List<TransactionDto> transactions) {
        var start = Instant.now().toEpochMilli();

        int size = transactions.size();
        // we will generate a map of: accountId -> accountData (balance, number of transactions)
        Map<String, AccountDto> accountsMapById = new ConcurrentHashMap<>(2 * size);

        // for every transaction provided (t)
        transactions.stream().parallel().forEach(transaction -> {
            // take account number (receiver, giver)
            String creditAccount = transaction.getCreditAccount();
            String debitAccount = transaction.getDebitAccount();
            // create an empty account in the map if this is the first time we got it
            AccountDto accountDtoCredit = accountsMapById.computeIfAbsent(creditAccount, k -> new AccountDto(creditAccount));
            AccountDto accountDtoDebit = accountsMapById.computeIfAbsent(debitAccount, k -> new AccountDto(debitAccount));
            // make the balance change
            accountDtoCredit.addBalance(transaction.getAmount());
            accountDtoDebit.subtractBalance(transaction.getAmount());
        });

        Comparator<AccountDto> comparator = Comparator.comparing(AccountDto::getAccount);
        // create a list to return
        List<AccountDto> returnList = accountsMapById.values().parallelStream() // creat a parallel stream
                .sorted(comparator) // sort it in parallel
                .toList(); // then collect it in order

        if(LOGGER.isDebugEnabled()) {
            var end = Instant.now().toEpochMilli();
            LOGGER.debug(String.format("Time passed: %d. Input transactions: %d, accounts: %d.", end - start, size, returnList.size()));
        }

        return returnList;
    }


}
