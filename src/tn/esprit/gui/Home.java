/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;


import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.entities.Job;
import tn.esprit.service.ServiceJob;



/**
 *
 * @author Mehdi Sarray
 */
public class Home {
    
    private Resources theme ;
    Form f = new Form(new BorderLayout());
    Login login ;
    Label H = new Label("Social Pro 2019/2020");
    Container vertContainer;
    ServiceJob sj = new ServiceJob();
    Label jobTitle,jobSalary,jobStatus ;
    List<Job> jobs ;
    Container contain ;
    Button showMore;
    Rate rate = new Rate();
    Comment comm = new Comment();
    Button goComment;
    
    public Home() 
    {
        theme = UIManager.initFirstTheme("/theme");
            H.setIcon(theme.getImage("icons8_myspace_64px.png"));
            H.setTextPosition(Component.RIGHT);
    f.getToolbar().setTitleComponent(H);
    f.getToolbar().setTitleCentered(true);
    vertContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
    
    }
    
    public void Show()
    {
        CommandSideM();
        ShowJob();
        f.show();
    }
    
    public void CommandSideM()
    {
         login = new Login();
        f.getToolbar().addCommandToRightSideMenu("Profile", theme.getImage("icons8_guest_male_64px_1.png"), new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
               
            }
        });
         
        
          f.getToolbar().setBackCommand("Logout", e -> login.Show() );
    }
    
    
    public void ShowJob()
    {
        jobs = new ArrayList(sj.fetchJobData());

        for(Job job : jobs)
        {
            showMore = new Button("Show detail");
            goComment = new Button("Comments");
            showMore.addActionListener(e -> ShowMoreButton(job.getId(),job.getTitle(),"Location :\n"+job.getLocation()+"\nJob Creation Date:\n"+job.getCreationDate()));
            JobTitleBorder(job.getTitle(),job.getId());
            craftContainer();
            goComment.addActionListener(e -> comm.Show(job.getId()));
            
            contain.add(jobTitle);
            jobSalary = new Label("Salary :"+job.getSalary());
            jobSalary.getUnselectedStyle().setAlignment(Component.CENTER);
            jobSalary.getAllStyles().setFgColor(0x000000);
            contain.add(jobSalary);
            jobStatus = new Label("Job Status"+job.getStatus());
            jobStatus.getUnselectedStyle().setAlignment(Component.CENTER);
            jobStatus.getAllStyles().setFgColor(0x000000);
            contain.add(jobStatus);
            contain.add(showMore);
           // contain.setLeadComponent(jobTitle);
            vertContainer.add(contain);
            
        }
        f.addComponent(BorderLayout.CENTER,vertContainer);
    }
    
    public void JobTitleBorder(String text,int jobId)
    {
         jobTitle = new Label(text);
         
            jobTitle.getUnselectedStyle().setAlignment(Component.CENTER);
            jobTitle.getUnselectedStyle().setBorder(
           RoundBorder.create().rectangle(true).stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 4)).
             strokeColor(0xDB6100).strokeOpacity(120));
            
            jobTitle.addPointerPressedListener(e -> rate.showStarPickingForm(jobId));
            
    }
    
    public void craftContainer()
    {
        contain = new Container(BoxLayout.y());
            Style boxStyle = contain.getUnselectedStyle();
            boxStyle.setBgTransparency(255);
            boxStyle.setBgColor(0xeeeeee);
            boxStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
            boxStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
            boxStyle.setMargin(4, 3, 3, 3);
            boxStyle.setPadding(2, 2, 2, 2);
            
    }
    
    public void ShowMoreButton(int id,String title,String body)
    {
       
        Dialog d = new Dialog(title);
        d.setLayout(new BorderLayout());
        d.add(BorderLayout.CENTER, new SpanLabel(body, body));
        d.add(BorderLayout.SOUTH,goComment);
        d.showPopupDialog(showMore);
      
      
        
    }
            
}
