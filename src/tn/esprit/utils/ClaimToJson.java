/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.utils;

/**
 *
 * @author Mehdi Sarray
 */
public class ClaimToJson {
    
    private int id ;
    private String type;
    private String details;
    private int job;
    private int comment;
    private int claimer;
    private int staff;
    private String feedback;
    private String status;

    public int getId() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setdetails(String details) {
        this.details = details;
    }

    public int getJob() {
        return job;
    }

    public void setjob(int job) {
        this.job = job;
    }

    public int getComment() {
        return comment;
    }

    public void setcomment(int comment) {
        this.comment = comment;
    }

    public int getClaimer() {
        return claimer;
    }

    public void setclaimer(int claimer) {
        this.claimer = claimer;
    }

    public int getStaff() {
        return staff;
    }

    public void setstaff(int staff) {
        this.staff = staff;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setfeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getStatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }
    
    
    
    
}
