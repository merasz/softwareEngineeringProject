package at.qe.skeleton.api.controller;

import at.qe.skeleton.api.model.PiRequest;
import at.qe.skeleton.api.service.APIService;
import at.qe.skeleton.model.Raspberry;
import at.qe.skeleton.services.RaspberryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class APIController {

    @Autowired
    APIService apiService;

    @Autowired
    RaspberryService raspberryService;

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public class RequestUnauthorizedException extends Exception {
        private static final long serialVersionUID = 1L;
    }

    private void authorizeRequest(String ipAddress,String authToken) throws RequestUnauthorizedException {

        String[] input = authToken.split(" ");
        if(input.length < 2) {
            throw new RequestUnauthorizedException();
        }
        String type = input[0];
        String apiKey = input[1];

        if(!type.equals("Bearer")) {throw new RequestUnauthorizedException();}

        /* TODO: read token from database */
        String testToken = "7173b055-4674-4ca2-8348-60e1b3fa8204";

        Raspberry raspi = raspberryService.loadRaspberryByIp(ipAddress);
        if (raspi == null) {throw new RequestUnauthorizedException(); }
        if(!apiKey.equals(raspi.getApiKey())) {throw new RequestUnauthorizedException();}
    }


    @PatchMapping("/api/update")
    private void updateTimeFlipFacet(@RequestBody PiRequest piRequest, @RequestHeader("Authorization") String authToken) throws RequestUnauthorizedException {
        String ipAdress = piRequest.getIpAddress();
        authorizeRequest(ipAdress,authToken);
        apiService.updateTimeFlip(piRequest, authToken);
    }


}
