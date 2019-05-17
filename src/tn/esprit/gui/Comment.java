/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import static com.codename1.ui.CN.RIGHT;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import tn.esprit.entities.Job;
import tn.esprit.entities.Reclamation;
import tn.esprit.entities.ReclamationType;
import tn.esprit.service.ServiceClaim;
import tn.esprit.service.ServiceComment;

/**
 *
 * @author Mehdi Sarray
 */
public class Comment {
    
    private Resources theme ;
    private Home home ;
    private Button claim,edit,makeChange;
    private Container contain,editContain;
    private TextArea claimDetail,editedText;
    RadioButton fraud;
    RadioButton racism ;
    RadioButton fakeuser;
    RadioButton violence;
    RadioButton harassement;
    RadioButton scam;
    RadioButton policyviolation;
    ButtonGroup btngrp;
     ServiceClaim svclaim = new ServiceClaim();
    TextField feed ;
    Button submit;
    String status;
    ComponentGroup comg = new ComponentGroup();
    List<tn.esprit.entities.Comment> comment ;
    ServiceComment svc = new ServiceComment();
    private Button applyClaim;
    private Container containerbuttonscom;
    
    
    public void Show(Job jobId) {
  Form hi = new Form("Comment Section", new BoxLayout(BoxLayout.Y_AXIS));
 Label hint = new Label("The List of people comments");
  hi.add(FlowLayout.encloseCenter(hint));
 theme = UIManager.initFirstTheme("/theme");
  
  home = new Home();
 hi.getToolbar().setBackCommand("Back", e -> home.Show() );
  

 submit = new Button("Submit");
 getComments(jobId.getId());
 hi.add(FlowLayout.encloseCenter(comg));
  feed = new TextField("", "Comment", 20, TextArea.ANY);
 hi.add(FlowLayout.encloseCenter(feed));
 hi.add(FlowLayout.encloseCenterMiddle(submit));
 

  hi.show();
  
  submit.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent evt) {
          if (!feed.getText().isEmpty()){
          tn.esprit.entities.Comment cmt = new tn.esprit.entities.Comment.Builder().job(jobId).user(Login.LOGGED_IN_USER).content(feed.getText()).date( new Date()).build() ;
        svc.pushComment(new tn.esprit.entities.Comment.Builder().job(jobId).user(Login.LOGGED_IN_USER).content(feed.getText()).date( new Date()).build());
        
        svc.craftNotification(jobId, cmt);
        Dialog.show("Comment", "Your comment has been Added", "OK", "Cancel");}
          else
              Dialog.show("Comment", "You can't add an empty comment", "OK", "Cancel");
      }
  });
  
}
    
    public void getComments(int JobId)
    {   
       
        comment = new ArrayList(svc.commentList(JobId)) ;
       
       
      for(tn.esprit.entities.Comment s : comment){
          claim = new Button("Claim");
          edit = new Button("Edit");
          containerbuttonscom = new Container(new FlowLayout(CENTER));
          containerbuttonscom.add(claim).add(edit);
          edit.setVisible(false);
          comg.add(s.getContent()+" "+s.getDate().toString());
          if (s.getUser().getId() == Login.LOGGED_IN_USER.getId())
          {
              edit.setVisible(true);
          }
          
          comg.add(containerbuttonscom);
          claim.addActionListener(e -> { showClaim(JobId , s.getId());} );
          edit.addActionListener(e -> { showEdit(s);}); //edit comment 
      }
        
        comg.setScrollableY(true);
        comg.setScrollableX(true);    
       
    }
     public void showEdit(tn.esprit.entities.Comment com)
        {
            Dialog d = new Dialog("Comment");
            d.setLayout(new BorderLayout());
            editContain = new Container(BoxLayout.y());
            editedText = new TextArea(com.getContent());
            
            makeChange = new Button("Submit Changes");
            d.add(BorderLayout.CENTER, editContain);
            editContain.add(editedText).add(makeChange);

            makeChange.addActionListener(e -> {
            if (editedText.getText().isEmpty()){
                Dialog.show("Edit", "You edited with empty comment!", "OK", "Cancel");
            }else{
                com.setContent(editedText.getText());
                 svc.editCommen(com);
                 Dialog.show("Edit", "Your comment Has been Edited", "OK", "Cancel");
                    }
            }); 
            d.showPopupDialog(edit);
        }
     public void showClaim(int job, int com)
        {
        Dialog d = new Dialog("Claim");
        d.setLayout(new BorderLayout());
        ClaimRadio();
        d.add(BorderLayout.CENTER, contain);
        applyClaim = new Button("Submit Claim");
        applyClaim.addActionListener(e -> {
            Reclamation rlc = new Reclamation.Builder().job(new Job.Builder().id(job).build()).comment(new tn.esprit.entities.Comment.Builder().id(com).build()).details(claimDetail.getText()).build();
           
            if (fraud.isSelected())
                rlc.setType(ReclamationType.FRAUD);
            else if (racism.isSelected())
                    rlc.setType(ReclamationType.RACISM);
            else if (fakeuser.isSelected())
                    rlc.setType(ReclamationType.FAKEUSER);
            else if (violence.isSelected())
                    rlc.setType(ReclamationType.VIOLENCE);
            else if (harassement.isSelected())
                    rlc.setType(ReclamationType.HARASSEMENT);
            else if (scam.isSelected())
                    rlc.setType(ReclamationType.SCAM);
            else rlc.setType(ReclamationType.POLICYVIOLATION);
               
            svclaim.pushClaim(rlc);
       Dialog.show("Claim", "Your claim has been sent!", "OK", "Cancel");
        
        });
       try { d.add(BorderLayout.SOUTH,applyClaim); }
       catch(IllegalArgumentException ie ) {
           
           System.out.println("Component already container ");
       }
        d.showPopupDialog(claim);

        }
     
     public void ClaimRadio()
     {
         contain = new Container(BoxLayout.y());
         claimDetail = new TextField("","Enter Detail",RIGHT,TextArea.ANY);
         
          fraud = new RadioButton("fraud",theme.getImage("icons8_error_96px.png"));
          racism = new RadioButton("racism",theme.getImage("icons8_high_priority_96px.png"));
          fakeuser = new RadioButton("fakeuser",theme.getImage("icons8_high_priority_96px.png"));
          violence = new RadioButton("violence",theme.getImage("icons8_error_96px.png"));
          harassement = new RadioButton("harassement",theme.getImage("icons8_error_96px.png"));
          scam = new RadioButton("scam",theme.getImage("icons8_error_96px.png"));
          policyviolation = new RadioButton("policyviolation",theme.getImage("icons8_high_priority_96px.png"));
         btngrp = new ButtonGroup(fraud,racism,fakeuser,violence,harassement,scam,policyviolation);
          
         contain.add(fraud);
         contain.add(racism);
         contain.add(fakeuser);
          contain.add(violence);
          contain.add(harassement);
          contain.add(scam);
          contain.add(policyviolation);
          contain.add(claimDetail);
         
     }
}
