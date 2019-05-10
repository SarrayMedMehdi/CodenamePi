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
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;

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
            
    
    public Login(){
        f = new Form(BoxLayout.y());
        
        login = new Button("Login");
        signIn= new Button("Signin"); 
        username= new Label("Username");
        password= new Label("Password");
        user = new TextField("","User",RIGHT,TextArea.ANY);
        pass = new TextField("","Passw",RIGHT,TextArea.PASSWORD);
    }
    
    public void Show()
    {
        
        f.add(username).add(user).add(password).add(pass);
        f.add(logSign()) ;
        if(CheckLogin())
            home.Show();
        
        f.show();
    }
    
    
    public Container logSign()
    {
        Container logsi = new Container(BoxLayout.x());
        logsi.add(login).add(signIn);
        
        return logsi;
    }
    
   public boolean CheckLogin(){
       return false;
   }
    
}
