/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthdiscovery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import static truthdiscovery.Main.TopK;
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
        double [][] copy_scores_for_sum = new double[7][7];
        
        for(int i = 0; i<copy_scores_for_sum.length; i++){
            for (int j=0; j<copy_scores_for_sum[i].length; j++){
                copy_scores_for_sum[i][j] = scores[i][j];
            }
        }
        
        MatrixOps.MatrixMult(copy_scores_for_sum, factor);

       //Source score calculation 
       ArrayList<Double> sourceScore = new ArrayList<Double>();
       ArrayList<Double> claimScore = new ArrayList<Double>(); 
        
       int count = 0; 
       while(count<totalIter){
           int iter = count;
           System.out.println("Iteration:"+iter);
           sourceScore = new ArrayList<Double>();
           claimScore = new ArrayList<Double>();
           
           //Source score calculation
           for(int i=0; i<copy_scores_for_sum.length; i++){
                double sum = 0;
                
                for(int j=0; j<copy_scores_for_sum[i].length; j++){
                   sum += copy_scores_for_sum[i][j];
                }
                
                if(isAvgLog){
                    sum = ((Math.log10(Vs_count_List.get(i)))/Vs_count_List.get(i))*sum;
                    //sum = sum;
                }
                
                sourceScore.add(sum);
                for(int j=0; j<copy_scores_for_sum[i].length; j++){
                   if(copy_scores_for_sum[i][j]>0){
                        copy_scores_for_sum[i][j] = sum;
                   } 
                }
            }

            System.out.println("Source Score:");
            for(int i=0; i<sourceScore.size(); i++){
                System.out.print(sourceScore.get(i)+ " ");
            }
            System.out.println();
            
            //Claim score calculation
            for(int i=0; i<copy_scores_for_sum[0].length; i++){
                double sum = 0;
                for(int j=0; j<copy_scores_for_sum.length; j++){
                   sum += copy_scores_for_sum[j][i]; 
                }
                claimScore.add(sum);
                for(int j=0; j<copy_scores_for_sum[i].length; j++){
                   if(copy_scores_for_sum[j][i]>0){
                       copy_scores_for_sum[j][i] = sum;
                   } 
                }
            }
       
            System.out.println("Claim Score:");
            for(int i=0; i<claimScore.size(); i++){
                System.out.print(claimScore.get(i)+ " ");
            }
            System.out.println();
            count++;
        }  

        System.out.println("Final Source Score:");
        for(int i=0; i<sourceScore.size(); i++){
            System.out.print(sourceScore.get(i)+ " ");
        }
        System.out.println();
        
        System.out.println("Final Claim Score:");
        for(int i=0; i<claimScore.size(); i++){
            System.out.print(claimScore.get(i)+ " ");
        }
        System.out.println();
        
        GeneralUtils.showOrderedSources(sourceScore);
        GeneralUtils.showOrderedClaims(claimScore);
       
    }
}
