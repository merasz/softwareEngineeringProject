package at.qe.skeleton.api.services;

import at.qe.skeleton.api.model.Team;
import org.springframework.stereotype.Service;

import java.util.ConcurrentModificationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TeamService {

    private static final AtomicLong ID_COUNTER = new AtomicLong(1);
    //statt concurrenthashmap brauchen wir eine datenbankverbindung
    private static final ConcurrentHashMap<Long, Team> teams = new ConcurrentHashMap<Long, Team>();

    public Team addTeam(Team team) {
        Team newTeam = new Team();
        newTeam.setId(ID_COUNTER.getAndIncrement());
        newTeam.setName(team.getName());
        newTeam.setPoints(team.getPoints() == null ? 0 : team.getPoints());
        teams.put(newTeam.getId(), newTeam);
        return teams.get(newTeam.getId());
    }

    public Team findOneTeam(Long id) {
        return teams.get(id);
    }

    public Team updateTeam(Long id, Team team) {
        if(team.getName() != null)
            teams.computeIfPresent(id, (key, value) -> {
                value.setName(team.getName());
                return value;
            });
        if(team.getPoints() != null)
            teams.computeIfPresent(id, (key, value) -> {
                value.setPoints(team.getPoints());
                return value;
            });
        return teams.get(id);
    }
}
