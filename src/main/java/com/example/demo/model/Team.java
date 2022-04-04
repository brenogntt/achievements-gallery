package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String shortName;
    private String nickName;
    private String dateOfFoundation;
    private String city;
    private String state;
    private String country;
    private String teamCrestImageUrl;
    private String currentNationalDivision;

    @OneToMany(mappedBy = "competition")
    Set<Achievement> achievements;

    public Team(){};

    public Team(String name, String shortName, String nickname ,String dateOfFoundation, String city, String state, String country, String teamCrestImageUrl, String currentNationalDivision) {
        this.name = name;
        this.shortName = shortName;
        this.nickName = nickname;
        this.dateOfFoundation = dateOfFoundation;
        this.city = city;
        this.state = state;
        this.country = country;
        this.teamCrestImageUrl = teamCrestImageUrl;
        this.currentNationalDivision = currentNationalDivision;
    }

    public Long getId() {
        return id;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDateOfFoundation() {
        return dateOfFoundation;
    }

    public void setDateOfFoundation(String dateOfFoundation) {
        this.dateOfFoundation = dateOfFoundation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTeamCrestImageUrl() {
        return teamCrestImageUrl;
    }

    public void setTeamCrestImageUrl(String teamCrestImageUrl) {
        this.teamCrestImageUrl = teamCrestImageUrl;
    }

    public String getCurrentNationalDivision() {
        return currentNationalDivision;
    }

    public void setCurrentNationalDivision(String currentNationalDivision) {
        this.currentNationalDivision = currentNationalDivision;
    }
}
