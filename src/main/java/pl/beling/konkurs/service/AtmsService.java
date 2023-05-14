package pl.beling.konkurs.service;

import pl.beling.konkurs.dtos.ATMDto;
import pl.beling.konkurs.dtos.TaskDto;

import java.util.List;

@FunctionalInterface
public interface AtmsService {
    List<ATMDto> calculate(List<TaskDto> tasks);
}
