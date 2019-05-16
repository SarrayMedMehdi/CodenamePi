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
import tn.esprit.entities.Reclamation;
import tn.esprit.entities.User;
import tn.esprit.gui.Login;
import tn.esprit.utils.ClaimToJson;
import tn.esprit.utils.PublicVars;

/**
 *
 * @author Mehdi Sarray
 */
public class ServiceClaim {
     String jsonStr = "" ;
    public void pushClaim(Reclamation clm)
    {
         
        ClaimToJson ctj = new ClaimToJson();
        ctj.setclaimer(Login.LOGGED_IN_USER.getId());
        ctj.setcomment(clm.getComment().getId());
        ctj.setdetails(clm.getDetails());
        ctj.setjob(clm.getJob().getId());
        ctj.settype(clm.getType());
        ctj.setstatus("OPEN");
        ctj.setstaff(2);
        //ctj.setid(21);
      
        ObjectMapper Obj = new ObjectMapper(); 
         try { 
            
            // get Oraganisation object as a json string 
             jsonStr = Obj.writeValueAsString(ctj);
             
             
        } 
  
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
         
          ConnectionRequest con = new ConnectionRequest(){
                        @Override
                        protected void buildRequestBody(OutputStream os) throws IOException {
                            os.write(jsonStr.getBytes("UTF-8"));
                        }};
        con.setUrl(PublicVars.ipAdress+"api/reclamation/");  
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
