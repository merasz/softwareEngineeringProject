package at.qe.skeleton.ui.controllers.demo;

import at.qe.skeleton.model.User;
import at.qe.skeleton.model.demo.LogEntry;
import at.qe.skeleton.model.demo.LogEntryType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


import at.qe.skeleton.model.*;
import at.qe.skeleton.model.demo.PlayerAvailability;
import at.qe.skeleton.services.GameStartService;


import at.qe.skeleton.services.TeamService;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import at.qe.skeleton.ui.controllers.gameSockets.GameJoinController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.primefaces.event.SelectEvent;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertTrue;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;


import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserStatusControllerTest {

    @InjectMocks
    UserStatusController userStatusController;
    @Test
    void setupUserStatus() {
    }

    @Test
    void addUserStatus() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {

        userStatusController.addUserStatus(new User());
    });
    }

    @Test
    void afterLogin() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {

        userStatusController.afterLogin("Paul");
        });
    }

    @Test
    void afterLogout() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
        userStatusController.afterLogout("Paul");
        });
    }

}