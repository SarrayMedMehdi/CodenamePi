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
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.Stroke;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.events.ScrollListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.entities.Job;
import tn.esprit.entities.JobStatus;
import static tn.esprit.gui.Login.getImageFromTheme;
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
    Container contain,containH ;
    Button showMore,apply;
    Rate rate = new Rate();
    Comment comm = new Comment();
    Button goComment;
    Apply apl = new Apply();
    
    public Home() 
    {
        theme = UIManager.initFirstTheme("/theme");
            H.setIcon(theme.getImage("icons8_myspace_64px.png"));
            H.setTextPosition(Component.RIGHT);
    f.getToolbar().setTitleComponent(H);
    f.getToolbar().setTitleCentered(true);
     
    }
    
    public void Show()
    {
        CommandSideM();
        ShowJob();
        f.show();
//       f.scrollComponentToVisible(contain);
//       f.scrollComponentToVisible(vertContainer);
     
       
    }
    
    public void CommandSideM()
    {
         login = new Login();
        f.getToolbar().addCommandToRightSideMenu("Profile", theme.getImage("icons8_guest_male_64px_1.png"), new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
               
            }
        });
         f.getToolbar().setBackCommand("", e -> login.Show() );
        
       
    }
    
    
    public void ShowJob()
    {
        jobs = new ArrayList(sj.fetchJobData());
      vertContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
      contain = new Container(BoxLayout.y());
      contain.add(new Label("There is no job available come back later"));
        for(Job job : jobs)
        {
          if (job.getStatus().toUpperCase().equals("CONFIRMED".toUpperCase())){
               
            showMore = new Button("Show detail");
            apply = new Button("Apply");
            goComment = new Button("Comments");
            showMore.addActionListener(e -> ShowMoreButton(job,job.getTitle(),"Location :\n"+job.getLocation()+"\nJob Creation Date:\n"+job.getCreationDate()));
            apply.addActionListener(e -> apl.Show(job.getId()));
            JobTitleBorder(job.getTitle(),job.getId());
            craftContainer();
            goComment.addActionListener(e -> comm.Show(job));
            
            contain.add(jobTitle);
            jobSalary = new Label("Salary :"+job.getSalary());
            jobSalary.getUnselectedStyle().setAlignment(Component.CENTER);
            jobSalary.getAllStyles().setFgColor(0x000000);
            contain.add(jobSalary);
         
            jobStatus = new Label("Expire Date"+job.getExpireDate().toString());
            jobStatus.getUnselectedStyle().setAlignment(Component.CENTER);
            jobStatus.getAllStyles().setFgColor(0x000000);
            contain.add(jobStatus);
            contain.add(containH.add(apply).add(showMore));
           // contain.setLeadComponent(jobTitle);
            vertContainer.add(contain);
            
          }
       
        
        }//End For loop;
           vertContainer.setScrollableY(true);
         f.addComponent(BorderLayout.CENTER,vertContainer);
         
       
    }
    
    
    public void JobTitleBorder(String text,int jobId)
    {
         jobTitle = new Label(text,getImageFromTheme("icons8_star_512px.png").scaledWidth(Math.round(Display.getInstance().getDisplayWidth() / 5))); //repainting the Image
         jobTitle.setTextPosition(Component.RIGHT);
            jobTitle.getUnselectedStyle().setAlignment(Component.CENTER);
            jobTitle.getUnselectedStyle().setBorder(
           RoundBorder.create().rectangle(true).stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 4)).
             strokeColor(0xDB6100).strokeOpacity(120));
            
            jobTitle.addPointerPressedListener(e -> rate.showStarPickingForm(jobId));
            
    }
    
    public void craftContainer()
    {
        contain = new Container(BoxLayout.y());
        containH = new Container(new FlowLayout(CENTER));
        
            Style boxStyle = contain.getUnselectedStyle();
            boxStyle.setBgTransparency(255);
            boxStyle.setBgColor(0xeeeeee);
            boxStyle.setMarginUnit(Style.UNIT_TYPE_DIPS);
            boxStyle.setPaddingUnit(Style.UNIT_TYPE_DIPS);
            boxStyle.setMargin(4, 3, 3, 3);
            boxStyle.setPadding(2, 2, 2, 2);
           
            
    }
    
    public void ShowMoreButton(Job id,String title,String body)
    {
       
        Dialog d = new Dialog(title);
        //d.removeAll();
        d.setLayout(new BorderLayout());
        d.add(BorderLayout.CENTER, new SpanLabel(body, body));
        goComment = new Button("Comment");
        goComment.addActionListener(e -> comm.Show(id));
       try { d.add(BorderLayout.SOUTH,goComment); }
       catch(IllegalArgumentException ie ) {
           
           System.out.println("Component already container ");
       }
        d.showPopupDialog(showMore);
      
      
        
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
