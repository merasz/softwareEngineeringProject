package at.qe.skeleton.api.controllers;

import at.qe.skeleton.api.model.RestTeamDemoModel;
import at.qe.skeleton.api.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping("/teams")
    private RestTeamDemoModel createTeam(@RequestBody RestTeamDemoModel restTeamDemoModel) {
        return teamService.addTeam(restTeamDemoModel);
    }

    @GetMapping("/teams/{id}")
    private RestTeamDemoModel getOneTeam(@PathVariable Long id) {
        return teamService.findOneTeam(id);
    }

    @PatchMapping("/teams/{id}")
    private RestTeamDemoModel updateTeam(@PathVariable Long id, @RequestBody RestTeamDemoModel restTeamDemoModel) {
        return teamService.updateTeam(id, restTeamDemoModel);
    }

}
