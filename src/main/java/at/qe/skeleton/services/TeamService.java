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

//    public List<User> getUsersByTeam(Team team) {
//        return teamRepository.
//    }

    public Team saveTeam(Team team){
        return teamRepository.save(team);
    }

    public Team createTeam(Game game) {
        Team t = new Team(game);
        teamRepository.save(t);
        return t;
    }

    public Team savePlayerToTeam(Team team, User player) {
        List<User> players = team.getTeamPlayers();

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

    public void deleteTeam(Team team) {
        team.setTeamPlayers(new ArrayList<User>());
        teamRepository.save(team);
        teamRepository.delete(team);
    }

    public List<Team> getTeamsByPlayer(User user) {
        return teamRepository.findAllByTeamPlayers(user);
    }

    public boolean isPlayerAssignedToEnemyTeam(Game game, User tmpPlayer) {
        List<Team> allTeamsInGame = getTeamsByGame(game);
        List<User> allGameParticipants = new ArrayList<>();
        for (int i = 0; i < allTeamsInGame.size(); i++)
            allGameParticipants.addAll(allTeamsInGame.get(i).getTeamPlayers());
        if(allGameParticipants.contains(tmpPlayer))
            return true;
        else
            return false;
    }
}
