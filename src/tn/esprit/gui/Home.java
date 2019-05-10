/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.gui;

import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author Mehdi Sarray
 */
public class Home {
    Form f = new Form(BoxLayout.y());
    Label H = new Label("Hello world");
    public Home()
    {
        f.add(H);
    }
    
    public void Show()
    {
        f.show();
    }
            
}
