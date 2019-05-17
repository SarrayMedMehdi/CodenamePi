/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import static com.codename1.ui.CN.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import static com.codename1.ui.layouts.BorderLayout.CENTER;
import static com.codename1.ui.layouts.BorderLayout.SOUTH;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.TextModeLayout;
import com.codename1.ui.validation.GroupConstraint;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import tn.esprit.entities.User;
import tn.esprit.entities.UserAccountStatus;
import tn.esprit.entities.UserRole;
import tn.esprit.service.ServiceUser;

/**
 *
 * @author Mehdi Sarray
 */
public class Profile {
    
    Form f = new Form("Edit Profile",new BorderLayout());
    TextField email ;
    TextField password ;
    Label firstNlb = new Label("FirstName");
    Label lastnNlb = new Label("LastName");
    Label userNlb = new Label("UserName");
    Label addrNlb = new Label("Adresse");
    Label emailAddress = new Label("Email");
    Label pass = new Label("Password");
    Button submit = new Button("Submit");
    Login lg ;
    ServiceUser sv ;
    Home home;
    
     TextModeLayout tm = new TextModeLayout(4, 2);
     Container content = new Container(tm);
     TextField firstname = new TextField("","FirstName",RIGHT,TextArea.ANY);
     TextField lastname  = new TextField("","LastName",RIGHT,TextArea.ANY);
     TextField username = new TextField("","Username",RIGHT,TextArea.ANY);
      TextField adresse = new TextField("","Adresse",RIGHT,TextArea.ANY);
 
        
     public void initialise()
     {
         sv = new ServiceUser();
         home = new Home();
          content.add(tm.createConstraint().horizontalSpan(2), new SpanLabel("Social Media Edit Profile in form"));
        firstNlb.setText(Login.LOGGED_IN_USER.getFirstName());
          content.add(firstNlb);
            content.add(tm.createConstraint().horizontalSpan(2), firstname);
             lastnNlb.setText(Login.LOGGED_IN_USER.getLastName());
            content.add(lastnNlb);
            content.add(tm.createConstraint().horizontalSpan(2), lastname);
            userNlb.setText(Login.LOGGED_IN_USER.getUserName());
            content.add(userNlb);
            content.add(tm.createConstraint().horizontalSpan(2), username);
         Validator val = new Validator();
        val.setShowErrorMessageForFocusedComponent(true);
        val.addConstraint(firstname, 
                new GroupConstraint(
                        new LengthConstraint(3), 
                        new RegexConstraint("^([a-zA-Z ]*)$", "Please only use latin characters for name"))).
                addSubmitButtons(submit);
            
            email = new TextField("","Email",RIGHT,TextArea.EMAILADDR);
            email.setText(Login.LOGGED_IN_USER.getEmail());
            password = new TextField("","Password",RIGHT,TextArea.PASSWORD);
            password.setText(Login.LOGGED_IN_USER.getPassword());
            content.add(emailAddress).add(email).add(pass).add(password);
           addrNlb.setText(Login.LOGGED_IN_USER.getAdress());
           content.add(addrNlb);
            content.add(tm.createConstraint().horizontalSpan(2), adresse);
            val.addConstraint(email, 
                new GroupConstraint(
                        new LengthConstraint(5), 
                        new RegexConstraint("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$", "Please only use a valid email"))).
                addSubmitButtons(submit);
            
            FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE);
        submit.addActionListener(e -> {
            
           User user = new User.Builder().id(Login.LOGGED_IN_USER.getId())
                 .firstName(firstname.getText().isEmpty() ? Login.LOGGED_IN_USER.getFirstName() : firstname.getText())
                 .lastName(lastname.getText().isEmpty() ? Login.LOGGED_IN_USER.getLastName() : lastname.getText() )
                 .userName(username.getText().isEmpty() ? Login.LOGGED_IN_USER.getUserName(): username.getText())
                 .password(password.getText().isEmpty() ? Login.LOGGED_IN_USER.getPassword(): password.getText())
                 .email(email.getText().isEmpty() ? Login.LOGGED_IN_USER.getEmail(): email.getText())
                 .adress(adresse.getText().isEmpty() ? Login.LOGGED_IN_USER.getAdress() : adresse.getText())
                 .build();
         sv.updateUser(user);
         
          Dialog.show("Edit", "Your profile has been edited", "OK", "Cancel");
        });
     }
     
     public void ShowSignIn()
     {
           initialise();
         f.add(CENTER, content);
        f.add(SOUTH, submit);
      
       f.setScrollableY(true);
        f.getToolbar().setBackCommand("Bac,", e -> home.Show() );
        f.show();
     }
     

}
