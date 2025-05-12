package com.example.Backend.model;

public class Resume {
    private String summary;
    private String Education;
    private String experience;
    private String awards;
    private String projects;

    public Resume(){
        this.summary = "";
        this.Education = "";
        this.experience = "";
        this.awards = "";
        this.projects = "";
    }

    public Resume(String summary, String Education, String experience, String awards, String projects) {
        this.summary = summary;
        this.Education = Education;
        this.experience = experience;
        this.awards = awards;
        this.projects = projects;
    }

    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getEducation() {
        return Education;
    }

    public void setEducation(String education) {
        Education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getProject() {
        return projects;
    }

    public void setProject(String projects) {
        this.projects = projects;
    }
}
