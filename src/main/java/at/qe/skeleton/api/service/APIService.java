package at.qe.skeleton.api.service;

import at.qe.skeleton.api.model.PiRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class APIService {

    private static final AtomicLong ID_COUNTER = new AtomicLong(1);


    public void updateTimeFlip(PiRequest piRequest, String authToken) {

        System.out.println("IP: " + piRequest.getIpAddress());
        System.out.println("Facet: " + piRequest.getFacetId());

        PiRequest newRequest = new PiRequest();
        newRequest.setIpAddress(piRequest.getIpAddress());
        newRequest.setFacetId(piRequest.getFacetId());

    }
}
