package pl.beling.konkurs.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.beling.konkurs.dtos.AccountDto;
import pl.beling.konkurs.dtos.TransactionDto;
import pl.beling.konkurs.service.TransactionsService;

import java.util.List;

/**
 * REST Controller implementation for Transactions API
 */
@RestController
public class TransactionsApiController implements TransactionsApi {
    private final TransactionsService transactionsService;

    public TransactionsApiController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @Override
    public ResponseEntity<List<AccountDto>> report(List<TransactionDto> transactions) {
        return ResponseEntity.ok(transactionsService.report(transactions));
    }
}
