package pl.beling.konkurs.api;

import pl.beling.konkurs.dtos.output.AccountDto;
import pl.beling.konkurs.dtos.input.TransactionDto;
import pl.beling.konkurs.service.TransactionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
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
