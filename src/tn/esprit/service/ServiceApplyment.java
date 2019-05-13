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
import tn.esprit.entities.Apply;
import tn.esprit.entities.Comment;
import tn.esprit.utils.ApplyToJson;
import tn.esprit.utils.CommentToJson;
import tn.esprit.utils.PublicVars;

/**
 *
 * @author Mehdi Sarray
 */
public class ServiceApplyment {
    
   String jsonStr ="";
   
     public void pushApply(Apply apply)
    {
        
        //CommentToJson ctj = new CommentToJson(comment.getUser().getId(), comment.getJob().getId(), comment.getContent(), comment.getDate());
     
        ApplyToJson atj = new ApplyToJson(apply.getJob().getId(),apply.getCandidate().getId(),apply.getLetter());
        ObjectMapper Obj = new ObjectMapper(); 
         try { 
           

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
        con.setUrl(PublicVars.ipAdress+"api/apply/");  
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
