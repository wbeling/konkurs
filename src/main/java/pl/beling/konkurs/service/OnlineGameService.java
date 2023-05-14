package pl.beling.konkurs.service;

import pl.beling.konkurs.dtos.ClanDto;
import pl.beling.konkurs.dtos.PlayersDto;

import java.util.List;

public interface OnlineGameService {
    List<List<ClanDto>> calculate(PlayersDto players);
}
