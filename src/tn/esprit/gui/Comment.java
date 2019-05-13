/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComponentGroup;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mehdi Sarray
 */
public class Comment {
    
    private Home home ;
    TextField feed ;
    Button submit;
    ComponentGroup comg = new ComponentGroup();
    List<String> comment ;
    
    
    public void Show(int jobId) {
  Form hi = new Form("Comment Section", new BoxLayout(BoxLayout.Y_AXIS));
 Label hint = new Label("The List of people comments");
  hi.add(FlowLayout.encloseCenter(hint));
 
  
  home = new Home();
 hi.getToolbar().setBackCommand("Back", e -> home.Show() );
  

 submit = new Button("Submit");
 getComments();
 hi.add(FlowLayout.encloseCenter(comg));
  feed = new TextField("", "Comment", 20, TextArea.ANY);
 hi.add(FlowLayout.encloseCenter(feed));
 hi.add(FlowLayout.encloseCenterMiddle(submit));
 

  hi.show();
  
}
    
    public void getComments()
    {
        comment = new ArrayList() ;
        comment.add("test1");
        comment.add("test1");
        comment.add("test1");
       for(String s : comment)
           comg.add(s);
        
    }
}
