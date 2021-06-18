package at.qe.skeleton.ui.controllers.demo;

import at.qe.skeleton.model.demo.Message;
import at.qe.skeleton.repositories.UserRepository;
import at.qe.skeleton.ui.websockets.WebSocketManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import at.qe.skeleton.model.*;
import at.qe.skeleton.services.*;
import at.qe.skeleton.ui.beans.SessionInfoBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)

class ChatManagerControllerTest {
    @Mock
    ChatManagerController chatManagerController;
    @Mock
    UserRepository userRepository;
    @Mock
    WebSocketManager websocketManager;
    Set<User> possibleRecipients = new ConcurrentSkipListSet<>();
    Map<String, List<Message>> chats = new ConcurrentHashMap<>();

    @Test
    void testOnLogin(){
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            String username = new String();
            User user = new User();
            Map<String, List<Message>> chats = new ConcurrentHashMap<>();
            Set<User> possibleRecipients = new ConcurrentSkipListSet<>();
            when(userRepository.findFirstByUsername(username)).thenReturn(user);
            possibleRecipients.add(user);
            chats.put(username, new LinkedList<>());
            chatManagerController.onLogin(username);
            assertTrue(possibleRecipients == chats);
        });



    }


}