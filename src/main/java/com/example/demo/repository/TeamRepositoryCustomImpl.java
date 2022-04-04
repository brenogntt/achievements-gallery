package com.example.demo.repository;

import com.example.demo.model.Team;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TeamRepositoryCustomImpl implements TeamRepositoryCustom {

    private static final String NATIONAL_FIRST_DIVISION = "A";
    private static final String NATIONAL_SECOND_DIVISION = "B";

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Team> getTeamsByNationalDivision() {
        Query query = entityManager.createQuery("SELECT a FROM Team AS a " +
                "WHERE current_national_division = :national_first_division " +
                "OR current_national_division = :national_second_division " +
                "ORDER BY a.shortName ASC", Team.class);
        query.setParameter("national_first_division", NATIONAL_FIRST_DIVISION);
        query.setParameter("national_second_division", NATIONAL_SECOND_DIVISION);

        return query.getResultList();
    }

    public List<Team> searchTeams (String teamName){
        Query query = entityManager.createQuery("SELECT a FROM Team AS a " +
                "WHERE lower(name) LIKE lower(:team_name) " +
                "ORDER BY a.shortName ASC", Team.class);
        query.setParameter("team_name", "%"+teamName+"%");

        return query.getResultList();
    }
}
