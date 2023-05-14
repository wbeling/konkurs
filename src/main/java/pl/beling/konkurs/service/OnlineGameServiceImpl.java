package pl.beling.konkurs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.beling.konkurs.dtos.ClanDto;
import pl.beling.konkurs.dtos.PlayersDto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class OnlineGameServiceImpl implements OnlineGameService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineGameServiceImpl.class.getName());

    @Override
    public List<List<ClanDto>> calculate(PlayersDto players) {
        var start = Instant.now().toEpochMilli();

        Integer maxGroupSize = players.getGroupCount();
        int numberOfClass = players.getClans().size(); // (t)

        // we store the size of every group in order, instead of computing it each time (if there is a space to add)
        Integer[] groupSizes = new Integer[numberOfClass]; // the worst case - number of groups == number of clans

        List<List<ClanDto>> returnList = new ArrayList<>();

        returnList.add(new ArrayList<>(maxGroupSize)); // we already know the max group size
        groupSizes[0] = 0;

        List<ClanDto> clans = players.getClans()
                .parallelStream() // generate parallel stream
                .sorted( // sort it in parallel
                        (a, b) -> {
                            // clan with more points will be first
                            int result = b.getPoints() - a.getPoints();
                            if (result != 0) {
                                return result;
                            } else {
                                // in case of a draw, clan with a smaller number of players will be first
                                return a.getNumberOfPlayers() - b.getNumberOfPlayers();
                            }
                        })
                .toList(); // collect in the same order of the received after the sorted step

        // going over all clans (t)
        clans.forEach(clan -> {
            // flag to mark if we will need to create a new group
            boolean newGroupIsNeeded = true;
            // iterate over existing groups
            for (int i = 0; i < returnList.size(); i++) {
                Integer currentSizeOfGroup = groupSizes[i];
                if(currentSizeOfGroup < maxGroupSize) { // if the group is not full already
                    int newSizeOfGroup = currentSizeOfGroup + clan.getNumberOfPlayers();
                    if (newSizeOfGroup <= maxGroupSize) { // check if we won't exceed the max size by adding the clan
                        // we add this clan to this group with index i
                        returnList.get(i).add(clan);
                        // update current size
                        groupSizes[i] = newSizeOfGroup;
                        // we found the group, we won't have to create a new one
                        newGroupIsNeeded = false;
                        break;
                    }
                }
            }
            // in case if the new group is needed
            if (newGroupIsNeeded) {
                List<ClanDto> newList = new ArrayList<>(maxGroupSize); // we already know the max group size
                // add this clan
                newList.add(clan);
                // this new group will be the last
                returnList.add(newList);
                // update the size
                groupSizes[returnList.size() - 1] = clan.getNumberOfPlayers();
            }
        });

        if (LOGGER.isDebugEnabled()) {
            var end = Instant.now().toEpochMilli();
            LOGGER.debug(String.format("Time passed: %d. Input players clans size: %d, maxGroupSize: %d, output size: %d.",
                    end - start, numberOfClass, maxGroupSize, returnList.size()));
        }

        return returnList;
    }
}
