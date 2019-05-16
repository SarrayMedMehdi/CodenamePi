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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.entities.User;
import tn.esprit.entities.UserAccountStatus;
import tn.esprit.entities.UserRole;
import tn.esprit.utils.PublicVars;
import org.codehaus.jackson.map.ObjectMapper; 
import tn.esprit.entities.Company;
import static tn.esprit.utils.PublicVars.ipAdress;
/**
 *
 * @author Mehdi Sarray
 */
public class ServiceUser {
    
    private List<User> users = new ArrayList() ;
    private String replaceString ="";
    
    public List<User> parseUsers(String json)
    {
        
        List<User> users = new ArrayList();
        try {
        JSONParser j = new JSONParser();

            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
           
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
            
            for (Map<String, Object> obj : list) {
                {
                    User user = new User.Builder().build();
                    
                    float id = Float.parseFloat(obj.get("id").toString());
                    System.out.println(id);
                    user.setId((int)id);
                    user.setFirstName(obj.get("firstname").toString());
                    user.setLastName(obj.get("lastname").toString());
                    user.setUserName(obj.get("username").toString());
                    user.setPassword(obj.get("password").toString());
                    user.setEmail(obj.get("email").toString());
                    user.setAdress(obj.get("adress").toString());
                    switch(obj.get("authorization").toString())
                    {
                        case "ADMINISTRATOR" : user.setAuthorization(UserRole.ADMINISTRATOR);break;
                        case "MODERATOR"    : user.setAuthorization(UserRole.MODERATOR);break;
                        case "CANDIDATE"    : user.setAuthorization(UserRole.CANDIDATE);break;
                        case "RECRUITER"    : user.setAuthorization(UserRole.RECRUITER);break;
                    }
                    if(obj.get("photo") != null)
                    user.setPhoto(obj.get("photo").toString());
                     switch(obj.get("accountstatus").toString())
                    {
                        case "ACTIVATED" : user.setAccountStatus(UserAccountStatus.ACTIVATED);break;
                        case "PENDING"    : user.setAccountStatus(UserAccountStatus.PENDING);break;
                        case "BANNED"    : user.setAccountStatus(UserAccountStatus.BANNED);break;
                   
                    }
                    user.setActivationCode(obj.get("activationcode").toString());
                    users.add(user);
                }
            }
            
    } catch (IOException ex) {
        }
        return users;
        
    }
    
    
    public User login(String username,String password)
    {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(PublicVars.ipAdress+"api/user/");  
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
              //  ServiceUser ser = new ServiceUser();
                users = parseUsers(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        for(User us : users)
        {
            if (us.getUserName().equals(username) && us.getPassword().equals(password))
                return us ;
        }
        
    return new User.Builder().build() ; 
//users.stream().filter(e -> e.getUserName().equals(username) && e.getPassword().equals(password)).;}
    
}
    public void pushUser(User user)
    {
        String jsonStr = "" ;
      
        ObjectMapper Obj = new ObjectMapper(); 
         try { 
            
            // get Oraganisation object as a json string 
             jsonStr = Obj.writeValueAsString(user);
             replaceString = jsonStr.substring(11) ;
         
            // Displaying JSON String 
             System.out.println(replaceString);
             replaceString = "{"+replaceString;
            System.out.println(replaceString); 
        } 
  
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
         
          ConnectionRequest con = new ConnectionRequest(){
                        @Override
                        protected void buildRequestBody(OutputStream os) throws IOException {
                            os.write(replaceString.getBytes("UTF-8"));
                        }};
        con.setUrl(PublicVars.ipAdress+"api/user/");  
        con.setPost(true);
        con.setContentType("application/json");
        con.addArgument("body", replaceString);

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());
            System.out.println(str);

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        
    }
    
 public User getUserById(int id)
 {
      ConnectionRequest con = new ConnectionRequest();
        con.setUrl(PublicVars.ipAdress+"api/user/"+id);  
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
              //  ServiceUser ser = new ServiceUser();
                users = parseUsers(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
       
        
    return users.get(0) ; 
 }
    
    
     
}
