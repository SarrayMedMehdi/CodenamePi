/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import tn.esprit.entities.Job;
import tn.esprit.service.ServiceRate;


/**
 *
 * @author Mehdi Sarray
 */
public class Rate {
    
    private Home home ;
    TextField feed ;
    Button submit;
    private tn.esprit.entities.Rate rate;
    private ServiceRate svr = new ServiceRate() ;
    
    
     Form hi = new Form("Job Rating", new BoxLayout(BoxLayout.Y_AXIS));
   public void initStarRankStyle(Style s, Image star) {
    s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
    s.setBorder(Border.createEmpty());
    s.setBgImage(star);
    s.setBgTransparency(0);
    
}

public Slider createStarRankSlider() {
    Slider starRank = new Slider();
    starRank.setEditable(true);
    starRank.setMinValue(0);
    starRank.setMaxValue(10);
    Font fnt = Font.createTrueTypeFont("native:MainLight", "native:MainLight").
            derive(Display.getInstance().convertToPixels(5, true), Font.STYLE_PLAIN);
    Style s = new Style(0xffff33, 0, fnt, (byte)0);
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
    starRank.setProgress(1);
    
    return starRank;
}
public void showStarPickingForm(int jobId) {
 
 Label hint = new Label("The Stars to rate are below this Text");
  hi.add(FlowLayout.encloseCenter(hint));
  Slider slider = createStarRankSlider();
  hi.add(FlowLayout.encloseCenter(slider));
  home = new Home();
 hi.getToolbar().setBackCommand("Back", e -> home.Show() );
  
 feed = new TextField("", "feedBack", 20, TextArea.ANY);
 hi.add(FlowLayout.encloseCenter(feed));
 submit = new Button("Submit");
 hi.add(FlowLayout.encloseCenterMiddle(submit));
  hi.show();
    rate = new tn.esprit.entities.Rate.Builder().build();
  submit.addActionListener(new ActionListener() {

     @Override
     public void actionPerformed(ActionEvent evt) {
        
         rate.setCandidate(Login.LOGGED_IN_USER);
         rate.setFeedback(feed.getText());
         rate.setJob(new Job.Builder().id(jobId).build());
         rate.setNote(new Double(slider.getProgress()));
         
         svr.pushRate(rate); // need to handle error 
        
         
          Dialog.show("Rate", "Your rate has been Submitted , you can't rate the same job more then once else your rate will not be valuated", "OK", "Cancel");
     }
 });
}



    
}

















