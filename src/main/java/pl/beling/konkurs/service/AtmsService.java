package pl.beling.konkurs.service;

import pl.beling.konkurs.dtos.output.ATMDto;
import pl.beling.konkurs.dtos.input.TaskDto;

import java.util.List;

public interface AtmsService {
    List<ATMDto> calculate(List<TaskDto> tasks);
}
