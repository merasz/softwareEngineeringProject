package at.qe.skeleton.services;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("application")
public class TeamService {
    @Autowired
    TeamRepository teamRepository;

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public List<Team> getTeamsByGame(Game game) {
        return teamRepository.findByGame(game.getGameId());
    }

    public Team saveTeam(Team team){
        return teamRepository.save(team);
    }
}
