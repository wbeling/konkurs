package pl.beling.konkurs.api;

import pl.beling.konkurs.dtos.output.ClanDto;
import pl.beling.konkurs.dtos.input.PlayersDto;
import pl.beling.konkurs.service.OnlineGameService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OnlineGameApiController implements OnlineGameApi {
    private final OnlineGameService onlinegameService;

    public OnlineGameApiController(OnlineGameService onlinegameService) {
        this.onlinegameService = onlinegameService;
    }

    @Override
    public ResponseEntity<List<List<ClanDto>>> calculate(PlayersDto players) {
        return ResponseEntity.ok(onlinegameService.calculate(players));
    }
}
