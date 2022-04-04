package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Competition {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String shortName;
    private String dateOfFoundation;
    private String level;
    private String trophyImageUrl;

    @OneToMany(mappedBy = "competition")
    Set <Achievement> achievements;

    public Competition() {
    }

    public Competition(String name, String shortName, String dateOfFoundation, String level, String trophyImageUrl) {
        this.name = name;
        this.shortName = shortName;
        this.dateOfFoundation = dateOfFoundation;
        this.level = level;
        this.trophyImageUrl = trophyImageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDateOfFoundation() {
        return dateOfFoundation;
    }

    public void setDateOfFoundation(String dateOfFoundation) {
        this.dateOfFoundation = dateOfFoundation;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTrophyImageUrl() {
        return trophyImageUrl;
    }

    public void setTrophyImageUrl(String trophyImageUrl) {
        this.trophyImageUrl = trophyImageUrl;
    }
}
