/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import com.codename1.charts.compat.Paint;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import tn.esprit.entities.User;
import tn.esprit.service.ServiceUser;

/**
 *
 * @author Mehdi Sarray
 */

public class Login {
    Form f ;
    Label username;
    Label password;
    TextField user;
    TextField pass;
    Button login,signIn ;
    Home home = new Home();
    Container center ;
    ServiceUser sv = new ServiceUser();
    static User LOGGED_IN_USER ;
    SignIn sign ;
            
    
    public Login(){
        f = new Form(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        login = new Button("Login");
        signIn= new Button("Signin"); 
        username= new Label("Username");
        password= new Label("Password");
        user = new TextField("","Userername",RIGHT,TextArea.ANY);
        pass = new TextField("","Password",RIGHT,TextArea.PASSWORD);
    }
    
    public void Show()
    {
        
        center.add(username).add(user).add(password).add(pass);
        center.add(logSign()) ;
        login.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
               if(CheckLogin(user.getText(),pass.getText()))
            home.Show();
               else 
            Dialog.show("Login failed", "Invalid Username/Password", "OK", "Cancel");
            }
        });
        signIn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                sign = new SignIn();
                sign.initialise();
                sign.ShowSignIn();
            }
        });
        
        f.addComponent(BorderLayout.CENTER, center);
        f.show();
    }
    
    
    public Container logSign()
    {
        Container logsi = new Container(BoxLayout.x());
        logsi.add(login).add(signIn);
        
        return logsi;
    }
    
   public boolean CheckLogin(String username,String password){
        
      User signedUser = sv.login(username, password);
      
        if(signedUser.getLastName() != null ){
            LOGGED_IN_USER = signedUser ;
       return true;}
        
        return false;
   }
    
}
