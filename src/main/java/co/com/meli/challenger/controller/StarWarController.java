package co.com.meli.challenger.controller;

import co.com.meli.challenger.model.*;
import co.com.meli.challenger.repository.MessageRepository;
import co.com.meli.challenger.service.StarWarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class StarWarController {

    private static Double[] SAT_KENOBI = {-500.0, -200.0, 0.0};
    private static Double[] SAT_SKYWALKER = {100.0, -100.0, 0.0};
    private static Double[] SAT_SATO = {500.0, 100.0, 0.0};

    @Autowired
    private MessageRepository repository;

    private StarWarService starWarService = new StarWarService();

    @Operation(summary = "It processes information supplied by 3 satellites, in order to obtain the origin position of a coded message.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location of the origin of the transmission and complete message.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ChallengeResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Information error, incorrect data entry or impossibility to obtain the message.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class)) })})
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

        repository.save(new DataMessage(0, String.valueOf(SAT_KENOBI[2]), String.valueOf(SAT_SATO[2]), String.valueOf(SAT_SKYWALKER[2]), message,"NORMAL", String.valueOf(position.getX()), String.valueOf(position.getY())));

        return new ResponseEntity<Object>(challengeResponse, httpStatus);
    }

    @Operation(summary = "This service allows you to add the information of a satellite in split mode.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Satellite information successfully added.", content = { @Content() }),
            @ApiResponse(responseCode = "400", description = "There is an error when adding information, data entry error or attempt to update the data of an existing satellite.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class)) })})
    @PostMapping(value = "/topsecret_split/{satellite_name}")
    @ResponseBody
    public ResponseEntity<Object> addInfoSatelliteSplit(@Valid @RequestBody ChallengeRequestSplit challengeRequestSplit, @PathVariable("satellite_name") String satelliteName) throws Exception {
        HttpStatus httpStatus = HttpStatus.OK;

        Satellite satellite = new Satellite(satelliteName, challengeRequestSplit.getDistance(), challengeRequestSplit.getMessage());

        starWarService.addSatellite(satellite);

        return new ResponseEntity<Object>(null, httpStatus);
    }

    @Operation(summary = "This service allows you to update the information of a satellite in split mode.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Satellite information successfully updated.", content = { @Content() }),
            @ApiResponse(responseCode = "400", description = "Error in the data entered to update a satellite or if you want to update a satellite that has not been previously added.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class)) })})
    @PostMapping(value = "/topsecret_split/update/{satellite_name}")
    @ResponseBody
    public ResponseEntity<Object> updateInfoSatelliteSplit(@Valid @RequestBody ChallengeRequestSplit challengeRequestSplit, @PathVariable("satellite_name") String satelliteName) throws Exception {
        HttpStatus httpStatus = HttpStatus.OK;

        Satellite satellite = new Satellite(satelliteName, challengeRequestSplit.getDistance(), challengeRequestSplit.getMessage());

        starWarService.updateSatellite(satellite);

        return new ResponseEntity<Object>(null, httpStatus);
    }


    @Operation(summary = "This service allows you to query the information of a satellite in split mode.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of satellites stored for split mode", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ChallegeResponseQueryResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Error consulted the satellites.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class)) })})
    @GetMapping(value = "/topsecret_split/query")
    @ResponseBody
    public ResponseEntity<Object> queryInfoSatelliteSplit() throws Exception {
        HttpStatus httpStatus = HttpStatus.OK;

        ChallegeResponseQueryResponse challegeResponseQueryResponse = new ChallegeResponseQueryResponse(starWarService.querySatellite());

        return new ResponseEntity<Object>(challegeResponseQueryResponse, httpStatus);
    }

    @Operation(summary = "This service obtains the position and the message, if possible, of the satellites stored for the split mode.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The location and the message are returned, if the location does not converge, the warning variable is added", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ChallengeResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "An error occurred when querying the position or the message could not be obtained or the message is ambiguous.", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorInfo.class)) })})
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

        repository.save(new DataMessage(0, String.valueOf(SAT_KENOBI[2]), String.valueOf(SAT_SATO[2]), String.valueOf(SAT_SKYWALKER[2]), message,"SPLIT", String.valueOf(position.getX()), String.valueOf(position.getY())));

        SingletonSatellite.getInfoSatellites().setListSatellite(new ArrayList());

        return new ResponseEntity<Object>(challengeResponse, httpStatus);
    }

    @Operation(summary = "This service obtain log of message decode.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Array of log message satellites", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = RepositoryResponse.class)) })})
    @GetMapping(value = "/topsecret_repo")
    @ResponseBody
    public ResponseEntity<Object> getDataRepo() throws Exception {
        HttpStatus httpStatus = HttpStatus.OK;

        RepositoryResponse repositoryResponse = new RepositoryResponse(repository.findAll());

        return new ResponseEntity<Object>(repositoryResponse, httpStatus);
    }
}
