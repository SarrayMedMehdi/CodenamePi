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
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import tn.esprit.entities.Job;
import tn.esprit.entities.Rate;
import tn.esprit.entities.User;
import tn.esprit.utils.PublicVars;
import tn.esprit.utils.RateToJson;

/**
 *
 * @author Mehdi Sarray
 */
public class ServiceRate {
    String jsonStr = "" ;

    
      public void pushRate(Rate rate)
    {

        RateToJson rtj = new RateToJson(rate.getCandidate().getId(), rate.getFeedback(), rate.getNote(), rate.getJob().getId());
        ObjectMapper Obj = new ObjectMapper(); 
         try { 
            

             jsonStr = Obj.writeValueAsString(rtj);
             
        } 
  
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
         
          ConnectionRequest con = new ConnectionRequest(){
                        @Override
                        protected void buildRequestBody(OutputStream os) throws IOException {
                            os.write(jsonStr.getBytes("UTF-8"));
                        }};
        con.setUrl(PublicVars.ipAdress+"api/rate/");  
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
