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
public class RateToJson {
    private int job;
    private int candidate;
    private double note;
    private String feedback;
    
   

    public RateToJson(int userId, String feedback, double note, int job) {
        this.candidate = userId;
        this.feedback = feedback;
        this.note = note;
        this.job = job;
    }
    
    
    public int getjob() {
        return job;
    }

    public void setjob(int job) {
        this.job = job;
    }
    public int getcandidate() {
        return 	candidate;
    }

    public void setcandidate(int userId) {
        this.candidate = userId;
    }
     public double getnote() {
        return note;
    }

    public void setnote(double note) {
        this.note = note;
    }


    public String getfeedback() {
        return feedback;
    }

    public void setfeedback(String feedback) {
        this.feedback = feedback;
    }

   
  
    
    
    
    
}
