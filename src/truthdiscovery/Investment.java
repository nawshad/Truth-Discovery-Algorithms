/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthdiscovery;

import java.util.ArrayList;
import java.util.List;
import static truthdiscovery.Main.Sv_count_List;
import static truthdiscovery.Main.Vs_count_List;
import static truthdiscovery.Main.listDataItems;
import static truthdiscovery.Main.scores;
import static truthdiscovery.Main.totalIter;
import utils.GeneralUtils;

/**
 *
 * @author nawshad
 */
public class Investment {
    public static void investmentCalculation(double Ts_0_value){     
        //init Sources
        double init_source_value = Ts_0_value; 
        ArrayList<Double> Ts_0 = new ArrayList<Double>();
        for(int i=0; i<scores.length; i++){
            Ts_0.add(init_source_value);
        }
         
        //init values for claims
        ArrayList<Double> Cv_0 = new ArrayList<Double>();
        ArrayList<Double> sourceScores = new ArrayList<Double>();
        ArrayList<Double> claimScores = new ArrayList<Double>();
        
        double Co = 0;
        for(int i=0; i<scores[0].length; i++){
            double Sv_Length = Sv_count_List.get(i);
            double S_Ds_Length = GeneralUtils.sourceListforDataItem(GeneralUtils.findDataItemIndex(i)).size();
            Co = (Sv_Length/S_Ds_Length);
            //System.out.println(Co);
            Cv_0.add(Co);
        }
        
        int iter = 0;
        while(iter < totalIter){
            sourceScores = new ArrayList<Double>();
            //Source value calculation
            double source_score = 0;
            for(int i=0; i<scores.length; i++){
                double multiply_Cv_Ts = 0;
                double denominator = 0;
                double sum_of_avg_Ts = 0;
                for (int j=0; j<scores[i].length; j++){
                    if(scores[i][j] == 1){
                        multiply_Cv_Ts = Cv_0.get(j) * Ts_0.get(i);        
                        //Find the source list of this claim
                        for(int k=0; k<GeneralUtils.sourceListforClaims(j).size(); k++){
                            int sourceID = GeneralUtils.sourceListforClaims(j).get(k);
                            //find T_score for that sourceID
                            double Ts = Ts_0.get(sourceID);
                            //divide it by that sources total claim count
                            double avg_Ts = Ts/Vs_count_List.get(sourceID);
                            sum_of_avg_Ts += avg_Ts; 
                        }
                    }
                }

                denominator = Vs_count_List.get(i)*sum_of_avg_Ts;
                source_score += multiply_Cv_Ts / denominator; 
                sourceScores.add(source_score);  
            }

            //System.out.println("Source Scores: "+sourceScores);
            Ts_0 = sourceScores;
            //System.out.println("Co_V values: "+Co_V);

            claimScores = new ArrayList<Double>();
            //Claim Score Calculation
            double sum_claim_score = 0;
            for(int i=0; i<scores.length; i++){
                double sum_avg_Ts = 0;
                for(int j=0; j<scores[i].length; j++){
                    if(scores[j][i] == 1){
                        double avg_Ts = Ts_0.get(j)/Vs_count_List.get(j);
                        sum_avg_Ts += avg_Ts;
                    }
                }
                sum_claim_score = Math.pow(sum_avg_Ts, 1.2);
                claimScores.add(sum_claim_score);
            }

            //System.out.println("Claim Scores: "+claimScores);
            Cv_0 = claimScores;
            
            System.out.println("\nIteration: "+iter);
            
            GeneralUtils.showOrderedSources(sourceScores);
            GeneralUtils.showOrderedClaims(claimScores);
            GeneralUtils.showClaimsPerDataItems(claimScores);
            
            iter++;
            
               
        }
   }
    
   public static void pooledInvestmentCalculation(double Ts_0_value, int totalIter){
           //init Sources
        double init_source_value = Ts_0_value; 
        ArrayList<Double> Ts_0 = new ArrayList<Double>();
        for(int i=0; i<scores.length; i++){
            Ts_0.add(init_source_value);
        }
         
        //init values for claims
        ArrayList<Double> Cv_0 = new ArrayList<Double>();
        ArrayList<Double> sourceScores = new ArrayList<Double>();
        ArrayList<Double> claimScores = new ArrayList<Double>();
        
        double Co = 0;
        for(int i=0; i<scores[0].length; i++){
            double S_Ds_Length = listDataItems.get(GeneralUtils.findDataItemIndex(i)).size();
            Co = (1/S_Ds_Length);
            //System.out.println(Co);
            Cv_0.add(Co);
        }
        
        //System.out.println("Co_V: "+ Co_V);
        
        int iter = 0;
        while(iter < totalIter){
            sourceScores = new ArrayList<Double>();
            //Source value calculation
            double source_score = 0;
            for(int i=0; i<scores.length; i++){
                double multiply_Cv_Ts = 0;
                double denominator = 0;
                double sum_of_avg_Ts = 0;
                for (int j=0; j<scores[i].length; j++){
                    if(scores[i][j] == 1){
                        multiply_Cv_Ts = Cv_0.get(j) * Ts_0.get(i);        
                        //Find the source list of this claim
                        for(int k=0; k<GeneralUtils.sourceListforClaims(j).size(); k++){
                            int sourceID = GeneralUtils.sourceListforClaims(j).get(k);
                            //find T_score for that sourceID
                            double Ts = Ts_0.get(sourceID);
                            //divide it by that sources total claim count
                            double avg_Ts = Ts/Vs_count_List.get(sourceID);
                            sum_of_avg_Ts += avg_Ts; 
                        }
                    }
                }

                denominator = Vs_count_List.get(i)*sum_of_avg_Ts;
                source_score += multiply_Cv_Ts / denominator; 
                sourceScores.add(source_score);  
            }

            //System.out.println("Source Scores: "+sourceScores);
            Ts_0 = sourceScores;
            //System.out.println("Co_V values: "+Co_V);

            claimScores = new ArrayList<Double>();
            
            //Claim Score Calculation
            double final_claim_score = 0;
            
            for(int i=0; i<scores.length; i++){
                double Hv_score = 0;
                double powered_Hr_score = 0;
                double powered_Hr_score_sum = 0;
                for(int j=0; j<scores[i].length; j++){
                    if(scores[j][i] == 1){
                        double avg_Ts = Ts_0.get(j)/Vs_count_List.get(j);
                        Hv_score += avg_Ts;
                    }  
                }
                //Calculate Avg Hr Score for each claim: i, and sum them up to find the Powered Hr_Score Summation
                double Hr_score = 0;
                for(int k=0; k<listDataItems.get(GeneralUtils.findDataItemIndex(i)).size(); k++){
                    ArrayList<Integer> sourceList = new ArrayList<Integer>();
                    sourceList = GeneralUtils.sourceListforClaims(listDataItems.get(GeneralUtils.findDataItemIndex(i)).get(k));
                    for(int m=0; m<sourceList.size(); m++){
                        Hr_score += Ts_0.get(m)/sourceList.size();
                    }
                    powered_Hr_score = Math.pow(Hr_score, 1.4);
                    powered_Hr_score_sum += powered_Hr_score;
                }
                final_claim_score = Hv_score * (Math.pow(Hv_score, 1.4) /powered_Hr_score_sum);
                claimScores.add(final_claim_score);
                
            }
            
            Cv_0 = claimScores;
            
            System.out.println("\nIteration: "+iter);
            
            GeneralUtils.showOrderedSources(sourceScores);
            GeneralUtils.showOrderedClaims(claimScores);
            GeneralUtils.showClaimsPerDataItems(claimScores);
            
            iter++;
         
        }
    } 
}
