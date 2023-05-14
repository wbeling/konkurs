package pl.beling.konkurs.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.beling.konkurs.dtos.ClanDto;
import pl.beling.konkurs.dtos.PlayersDto;
import pl.beling.konkurs.service.OnlineGameService;

import java.util.List;

/**
 * REST Controller implementation for Online Games API
 */
@RestController
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
