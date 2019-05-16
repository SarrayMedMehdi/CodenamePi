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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import tn.esprit.entities.Category;
import tn.esprit.entities.Company;
import tn.esprit.entities.Job;
import tn.esprit.entities.JobStatus;
import tn.esprit.entities.User;
import tn.esprit.utils.PublicVars;

/**
 *
 * @author Mehdi Sarray
 */
public class ServiceJob {
    
    List<Job> jobs = new ArrayList();
    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") ;
   
    public List<Job> parseJob(String json)
    {
          List<Job> jobz = new ArrayList();
        try {
            JSONParser j = new JSONParser();
            
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
            
               for (Map<String, Object> obj : list) {
              
                   Job job = new Job.Builder().build();
                   float id = Float.parseFloat(obj.get("id").toString());
                    System.out.println(id);
                    job.setId((int)id);
                     float companyid = Float.parseFloat(obj.get("company").toString());
                    job.setCompany(new Company.Builder().recruiter(new User.Builder().id((int)companyid).build()).build());
                    job.setTitle(obj.get("title").toString());
                    job.setDescription(obj.get("description").toString());
                    float categoryid = Float.parseFloat(obj.get("category").toString());
                    job.setCategory(new Category.Builder().id((int)categoryid).build());
                    job.setLocation(obj.get("location").toString());
                try {
                    job.setCreationDate(inputFormat.parse(obj.get("creationdate").toString()));
                    System.out.println(obj.get("salary").toString());
                    job.setExpireDate(inputFormat.parse(obj.get("expiredate").toString()));
                } catch (ParseException ex) {
                    System.out.println("can't parse shit");
                }
                double salary = Double.parseDouble(obj.get("salary").toString());
                job.setSalary(salary);
                switch(obj.get("status").toString()){
                    case "CONFIRMED" : job.setStatus(JobStatus.CONFIRMED);break;
                    case "PENDING"   : job.setStatus(JobStatus.PENDING);break; 
                    case "DISABLED"  : job.setStatus(JobStatus.DISABLED);break;
                }
                  jobz.add(job);
               }
            
        } catch (IOException ex) {
            
        }
        return jobz;
    }
    
  public List<Job>  fetchJobData()
    {
        
         ConnectionRequest con = new ConnectionRequest();
        con.setUrl(PublicVars.ipAdress+"api/job/");  
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
             
               jobs = parseJob(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return jobs;
    }
    
}
