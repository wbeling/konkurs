package pl.beling.konkurs.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.beling.konkurs.dtos.input.TaskDto;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AtmsServiceExampleTest {
    private final AtmsService atmsService = new AtmsServiceImpl();

    /**
     * Test based on provided example 1
     */
    @Test
    void exampleCalculate1() {
        List<TaskDto> tasks = List.of(
                new TaskDto().region(4).requestType(TaskDto.RequestTypeEnum.STANDARD).atmId(1),
                new TaskDto().region(1).requestType(TaskDto.RequestTypeEnum.STANDARD).atmId(1),
                new TaskDto().region(2).requestType(TaskDto.RequestTypeEnum.STANDARD).atmId(1),
                new TaskDto().region(3).requestType(TaskDto.RequestTypeEnum.PRIORITY).atmId(2),
                new TaskDto().region(3).requestType(TaskDto.RequestTypeEnum.STANDARD).atmId(1),
                new TaskDto().region(2).requestType(TaskDto.RequestTypeEnum.SIGNAL_LOW).atmId(1),
                new TaskDto().region(5).requestType(TaskDto.RequestTypeEnum.STANDARD).atmId(2),
                new TaskDto().region(5).requestType(TaskDto.RequestTypeEnum.FAILURE_RESTART).atmId(1)
        );
        var result = atmsService.calculate(tasks);

        assertNotNull(result);
        assertEquals(7, result.size());

        var item = result.get(0);
        Assertions.assertEquals(1, item.getRegion());
        Assertions.assertEquals(1, item.getAtmId());

        item = result.get(1);
        Assertions.assertEquals(2, item.getRegion());
        Assertions.assertEquals(1, item.getAtmId());

        item = result.get(2);
        Assertions.assertEquals(3, item.getRegion());
        Assertions.assertEquals(2, item.getAtmId());

        item = result.get(3);
        Assertions.assertEquals(3, item.getRegion());
        Assertions.assertEquals(1, item.getAtmId());

        item = result.get(4);
        Assertions.assertEquals(4, item.getRegion());
        Assertions.assertEquals(1, item.getAtmId());

        item = result.get(5);
        Assertions.assertEquals(5, item.getRegion());
        Assertions.assertEquals(1, item.getAtmId());

        item = result.get(6);
        Assertions.assertEquals(5, item.getRegion());
        Assertions.assertEquals(2, item.getAtmId());
    }

    /**
     * Test based on provided example 2
     */
    @Test
    void exampleCalculate2() {
        List<TaskDto> tasks = List.of(
                new TaskDto().region(1).requestType(TaskDto.RequestTypeEnum.STANDARD).atmId(2),
                new TaskDto().region(1).requestType(TaskDto.RequestTypeEnum.STANDARD).atmId(1),
                new TaskDto().region(2).requestType(TaskDto.RequestTypeEnum.PRIORITY).atmId(3),
                new TaskDto().region(3).requestType(TaskDto.RequestTypeEnum.STANDARD).atmId(4),
                new TaskDto().region(4).requestType(TaskDto.RequestTypeEnum.STANDARD).atmId(5),
                new TaskDto().region(5).requestType(TaskDto.RequestTypeEnum.PRIORITY).atmId(2),
                new TaskDto().region(5).requestType(TaskDto.RequestTypeEnum.STANDARD).atmId(1),
                new TaskDto().region(3).requestType(TaskDto.RequestTypeEnum.SIGNAL_LOW).atmId(2),
                new TaskDto().region(2).requestType(TaskDto.RequestTypeEnum.SIGNAL_LOW).atmId(1),
                new TaskDto().region(3).requestType(TaskDto.RequestTypeEnum.FAILURE_RESTART).atmId(1)
        );
        var result = atmsService.calculate(tasks);

        assertNotNull(result);
        assertEquals(10, result.size());

        var item = result.get(0);
        Assertions.assertEquals(1, item.getRegion());
        Assertions.assertEquals(2, item.getAtmId());

        item = result.get(1);
        Assertions.assertEquals(1, item.getRegion());
        Assertions.assertEquals(1, item.getAtmId());

        item = result.get(2);
        Assertions.assertEquals(2, item.getRegion());
        Assertions.assertEquals(3, item.getAtmId());

        item = result.get(3);
        Assertions.assertEquals(2, item.getRegion());
        Assertions.assertEquals(1, item.getAtmId());

        item = result.get(4);
        Assertions.assertEquals(3, item.getRegion());
        Assertions.assertEquals(1, item.getAtmId());

        item = result.get(5);
        Assertions.assertEquals(3, item.getRegion());
        Assertions.assertEquals(2, item.getAtmId());

        item = result.get(6);
        Assertions.assertEquals(3, item.getRegion());
        Assertions.assertEquals(4, item.getAtmId());

        item = result.get(7);
        Assertions.assertEquals(4, item.getRegion());
        Assertions.assertEquals(5, item.getAtmId());

        item = result.get(8);
        Assertions.assertEquals(5, item.getRegion());
        Assertions.assertEquals(2, item.getAtmId());

        item = result.get(9);
        Assertions.assertEquals(5, item.getRegion());
        Assertions.assertEquals(1, item.getAtmId());
    }

}