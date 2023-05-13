package pl.beling.konkurs.api;

import pl.beling.konkurs.dtos.output.ATMDto;
import pl.beling.konkurs.dtos.input.TaskDto;
import pl.beling.konkurs.service.AtmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
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
