package at.qe.skeleton.services;

import at.qe.skeleton.model.Raspberry;
import at.qe.skeleton.model.User;
import at.qe.skeleton.repositories.RaspberryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("application")
public class RaspberryService {

    @Autowired
    private RaspberryRepository raspberryRepository;

    public Raspberry loadRaspberryByIp(String ipAddress) {
        return raspberryRepository.findByIpAddress(ipAddress);
    }

}
