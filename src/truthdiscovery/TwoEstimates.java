/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthdiscovery;

import java.util.ArrayList;
import static truthdiscovery.Main.scores;
import static truthdiscovery.Main.totalIter;
import utils.GeneralUtils;

/**
 *
 * @author nawshad
 */
public class TwoEstimates {
    public static void twoEstimatesFunction(){
        ArrayList<Double> To_S = new ArrayList<Double>(); 
        double init_value = 0.8;
        for(int i=0; i<scores.length; i++){
            To_S.add(init_value);
        }
        
        int totalIter = 1;
        
        int iter = 0;
        while (iter < totalIter){
            //ArrayList<Double> claimScores = new ArrayList<Double>();
            ArrayList<Double> sourceScores = new ArrayList<Double>();
            //claim Score calculation
            //posiive, negative, norm
            double positive = 0;
            double negative = 0;
            double norm = 0;
            double claim_score = 0;
            ArrayList<Double>  Cv = new ArrayList<Double>();
            //for each claim
            for(int i=0; i<scores[0].length;i++){
                //find out source list for that claim ID
                for(int j=0; j<GeneralUtils.sourceListforClaims(i).size(); j++){
                    positive += To_S.get(GeneralUtils.sourceListforClaims(i).get(j));
                }

                //negative lists
                ArrayList<Integer> negativeSourceList = new ArrayList<Integer>();
                for(int k=0; k<GeneralUtils.sourceListforDataItem(i).size(); k++){
                    
                    if(!GeneralUtils.sourceListforClaims(i).contains(GeneralUtils.sourceListforDataItem(i).get(k))){
                        negativeSourceList.add(GeneralUtils.sourceListforDataItem(i).get(k));
                    }
                }

                System.out.println("For claim("+i+"): Negative Source List: "+ negativeSourceList);               
                for(int m=0; m<negativeSourceList.size(); m++){
                    negative += To_S.get(negativeSourceList.get(m));
                }

                //sum scores for all sources of data items pointed by a claim
                ArrayList<Integer> allSourceListforClaim = new ArrayList<Integer>();

                for(int p=0; p<GeneralUtils.sourceListforDataItem(i).size(); p++){
                    //find all sources from different data item list pointed by a claim 
                    allSourceListforClaim.add(GeneralUtils.sourceListforDataItem(i).get(p));
                }

                claim_score = (positive + negative) / allSourceListforClaim.size();
                Cv.add(claim_score);

            }

            //System.out.println("Claim Scores: "+Cv);

            //Source Score 
            positive = 0;
            negative = 0;
            norm = 0;
            double source_score = 0;
            for(int i=0;i<scores.length; i++){
                double length_V_Ds = 0;
                double prev_source_score = To_S.get(i);
                ArrayList<Integer> positiveClaimListforSource = new ArrayList<Integer>();
                ArrayList<Integer> negativeClaimListforSource = new ArrayList<Integer>();

                for(int j=0; j<scores[i].length; j++){      
                    if(scores[i][j] == 1){
                        positive += Cv.get(j);
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
                    negative += (1 - Cv.get(negativeClaimListforSource.get(l)));
                }

                length_V_Ds = GeneralUtils.claimsListforDataItemGivenSourceID(i).size();
                source_score = (positive + negative) / length_V_Ds;
                sourceScores.add(source_score);

            }
            
            To_S = sourceScores;
            //Cv = claimScores; 
            
            System.out.println("Iter: "+iter);
            
            /*GeneralUtils.showOrderedSources(sourceScores);
            GeneralUtils.showOrderedClaims(Cv);
            GeneralUtils.showClaimsPerDataItems(Cv);
            
            System.out.println("Source Scores: "+sourceScores);
            System.out.println("Claim Scores: "+Cv);*/
            
            iter++;
                
        }
        
    }
    
}
