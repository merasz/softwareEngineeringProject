package at.qe.skeleton.ui.controllers.gameSockets;


import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.model.demo.TeamInfo;
import at.qe.skeleton.repositories.UserRepository;
import at.qe.skeleton.ui.websockets.WebSocketManager;
import at.qe.skeleton.utils.CDIAutowired;
import at.qe.skeleton.utils.CDIContextRelated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Controller
@Scope("application")
@CDIContextRelated
public class GameStartController {

    @CDIAutowired
    private WebSocketManager webSocketManager;

    @Autowired
    private UserRepository userRepository;

    private List<TeamInfo> teamInfo = new CopyOnWriteArrayList<>();

    public void onJoin(Game game) {
        List<String> sendTo = userRepository.findAllByRaspberry(game.getRaspberry()).stream()
                                .map(User::getUsername).collect(Collectors.toList());
        if (teamInfo.isEmpty()) {
            game.getTeamList().forEach(t -> teamInfo.add(new TeamInfo(game, t)));
        }
        this.webSocketManager.getJoinChannel().send("teamJoin", sendTo);
    }

    public Collection<TeamInfo> getTeamInfo() {
        return Collections.unmodifiableCollection(this.teamInfo);
    }
}
