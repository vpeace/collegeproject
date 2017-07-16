package com.example.root.college;

/**
 * Created by root on 25/9/16.
 */
public class Student {
    private String namestring;
    private String stdstring;
    private String branchstring;
    private String preferencestring;
    private String communicationskills;
    private String appearanceandpersonality;
    private String technicalachievements;
    private String organisationalskills;
    private String overallsuitability;
    private String total;
    private String remarks;

    public Student(String namestring, String stdstring, String branchstring, String preferencestring, String communicationskills, String appearanceandpersonality, String technicalachievements, String organisationalskills, String overallsuitability, String total, String remarks) {
        this.namestring = namestring;
        this.stdstring = stdstring;
        this.branchstring = branchstring;
        this.preferencestring = preferencestring;
        this.communicationskills = communicationskills;
        this.appearanceandpersonality = appearanceandpersonality;
        this.technicalachievements = technicalachievements;
        this.organisationalskills = organisationalskills;
        this.overallsuitability = overallsuitability;
        this.total = total;
        this.remarks = remarks;
    }
    public Student() {

    }
    public void setNamestring(String namestring) {
        this.namestring = namestring;
    }

    public String getNamestring() {
        return namestring;
    }

    public void setStdstring(String stdstring) {
        this.stdstring = stdstring;
    }

    public String getStdstring() {
        return stdstring;
    }

    public void setBranchstring(String branchstring) {
        this.branchstring = branchstring;
    }

    public String getBranchstring() {
        return branchstring;
    }

    public void setPreferencestring(String preferencestring) {
        this.preferencestring = preferencestring;
    }

    public String getPreferencestring() {
        return preferencestring;
    }

    public void setCommunicationskills(String communicationskills) {
        this.communicationskills = communicationskills;
    }

    public String getCommunicationskills() {
        return communicationskills;
    }

    public void setAppearanceandpersonality(String appearanceandpersonality) {
        this.appearanceandpersonality = appearanceandpersonality;
    }

    public String getAppearanceandpersonality() {
        return appearanceandpersonality;
    }

    public void setTechnicalachievements(String technicalachievements) {
        this.technicalachievements = technicalachievements;
    }

    public String getTechnicalachievements() {
        return technicalachievements;
    }

    public void setOrganisationalskills(String organisationalskills) {
        this.organisationalskills = organisationalskills;
    }

    public String getOrganisationalskills() {
        return organisationalskills;
    }

    public void setOverallsuitability(String overallsuitability) {
        this.overallsuitability = overallsuitability;
    }

    public String getOverallsuitability() {
        return overallsuitability;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotal() {
        return total;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }
}
