package at.qe.skeleton.utils;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class ErrorMessage implements Serializable {

    private static final long serialVersionUID = -7029377764414225966L;
    private String message = "";



    public String getMessage() {
        return message;
    }

//	public void setMessage(String message) {
//		this.message = message;
//	}

    public void reset() {
        message = "";
    }


    public void pushMessage(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.validationFailed();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + msg, "" ) );
    }


    public boolean hasError() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.isValidationFailed();
    }
}

