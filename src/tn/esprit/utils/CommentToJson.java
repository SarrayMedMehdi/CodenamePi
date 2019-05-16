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
public class CommentToJson {
    
    private int user;
    private int job;
    private String content;
    private Date datecom;

    public CommentToJson(int user, int job, String content, Date  datecom) {
        this.user = user;
        this.job = job;
        this.content = content;
        this.datecom = datecom;
    }
    
    
    

    public int getuser() {
        return user;
    }

    public void setuser(int user) {
        this.user = user;
    }

    public int getjob() {
        return job;
    }

    public void setjob(int job) {
        this.job = job;
    }

    public String getcontent() {
        return content;
    }

    public void setcontent(String content) {
        this.content = content;
    }

    public Date  getdatecom() {
        return datecom;
    }

    public void setdatecom(Date  datecom) {
        this.datecom = datecom;
    }
    
    
    
}
