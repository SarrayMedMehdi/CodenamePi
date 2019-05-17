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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.entities.Company;
import tn.esprit.entities.User;
import tn.esprit.utils.PublicVars;

/**
 *
 * @author mmsarray
 */
public class ServiceCompany {
    
      List<Company> listCom = new ArrayList<>();
    public List<Company> fetchCompanys(String json)
    {
        
        try {
        JSONParser j = new JSONParser();

            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
            
            for (Map<String, Object> obj : list) {
                {
                    Company cmp = new Company.Builder().build() ; 
                    float id = Float.parseFloat(obj.get("recruiter").toString());
                    cmp.setRecruiter(new User.Builder().id((int)id).build());
                    cmp.setDescription(obj.get("description").toString());
                    cmp.setName(obj.get("name").toString());
                    cmp.setAdress(obj.get("adress").toString());
                    cmp.setDomain(obj.get("domain").toString());
                    cmp.setPhone(obj.get("phone").toString());
                    cmp.setImage(obj.get("image").toString());
                  
                    listCom.add(cmp);
                    
                }
            }
            
    } catch (IOException ex) {
        }
        return listCom;
        
    }
    
    public List<Company> getCompanys()
    {
        
         ConnectionRequest con = new ConnectionRequest();
        con.setUrl(PublicVars.ipAdress+"api/company/");  
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
             
               listCom = fetchCompanys(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listCom;
    }
        
    
    
}
