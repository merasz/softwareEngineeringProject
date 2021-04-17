package at.qe.skeleton.ui.controllers;

import at.qe.skeleton.model.Raspberry;
import at.qe.skeleton.services.RaspberryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

@Component
@Scope("view")
public class RaspberryListController implements Serializable {

    @Autowired
    private RaspberryService raspberryService;

    /**
     * Returns a list of all users.
     *
     * @return
     */
    public Collection<Raspberry> getRaspberries() {
        return raspberryService.getAllRaspberries();
    }

}
