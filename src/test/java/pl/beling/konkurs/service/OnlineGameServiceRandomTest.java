package pl.beling.konkurs.service;

import org.junit.jupiter.api.RepeatedTest;
import pl.beling.konkurs.dtos.input.PlayersDto;
import pl.beling.konkurs.dtos.output.ClanDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Few tests based on random values, most to check how fast implementation is
 */
class OnlineGameServiceRandomTest {
    private final OnlineGameService onlinegameService = new OnlineGameServiceImpl();

    @RepeatedTest(10)
    void randomMaxTest() {
        Random r = new Random();
        PlayersDto input = new PlayersDto();
        // size of the group: 1-1000
        input.setGroupCount(1000);
        // clan number of player: 1-1000, but always less than size of the group
        // points: 1-100000
        // number of clans - up to 20k
        List<ClanDto> list = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            list.add(new ClanDto().numberOfPlayers(1 + r.nextInt(999)).points(r.nextInt(100000)));
        }
        ;
        input.setClans(list);

        List<List<ClanDto>> result = onlinegameService.calculate(input);
        assertNotNull(result);
    }

    @RepeatedTest(10)
    void randomSmallClansTest() {
        Random r = new Random();
        PlayersDto input = new PlayersDto();
        // size of the group: 1-1000
        input.setGroupCount(1000);
        // clan number of player: 1-1000, but always less than size of the group
        // points: 1-100000
        // number of clans - up to 20k
        List<ClanDto> list = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            list.add(new ClanDto().numberOfPlayers(1 + r.nextInt(9)).points(r.nextInt(100000)));
        }
        ;
        input.setClans(list);

        List<List<ClanDto>> result = onlinegameService.calculate(input);
        assertNotNull(result);
    }

    @RepeatedTest(10)
    void randomBigClansTest() {
        Random r = new Random();
        PlayersDto input = new PlayersDto();
        // size of the group: 1-1000
        input.setGroupCount(1000);
        // clan number of player: 1-1000, but always less than size of the group
        // points: 1-100000
        // number of clans - up to 20k
        List<ClanDto> list = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            list.add(new ClanDto().numberOfPlayers(450 + r.nextInt(549)).points(r.nextInt(100000)));
        }
        ;
        input.setClans(list);

        List<List<ClanDto>> result = onlinegameService.calculate(input);
        assertNotNull(result);
    }

    @RepeatedTest(10)
    void randomWorstSizeClansTest() {
        Random r = new Random();
        PlayersDto input = new PlayersDto();
        // size of the group: 1-1000
        input.setGroupCount(1000);
        // clan number of player: 1-1000, but always less than size of the group
        // points: 1-100000
        // number of clans - up to 20k
        List<ClanDto> list = new ArrayList<>();
        for (int i = 0; i < 20000; i++) {
            list.add(new ClanDto().numberOfPlayers(1000).points(r.nextInt(100000)));
        }
        ;
        input.setClans(list);

        List<List<ClanDto>> result = onlinegameService.calculate(input);
        assertNotNull(result);
    }
}