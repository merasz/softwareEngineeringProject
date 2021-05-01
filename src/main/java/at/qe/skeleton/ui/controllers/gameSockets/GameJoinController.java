package at.qe.skeleton.ui.controllers.gameSockets;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.User;
import at.qe.skeleton.model.demo.PlayerAvailability;
import at.qe.skeleton.repositories.UserRepository;
import at.qe.skeleton.ui.websockets.WebSocketManager;
import at.qe.skeleton.utils.CDIAutowired;
import at.qe.skeleton.utils.CDIContextRelated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Controller
@Scope("application")
@CDIContextRelated
public class GameJoinController {

    @CDIAutowired
    private WebSocketManager webSocketManager;

    @Autowired
    private UserRepository userRepository;

    private List<User> playerCircle = new CopyOnWriteArrayList<>();
    private List<String> sendTo = new CopyOnWriteArrayList<>();
    private List<PlayerAvailability> playerAvailability = new CopyOnWriteArrayList<>();
    private boolean allTeamsReady = false;

    public void onJoin(Game game) {
        playerCircle = userRepository.findAllByRaspberry(game.getRaspberry());
        sendTo = playerCircle.stream().map(User::getUsername).collect(Collectors.toList());

        playerCircle.forEach(u -> playerAvailability.add(new PlayerAvailability(u)));

        this.webSocketManager.getJoinChannel().send("teamJoin", sendTo);
    }

    public void onSelect(User user) {
        playerAvailability.stream().filter(x -> x.getUsername().equals(user.getUsername()))
                .forEach(x -> x.setAvailable(false));
        this.webSocketManager.getJoinChannel().send("teamJoin", sendTo);
    }

    public List<PlayerAvailability> getPlayerAvailability() {
        return Collections.unmodifiableList(playerAvailability);
    }

    public boolean getAllTeamsReady() {
        setAllTeamsReady();
        return this.allTeamsReady;
    }

    public void setAllTeamsReady() {
        this.allTeamsReady = playerAvailability.stream().allMatch(PlayerAvailability::isAvailable);
    }
}
