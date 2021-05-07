package at.qe.skeleton.converter;

import at.qe.skeleton.model.User;
import at.qe.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@Service
public class UserConverter implements Converter {

    @Autowired
    UserService userService;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.equals("")){
            return null;
        }

        return userService.loadUser(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object val) {

        if(val == null){
            return null;
        }

        if(val.getClass().equals(String.class)) {
            return val.toString();
        }

        return ((User) val).toString();
    }
}
