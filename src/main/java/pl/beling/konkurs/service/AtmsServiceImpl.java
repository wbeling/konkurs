package pl.beling.konkurs.service;

import pl.beling.konkurs.dtos.input.TaskDto;
import pl.beling.konkurs.dtos.output.ATMDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AtmsServiceImpl implements AtmsService {
    private static final int MAX_REGIONS = 10_000;
    private static final int MAX_TASKS_SIZE_PER_REGION_IN_RESULT = 10_000 - 1;
    private static final int NUMBER_OF_STATUSES = 4;
    private static final Logger LOGGER = LoggerFactory.getLogger(AtmsServiceImpl.class.getName());

    @Override
    public List<ATMDto> calculate(List<TaskDto> tasks) {
        var start = Instant.now().toEpochMilli();

        int tasksSize = tasks.size();
        Map<Integer, Set<Integer>> mapRegionToSetOfAtms = new ConcurrentHashMap<>(NUMBER_OF_STATUSES * MAX_TASKS_SIZE_PER_REGION_IN_RESULT);

        Set<Integer> orderedRegionSet = prepareRegionSet(tasks);

        var end1 = Instant.now().toEpochMilli();

        Map<Integer, List<TaskDto>> tasksListByRegionId = new HashMap<>();
        tasks.forEach(task -> {
            var regionId = task.getRegion();
            var list = tasksListByRegionId.computeIfAbsent(regionId, k -> new ArrayList<>());
            list.add(task);
        });

        tasksListByRegionId.values().parallelStream().forEach(list -> {
            if (!list.isEmpty()) {
                var regionId = list.get(0).getRegion();
                Set<Integer> result = new LinkedHashSet<>();
                List<Integer> list1 = new ArrayList<>();
                List<Integer> list2 = new ArrayList<>();
                List<Integer> list3 = new ArrayList<>();
                List<Integer> list4 = new ArrayList<>();

                for (TaskDto task : list) {
                    var requestType = task.getRequestType();
                    var atmId = task.getAtmId();
                    switch (requestType) {
                        case FAILURE_RESTART -> list1.add(atmId);
                        case PRIORITY -> list2.add(atmId);
                        case SIGNAL_LOW -> list3.add(atmId);
                        default -> list4.add(atmId);
                    }
                }

                result.addAll(list1);
                result.addAll(list2);
                result.addAll(list3);
                result.addAll(list4);
                mapRegionToSetOfAtms.put(regionId, result);
            }
        });

        var end2 = Instant.now().toEpochMilli();

        List<ATMDto> result = new ArrayList<>(orderedRegionSet.size() * (Math.min(tasksSize, MAX_TASKS_SIZE_PER_REGION_IN_RESULT)));
        for (Integer regionId : orderedRegionSet) {
            Set<Integer> listOfAtms = mapRegionToSetOfAtms.get(regionId);
            if (listOfAtms != null && !listOfAtms.isEmpty()) {
                listOfAtms.forEach(atmId -> result.add(new ATMDto().region(regionId).atmId(atmId)));
            }
        }

        var end3 = Instant.now().toEpochMilli();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(
                    String.format("Time passed (1): %d, (2): %d, (3): %d. Input tasks: %d, output size: %d.",
                            end1 - start, end2 - start, end3 - start, tasksSize, result.size())
            );
        }

        return result;
    }

    private Set<Integer> prepareRegionSet(List<TaskDto> tasks) {
        Set<Integer> set = new TreeSet<>();

        if (tasks.size() < MAX_REGIONS) {
            tasks.forEach(task -> {
                var regionId = task.getRegion();
                set.add(regionId);
            });
        } else {
            for (int regionId = 1; regionId < MAX_REGIONS; regionId++) {
                set.add(regionId);
            }
        }

        return set;
    }
}
