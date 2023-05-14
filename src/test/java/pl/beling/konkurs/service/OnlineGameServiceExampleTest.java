package pl.beling.konkurs.service;

import org.junit.jupiter.api.Test;
import pl.beling.konkurs.dtos.ClanDto;
import pl.beling.konkurs.dtos.PlayersDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OnlineGameServiceExampleTest {
    private final OnlineGameService onlinegameService = new OnlineGameServiceImpl();

    /**
     * Test based on provided example
     */
    @Test
    void exampleTest() {
        PlayersDto input = new PlayersDto();
        input.setGroupCount(6);
        input.setClans(List.of(
                new ClanDto().numberOfPlayers(4).points(50),
                new ClanDto().numberOfPlayers(2).points(70),
                new ClanDto().numberOfPlayers(6).points(60),
                new ClanDto().numberOfPlayers(1).points(15),
                new ClanDto().numberOfPlayers(5).points(40),
                new ClanDto().numberOfPlayers(3).points(45),
                new ClanDto().numberOfPlayers(1).points(12),
                new ClanDto().numberOfPlayers(4).points(40)
        ));

        List<List<ClanDto>> result = onlinegameService.calculate(input);
        assertEquals(5, result.size());

        List<ClanDto> clansGroup = result.get(0);
        assertEquals(2, clansGroup.size());
        ClanDto clan = clansGroup.get(0);
        assertEquals(2, clan.getNumberOfPlayers());
        assertEquals(70, clan.getPoints());
        clan = clansGroup.get(1);
        assertEquals(4, clan.getNumberOfPlayers());
        assertEquals(50, clan.getPoints());

        clansGroup = result.get(1);
        assertEquals(1, clansGroup.size());
        clan = clansGroup.get(0);
        assertEquals(6, clan.getNumberOfPlayers());
        assertEquals(60, clan.getPoints());

        clansGroup = result.get(2);
        assertEquals(3, clansGroup.size());
        clan = clansGroup.get(0);
        assertEquals(3, clan.getNumberOfPlayers());
        assertEquals(45, clan.getPoints());
        clan = clansGroup.get(1);
        assertEquals(1, clan.getNumberOfPlayers());
        assertEquals(15, clan.getPoints());
        clan = clansGroup.get(2);
        assertEquals(1, clan.getNumberOfPlayers());
        assertEquals(12, clan.getPoints());

        clansGroup = result.get(3);
        assertEquals(1, clansGroup.size());
        clan = clansGroup.get(0);
        assertEquals(4, clan.getNumberOfPlayers());
        assertEquals(40, clan.getPoints());

        clansGroup = result.get(4);
        assertEquals(1, clansGroup.size());
        clan = clansGroup.get(0);
        assertEquals(5, clan.getNumberOfPlayers());
        assertEquals(40, clan.getPoints());
    }
}