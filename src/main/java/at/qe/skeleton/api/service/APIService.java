package at.qe.skeleton.api.service;

import at.qe.skeleton.api.controller.APIController;
import at.qe.skeleton.api.model.PiRequest;
import at.qe.skeleton.model.Raspberry;
import at.qe.skeleton.repositories.RaspberryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class APIService {

    @Autowired
    RaspberryRepository raspberryRepository;


    public String generateApiKeyForRaspberry(Raspberry raspberry) {

        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 36;
        Random random = new Random();

        /* https://www.baeldung.com/java-random-string */
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        raspberry.setApiKey(generatedString);
        raspberryRepository.save(raspberry);

        return generatedString;
    }

    public void updateTimeFlip(PiRequest piRequest, String authToken) {

        System.out.println("IP: " + piRequest.getIpAddress());
        System.out.println("Facet: " + piRequest.getFacetId());

        PiRequest newRequest = new PiRequest();
        newRequest.setIpAddress(piRequest.getIpAddress());
        newRequest.setFacetId(piRequest.getFacetId());

    }
}
