package at.qe.skeleton.services;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public Team savePlayerToTeam(Team team, User player) {
        List<User> players = team.getTeamPlayers();
        System.out.println(players);
        if(players == null) {
            players = new ArrayList<User>();
            team.setTeamPlayers(players);
            return teamRepository.save(team);
        } else if(!players.contains(player)){
            List<User> emptyList = new ArrayList<User>();
            team.setTeamPlayers(emptyList);
            teamRepository.save(team);

            players.add(player);
            team.setTeamPlayers(players);
            System.out.println(players);
            return teamRepository.save(team);
        }


        return team;

    }

    public Team deletePlayerFromTeam(Team team, User tmpPlayer) {
        List<User> players = team.getTeamPlayers();
        players.remove(tmpPlayer);
        team.setTeamPlayers(players);
        return teamRepository.save(team);
    }
}
