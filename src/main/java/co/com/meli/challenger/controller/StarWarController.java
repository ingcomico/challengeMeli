package co.com.meli.challenger.controller;

import co.com.meli.challenger.model.*;
import co.com.meli.challenger.service.StarWarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class StarWarController {

    private static Double[] SAT_KENOBI = {-500.0, -200.0, 0.0};
    private static Double[] SAT_SKYWALKER = {100.0, -100.0, 0.0};
    private static Double[] SAT_SATO = {500.0, 100.0, 0.0};
    ;

    private StarWarService starWarService = new StarWarService();

    @PostMapping(value = "/topsecret")
    @ResponseBody
    public ResponseEntity<Object> processDataSatellites(@Valid @RequestBody ChallengeRequest challengeRequest) throws Exception {
        HttpStatus httpStatus = HttpStatus.OK;

        ChallengeResponse challengeResponse = new ChallengeResponse();

        starWarService.getDistanceToSatellite(challengeRequest.getSatellites(), SAT_KENOBI, SAT_SKYWALKER, SAT_SATO);

        Position position = starWarService.getPosition(SAT_KENOBI, SAT_SKYWALKER, SAT_SATO);

        challengeResponse =  starWarService.validatePosition(SAT_KENOBI, SAT_SKYWALKER, SAT_SATO, position, challengeResponse);

        String message = starWarService.getMessage(challengeRequest.getSatellites());

        challengeResponse.setPosition(position);
        challengeResponse.setMessage(message);

        return new ResponseEntity<Object>(challengeResponse, httpStatus);
    }

    @PostMapping(value = "/topsecret_split/{satellite_name}")
    @ResponseBody
    public ResponseEntity<Object> addInfoSatelliteSplit(@Valid @RequestBody ChallengeRequestSplit challengeRequestSplit, @PathVariable("satellite_name") String satelliteName) throws Exception {
        HttpStatus httpStatus = HttpStatus.OK;

        Satellite satellite = new Satellite(satelliteName, challengeRequestSplit.getDistance(), challengeRequestSplit.getMessage());

        List<Satellite> listSatellite = starWarService.addSatellite(satellite);

        return new ResponseEntity<Object>(listSatellite, httpStatus);
    }

    @GetMapping(value = "/topsecret_split")
    @ResponseBody
    public ResponseEntity<Object> processDataSatellitesSplit() throws Exception {
        HttpStatus httpStatus = HttpStatus.OK;

        ChallengeResponse challengeResponse =  new ChallengeResponse();

        ChallengeRequest challengeRequestSplit = new ChallengeRequest(SingletonSatellite.getInfoSatellites().getListSatellite());

        starWarService.objectValidate(challengeRequestSplit);

        starWarService.getDistanceToSatellite(challengeRequestSplit.getSatellites(), SAT_KENOBI, SAT_SKYWALKER, SAT_SATO);

        Position position = starWarService.getPosition(SAT_KENOBI, SAT_SKYWALKER, SAT_SATO);

        challengeResponse =  starWarService.validatePosition(SAT_KENOBI, SAT_SKYWALKER, SAT_SATO, position, challengeResponse);

        String message = starWarService.getMessage(challengeRequestSplit.getSatellites());

        challengeResponse.setPosition(position);
        challengeResponse.setMessage(message);

        SingletonSatellite.getInfoSatellites().setListSatellite(new ArrayList());

        return new ResponseEntity<Object>(challengeResponse, httpStatus);
    }
}
