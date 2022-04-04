package com.example.demo.repository;

import com.example.demo.model.Achievement;

import java.util.List;

public interface AchievementRepositoryCustom  {

    List<Achievement> findAllAchievementsByTeamId(Long teamId);
}
