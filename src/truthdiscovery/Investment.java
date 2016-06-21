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
    public static void investmentCalculation(){     
        //init Sources
        double init_source_value = 0.8; 
        ArrayList<Double> To_S = new ArrayList<Double>();
        for(int i=0; i<scores.length; i++){
            To_S.add(init_source_value);
        }
         
        //init values for claims
        ArrayList<Double> Co_V = new ArrayList<Double>();
        ArrayList<Double> sourceScores = new ArrayList<Double>();
        ArrayList<Double> claimScores = new ArrayList<Double>();
        
        double Co = 0;
        for(int i=0; i<scores[0].length; i++){
            double Sv_Length = Sv_count_List.get(i);
            double S_Ds_Length = GeneralUtils.sourceListforDataItem(GeneralUtils.findDataItemIndex(i)).size();
            Co = (Sv_Length/S_Ds_Length);
            System.out.println(Co);
            Co_V.add(Co);
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
                        multiply_Cv_Ts = Co_V.get(j) * To_S.get(i);        
                        //Find the source list of this claim
                        for(int k=0; k<GeneralUtils.sourceListforClaims(j).size(); k++){
                            int sourceID = GeneralUtils.sourceListforClaims(j).get(k);
                            //find T_score for that sourceID
                            double Ts = To_S.get(sourceID);
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

            System.out.println("Source Scores: "+sourceScores);
            To_S = sourceScores;
            //System.out.println("Co_V values: "+Co_V);

            claimScores = new ArrayList<Double>();
            //Claim Score Calculation
            double sum_claim_score = 0;
            for(int i=0; i<scores.length; i++){
                double sum_avg_Ts = 0;
                for(int j=0; j<scores[i].length; j++){
                    if(scores[j][i] == 1){
                        double avg_Ts = To_S.get(j)/Vs_count_List.get(j);
                        sum_avg_Ts += avg_Ts;
                    }
                }
                sum_claim_score = Math.pow(sum_avg_Ts, 1.2);
                claimScores.add(sum_claim_score);
            }

            System.out.println("Claim Scores: "+claimScores);
            Co_V = claimScores;
            
            GeneralUtils.showOrderedSources(sourceScores);
            GeneralUtils.showOrderedClaims(claimScores);
            GeneralUtils.showClaimsPerDataItems(claimScores);
            
            iter++;
            
               
        }
   }
    
   public static void pooledInvestmentCalculation(){
           //init Sources
        double init_source_value = 0.8; 
        ArrayList<Double> To_S = new ArrayList<Double>();
        for(int i=0; i<scores.length; i++){
            To_S.add(init_source_value);
        }
         
        //init values for claims
        ArrayList<Double> Co_V = new ArrayList<Double>();
        ArrayList<Double> sourceScores = new ArrayList<Double>();
        ArrayList<Double> claimScores = new ArrayList<Double>();
        
        double Co = 0;
        for(int i=0; i<scores[0].length; i++){
            double S_Ds_Length = listDataItems.get(GeneralUtils.findDataItemIndex(i)).size();
            Co = (1/S_Ds_Length);
            //System.out.println(Co);
            Co_V.add(Co);
        }
        
        System.out.println("Co_V: "+ Co_V);
        
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
                        multiply_Cv_Ts = Co_V.get(j) * To_S.get(i);        
                        //Find the source list of this claim
                        for(int k=0; k<GeneralUtils.sourceListforClaims(j).size(); k++){
                            int sourceID = GeneralUtils.sourceListforClaims(j).get(k);
                            //find T_score for that sourceID
                            double Ts = To_S.get(sourceID);
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

            System.out.println("Source Scores: "+sourceScores);
            To_S = sourceScores;
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
                        double avg_Ts = To_S.get(j)/Vs_count_List.get(j);
                        Hv_score += avg_Ts;
                    }  
                }
                //calculate Avg Hr Score for each each claim i, and sum them up to find the 
                //Calculate Powered Hr_Score Summation
                double Hr_score = 0;
                for(int k=0; k<listDataItems.get(GeneralUtils.findDataItemIndex(i)).size(); k++){
                    ArrayList<Integer> sourceList = new ArrayList<Integer>();
                    sourceList = GeneralUtils.sourceListforClaims(listDataItems.get(GeneralUtils.findDataItemIndex(i)).get(k));
                    for(int m=0; m<sourceList.size(); m++){
                        Hr_score += To_S.get(m)/sourceList.size();
                    }
                    powered_Hr_score = Math.pow(Hr_score, 1.4);
                    powered_Hr_score_sum += powered_Hr_score;
                }
                final_claim_score = Hv_score * (Math.pow(Hv_score, 1.4) /powered_Hr_score_sum);
                claimScores.add(final_claim_score);
                
            }
            
            Co_V = claimScores;
            
            GeneralUtils.showOrderedSources(sourceScores);
            GeneralUtils.showOrderedClaims(claimScores);
            GeneralUtils.showClaimsPerDataItems(claimScores);
            
            iter++;
         
        }
    }
    
}
