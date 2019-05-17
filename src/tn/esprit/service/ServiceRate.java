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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;
import tn.esprit.entities.Comment;
import tn.esprit.entities.Job;
import tn.esprit.entities.Rate;
import tn.esprit.entities.User;
import tn.esprit.gui.Login;
import tn.esprit.utils.PublicVars;
import tn.esprit.utils.RateToJson;

/**
 *
 * @author Mehdi Sarray
 */
public class ServiceRate {
    String jsonStr = "" ;
    List<Rate> rates = new ArrayList() ;
  
    
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
      
      public void ParseRates(String json)
      {
          
             try {
            JSONParser j = new JSONParser();
            
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
            
               for (Map<String, Object> obj : list) {
              
                  Rate rtn = new Rate.Builder().build();
                  
                  
                  float jid = Float.parseFloat(obj.get("job").toString());
                  rtn.setJob(new Job.Builder().id((int)jid).build());
                  float uid = Float.parseFloat(obj.get("candidate").toString());
                  rtn.setCandidate(new User.Builder().id((int)uid).build());
                  rtn.setFeedback(obj.get("feedback").toString());
                  double note = Double.parseDouble(obj.get("note").toString());
                  rtn.setNote(note);
                rates.add(rtn);
               }
            
        } catch (IOException ex) {
            
        }
      }
      
      public List<Rate> findRateByJob(int jobId)
      {
          List<Rate> lst = new ArrayList();
          ConnectionRequest con = new ConnectionRequest();
        con.setUrl(PublicVars.ipAdress+"api/rate/");  
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
             
              ParseRates(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        for(Rate rt : rates)
        {
            if (rt.getJob().getId() == jobId )
            lst.add(rt);
        }
          return lst;
      }
      
      public List<Rate> getRateJob()
      {
          
          ConnectionRequest con = new ConnectionRequest();
        con.setUrl(PublicVars.ipAdress+"api/rate/");  
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
             
              ParseRates(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
          return rates;
      }
      
      

}
