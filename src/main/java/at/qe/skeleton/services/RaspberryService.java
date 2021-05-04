package at.qe.skeleton.services;

import at.qe.skeleton.model.Raspberry;
import at.qe.skeleton.model.User;
import at.qe.skeleton.repositories.RaspberryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Scope("application")
public class RaspberryService {

    @Autowired
    private RaspberryRepository raspberryRepository;

    /**
     * Returns a collection of all raspberries.
     *
     * @return
     */

    public Collection<Raspberry> getAllRaspberries() {
        return raspberryRepository.findAll();
    }

    /**
     * Returns the raspberry with the given ip address
     *
     * @param ipAddress
     * @return
     */
    public Raspberry loadRaspberryByIp(String ipAddress) {
        return raspberryRepository.findByIpAddress(ipAddress);
    }

    /**
     *  creates a new Raspberry and returns it
     *
     * @return
     */
    public Raspberry createNewRaspberry() {
        return new Raspberry();
    }

    public void deleteRaspberry(Raspberry raspberry) {
        raspberryRepository.delete(raspberry);
    }

    public Raspberry saveRaspberry(Raspberry raspberry) {
        return raspberryRepository.save(raspberry);
    }

    public Raspberry invalidateApiKey(Raspberry raspberry) {
        raspberry.setApiKey("");
        raspberry.setInUse(false);
        return raspberryRepository.save(raspberry);
    }
}
