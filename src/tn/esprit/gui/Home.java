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
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.Stroke;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.events.ScrollListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import tn.esprit.entities.Job;
import tn.esprit.entities.JobStatus;
import tn.esprit.service.ServiceJob;
import tn.esprit.service.ServiceRate;
import tn.esprit.utils.SortByRate;



/**
 *
 * @author Mehdi Sarray
 */
public class Home {
    
    private Resources theme ;
    Form f = new Form(new BorderLayout());
    Login login ;
    Label H = new Label("Social Pro 2019/2020");
    Container vertContainer,sliderContainer;
    ServiceJob sj = new ServiceJob();
    ServiceRate svc = new ServiceRate();
    Label jobTitle,jobSalary,jobStatus ;
    List<Job> jobs ;
    Container contain,containH ;
    Button showMore,apply;
    Rate rate = new Rate();
    Comment comm = new Comment();
    Button goComment;
    Apply apl = new Apply();
    Slider slider ;
    Profile prf = new Profile();
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
               prf.ShowSignIn();
            }
        });
         f.getToolbar().setBackCommand("", e -> { f = new Form(new BorderLayout()); login.Show();} );
        
       
    }
    
    
    public void ShowJob()
    {
        jobs = new ArrayList(sj.fetchJobData());
        Trirate(jobs);
        
      vertContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
      contain = new Container(BoxLayout.y());
      
      contain.add(new Label("There is no job available come back later"));
        for(Job job : jobs)
        {
          if (job.getStatus().toUpperCase().equals("CONFIRMED".toUpperCase())){
              sliderContainer = new Container(BoxLayout.y()); 
               slider = createStarRankSlider();
              
               slider.setProgress(SortByRate.getRateSum(svc.findRateByJob(job.getId())));
               sliderContainer.add(FlowLayout.encloseCenter(slider));
            showMore = new Button("Show detail");
            apply = new Button("Apply");
            goComment = new Button("Comments");
            showMore.addActionListener(e -> ShowMoreButton(job,job.getTitle(),"Location :\n"+job.getLocation()+"\nJob Creation Date:\n"+job.getCreationDate()));
            apply.addActionListener(e -> apl.Show(job.getId()));
            JobTitleBorder(job.getTitle(),job.getId());
            craftContainer();
            goComment.addActionListener(e -> comm.Show(job));
            contain.add(sliderContainer);
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
    
    public void Trirate(List<Job> job) //doing some Magic
    {
        Collections.sort(job , new SortByRate());
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
  
    
       //Crafting STARS 
    public void initStarRankStyle(Style s, Image star) {
        s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
        s.setBorder(Border.createEmpty());
        s.setBgImage(star);
        s.setBgTransparency(0);

    }

    public Slider createStarRankSlider() {
        Slider starRank = new Slider();
        Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
                derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
        Style s = new Style(0xffff33, 0, fnt, (byte) 0);
        Image fullStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        s.setOpacity(100);
        s.setFgColor(0);
        Image emptyStar = FontImage.createMaterial(FontImage.MATERIAL_STAR, s).toImage();
        initStarRankStyle(starRank.getSliderEmptySelectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderEmptyUnselectedStyle(), emptyStar);
        initStarRankStyle(starRank.getSliderFullSelectedStyle(), fullStar);
        initStarRankStyle(starRank.getSliderFullUnselectedStyle(), fullStar);
        starRank.setPreferredSize(new Dimension(fullStar.getWidth() * 5, fullStar.getHeight()));
        starRank.setMinValue(1);
        starRank.setMaxValue(6);
        starRank.setEditable(false);

        return starRank;
    }
   
}
