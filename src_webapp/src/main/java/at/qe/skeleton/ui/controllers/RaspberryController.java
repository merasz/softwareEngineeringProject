package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Raspberry;
import at.qe.skeleton.services.RaspberryService;
import org.primefaces.PrimeFaces;
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

    @Autowired
    private UserDetailController userDetailController;

    /**
     * Attribute to cache the currently displayed raspberry
     */
    private Raspberry raspberry;
    private String ipAddress;
    private String action;

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
     * see if user is already authorized to perform an operation on selected raspberry
     * @param isAdmin true if current user has role 'ADMIN'
     */
    public void accessRaspberry(boolean isAdmin) {
        if (isAdmin) {
            doPerformAction();
        } else {
            PrimeFaces.current().executeScript("PF('authorizeDialog').show()");
        }
    }

    /**
     * check ip address entered by user to check authorization to perform an operation on selected raspberry
     */
    public void authorize() {
        if (raspberry.getIpAddress().equals(ipAddress)) {
            PrimeFaces.current().executeScript("PF('authorizeDialog').hide()");
            doPerformAction();
        } else {
            displayError("Authorization failed", "Make sure you enter the correct ip-address for this Raspberry Pi");
        }
        ipAddress = null;
    }

    /**
     * perform an operation on selected raspberry
     */
    public void doPerformAction() {
        switch (action) {
            case "EDIT":
                PrimeFaces.current().executeScript("PF('raspberryEditDialog').show()");
                break;
            case "INVALIDATE":
                doInvalidateApiKey();
                break;
            case "DELETE":
                doDeleteRaspberry();
                break;
            case "SET":
                userDetailController.saveUser();
                displayInfo("Raspberry Pi changed", "");
                PrimeFaces.current().executeScript("PF('raspberryEditDialog').hide()");
                break;
            default:
                break;
        }
        action = null;
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

    //region getter & setter
    public RaspberryService getRaspberryService() {
        return raspberryService;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    //endregion
}
