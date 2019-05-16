/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;
import tn.esprit.entities.Category;
import tn.esprit.entities.Comment;
import tn.esprit.entities.Company;
import tn.esprit.entities.Job;
import tn.esprit.entities.JobStatus;
import tn.esprit.entities.User;
import tn.esprit.utils.CommentToJson;
import tn.esprit.entities.Notification;
import tn.esprit.utils.NotificationToJson;
import tn.esprit.utils.PublicVars;
import tn.esprit.gui.Login;

/**
 *
 * @author Mehdi Sarray
 */
public class ServiceComment {
    String jsonStr = "" ;
  SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
  SimpleDateFormat inputFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") ;
   List<Comment> cmt = new ArrayList() ;
    
      public void pushComment(Comment comment)
    {
        
        CommentToJson ctj = new CommentToJson(comment.getUser().getId(), comment.getJob().getId(), comment.getContent(), comment.getDate());
        ObjectMapper Obj = new ObjectMapper(); 
         try { 
            Obj.setDateFormat(inputFormat);

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
        con.setUrl(PublicVars.ipAdress+"api/comment/");  
        con.setPost(true);
        con.setContentType("application/json");
        con.addArgument("body", jsonStr);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);
            

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        
    }
       public List<Comment> parseComment(String json,int jobId){
           List<Comment> cmt = new ArrayList() ;
             try {
            JSONParser j = new JSONParser();
            
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
            
               for (Map<String, Object> obj : list) {
              
                  Comment cment = new Comment.Builder().build();
                  
                   float id = Float.parseFloat(obj.get("id").toString());
                  
                  cment.setId((int)id);
                  float uid = Float.parseFloat(obj.get("user").toString());
                  cment.setUser(new User.Builder().id((int)uid).build());
                  cment.setContent(obj.get("content").toString());
                try {
                    cment.setDate(inputFormat2.parse(obj.get("datecom").toString()));
                } catch (ParseException ex) {
                   
                }
                if(id == jobId)
                  cmt.add(cment);
               }
            
        } catch (IOException ex) {
            
        }
            
           return cmt;
       }
      
      
      public List<Comment> commentList(int jobId)
      {
         
          
            
         ConnectionRequest con = new ConnectionRequest();
        con.setUrl(PublicVars.ipAdress+"api/comment/");  
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
             
               cmt = parseComment(new String(con.getResponseData()),jobId);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
          
          return cmt ;
      }
      public void craftNotification(Job company,Comment comment)
      {
          NotificationToJson ntf = new NotificationToJson();
          
          ntf.setIs_read(0);
          ntf.setJob(company.getId());
          ntf.setRecruiter(comment.getJob().getCompany().getRecruiter().getId());
          ntf.setDate_notif(new Date());
            ObjectMapper Obj = new ObjectMapper(); 
         try { 
            Obj.setDateFormat(inputFormat);

             jsonStr = Obj.writeValueAsString(ntf);
             
        } 
  
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
         
          ConnectionRequest con = new ConnectionRequest(){
                        @Override
                        protected void buildRequestBody(OutputStream os) throws IOException {
                            os.write(jsonStr.getBytes("UTF-8"));
                        }};
        con.setUrl(PublicVars.ipAdress+"api/notification/");  
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
