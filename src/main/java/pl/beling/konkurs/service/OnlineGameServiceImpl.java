package pl.beling.konkurs.service;

import pl.beling.konkurs.dtos.input.PlayersDto;
import pl.beling.konkurs.dtos.output.ClanDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        Integer[] groupSizes = new Integer[players.getClans().size()];
        List<List<ClanDto>> returnList = new ArrayList<>();
        returnList.add(new ArrayList<>());
        groupSizes[0] = 0;

        List<ClanDto> clans = players.getClans()
                .stream()
                .sorted(
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
                .toList();

        clans.forEach(clan -> {
            boolean newGroupIsNeeded = true;
            for (int i = 0; i < returnList.size(); i++) {
                Integer currentSizeOfGroup = groupSizes[i];
                int newSizeOfGroup = currentSizeOfGroup + clan.getNumberOfPlayers();
                if (newSizeOfGroup <= maxGroupSize) {
                    // we add this clan to this group (group with index i)
                    returnList.get(i).add(clan);
                    groupSizes[i] = newSizeOfGroup;
                    newGroupIsNeeded = false;
                    break;
                }
            }
            if (newGroupIsNeeded) {
                List<ClanDto> newList = new ArrayList<>();
                newList.add(clan);
                returnList.add(newList);
                groupSizes[returnList.size() - 1] = clan.getNumberOfPlayers();
            }
        });

        if (LOGGER.isDebugEnabled()) {
            var end = Instant.now().toEpochMilli();
            LOGGER.debug(String.format("Time passed: %d. Input players clans size: %d, maxGroupSize: %d, output size: %d.",
                    end - start, players.getClans().size(), maxGroupSize, returnList.size()));
        }

        return returnList;
    }
}
