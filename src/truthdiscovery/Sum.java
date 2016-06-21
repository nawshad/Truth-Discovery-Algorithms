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
       double init_claim_value = factor; 
       ArrayList<Double> Cv_0 = new ArrayList<Double>();
       for(int i=0; i<scores[0].length; i++){
           Cv_0.add(init_claim_value);
       }
       //Source score calculation 
       ArrayList<Double> sourceScore = new ArrayList<Double>();
       ArrayList<Double> claimScore = new ArrayList<Double>(); 
        
       int iter = 0; 
       while(iter<totalIter){
           sourceScore = new ArrayList<Double>();
           claimScore = new ArrayList<Double>();
           
           //Source score calculation
            for(int i=0; i<scores.length; i++){
                double sum = 0;
                for(int j=0; j<scores[i].length; j++){
                   if(scores[i][j] == 1){
                       sum += Cv_0.get(j);
                   } 
                }
                sourceScore.add(sum);
            }
            
            ArrayList<Double> Ts_0 = new ArrayList<Double>();
            System.out.println();
            Ts_0 = sourceScore;

           //Claim score calculation
           for(int i=0; i<scores.length; i++){
                double sum = 0;     
                for(int j=0; j<scores[i].length; j++){
                    if(scores[j][i] == 1){
                        sum += Ts_0.get(j) ;
                    }
                }
                if(isAvgLog){
                    sum = ((Math.log10(Vs_count_List.get(i)))/Vs_count_List.get(i))*sum;
                }
                claimScore.add(sum);
            }

            Cv_0 = claimScore;
            
            System.out.println("\nIteration:"+iter);
            
            GeneralUtils.showOrderedSources(sourceScore);
            GeneralUtils.showOrderedClaims(claimScore);
            GeneralUtils.showClaimsPerDataItems(claimScore);
            
            iter++;
        }  

    }
}
