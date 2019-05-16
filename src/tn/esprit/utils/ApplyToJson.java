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
public class ApplyToJson {
    
    private int job;
    private int candidate;
    private String letter;

    public ApplyToJson(int job, int candidate, String letter) {
        this.job = job;
        this.candidate = candidate;
        this.letter = letter;
    }

    public ApplyToJson() {
    }

    
    public int getjob() {
        return job;
    }

    public void setjob(int job) {
        this.job = job;
    }

    public int getcandidate() {
        return candidate;
    }

    public void setcandidate(int candidate) {
        this.candidate = candidate;
    }

    public String getletter() {
        return letter;
    }

    public void setletter(String letter) {
        this.letter = letter;
    }
    
    
    
    
    
}
