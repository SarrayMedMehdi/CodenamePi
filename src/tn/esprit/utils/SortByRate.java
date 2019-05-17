/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import tn.esprit.entities.Job;
import tn.esprit.entities.Rate;
import tn.esprit.service.ServiceRate;
/**
 *
 * @author Mehdi Sarray
 */
public class SortByRate implements Comparator<Job>{

    private Rate rate;
    private ServiceRate svr = new ServiceRate() ;
    private List<Rate> rateListo1 = new ArrayList(); 
    private List<Rate> rateListo2 = new ArrayList(); 
    @Override
    public int compare(Job o1, Job o2) {
       rateListo1 = svr.findRateByJob(o1.getId());
       rateListo2 = svr.findRateByJob(o2.getId());
       return getRateSum(rateListo2)-getRateSum(rateListo1);
    }
    
    public static int getRateSum(List<Rate> rt){
        int value = 0 ;
        double sumRate = 0 ;
        for(Rate r : rt)
        {
            sumRate += r.getNote();
            System.out.println(r.getFeedback());
        }
        if (!rt.isEmpty() && Math.round(sumRate)/rt.size() >0)
        {
            value =  (int)Math.round(sumRate)/rt.size();
        //  System.out.println(value);
            return value;
        }else
           // System.out.println("gotOne");
            return  1;
        
        
    }
    
     
    
    
    
}
