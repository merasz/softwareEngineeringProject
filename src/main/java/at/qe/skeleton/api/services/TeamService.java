package at.qe.skeleton.api.services;

import at.qe.skeleton.api.model.RestTeamDemoModel;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TeamService {

    private static final AtomicLong ID_COUNTER = new AtomicLong(1);
    //statt concurrenthashmap brauchen wir eine datenbankverbindung
    private static final ConcurrentHashMap<Long, RestTeamDemoModel> teams = new ConcurrentHashMap<Long, RestTeamDemoModel>();

    public RestTeamDemoModel addTeam(RestTeamDemoModel restTeamDemoModel) {
        RestTeamDemoModel newRestTeamDemoModel = new RestTeamDemoModel();
        newRestTeamDemoModel.setId(ID_COUNTER.getAndIncrement());
        newRestTeamDemoModel.setName(restTeamDemoModel.getName());
        newRestTeamDemoModel.setPoints(restTeamDemoModel.getPoints() == null ? 0 : restTeamDemoModel.getPoints());
        teams.put(newRestTeamDemoModel.getId(), newRestTeamDemoModel);
        return teams.get(newRestTeamDemoModel.getId());
    }

    public RestTeamDemoModel findOneTeam(Long id) {
        return teams.get(id);
    }

    public RestTeamDemoModel updateTeam(Long id, RestTeamDemoModel restTeamDemoModel) {
        if(restTeamDemoModel.getName() != null)
            teams.computeIfPresent(id, (key, value) -> {
                value.setName(restTeamDemoModel.getName());
                return value;
            });
        if(restTeamDemoModel.getPoints() != null)
            teams.computeIfPresent(id, (key, value) -> {
                value.setPoints(restTeamDemoModel.getPoints());
                return value;
            });
        return teams.get(id);
    }
}
