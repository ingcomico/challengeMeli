package co.com.meli.challenger.service;

import co.com.meli.challenger.handler.CustomException;
import co.com.meli.challenger.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StarWarService {

    private static final String KENOBI = "kenobi";
    private static final String SKYWALKER = "skywalker";
    private static final String SATO = "sato";

    public void getDistanceToSatellite(List<Satellite> listSatellite, Double[] SAT_KENOBI, Double[] SAT_SKYWALKER, Double[] SAT_SATO) throws Exception {
        List<Satellite> listSatelliteKenobi = listSatellite.stream().filter(satellite -> satellite.getName().equalsIgnoreCase(KENOBI)).collect(Collectors.toList());
        List<Satellite> listSatelliteSkyWalker = listSatellite.stream().filter(satellite -> satellite.getName().equalsIgnoreCase(SKYWALKER)).collect(Collectors.toList());
        List<Satellite> listSatelliteSato = listSatellite.stream().filter(satellite -> satellite.getName().equalsIgnoreCase(SATO)).collect(Collectors.toList());
        SAT_KENOBI[2] = (listSatelliteKenobi!=null && listSatelliteKenobi.size()==1)?listSatelliteKenobi.get(0).getDistance():0.0;
        SAT_SKYWALKER[2] = (listSatelliteSkyWalker!=null && listSatelliteSkyWalker.size()==1)?listSatelliteSkyWalker.get(0).getDistance():0.0;
        SAT_SATO[2] = (listSatelliteSato!=null && listSatelliteSato.size()==1)?listSatelliteSato.get(0).getDistance():0.0;
        if(SAT_KENOBI[2] == 0 || SAT_SKYWALKER[2] == 0 || SAT_SATO[2] == 0 ){
            throw new CustomException(HttpStatus.BAD_REQUEST, "Error with the data provided by the satellite, please check the data before continuing.");
        }
    }

    public String getMessage(List<Satellite> listSatellite) throws Exception {
        boolean flag = true;
        List<String> listFinalMessage = new ArrayList();
        String finalMessage = "";
        for(Satellite message:listSatellite){
            if(flag){
                listFinalMessage.addAll(message.getMessage());
                listFinalMessage.replaceAll(s -> (s == null || s.isEmpty())?"...":s);
                flag = false;
            }else {
                int count = 0;
                for (String dataMsg : message.getMessage()) {
                    if (dataMsg != null && !dataMsg.isEmpty() && !dataMsg.equals("...")) {
                        if(!listFinalMessage.get(count).equals("...") && listFinalMessage.get(count)!=dataMsg){
                            throw new CustomException(HttpStatus.BAD_REQUEST, "The message intercepted by the satellites is ambiguous, different text in the same position. [" + listFinalMessage.get(count) + "!=" + dataMsg+  "].");
                        }
                        listFinalMessage.set(count, dataMsg);
                    }
                    count++;
                }
            }
        }
        for(String data:listFinalMessage){
            if(data != null && !data.isEmpty() && !data.equals("...")){
                finalMessage += data + " ";
            }else{
                throw new CustomException(HttpStatus.BAD_REQUEST, "Error receiving message, corrupt message.");
            }
        }
        return finalMessage.trim();
    }

    public Position getPosition(Double[] SAT_KENOBI, Double[] SAT_SKYWALKER, Double[] SAT_SATO){

        double x = ( ( ( (Math.pow(SAT_KENOBI[2],2)-Math.pow(SAT_SKYWALKER[2],2)) + (Math.pow(SAT_SKYWALKER[0],2)-Math.pow(SAT_KENOBI[0],2)) + (Math.pow(SAT_SKYWALKER[1],2)-Math.pow(SAT_KENOBI[1],2)) ) * (2*SAT_SATO[1]-2*SAT_SKYWALKER[1]) - ( (Math.pow(SAT_SKYWALKER[2],2)-Math.pow(SAT_SATO[2],2)) + (Math.pow(SAT_SATO[0],2)-Math.pow(SAT_SKYWALKER[0],2)) + (Math.pow(SAT_SATO[1],2)-Math.pow(SAT_SKYWALKER[1],2)) ) * (2*SAT_SKYWALKER[1]-2*SAT_KENOBI[1]) ) / ( (2*SAT_SKYWALKER[0]-2*SAT_SATO[0]) * (2*SAT_SKYWALKER[1]-2*SAT_KENOBI[1]) - (2*SAT_KENOBI[0]-2*SAT_SKYWALKER[0]) * (2*SAT_SATO[1]-2*SAT_SKYWALKER[1] ) ) );

        double y = ( (Math.pow(SAT_KENOBI[2],2)-Math.pow(SAT_SKYWALKER[2],2)) + (Math.pow(SAT_SKYWALKER[0],2)-Math.pow(SAT_KENOBI[0],2)) + (Math.pow(SAT_SKYWALKER[1],2)-Math.pow(SAT_KENOBI[1],2)) + x*(2*SAT_KENOBI[0]-2*SAT_SKYWALKER[0])) / (2*SAT_SKYWALKER[1]-2*SAT_KENOBI[1]);

        return new Position(x, y);
    }

    public ChallengeResponse validatePosition(Double[] SAT_KENOBI, Double[] SAT_SKYWALKER, Double[] SAT_SATO, Position position, ChallengeResponse challengeResponse) {
        double result1 = Math.sqrt(Math.pow((SAT_KENOBI[0]-position.getX()),2) + Math.pow((SAT_KENOBI[1]-position.getY()),2)) - SAT_KENOBI[2];
        double result2 = Math.sqrt(Math.pow((SAT_SKYWALKER[0]-position.getX()),2) + Math.pow((SAT_SKYWALKER[1]-position.getY()),2)) -SAT_SKYWALKER[2];
        double result3 = Math.sqrt(Math.pow((SAT_SATO[0]-position.getX()),2) + Math.pow((SAT_SATO[1]-position.getY()),2)) - SAT_SATO[2];
        if(result1 != 0 && result2 != 0 && result3 != 0){
            challengeResponse = new ChallengeResponseWarning( "The position is approximate, the distances provided by the satellites do not converge.");
        }
        return challengeResponse;
    }

    public List<Satellite> addSatellite(Satellite satellite) throws CustomException {
        if(!(satellite.getName().equalsIgnoreCase("kenobi") || satellite.getName().equalsIgnoreCase("skywalker") || satellite.getName().equalsIgnoreCase("sato"))){
            throw  new CustomException(HttpStatus.BAD_REQUEST, "The satellite that is sending information is not registered.");
        }
        SingletonSatellite singletonSatellite = SingletonSatellite.getInfoSatellites();
        if(singletonSatellite.getListSatellite().isEmpty()){
            singletonSatellite.getListSatellite().add(satellite);
        }else{
            if(singletonSatellite.getListSatellite().stream().anyMatch(x -> x.getName().equalsIgnoreCase(satellite.getName()))){
                singletonSatellite.getListSatellite().replaceAll(x -> x.getName().equalsIgnoreCase(satellite.getName()) ? satellite : x);
            }else{
                singletonSatellite.getListSatellite().add(satellite);
            }
        }
        return singletonSatellite.getListSatellite();
    }

    public void objectValidate(Object object) throws Exception {
        String dataError = "";
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        for(ConstraintViolation<Object> data:violations){
            dataError += data.getPropertyPath() + " : " + data.getMessage() + "|";
        }
        if(!violations.isEmpty())
            throw new Exception(dataError);
    }
}
