package com.example.demo.repository;

import com.example.demo.model.Achievement;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AchievementRepositoryCustomImpl implements AchievementRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Achievement> findAllAchievementsByTeamId(Long teamId) {
        Query query = entityManager.createQuery("SELECT a FROM Achievement AS a " +
                "WHERE team_id = :team_id", Achievement.class);
        query.setParameter("team_id", teamId);

        return query.getResultList();
    }
}
