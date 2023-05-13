package pl.beling.konkurs.service;

import pl.beling.konkurs.dtos.output.AccountDto;
import pl.beling.konkurs.dtos.input.TransactionDto;

import java.util.List;

public interface TransactionsService {
    List<AccountDto> report(List<TransactionDto> transactions);
}
