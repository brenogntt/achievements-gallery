package com.example.demo.repository;

import com.example.demo.model.Team;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepositoryCustom {

    List<Team> getTeamsByNationalDivision();
    List<Team> searchTeams(String teamName);
}
