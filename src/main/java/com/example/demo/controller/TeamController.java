package com.example.demo.controller;

import com.example.demo.model.Team;
import com.example.demo.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@CrossOrigin
@Controller
@RequestMapping("teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping(value="/{id}", produces="application/json")
    public @ResponseBody
    Team findTeamById(@PathVariable Long id) {
        return teamService.findTeamById(id);
    }

    @GetMapping(value="/menu", produces="application/json")
    public @ResponseBody Map<String, Object> getTeamsByNationalDivision() {
        return teamService.getTeamsByNationalDivision();
    }

    @GetMapping(value="/{id}/achievements", produces="application/json")
    public @ResponseBody Map<String, Object> getAchievements(@PathVariable Long id) {
        return teamService.getAchievements(id);
    }

    @GetMapping(value="/search", produces="application/json")
    public @ResponseBody Map<String, Object> searchTeams(@RequestParam String teamName) {
        return teamService.searchTeams(teamName);
    }
}
