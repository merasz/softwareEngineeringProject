package at.qe.skeleton.api.service;

import at.qe.skeleton.api.model.PiRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class APIService {

    private static final AtomicLong ID_COUNTER = new AtomicLong(1);

    /* Test Method */
    public String getString(String input) {

        return "ID: " + ID_COUNTER.getAndIncrement()  + " " + input;
    }


    public void updateTimeFlip(PiRequest piRequest, String authToken) {

        System.out.println("MAC: " + piRequest.getMacAddress());
        System.out.println("Facet: " + piRequest.getFacetId());

        PiRequest newRequest = new PiRequest();
        newRequest.setMacAddress(piRequest.getMacAddress());
        newRequest.setFacetId(piRequest.getFacetId());

    }
}
