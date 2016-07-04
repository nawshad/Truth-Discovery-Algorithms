/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthdiscovery;

import java.util.ArrayList;
import static truthdiscovery.Main.col;
import static truthdiscovery.Main.row;
import static truthdiscovery.Main.scores;
import static truthdiscovery.Main.totalIter;
import utils.GeneralUtils;

/**
 *
 * @author nawshad
 */
public class Cosine {
    public static void cosineFunctionCalculation(double Ts_0_value, double Cv_0_value, int totalIter){
         
         //init To_S;
        double source_init_value = Ts_0_value;
        ArrayList<Double> Ts_0 = new ArrayList<Double>();
        for(int i=0; i<row; i++){
            Ts_0.add(source_init_value);
        } 
        
        //init claim
        double claim_init_value = Cv_0_value;
        ArrayList<Double> Cv_0 = new ArrayList<Double>();
        for(int i=0; i<col; i++){
            Cv_0.add(source_init_value);
        } 
        
        //Calculate source scores
        int iter = 0;
        while(iter < totalIter){
            double source_score = 0;
             ArrayList<Double> sourceScores = new ArrayList<Double>();
             ArrayList<Double> claimScores = new ArrayList<Double>();

            for(int i=0; i<row; i++){       
                double positive = 0;
                double negative = 0;
                double norm = 0;
                double length_V_Ds = 0;
                double prev_source_score = Ts_0.get(i);
                ArrayList<Integer> positiveClaimListforSource = new ArrayList<Integer>();
                ArrayList<Integer> negativeClaimListforSource = new ArrayList<Integer>();

                for(int j=0; j<col; j++){      
                    if(scores.get(i).get(j) == 1){
                        positive += Cv_0.get(j);
                        positiveClaimListforSource.add(j);
                    }
                }

                //calculating negative claim List
                for(int k=0; k<GeneralUtils.claimsListforDataItemGivenSourceID(i).size(); k++){
                    if(!positiveClaimListforSource.contains(GeneralUtils.claimsListforDataItemGivenSourceID(i).get(k))){
                        negativeClaimListforSource.add(GeneralUtils.claimsListforDataItemGivenSourceID(i).get(k));
                    }
                }

                //negative score calculation
                for(int l=0; l<negativeClaimListforSource.size(); l++){
                    negative += Cv_0.get(negativeClaimListforSource.get(l));
                }

                //length of V_Ds 
                length_V_Ds = GeneralUtils.claimsListforDataItemGivenSourceID(i).size();

                //calculate sum of all claims
                double sum_Cv = 0;
                for(int m=0; m<GeneralUtils.claimsListforDataItemGivenSourceID(i).size(); m++){
                    sum_Cv += Cv_0.get(GeneralUtils.claimsListforDataItemGivenSourceID(i).get(m));
                }

                double sum_Cv_sq = Math.pow(sum_Cv, 2);
                norm = Math.sqrt(length_V_Ds * sum_Cv_sq);

                source_score = (0.8*prev_source_score) + 0.2*((positive-negative)/norm);
                sourceScores.add(source_score);
                //save in a arrayList
            }

            //System.out.println("Source Scores: "+sourceScores);

            //claim Score calculation
            
            //posiive, negative, norm
            double positive = 0;
            double negative = 0;
            double norm = 0;
            double claim_score = 0;
            //for each claim
            for(int i=0; i<col;i++){
                //find out source list for that claim ID
                for(int j=0; j<GeneralUtils.sourceListforClaims(i).size(); j++){
                    positive += Math.pow(Ts_0.get(GeneralUtils.sourceListforClaims(i).get(j)), 3);
                }
                
                //negative lists
                ArrayList<Integer> negativeSourceList = new ArrayList<Integer>();
                for(int k=0; k<GeneralUtils.sourceListforDataItem(i).size(); k++){
                    if(!GeneralUtils.sourceListforClaims(i).contains(GeneralUtils.sourceListforDataItem(i).get(k))){
                        negativeSourceList.add(GeneralUtils.sourceListforDataItem(i).get(k));
                    }
                }
            
                for(int m=0; m<negativeSourceList.size(); m++){
                    negative += Math.pow(Ts_0.get(negativeSourceList.get(m)), 3);
                }
                
                //sum scores for all sources of data items pointed by a claim
                ArrayList<Integer> allSourceListforClaim = new ArrayList<Integer>();

                for(int p=0; p<GeneralUtils.sourceListforDataItem(i).size(); p++){
                    //find all sources from different data item list pointed by a claim 
                    allSourceListforClaim.add(GeneralUtils.sourceListforDataItem(i).get(p));
                }

                
                for (int q=0; q<allSourceListforClaim.size(); q++){
                    norm += Math.pow(Ts_0.get(allSourceListforClaim.get(q)),3);
                }
                
                claim_score = (positive - negative) / norm;
                claimScores.add(claim_score);
                
            }
            
            /*if(GeneralUtils.isSameSet(GeneralUtils.showOrderedSources(Ts_0),GeneralUtils.showOrderedSources(sourceScores))){
                break;
            };*/
            
            Cv_0 = claimScores;
            Ts_0 = sourceScores;
            
            System.out.println("\nIteration: "+iter);
            
            System.out.println("Ordered Sources:"+GeneralUtils.showOrderedSources(sourceScores));
            System.out.println("Ordered Claims:"+GeneralUtils.showOrderedClaims(claimScores));
            System.out.println("Source scores:" +sourceScores);
            System.out.println("Claim scores: "+claimScores);
            GeneralUtils.showClaimsPerDataItems(claimScores);

            iter++;
        }

    }
}
