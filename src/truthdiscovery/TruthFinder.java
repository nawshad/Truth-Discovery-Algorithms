/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthdiscovery;

import java.util.ArrayList;
import static truthdiscovery.Main.Vs_count_List;
import static truthdiscovery.Main.gamma;
import static truthdiscovery.Main.scores;
import static truthdiscovery.Main.totalIter;
import utils.GeneralUtils;

/**
 *
 * @author nawshad
 */
public class TruthFinder {
    public static void truthFinderFunction(){
        //init To_S;
       double source_init_value = 0.8;
       ArrayList<Double> To_S = new ArrayList<Double>();
        for(int i=0; i<scores.length; i++){
            To_S.add(source_init_value);
        } 

       //Copy scores matrix 
       
        //GeneralUtils.showMatrix(scores);
        
        double [][] copy_scores_for_truthfinder = new double[7][7];
        
        for(int i = 0; i<copy_scores_for_truthfinder.length; i++){
            for (int j=0; j<copy_scores_for_truthfinder[i].length; j++){
                copy_scores_for_truthfinder[i][j] = scores[i][j];
            }
        }
        
        int iter = 0;
        
        while(iter < totalIter){
            ArrayList<Double> claimScores = new ArrayList<Double>(); 
            System.out.println("Iteration:"+iter);
            
            for(int i=0; i<copy_scores_for_truthfinder.length; i++){
                double claim_Score = 0;
                for(int j=0; j<copy_scores_for_truthfinder[i].length; j++){
                    if(copy_scores_for_truthfinder[j][i]>0){
                        claim_Score += -(Math.log(1-To_S.get(j)));
                    }
                }
                claimScores.add(claim_Score);
            }

            System.out.println("ClaimScores:"+claimScores);

            //Update the matrix with this score;
            /*for(int i=0; i<copy_scores_for_truthfinder.length; i++){
                for(int j=0; j<copy_scores_for_truthfinder[i].length; j++){
                    if(scores[j][i] > 0){
                       copy_scores_for_truthfinder[j][i] = claimScores.get(i);
                    }
                }

            }*/

            ArrayList<Double> sourceScores = new ArrayList<Double>();
            for(int i=0; i<copy_scores_for_truthfinder.length; i++){
                double sum_per_source = 0;
                for(int j=0; j<copy_scores_for_truthfinder[i].length; j++){
                    if(copy_scores_for_truthfinder[i][j]>0){
                        sum_per_source += (1 - Math.exp(-gamma*(claimScores.get(j)/*copy_scores_for_truthfinder[i][j])*/)));
                    }
                }
                double final_sourceScore = (sum_per_source / Vs_count_List.get(i));
                sourceScores.add(final_sourceScore);
            }
            
            
            System.out.println("Scource Scores: "+sourceScores);
            
            /*for(int i=0; i<copy_scores_for_truthfinder.length; i++){
                for(int j=0; j<copy_scores_for_truthfinder[i].length; j++){ 
                    if(copy_scores_for_truthfinder[i][j]>0) copy_scores_for_truthfinder[i][j] = sourceScores.get(i); 
                }
            }*/

            To_S = sourceScores;
            
            GeneralUtils.showOrderedSources(sourceScores);
            GeneralUtils.showOrderedClaims(claimScores);
            GeneralUtils.showClaimsPerDataItems(claimScores);
            
            iter++;
            
        }
        
    }
      
}
