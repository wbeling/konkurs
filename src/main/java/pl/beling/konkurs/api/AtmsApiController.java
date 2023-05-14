package pl.beling.konkurs.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.beling.konkurs.dtos.ATMDto;
import pl.beling.konkurs.dtos.TaskDto;
import pl.beling.konkurs.service.AtmsService;

import java.util.List;

/**
 * REST Controller implementation for ATMs API
 */
@RestController
public class AtmsApiController implements AtmsApi {
    private final AtmsService atmsService;

    public AtmsApiController(AtmsService atmsService) {
        this.atmsService = atmsService;
    }

    @Override
    public ResponseEntity<List<ATMDto>> calculate(List<TaskDto> tasks) {
        return ResponseEntity.ok(atmsService.calculate(tasks));
    }
}
