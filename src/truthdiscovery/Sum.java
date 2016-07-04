/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthdiscovery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import static truthdiscovery.Main.Vs_count_List;
import static truthdiscovery.Main.claimList;
import static truthdiscovery.Main.col;
import static truthdiscovery.Main.row;
import static truthdiscovery.Main.scores;
import static truthdiscovery.Main.sourceList;
import static truthdiscovery.Main.totalIter;
import utils.GeneralUtils;
import utils.MatrixOps;

/**
 *
 * @author nawshad
 */
public class Sum {
    public static void SumFactFinder(double factor , boolean isAvgLog){
       ///System.out.println("Scores Length: "+scores.length);
       double init_claim_value = 1; 
       ArrayList<Double> Cv_0 = new ArrayList<Double>();
       for(int i=0; i< col; i++){
           Cv_0.add(init_claim_value);
       }
       //Source score calculation 
       ArrayList<Double> sourceScores = new ArrayList<Double>();
       ArrayList<Double> claimScores = new ArrayList<Double>(); 
       ArrayList<Double> Ts_0 = new ArrayList<Double>();
       
        
       int iter = 0; 
       while(iter <totalIter){
           sourceScores = new ArrayList<Double>();
           claimScores = new ArrayList<Double>();
           
           //Source score calculation
            for(int i=0; i< row; i++){
                double sum = 0;
                for(int j=0; j< col; j++){
                   if(scores.get(i).get(j) == 1){
                       sum += Cv_0.get(j);
                   } 
                   if(isAvgLog){
                       sum = ((Math.log10(Vs_count_List.get(i)))/Vs_count_List.get(i))*sum;
                       
                   }
                }
                //System.out.println("Sum:" +sum);
                sourceScores.add(sum);
            }
            
            //ArrayList<Double> Ts_0 = new ArrayList<Double>();
            System.out.println();
             
            //System.out.println("Previous Scource Score:"+Ts_0);
            //System.out.println("New Source Score:"+sourceScore);
            

            //Claim score calculation
            for(int i=0; i<col; i++){
                 double sum = 0;     
                 for(int j=0; j<row; j++){
                     if(scores.get(j).get(i) == 1){
                         sum += sourceScores.get(j) ;
                     }
                 }
                 claimScores.add(sum);
             }
            
            System.out.println("\nIteration:"+iter);
           
            System.out.println("Ordered Source: "+GeneralUtils.showOrderedSources(sourceScores));
            System.out.println("Ordered Claims: "+GeneralUtils.showOrderedClaims(claimScores));
            System.out.println("Source Scores: "+sourceScores);
            System.out.println("Claim Score: "+claimScores);
            GeneralUtils.showClaimsPerDataItems(claimScores);
            
            /*if(GeneralUtils.isSameSet(GeneralUtils.showOrderedSources(Ts_0),GeneralUtils.showOrderedSources(sourceScores))){
                break;
            };*/
            
            Cv_0 = claimScores;
            Ts_0 = sourceScores;
            
            iter++;
        }  
    }
}
