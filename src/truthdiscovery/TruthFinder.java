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
    public static void truthFinderFunction(double Ts_0_value){
        //init To_S;
       double source_init_value = Ts_0_value;
       ArrayList<Double> Ts_0 = new ArrayList<Double>();
        for(int i=0; i<scores.length; i++){
            Ts_0.add(source_init_value);
        } 

        int iter = 0;    
        while(iter < totalIter){
            ArrayList<Double> claimScores = new ArrayList<Double>(); 
            
            for(int i=0; i<scores.length; i++){
                double claim_Score = 0;
                for(int j=0; j<scores[i].length; j++){
                    if(scores[j][i] == 1){
                        claim_Score += -(Math.log(1-Ts_0.get(j)));
                    }
                }
                claimScores.add(claim_Score);
            }

            //System.out.println("ClaimScores:"+claimScores);

            ArrayList<Double> sourceScores = new ArrayList<Double>();
            for(int i=0; i<scores.length; i++){
                double sum_per_source = 0;
                for(int j=0; j<scores[i].length; j++){
                    if(scores[i][j] == 1){
                        sum_per_source += (1 - Math.exp(-gamma*(claimScores.get(j)/*copy_scores_for_truthfinder[i][j])*/)));
                    }
                }
                double final_sourceScore = (sum_per_source / Vs_count_List.get(i));
                sourceScores.add(final_sourceScore);
            }
            
            
           

            Ts_0 = sourceScores;
            
            System.out.println("\nIteration: " +iter);
            
            System.out.println("Ordered Sources:"+GeneralUtils.showOrderedSources(sourceScores));
            System.out.println("Ordered Claims:"+GeneralUtils.showOrderedClaims(claimScores));
            System.out.println("Scource Scores: "+sourceScores);
            System.out.println("Claim Scores: "+claimScores);
            GeneralUtils.showClaimsPerDataItems(claimScores);
            
            iter++;
            
        }
        
    }
      
}
