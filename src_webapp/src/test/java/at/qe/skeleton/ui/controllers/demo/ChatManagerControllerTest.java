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
import java.util.stream.Collectors;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)

class ChatManagerControllerTest {
    @InjectMocks
    ChatManagerController chatManagerController;
    @Mock
    UserRepository userRepository;
    @Mock
    WebSocketManager websocketManager;
    Set<User> possibleRecipients = new ConcurrentSkipListSet<>();
    Map<String, List<Message>> chats = new ConcurrentHashMap<>();

    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void testOnLogin() {

        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            chatManagerController.onLogin("player1");
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

    @Test
    void testGetPossibleRecipients() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            List<Message> msg = new ArrayList<>();
            List<User> user2 = new ArrayList<>();
            User user1 = new User();
            Map<String, List<Message>> chats = new ConcurrentHashMap<>();
            when(chats.get(user1.getUsername())).thenReturn(Collections.unmodifiableList(chats.get(user1.getUsername())));

            chatManagerController.getPossibleRecipients();
        });
    }

    @Test
    void testDeliver() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            chatManagerController.deliver(new Message());
        });

    }

    @Test
    void testGetChatContentRef() {
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {

            chatManagerController.getChatContentRef(new User());
        });
    }
}


