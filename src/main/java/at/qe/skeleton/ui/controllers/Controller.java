package at.qe.skeleton.ui.controllers;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

// gives access to display message methods to all controllers
public abstract class Controller {

    protected void displayError(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail));
    }

    protected void displayInfo(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
    }
}
