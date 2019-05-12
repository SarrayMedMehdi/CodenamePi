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
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextComponent;
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
public class SignIn {
    
    Form f = new Form("Sign In",new BorderLayout());
    TextField email ;
    TextField password ;
    Label emailAddress = new Label("Email");
    Label pass = new Label("Password");
    Button submit = new Button("Submit");
    Login lg ;
    ServiceUser sv ;
    
     TextModeLayout tm = new TextModeLayout(4, 2);
     Container content = new Container(tm);
     TextComponent firstname = new TextComponent().labelAndHint("FirstName");
     TextComponent lastname = new TextComponent().labelAndHint("LastName");
     TextComponent username = new TextComponent().labelAndHint("username");
      TextComponent adresse = new TextComponent().labelAndHint("Adresse");
    PickerComponent authorization = PickerComponent.createStrings("RECRUTER", "CANDIDATE").label("Account Type");
        
     public void initialise()
     {
         sv = new ServiceUser();
          content.add(tm.createConstraint().horizontalSpan(2), new SpanLabel("Social Media Sign in form"));
          
            content.add(tm.createConstraint().horizontalSpan(2), firstname);
              content.add(tm.createConstraint().horizontalSpan(2), lastname);
            
            
            content.add(tm.createConstraint().horizontalSpan(2), username);
         
            
            email = new TextField("","Email",RIGHT,TextArea.EMAILADDR);
            password = new TextField("","Password",RIGHT,TextArea.PASSWORD);
            content.add(emailAddress).add(email).add(pass).add(password);
            content.add(authorization);
           
            content.add(tm.createConstraint().horizontalSpan(2), adresse);
            FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE);
        submit.addActionListener(e -> {
            showOKForm(firstname.getField().getText());
        });
     }
     
     public void ShowSignIn()
     {
         f.add(CENTER, content);
        f.add(SOUTH, submit);

        
        f.show();
     }
     
     public void showOKForm(String fname)
     {
         
         
         User user = new User.Builder().firstName(fname).lastName(lastname.getField().getText()).userName(username.getField().getText())
                        .password(password.getText()).email(email.getText()).adress(adresse.getField().getText())
                        .authorization(authorization.getSelectCommandText().equals("RECRUTER") ? UserRole.RECRUITER : UserRole.CANDIDATE).photo("")
                        .accountStatus(UserAccountStatus.ACTIVATED).activationCode("").build();
         sv.pushUser(user);
         
         Form f = new Form("Thanks", BoxLayout.y());
        f.add(new SpanLabel("Thanks " + fname + " for your submission. You can press the back arrow and login "));
         lg = new Login();
        f.getToolbar().setBackCommand("", e -> lg.Show() );

        f.showBack();
     }
    
}
