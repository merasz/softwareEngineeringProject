package at.qe.skeleton.api.controllers;

import at.qe.skeleton.api.model.Team;
import at.qe.skeleton.api.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping("/teams")
    private Team createTeam(@RequestBody Team team) {
        return teamService.addTeam(team);
    }

    @GetMapping("/teams/{id}")
    private Team getOneTeam(@PathVariable Long id) {
        return teamService.findOneTeam(id);
    }

    @PatchMapping("/teams/{id}")
    private Team updateTeam(@PathVariable Long id, @RequestBody Team team) {
        return teamService.updateTeam(id, team);
    }

}
