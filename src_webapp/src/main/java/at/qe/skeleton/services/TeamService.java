package at.qe.skeleton.services;

import at.qe.skeleton.model.Game;
import at.qe.skeleton.model.Team;
import at.qe.skeleton.model.User;
import at.qe.skeleton.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("application")
public class TeamService {
    @Autowired
    TeamRepository teamRepository;

    /**
     * return all teams
     * @return list of teams
     */
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    /**
     * return all teams in a game
     * @param game
     * @return list of teams
     */
    public List<Team> getTeamsByGame(Game game) {
        return teamRepository.findByGame(game.getGameId());
    }

    /**
     * saves a given team to the repo and returns it
     * @param team
     * @return
     */
    public Team saveTeam(Team team){
        return teamRepository.save(team);
    }

    /**
     * saves a player to a given team
     * @param team
     * @param player
     * @return Team object
     */
    public Team savePlayerToTeam(Team team, User player) {
        List<User> players = team.getTeamPlayers();

        if(players == null) {
            players = new ArrayList<>();
            players.add(player);
            team.setTeamPlayers(players);
            return teamRepository.save(team);
        } else if(!players.contains(player)){
            team = reloadTeam(team);
            players.add(player);
            team.setTeamPlayers(players);
            return teamRepository.save(team);
        }

        return team;
    }

    /**
     * reloads a given team
     * @param team
     * @return team object
     */
    public Team reloadTeam(Team team) {
        return teamRepository.findByTeamId(team.getTeamId());
    }

    /**
     * deletes a player from a team and saves the team again
     * @param team
     * @param tmpPlayer
     * @return team object
     */
    public Team deletePlayerFromTeam(Team team, User tmpPlayer) {
        List<User> players = team.getTeamPlayers();
        players.remove(tmpPlayer);
        team.setTeamPlayers(players);
        return teamRepository.save(team);
    }

    /**
     * deletes a given team
     * @param team
     */
    public void deleteTeam(Team team) {
        team.setTeamPlayers(null);
        team.setScores(null);
        teamRepository.save(team);
        teamRepository.delete(team);
    }

    /**
     * returns for a given user the teams he is assigned
     * @param user
     * @return
     */
    public List<Team> getTeamsByPlayer(User user) {
        return teamRepository.findAllByTeamPlayers(user);
    }

    /**
     * return team for a user and for the given game
     * @param user
     * @param game
     * @return team object
     */
    public Team getTeamByPlayerAndGame(User user, Game game) {
        return teamRepository.findByTeamPlayersAndGame(user, game);
    }
}