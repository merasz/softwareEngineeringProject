package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Raspberry;
import at.qe.skeleton.services.RaspberryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.el.MethodExpression;
import java.io.Serializable;

@Component
@Scope("view")
public class RaspberryController extends Controller implements Serializable {

    @Autowired
    private RaspberryService raspberryService;

    /**
     * Attribute to cache the currently displayed raspberry
     */
    private Raspberry raspberry;

    /**
     * Sets the currently displayed raspberry and reloads it form db.
     *
     * @param raspberry
     */
    public void setRaspberry(Raspberry raspberry) {
        this.raspberry = raspberry;
    }


    /**
     * Returns the currently displayed raspberry.
     *
     * @return Raspberry
     */
    public Raspberry getRaspberry() {return raspberry;}

    /**
     * Action to force a reload of the currently displayed raspberry.
     */
    public void doReloadRaspberry() {
        raspberry = raspberryService.loadRaspberryByIp(raspberry.getIpAddress());
    }

    /**
     * Action to save the current raspberry
     *
     */
    public void doSaveRaspberry() {
        raspberry = raspberryService.saveRaspberry(raspberry);
    }


    /**
     * Action to delete the currently displayed raspberry.
     */
    public void doDeleteRaspberry() {
        try {
            this.raspberryService.deleteRaspberry(raspberry);
            raspberry = null;
            displayInfo("Raspberry deleted", "Raspberry successfully deleted.");
        } catch (IllegalArgumentException e){
            displayError("Error", e.getMessage());
        } catch (Exception e) {
            displayError("Error", "Raspberry could not be deleted.");
        }
    }

    /**
     * Action to create a new raspberry
     *
     */
    public void doCreateRaspberry() {
        raspberry = raspberryService.createNewRaspberry();
    }

    /**
     * Action to invalidate the api key of the current raspberry
     */
    public void doInvalidateApiKey() {
        raspberry = raspberryService.invalidateApiKey(raspberry);
    }

    public RaspberryService getRaspberryService() {return raspberryService;}


}
