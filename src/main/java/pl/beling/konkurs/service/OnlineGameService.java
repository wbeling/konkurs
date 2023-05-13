package pl.beling.konkurs.service;

import pl.beling.konkurs.dtos.output.ClanDto;
import pl.beling.konkurs.dtos.input.PlayersDto;

import java.util.List;

public interface OnlineGameService {
    List<List<ClanDto>> calculate(PlayersDto players);
}
