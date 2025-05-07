package com.example.Backend.model;

public class CoverLetter {
    private String summary;
    private String education;
    private String experience;
    private String awards;
    private String projects;
    private String recruiter;

    public CoverLetter() {
        this.summary = "";
        this.education = "";
        this.experience = "";
        this.awards = "";
        this.projects = "";
        this.recruiter = "";
    }

    public CoverLetter(String summary, String education, String experience, String awards, String projects, String recruiter) {
        this.summary = summary;
        this.education = education;
        this.experience = experience;
        this.awards = awards;
        this.projects = projects;
        this.recruiter = recruiter;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
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

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public String getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(String recruiter) {
        this.recruiter = recruiter;
    }
}
