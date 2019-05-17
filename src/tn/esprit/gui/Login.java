/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import com.codename1.charts.compat.Paint;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.RIGHT;
import static com.codename1.ui.CN.TOP;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import tn.esprit.entities.User;
import tn.esprit.service.ServiceUser;

/**
 *
 * @author Mehdi Sarray
 */

public class Login {
    Form f ;
    private Resources theme ;
    Label username;
    Label password;
    TextField user;
    TextField pass;
    Button login,signIn ;
    Home home = new Home();
    Container center ;
    ServiceUser sv = new ServiceUser();
    public static User LOGGED_IN_USER ;
    SignIn sign ;
    Container photoLogin;
            
    
    public Login(){
        f = new Form(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        center = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        photoLogin = new Container(new FlowLayout(TOP));
        login = new Button("Login");
        signIn= new Button("Signin"); 
        username= new Label("Username");
        password= new Label("Password");
        user = new TextField("","Username",RIGHT,TextArea.ANY);
        pass = new TextField("","Password",RIGHT,TextArea.PASSWORD);
        theme = UIManager.initFirstTheme("/theme");
    }
    
    public void Show()
    {
       
        center.add(photoLogin.add(getImageFromTheme("logologin.png").scaledWidth(Math.round(Display.getInstance().getDisplayWidth() / 6))));
        
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
   public static Image getImageFromTheme(String name) {
    try {
        Resources resFile = Resources.openLayered("/theme");
        Image image = resFile.getImage(name);
        return image;
    } catch (IOException ioe) {
       
    }
    return null;
}
       
}
