package com.example.demo.service;

import com.example.demo.model.Achievement;
import com.example.demo.model.Team;
import com.example.demo.repository.AchievementRepositoryCustomImpl;
import com.example.demo.repository.CompetitionRepository;
import com.example.demo.repository.TeamRepository;
import com.example.demo.repository.TeamRepositoryCustomImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamService {

    public final static String INTERNATIONAL = "Internacional";
    public final static String NATIONAL = "Nacional";
    public final static String INTERSTATE = "Interestadual";
    public final static String STATE = "Estadual";

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamRepositoryCustomImpl teamRepositoryCustomImpl;

    @Autowired
    private CompetitionRepository competitionRepository;

    @Autowired
    private AchievementRepositoryCustomImpl achievementRepositoryCustomImpl;

    public Team findTeamById(Long id) {
        Optional<Team> foundTeam = teamRepository.findById(id);

        if(foundTeam.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found. Id: " + id);
        } else {
            return foundTeam.get();
        }
    }

    public Map<String, Object> getTeamsByNationalDivision() {
        List<Map<String, String>> teamsFromSpecificNationalDivision = new ArrayList();
        Map<String, Object> response = new HashMap();

        List<Team> foundTeams = teamRepositoryCustomImpl.getTeamsByNationalDivision();

        if(foundTeams.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Teams from first and second national division were not found.");
        }

        for(Team foundTeam: foundTeams) {

            Map<String, String> team = new HashMap();

            team.put("teamId", foundTeam.getId().toString());
            team.put("teamShortName", foundTeam.getShortName());
            team.put("teamCurrentNationalDivision", foundTeam.getCurrentNationalDivision());
            team.put("teamCrestImageUrl", foundTeam.getTeamCrestImageUrl());

            teamsFromSpecificNationalDivision.add(team);
        }

        response.put("teamsMenu", teamsFromSpecificNationalDivision);

        return response;
    }

    public Map<String, Object> searchTeams(String teamName) {
        List<Map<String, String>> teamsFoundFromSearch = new ArrayList();
        Map<String, Object> response = new HashMap();

        List<Team> foundTeams = teamRepositoryCustomImpl.searchTeams(teamName);

        if(foundTeams.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This team was not found. Team: " + teamName);
        }

        for(Team foundTeam: foundTeams) {

            Map<String, String> team = new HashMap();

            team.put("teamId", foundTeam.getId().toString());
            team.put("teamFullName", foundTeam.getName());
            team.put("city", foundTeam.getCity());
            team.put("state", foundTeam.getState());
            team.put("country", foundTeam.getCountry());
            team.put("teamCrestImageUrl", foundTeam.getTeamCrestImageUrl());

            teamsFoundFromSearch.add(team);
        }

        response.put("teamsFoundFromSearch", teamsFoundFromSearch);

        return response;

    }

    public Map<String, Object> getAchievements (Long teamId) {

        Map<String, Object> achievements = new HashMap();
        Map<String, Object> response = new HashMap();

        List<Achievement> foundAchievements = achievementRepositoryCustomImpl.findAllAchievementsByTeamId(teamId);

        if(foundAchievements.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team id " + teamId + " has no achievements.");
        }

        achievements.put("internationals", getInternationalsAchievements(foundAchievements));
        achievements.put("nationals", getNationalsAchievements(foundAchievements));
        achievements.put("interstates", getInterStatesAchievements(foundAchievements));
        achievements.put("states", getStatesAchievements(foundAchievements));

        response.put("achievements", achievements);

        return response;
    }

    private List<Map<String, String>> getInternationalsAchievements(List<Achievement> foundAchievements) {

        List<Achievement> internationalAchievements = foundAchievements.stream()
                .filter(achievement -> Objects.nonNull(achievement.getCompetition().getLevel()) && achievement.getCompetition().getLevel().equals(INTERNATIONAL))
                .collect(Collectors.toList());

        if(internationalAchievements.size() == 0) {
            return  Collections.emptyList();
        }

        return parseAchievements(internationalAchievements);
    }

    private List<Map<String, String>> getNationalsAchievements(List<Achievement> foundAchievements) {

        List<Achievement> nationalsAchievements = foundAchievements.stream()
                .filter(achievement -> Objects.nonNull(achievement.getCompetition().getLevel()) && achievement.getCompetition().getLevel().equals(NATIONAL))
                .collect(Collectors.toList());

        if(nationalsAchievements.size() == 0) {
            return  Collections.emptyList();
        }

        return parseAchievements(nationalsAchievements);
    }

    private List<Map<String, String>> getInterStatesAchievements(List<Achievement> foundAchievements) {

        List<Achievement> interStatesAchievements = foundAchievements.stream()
                .filter(achievement -> Objects.nonNull(achievement.getCompetition().getLevel()) && achievement.getCompetition().getLevel().equals(INTERSTATE))
                .collect(Collectors.toList());

        if(interStatesAchievements.size() == 0) {
            return  Collections.emptyList();
        }

        return parseAchievements(interStatesAchievements);
    }

    private List<Map<String, String>> getStatesAchievements(List<Achievement> foundAchievements) {

        List<Achievement> statesAchievements = foundAchievements.stream()
                .filter(achievement -> Objects.nonNull(achievement.getCompetition().getLevel()) && achievement.getCompetition().getLevel().equals(STATE))
                .collect(Collectors.toList());

        if(statesAchievements.size() == 0) {
            return  Collections.emptyList();
        }

        return parseAchievements(statesAchievements);
    }

    private List<Map<String, String>> parseAchievements(List<Achievement> filteredAchievements) {

        List<Map<String, String>> achievements = new ArrayList();

        for (Achievement filteredAchievement : filteredAchievements) {

            Map<String, String> achievement;

            if (filteredAchievement.getCompetition() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Competition not found in the achievement. Achievement: " + filteredAchievement);
            }

            if (filteredAchievement.getTeam() == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found in the achievement. Achievement: " + filteredAchievement);
            }

            if (achievements.isEmpty() || isANewAchievement(achievements, filteredAchievement)) {
                achievement = addNewAchievement(filteredAchievement);
                achievements.add(achievement);
            } else {
                Optional<Map<String, String>> repeatedAchievement = achievements.stream()
                        .filter(element -> element.get("competitionId").equals(filteredAchievement.getCompetition().getId().toString()))
                        .findFirst();

                if (repeatedAchievement.isPresent()) {
                      addRepeatedAchievement(repeatedAchievement.get(), filteredAchievement);
                }
            }
        }
        return achievements;
    }

    private boolean isANewAchievement(List<Map<String, String>> achievements, Achievement achievement) {
        return achievements.stream()
                .noneMatch(element -> element.get("competitionId").equals(achievement.getCompetition().getId().toString()));
    }

    private Map<String, String> addNewAchievement(Achievement filteredAchievement) {
        Map<String, String> achievement = new HashMap();

        achievement.put("teamId", filteredAchievement.getTeam().getId().toString());
        achievement.put("competitionId", filteredAchievement.getCompetition().getId().toString());
        achievement.put("competitionName", filteredAchievement.getCompetition().getName());
        achievement.put("numberOfTitles", "1");
        achievement.put("seasons", filteredAchievement.getYear() + ".");
        achievement.put("trophyImageUrl", filteredAchievement.getCompetition().getTrophyImageUrl());

        return achievement;
    }

    private void addRepeatedAchievement(Map<String, String> repeatedAchievement, Achievement filteredAchievement) {
        int numberOfTitles = Integer.parseInt(repeatedAchievement.get("numberOfTitles"));
        String seasons = repeatedAchievement.get("seasons");

        String concatSeasons = seasons.substring(0, seasons.length() - 1) + ", " + filteredAchievement.getYear() + ".";

        repeatedAchievement.put("numberOfTitles", String.valueOf(numberOfTitles + 1));
        repeatedAchievement.put("seasons", concatSeasons);
    }
}
