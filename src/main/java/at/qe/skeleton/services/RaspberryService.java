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
     * @return Collection of Raspberries
     */

    public Collection<Raspberry> getAllRaspberries() {
        return raspberryRepository.findAll();
    }

    /**
     * Returns the raspberry with the given ip address
     * @param ipAddress ipAdress for the Raspberry
     * @return Raspberry with the given ipAddress
     */
    public Raspberry loadRaspberryByIp(String ipAddress) {
        return raspberryRepository.findByIpAddress(ipAddress);
    }

    /**
     * creates a new Raspberry and returns it
     * @return Raspberry Object
     */
    public Raspberry createNewRaspberry() {
        return new Raspberry();
    }

    /**
     * deletes a raspberry from the database
     * @param raspberry Raspberry to delete
     */
    public void deleteRaspberry(Raspberry raspberry) {
        raspberryRepository.delete(raspberry);
    }

    /**
     * saves a Raspberry Object in the database
     * @param raspberry
     * @return
     */
    public Raspberry saveRaspberry(Raspberry raspberry) {
        return raspberryRepository.save(raspberry);
    }

    /**
     * Method to invalidate an API Key for the given raspberry
     * @param raspberry Raspberry
     * @return Raspberry Object with deleted API Key
     */
    public Raspberry invalidateApiKey(Raspberry raspberry) {
        raspberry.setApiKey("");
        raspberry.setInUse(false);
        return raspberryRepository.save(raspberry);
    }
}
