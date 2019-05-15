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
    Label firstNlb = new Label("FirstName");
    Label lastnNlb = new Label("LastName");
    Label userNlb = new Label("UserName");
    Label addrNlb = new Label("Adresse");
    Label emailAddress = new Label("Email");
    Label pass = new Label("Password");
    Button submit = new Button("Submit");
    Login lg ;
    ServiceUser sv ;
    
     TextModeLayout tm = new TextModeLayout(4, 2);
     Container content = new Container(tm);
     TextField firstname = new TextField("","FirstName",RIGHT,TextArea.ANY);
     TextField lastname  = new TextField("","LastName",RIGHT,TextArea.ANY);
     TextField username = new TextField("","Username",RIGHT,TextArea.ANY);
      TextField adresse = new TextField("","Adresse",RIGHT,TextArea.ANY);
    PickerComponent authorization = PickerComponent.createStrings("RECRUTER", "CANDIDATE").label("Account Type");
        
     public void initialise()
     {
         sv = new ServiceUser();
          content.add(tm.createConstraint().horizontalSpan(2), new SpanLabel("Social Media Sign in form"));
           content.add(firstNlb);
            content.add(tm.createConstraint().horizontalSpan(2), firstname);
            content.add(lastnNlb);
            content.add(tm.createConstraint().horizontalSpan(2), lastname);
            
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
            password = new TextField("","Password",RIGHT,TextArea.PASSWORD);
            content.add(emailAddress).add(email).add(pass).add(password);
            content.add(authorization);
           content.add(addrNlb);
            content.add(tm.createConstraint().horizontalSpan(2), adresse);
            val.addConstraint(email, 
                new GroupConstraint(
                        new LengthConstraint(5), 
                        new RegexConstraint("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$", "Please only use a valid email"))).
                addSubmitButtons(submit);
            
            FontImage.setMaterialIcon(submit, FontImage.MATERIAL_DONE);
        submit.addActionListener(e -> {
            showOKForm(firstname.getText());
        });
     }
     
     public void ShowSignIn()
     {
         f.add(CENTER, content);
        f.add(SOUTH, submit);
        lg = new Login();
        f.getToolbar().setBackCommand("Logout", e -> lg.Show() );
        f.show();
     }
     
     public void showOKForm(String fname)
     {
         
         
         User user = new User.Builder().firstName(fname).lastName(lastname.getText()).userName(username.getText())
                        .password(password.getText()).email(email.getText()).adress(adresse.getText())
                        .authorization(authorization.getSelectCommandText().equals("RECRUTER") ? UserRole.RECRUITER : UserRole.CANDIDATE).photo("")
                        .accountStatus(UserAccountStatus.ACTIVATED).activationCode("").build();
         sv.pushUser(user);
         
         Form f = new Form("Thanks", BoxLayout.y());
        f.add(new SpanLabel("Thanks " + fname + " for your submission. You can press the back arrow and login "));
        
        f.getToolbar().setBackCommand("", e -> lg.Show() );

        f.showBack();
     }
    
}
