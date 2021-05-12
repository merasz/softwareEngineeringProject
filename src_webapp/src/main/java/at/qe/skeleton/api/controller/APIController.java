package at.qe.skeleton.api.controller;

import at.qe.skeleton.api.model.PiRequest;
import at.qe.skeleton.api.service.APIService;
import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Raspberry;
import at.qe.skeleton.services.GameService;
import at.qe.skeleton.services.RaspberryService;
import at.qe.skeleton.ui.controllers.gameSockets.GamePlaySocketController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class APIController {

    @Autowired
    APIService apiService;

    @Autowired
    RaspberryService raspberryService;

    /**
     * Exception which returns an 401 response code, when raised
     */
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public class RequestUnauthorizedException extends Exception {
        private static final long serialVersionUID = 1L;
    }

    /**
     * Exception which returns an 403 response code, when raised
     */
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public class RequestErrorException extends Exception {
        private static final long serialVersionUID = 2L;
    }


    /**
     * Method to authorize an incoming request based on the {@param authToken}.
     * @param ipAddress IPAddress of the request/Raspberry Pi
     * @param authToken AuthToken sent with the Request
     * @throws RequestUnauthorizedException if request cannot be authorized
     */
    private void authorizeRequest(String ipAddress,String authToken) throws RequestUnauthorizedException {

        String[] input = authToken.split(" ");
        if(input.length < 2) {
            throw new RequestUnauthorizedException();
        }
        String type = input[0];
        String apiKey = input[1];

        if(!type.equals("Bearer")) {throw new RequestUnauthorizedException();}

        Raspberry raspi = raspberryService.loadRaspberryByIp(ipAddress);
        if (raspi == null) {throw new RequestUnauthorizedException(); }
        if(!apiKey.equals(raspi.getApiKey())) {throw new RequestUnauthorizedException();}
    }


    /**
     * Method to get an API Key for a Raspberry in the System
     * @param ipAddress of the request/Raspberry Pi which needs an API Key
     * @return the API Key
     * @throws RequestErrorException if no PI with this IP Address is present in the system
     */
    @GetMapping("/api/apikey")
    private String getApiKey(@RequestParam String ipAddress) throws RequestErrorException {

        Raspberry raspi = raspberryService.loadRaspberryByIp(ipAddress);
        if (raspi == null) {throw new RequestErrorException(); }

        String apiKey = raspi.getApiKey();
        if(apiKey != null && !apiKey.equals("")) {
            return apiKey;
        } else {
            return apiService.generateApiKeyForRaspberry(raspi);
        }

    }

    /**
     * Method to process an incoming TimeFlip update.
     * @param piRequest request from a Raspberry Pi
     * @param authToken Authooritation Token
     * @throws RequestUnauthorizedException if Raspberry cannot be authorized
     */
    @PatchMapping("/api/update")
    private void updateTimeFlipFacet(@RequestBody PiRequest piRequest, @RequestHeader("Authorization") String authToken) throws RequestUnauthorizedException {
        String ipAdress = piRequest.getIpAddress();
        authorizeRequest(ipAdress,authToken);
        apiService.updateTimeFlip(piRequest);
    }


}
