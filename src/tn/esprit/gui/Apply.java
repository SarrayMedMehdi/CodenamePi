/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import tn.esprit.entities.Job;
import tn.esprit.entities.User;
import tn.esprit.service.ServiceApplyment;

/**
 *
 * @author Mehdi Sarray
 */
public class Apply {
    
    private Home home ;
    private TextField letter ;
    private Button submit;
    private ServiceApplyment sa=new ServiceApplyment() ; 
    
    public Apply()
    {
        
    }
    
    public void Show(int id )
    {
         Form hi = new Form("Applyment Section", new BoxLayout(BoxLayout.Y_AXIS));
        Label hint = new Label("You can apply the job you've choosen by sbumiting the letter below");
        hi.add(FlowLayout.encloseCenter(hint));
        home = new Home();
        hi.getToolbar().setBackCommand("Back", e -> home.Show() );
        letter = new TextField("", "Letter", 20, TextArea.ANY);
 hi.add(FlowLayout.encloseCenter(letter));
 submit = new Button("Submit");
 hi.add(FlowLayout.encloseCenterMiddle(submit));
  
 submit.addActionListener(new ActionListener() {

             @Override
             public void actionPerformed(ActionEvent evt) {
                 
                 
                 if (Dialog.show("Confirm", "Do you want to proceed?", "OK", "Cancel")) {
                         tn.esprit.entities.Apply appl = new tn.esprit.entities.Apply.Builder().build();
                         appl.setJob(new Job.Builder().id(id).build());
                         appl.setLetter(letter.getText());
                 appl.setCandidate(new User.Builder().id(Login.LOGGED_IN_USER.getId()).build());
                 
                 sa.pushApply(appl);
                 pushDialog();
                 
                            }
                 
             }
         });
 
 hi.show();
    }
    
    
    public void pushDialog()
    {
        Dialog dlg = new Dialog("Applyment");
        dlg.setLayout(new BorderLayout());
        // span label accepts the text and the UIID for the dialog body
        dlg.add(BorderLayout.CENTER,new SpanLabel("Thanks!", "Your applyment done."));
        int h = Display.getInstance().getDisplayHeight();
        dlg.setDisposeWhenPointerOutOfBounds(true);
        dlg.show(h /8 * 7, 0, 0, 0);
    }
    
}
