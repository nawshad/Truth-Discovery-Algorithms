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
       
       //use a copy of scores instead
        /*double [][] copy_scores_for_sum = new double[7][7];
        
        for(int i = 0; i<copy_scores_for_sum.length; i++){
            for (int j=0; j<copy_scores_for_sum[i].length; j++){
                copy_scores_for_sum[i][j] = scores[i][j];
            }
        }*/
        
        //MatrixOps.MatrixMult(copy_scores_for_sum, factor);
        
       double init_claim_value = factor; 
       ArrayList<Double> Co_V = new ArrayList<Double>();
       for(int i=0; i<scores[0].length; i++){
           Co_V.add(init_claim_value);
       }
       
       /*double init_source_value = 1; 
       ArrayList<Double> To_S = new ArrayList<Double>();
       for(int i=0; i<scores.length; i++){
           To_S.add(init_source_value);
       }*/

       //Source score calculation 
       ArrayList<Double> sourceScore = new ArrayList<Double>();
       ArrayList<Double> claimScore = new ArrayList<Double>(); 
        
       int iter = 0; 
       while(iter<totalIter){
           System.out.println("Iteration:"+iter);
           sourceScore = new ArrayList<Double>();
           claimScore = new ArrayList<Double>();
           
           //Source score calculation
            for(int i=0; i<scores.length; i++){
                double sum = 0;
                for(int j=0; j<scores[i].length; j++){
                   if(scores[i][j] == 1){
                       sum += Co_V.get(j);
                   } 
                }
                sourceScore.add(sum);
                /*for(int j=0; j<copy_scores_for_sum[i].length; j++){
                   if(copy_scores_for_sum[j][i]>0){
                       copy_scores_for_sum[j][i] = sum;
                   } 
                }*/
            }
       
            
            System.out.println("Source Score:");
            for(int i=0; i<sourceScore.size(); i++){
                System.out.print(sourceScore.get(i)+ " ");
            }
            
            ArrayList<Double> To_S = new ArrayList<Double>();
            System.out.println();
            To_S = sourceScore;

           //Claim score calculation
           for(int i=0; i<scores.length; i++){
                double sum = 0;     
                for(int j=0; j<scores[i].length; j++){
                    if(scores[j][i] == 1){
                        sum += To_S.get(j) ;
                    }
                }
                if(isAvgLog){
                    sum = ((Math.log10(Vs_count_List.get(i)))/Vs_count_List.get(i))*sum;
                }
                claimScore.add(sum);
            }

            System.out.println("Claim Score:");
            for(int i=0; i<claimScore.size(); i++){
                System.out.print(claimScore.get(i)+ " ");
            }
            System.out.println();
            
            //To_S = sourceScore;
            Co_V = claimScore;
            
            iter++;
            
            GeneralUtils.showOrderedSources(sourceScore);
            GeneralUtils.showOrderedClaims(claimScore);
            GeneralUtils.showClaimsPerDataItems(claimScore);
        }  

        /*System.out.println("Final Source Score:");
        for(int i=0; i<sourceScore.size(); i++){
            System.out.print(sourceScore.get(i)+ " ");
        }
        System.out.println();
        
        System.out.println("Final Claim Score:");
        for(int i=0; i<claimScore.size(); i++){
            System.out.print(claimScore.get(i)+ " ");
        }
        System.out.println();*/
        
        
    }
}
