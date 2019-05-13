/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import tn.esprit.entities.Job;
import tn.esprit.service.ServiceComment;

/**
 *
 * @author Mehdi Sarray
 */
public class Comment {
    
    private Home home ;
    TextField feed ;
    Button submit;
    ComponentGroup comg = new ComponentGroup();
    List<tn.esprit.entities.Comment> comment ;
    ServiceComment svc = new ServiceComment();
    
    
    public void Show(Job jobId) {
  Form hi = new Form("Comment Section", new BoxLayout(BoxLayout.Y_AXIS));
 Label hint = new Label("The List of people comments");
  hi.add(FlowLayout.encloseCenter(hint));
 
  
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
        svc.pushComment(new tn.esprit.entities.Comment.Builder().job(jobId).user(Login.LOGGED_IN_USER).content(feed.getText()).date( new Date()).build());
        Dialog.show("Rate", "Your comment has been Added", "OK", "Cancel");
      }
  });
  
}
    
    public void getComments(int JobId)
    {
        comment = new ArrayList(svc.commentList(JobId)) ;
        
       for(tn.esprit.entities.Comment s : comment)
           comg.add(new Label(s.getContent()+"\n"+s.getDate().toString()));
        
    }
}
