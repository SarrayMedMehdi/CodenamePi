/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.service;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import java.io.IOException;
import java.io.OutputStream;
import org.codehaus.jackson.map.ObjectMapper;
import tn.esprit.entities.Abonnement;
import tn.esprit.utils.AbonnementToJson;
import tn.esprit.utils.PublicVars;

/**
 *
 * @author habib
 */

    
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import org.codehaus.jackson.map.ObjectMapper;
import tn.esprit.entities.Abonnement;
import tn.esprit.entities.Apply;
import tn.esprit.utils.AbonnementToJson;
import tn.esprit.utils.ApplyToJson;
import tn.esprit.utils.PublicVars;

/**
 *
 * @author Alai Zid
 */
public class ServiceAbonnement {
    
    
     SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
     SimpleDateFormat inputFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") ;
     String jsonStr ="";
   
     public void AddAbonnement(Abonnement abonnement)
    {
     
        AbonnementToJson atj = new AbonnementToJson(abonnement.getCompany().getRecruiter().getId(),abonnement.getCandidate().getId(),abonnement.getDateAbonnement());
        ObjectMapper Obj = new ObjectMapper(); 
         try { 
            Obj.setDateFormat(inputFormat);

             jsonStr = Obj.writeValueAsString(atj);
             
        } 
  
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
         
          ConnectionRequest con = new ConnectionRequest(){
                        @Override
                        protected void buildRequestBody(OutputStream os) throws IOException {
                            os.write(jsonStr.getBytes("UTF-8"));
                        }};
        con.setUrl(PublicVars.ipAdress+"api/abonnement/");  
        con.setPost(true);
        con.setContentType("application/json");
        con.addArgument("body", jsonStr);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
            

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        
    }
}

    

