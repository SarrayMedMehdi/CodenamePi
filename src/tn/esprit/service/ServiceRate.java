/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.service;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.util.List;
import tn.esprit.entities.Job;
import tn.esprit.utils.PublicVars;

/**
 *
 * @author Mehdi Sarray
 */
public class ServiceRate {
    
     public List<Job>  fetchRateData()
    {
        
         ConnectionRequest con = new ConnectionRequest();
        con.setUrl(PublicVars.ipAdress+"api/job/");  
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
             
               //jobs = parseJob(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return null;
    }
    
}
