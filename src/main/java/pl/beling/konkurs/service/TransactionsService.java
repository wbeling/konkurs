package pl.beling.konkurs.service;

import pl.beling.konkurs.dtos.AccountDto;
import pl.beling.konkurs.dtos.TransactionDto;

import java.util.List;

public interface TransactionsService {
    List<AccountDto> report(List<TransactionDto> transactions);
}
