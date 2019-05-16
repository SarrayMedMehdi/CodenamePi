/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.utils;

import java.util.Date;

/**
 *
 * @author Mehdi Sarray
 */
public class NotificationToJson {
    
    private int recruiter ; 
    private int is_read;
    private Date date_notif;
    private int job;

    public NotificationToJson() {
    }

    public int getrecruiter() {
        return recruiter;
    }

    public void setRecruiter(int recruiter) {
        this.recruiter = recruiter;
    }

    public int getis_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public Date getdate_notif() {
        return date_notif;
    }

    public void setDate_notif(Date date_notif) {
        this.date_notif = date_notif;
    }

    public int getjob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }
    
    
    
    
}
