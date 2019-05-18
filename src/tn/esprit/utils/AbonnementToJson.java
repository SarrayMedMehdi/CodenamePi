/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.utils;

import java.util.Date;

/**
 *
 * @author habib
 */
public class AbonnementToJson {
    
    private int company;
    private int candidate;
    private Date dateAbonnement;

    public AbonnementToJson(int company, int candidate, Date dateAbonnement) {
        this.company = company;
        this.candidate = candidate;
        this.dateAbonnement = dateAbonnement;
    }

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public int getCandidate() {
        return candidate;
    }

    public void setCandidate(int candidate) {
        this.candidate = candidate;
    }

    public Date getDateAbonnement() {
        return dateAbonnement;
    }

    public void setDateAbonnement(Date dateAbonnement) {
        this.dateAbonnement = dateAbonnement;
    }
    
}
