package pl.beling.konkurs.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.beling.konkurs.dtos.TaskDto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Few tests based on random values, most to check how fast implementation is
 */
class AtmsServiceRandomTest {
    @ParameterizedTest
    @CsvSource({
            "2,2",
            "10,2",
            "10,10",
            "10,100",
            "100,2",
            "100,10",
            "100,100",
            "100,1000",
            "1000,2",
            "1000,10",
            "1000,100",
            "1000,1000",
            "1000,10000",
            "10000,2",
            "10000,10",
            "10000,100",
            "10000,1000"
    })
    void testMaxSorted(int maxRegions, int maxAtms) {
        List<TaskDto> tasks = new ArrayList<>();
        for (int regionId = 1; regionId < maxRegions; regionId++) {
            for (int atmId = 1; atmId < maxAtms; atmId++) {
                tasks.add(new TaskDto().region(regionId).requestType(TaskDto.RequestTypeEnum.FAILURE_RESTART).atmId(atmId));
            }
        }
        var result = atmsService.calculate(tasks);

        assertNotNull(result);
        assertEquals((maxRegions - 1) * (maxAtms - 1), result.size());

        var first = result.get(0);
        assertEquals(1, first.getRegion());
        assertEquals(1, first.getAtmId());

        var last = result.get(result.size() - 1);
        assertEquals(maxRegions - 1, last.getRegion());
        assertEquals(maxAtms - 1, last.getAtmId());
    }

    private final AtmsService atmsService = new AtmsServiceImpl();

    @ParameterizedTest
    @CsvSource({
            "4,2,2",
            "20,10,2",
            "100,10,10",
            "1000,10,100",
            "200,100,2",
            "1000,100,10",
            "10000,100,100",
            "100000,100,1000",
            "2000,1000,2",
            "10000,1000,10",
            "100000,1000,100",
            "1000000,1000,1000",
            "20000,100,100",
            "100000,100,100",
            "1000000,100,100",
            "10000000,100,100",
            "100000000,100,100",
            "100000000,2,100",
            "100000000,100,200",
            "100000000,100,300",
            "100000000,100,400",
            "100000000,100,500",
            "100000000,100,600",
            "100000000,100,700",
            "100000000,100,800",
            "100000000,100,900",
            "100000000,100,1000",
            "100000000,1000,1000"
    })
    void testRandom(int maxTasks, int maxRegionNumber, int maxAtmsNumber) {
        List<TaskDto> tasks = new ArrayList<>();
        Random random = new Random();
        Map<Integer, Set<Integer>> mapRegionToAtms = new TreeMap<>();
        int maxEntries = (maxRegionNumber - 1) * (maxAtmsNumber - 1);
        int currentSize = 0;
        tasks.add(new TaskDto().region(1).atmId(1).requestType(TaskDto.RequestTypeEnum.FAILURE_RESTART));
        for (int i = 1; i < maxTasks; i++) {
            var regionId = 1 + random.nextInt(maxRegionNumber - 1);
            var atmId = 1 + random.nextInt(maxAtmsNumber - 1);
            var set = mapRegionToAtms.computeIfAbsent(regionId, (k) -> new TreeSet<>());
            if (!set.contains(atmId)) {
                var type = random.nextInt(4);
                if (type == 0) {
                    tasks.add(new TaskDto().region(regionId).requestType(TaskDto.RequestTypeEnum.STANDARD).atmId(atmId));
                } else if (type == 1) {
                    tasks.add(new TaskDto().region(regionId).requestType(TaskDto.RequestTypeEnum.SIGNAL_LOW).atmId(atmId));
                } else if (type == 2) {
                    tasks.add(new TaskDto().region(regionId).requestType(TaskDto.RequestTypeEnum.PRIORITY).atmId(atmId));
                } else {
                    tasks.add(new TaskDto().region(regionId).requestType(TaskDto.RequestTypeEnum.FAILURE_RESTART).atmId(atmId));
                }
                set.add(atmId);
                currentSize++;
            }
            if (currentSize == maxEntries) {
                break;
            }
        }

        var result = atmsService.calculate(tasks);

        assertNotNull(result);

        var first = result.get(0);
        assertEquals(1, first.getRegion());
        assertEquals(1, first.getAtmId());
    }

    @Test
    void testAlmostFull() {
        List<TaskDto> tasks = new ArrayList<>();
        Random random = new Random();
        int maxRegions = 8000 + random.nextInt(2000);
        System.out.println("MaxRegions: " + maxRegions);
        List<Integer> regions = new ArrayList<>(9999);
        for (int i = 1; i < 10000; i++) {
            regions.add(i);
        }
        while (regions.size() > maxRegions) { // fix it somehow
            regions.remove(random.nextInt(regions.size()));
        }
        regions.forEach(regionId -> {
            for (int atmId = 1; atmId < 1000; atmId++) {
                var type = random.nextInt(4);
                if (type == 0) {
                    tasks.add(new TaskDto().region(regionId).requestType(TaskDto.RequestTypeEnum.STANDARD).atmId(atmId));
                } else if (type == 1) {
                    tasks.add(new TaskDto().region(regionId).requestType(TaskDto.RequestTypeEnum.SIGNAL_LOW).atmId(atmId));
                } else if (type == 2) {
                    tasks.add(new TaskDto().region(regionId).requestType(TaskDto.RequestTypeEnum.PRIORITY).atmId(atmId));
                } else {
                    tasks.add(new TaskDto().region(regionId).requestType(TaskDto.RequestTypeEnum.FAILURE_RESTART).atmId(atmId));
                }
            }
        });
        System.out.println("TasksTotal: " + tasks.size());

        int max = Math.min(tasks.size(), 1000000);
        StringBuilder s = new StringBuilder("[");
        for (int i = 0; i < max; i++) {
            if (i > 0) {
                s.append(",");
            }
            s.append(asJson(tasks.get(i)));
        }
        s.append("]");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("ing-test-file1.json"));
            writer.write(s.toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var result = atmsService.calculate(tasks);

        assertNotNull(result);
    }

    private String asJson(TaskDto task) {
        return "{\"region\":" + task.getRegion() + ",\"atmId\":" + task.getAtmId() + ",\"requestType\":\"" + task.getRequestType().toString() + "\"}";
    }

    @Test
    void memoryTest1() {
        List<TaskDto> tasks = new ArrayList<>(4 * 999 * 999);
        for (int i = 1; i < 1000; i++) {
            for (int j = 1; j < 1000; j++) {
                tasks.add(new TaskDto().region(i).atmId(j).requestType(TaskDto.RequestTypeEnum.STANDARD));
                tasks.add(new TaskDto().region(i).atmId(j).requestType(TaskDto.RequestTypeEnum.PRIORITY));
                tasks.add(new TaskDto().region(i).atmId(j).requestType(TaskDto.RequestTypeEnum.SIGNAL_LOW));
                tasks.add(new TaskDto().region(i).atmId(j).requestType(TaskDto.RequestTypeEnum.FAILURE_RESTART));
            }
        }
        assertEquals(4 * 999 * 999, tasks.size());
    }
}