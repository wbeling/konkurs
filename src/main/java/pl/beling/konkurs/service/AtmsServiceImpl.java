package pl.beling.konkurs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.beling.konkurs.dtos.ATMDto;
import pl.beling.konkurs.dtos.TaskDto;

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

        int tasksSize = tasks.size(); // or just (t)
        // this will contain the ordered list of atm ids per region
        Map<Integer, Set<Integer>> mapRegionToSetOfAtms = new ConcurrentHashMap<>(NUMBER_OF_STATUSES * MAX_TASKS_SIZE_PER_REGION_IN_RESULT);

        Map<Integer, List<TaskDto>> tasksListByRegionId = new HashMap<>();
        // create a list for every region - we need to iterate over all tasks once (t)
        tasks.forEach(task -> {
            var regionId = task.getRegion();
            // create a list and add it to map if is not present
            var list = tasksListByRegionId.computeIfAbsent(regionId, k -> new ArrayList<>()); // dynamic size because there is no limit of repeated tasks
            // add task to list per region
            list.add(task);
        });

        var end1 = Instant.now().toEpochMilli();

        // we can split work per region, tasks in different regions are not related at all
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
                // because the result is LinkedHashSet then we will not add duplicates
                result.addAll(list1);
                result.addAll(list2);
                result.addAll(list3);
                result.addAll(list4);
                mapRegionToSetOfAtms.put(regionId, result);
            }
        });

        var end2 = Instant.now().toEpochMilli();

        // prepare the ordered set of regions
        Set<Integer> orderedRegionSet = prepareRegionSet(tasks);
        // prepare the result with correct size
        List<ATMDto> result = new ArrayList<>(orderedRegionSet.size() * (Math.min(tasksSize, MAX_TASKS_SIZE_PER_REGION_IN_RESULT)));
        // fill the list (we conter map<regionId, list of atm ids> to list of objects that have region id and the ATM id
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
