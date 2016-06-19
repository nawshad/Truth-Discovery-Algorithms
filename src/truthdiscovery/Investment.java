/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthdiscovery;

import java.util.ArrayList;
import java.util.List;
import static truthdiscovery.Main.Sv_count_List;
import static truthdiscovery.Main.To_S;
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
        //Calculating Cv_0 
        ArrayList<Double> Cv_0_List = new ArrayList<Double>();
        for(int i = 0; i<scores.length; i++){
            double Cv_0_SourceCount = Sv_count_List.get(i);
            //System.out.println(Cv_0_SourceCount);
            double val = GeneralUtils.numberSourcesForDataItem(GeneralUtils.findDataItemIndex(i));
            //System.out.println("Val: "+val);
            double Cv_0_Value = Cv_0_SourceCount/val;
            //System.out.println("C_"+i+":"+(float)(Cv_0_Value/val));
            Cv_0_List.add(Cv_0_Value);
        }
        
        double[][] scores_for_investment = new double[7][7];
        
        System.out.println("Cv_0_values: "+Cv_0_List);
       
        //Should update the scores_for_investment with this claim value, init Ts with another const value.
        for(int i = 0; i<scores.length; i++){
            for (int j=0; j<scores[i].length; j++){
                if(scores[i][j]==1){
                    scores_for_investment[i][j] = Cv_0_List.get(i);
                }else{
                    scores_for_investment[i][j] = scores[i][j];
                }
            }
        }
       
        System.out.println("Updated Matrix with initial Cv_0 value");
        GeneralUtils.showMatrix(scores_for_investment);
       
        //Traverse through the matrix and update the claim values as per calculated source score. 
        int iter = 0;
        ArrayList<Double> source_Scores = new ArrayList<Double>();
        ArrayList<Double> claim_Scores = new ArrayList<Double>();
        
       
        while(iter<totalIter){
            source_Scores = new ArrayList<Double>();
            for (int i=0; i<scores_for_investment.length; i++){
                double per_source_sum  = 0;
                double claim_source_multiplied_score = 0;
                double denominator = 0;
                for(int j=0; j<scores_for_investment[i].length; j++){
                    claim_source_multiplied_score = scores_for_investment[i][j]*To_S.get(i);
                    //System.out.println("Claim Source Multiply Score:"+claim_source_multiplied_score);
                    //denominator = 0;
                    for(int k = 0; k < GeneralUtils.sourceListforClaims(j /*,scores_for_investment*/).size(); k++){
                        int Vr_count = GeneralUtils.sourceListforClaims(j /*scores_for_investment*/).size(); 
                        //System.out.println("Vr_count:"+Vr_count);
                        denominator+= To_S.get(GeneralUtils.sourceListforClaims(j /* scores_for_investment*/).get(k))/Vr_count;
                    }
                    //System.out.println("Denominator:"+denominator);
                    denominator*= Vs_count_List.get(i);
                    //System.out.println("Denominator after multiplication:"+denominator);
                    per_source_sum += claim_source_multiplied_score / denominator;
                }

                source_Scores.add(per_source_sum);

                //System.out.println("Source scores:"+per_source_sum);
                //Update claims of that source 
                for(int l=0; l<scores_for_investment.length; l++){
                    for (int m = 0; m<scores_for_investment[l].length; m++){
                        if(scores [l][m] == 1){
                            scores_for_investment[l][m] = per_source_sum;

                        }
                    }
                } 
            }
            System.out.println("To Scores: "+To_S);
            To_S =  source_Scores;
        
            //Calculate claims and update source values, here we have to initialize source values again and then iterate.
            claim_Scores = new ArrayList<Double>();
            for(int i = 0; i<scores_for_investment[0].length; i++){
                double sum_per_claim = 0;
                for(int j=0; j<scores_for_investment.length; j++){
                    sum_per_claim += scores_for_investment[j][i]/Vs_count_List.get(j);
                }
                double powered_sum_per_claim = Math.pow(sum_per_claim, 1.2);
                claim_Scores.add(powered_sum_per_claim);
                for(int j=0; j<scores_for_investment.length; j++){
                    scores_for_investment[j][i] = powered_sum_per_claim;
                } 
            }
            
           System.out.println("Iteration: "+iter);
           System.out.println("Source Scores: "+source_Scores);
           System.out.println("Claim Scores: "+claim_Scores);
            
           iter++;
        }
        
        GeneralUtils.showOrderedSources(source_Scores);
        GeneralUtils.showOrderedClaims(claim_Scores); 
        GeneralUtils.showClaimsPerDataItems(claim_Scores);
   }
    
   public static void pooledInvestmentCalculation(){
       //Calculating Cv_0 
        ArrayList<Double> Cv_0_List = new ArrayList<Double>();
        //System.out.println(listDataItems.get(GeneralUtils.findDataItemIndex(0)).size());
        for(int i = 0; i<scores.length; i++){
            double dataItemSize = listDataItems.get(GeneralUtils.findDataItemIndex(i)).size();
            //System.out.println(dataItemSize);
            double Cv_0_Value = 1/ dataItemSize;
            Cv_0_List.add(Cv_0_Value);
        }
        
        double[][] scores_for_investment = new double[7][7];
        
        System.out.println("Cv_0_values: "+Cv_0_List);
       
        //Should update the scores_for_investment with this claim value, init Ts with another const value.
        for(int i = 0; i<scores.length; i++){
            for (int j=0; j<scores[i].length; j++){
                if(scores[i][j]==1){
                    scores_for_investment[i][j] = Cv_0_List.get(i);
                }else{
                    scores_for_investment[i][j] = scores[i][j];
                }
            }
        }
       
        System.out.println("Updated Matrix with initial Cv_0 value");
        GeneralUtils.showMatrix(scores_for_investment);
        
       
        //Traverse through the matrix and update the claim values as per calculated source score. 
        int iter = 0;
        ArrayList<Double> source_Scores_List = new ArrayList<Double>();
        ArrayList<Double> claim_Scores_List = new ArrayList<Double>();
        
        while(iter<totalIter){
            source_Scores_List = new ArrayList<Double>();
            for (int i=0; i<scores_for_investment.length; i++){
                double per_source_sum  = 0;
                double claim_source_multiplied_score = 0;
                double denominator = 0;
                for(int j=0; j<scores_for_investment[i].length; j++){
                    claim_source_multiplied_score = scores_for_investment[i][j]*To_S.get(i);
                    //System.out.println("Claim Source Multiply Score:"+claim_source_multiplied_score);
                    //denominator = 0;
                    for(int k = 0; k < GeneralUtils.sourceListforClaims(j/* scores*/).size(); k++){
                        int Vr_count = GeneralUtils.sourceListforClaims(j/*, scores*/).size();
                        //System.out.println("Vr_Count:"+Vr_count);
                        denominator+= To_S.get(GeneralUtils.sourceListforClaims(j/*, scores*/).get(k))/Vr_count;

                    }
                    //System.out.println("Denominator:"+denominator);
                    denominator*= Vs_count_List.get(i);
                    //System.out.println("Denominator after multiplication:"+denominator);
                    per_source_sum += claim_source_multiplied_score / denominator;
                }

                source_Scores_List.add(per_source_sum);

                //System.out.println("Source scores:"+per_source_sum);
                //Update claims of that source 
                for(int l=0; l<scores_for_investment.length; l++){
                    for (int m = 0; m<scores_for_investment[l].length; m++){
                        if(scores [l][m] == 1){
                            scores_for_investment[l][m] = per_source_sum;

                        }
                    }
                } 
            }
            System.out.println("To Scores: "+To_S);
            To_S =  source_Scores_List;
        
            //Calculate claims and update source values, here we have to initialize source values again and then iterate.
            claim_Scores_List = new ArrayList<Double>();
            
            
            for(int i = 0; i<scores_for_investment[0].length; i++){
                double Hv_Score = 0;
                double powered_hr_score = 0;
                double powered_hr_score_sum = 0;
                double final_claim_Score = 0;
                
                for(int j=0; j<scores_for_investment.length; j++){
                    Hv_Score += scores_for_investment[j][i]/Vs_count_List.get(j);
                }
                
                //Calculate Powered Hr_Score Summation
                double Hr_score = 0;
                for(int k=0; k<listDataItems.get(GeneralUtils.findDataItemIndex(i)).size(); k++){
                    ArrayList<Integer> sourceList = new ArrayList<Integer>();
                    sourceList = GeneralUtils.sourceListforClaims(listDataItems.get(GeneralUtils.findDataItemIndex(i)).get(k)/*, scores_for_investment*/);
                    for(int m=0; m<sourceList.size(); m++){
                        Hr_score += To_S.get(m)/sourceList.size();
                    }
                    powered_hr_score = Math.pow(Hr_score, 1.4);
                    powered_hr_score_sum += powered_hr_score;
                }
                final_claim_Score = Hv_Score * (Math.pow(Hv_Score, 1.4) /powered_hr_score_sum);
                
                claim_Scores_List.add(final_claim_Score);
                for(int j=0; j<scores_for_investment.length; j++){
                    scores_for_investment[j][i] =  final_claim_Score;
                }
                
            }
            
           System.out.println("Iteration: "+iter);
           System.out.println("Source Scores: "+source_Scores_List);
           System.out.println("Claim Scores: "+claim_Scores_List);
            
           iter++;
        }
        
        GeneralUtils.showOrderedSources(source_Scores_List);
        GeneralUtils.showOrderedClaims(claim_Scores_List); 
        GeneralUtils.showClaimsPerDataItems(claim_Scores_List);
       
       
   } 
    
}
