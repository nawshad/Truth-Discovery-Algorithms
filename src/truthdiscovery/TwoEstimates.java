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
public class TwoEstimates {
    public static void twoEstimatesFunction(double Ts_0_value, int totalIter){
        ArrayList<Double> Ts_0 = new ArrayList<Double>(); 
        double init_value = Ts_0_value;
        for(int i=0; i<row; i++){
            Ts_0.add(init_value);
        }
        
        //int totalIter = 400;
        int iter = 0;
        while (iter < totalIter){
            //claim Score calculation
            //positive, negative, norm
            double positive = 0;
            double negative = 0;
            double norm = 0;
            double claim_score = 0;
            ArrayList<Double>  Cv = new ArrayList<Double>();
            //for each claim
            for(int i=0; i<col;i++){
                //find out source list for that claim ID
                for(int j=0; j<GeneralUtils.sourceListforClaims(i).size(); j++){
                    positive += Ts_0.get(GeneralUtils.sourceListforClaims(i).get(j));
                }
                //negative lists
                ArrayList<Integer> negativeSourceList = new ArrayList<Integer>();
                for(int k=0; k<GeneralUtils.sourceListforDataItem(i).size(); k++){
                    if(!GeneralUtils.sourceListforClaims(i).contains(GeneralUtils.sourceListforDataItem(i).get(k))){
                        negativeSourceList.add(GeneralUtils.sourceListforDataItem(i).get(k));
                    }
                }

                //System.out.println("For claim("+i+"): Negative Source List: "+ negativeSourceList);               
                for(int m=0; m<negativeSourceList.size(); m++){
                    negative += Ts_0.get(negativeSourceList.get(m));
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
            
            //Source Score calculation
            ArrayList<Double> sourceScores = new ArrayList<Double>();
            positive = 0;
            negative = 0;
            norm = 0;
            double source_score = 0;
            for(int i=0;i<row; i++){
                double length_V_Ds = 0;
                double prev_source_score = Ts_0.get(i);
                ArrayList<Integer> positiveClaimListforSource = new ArrayList<Integer>();
                ArrayList<Integer> negativeClaimListforSource = new ArrayList<Integer>();

                for(int j=0; j<col; j++){      
                    if(scores.get(i).get(j) == 1){
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
            
            Ts_0 = sourceScores;
            //Cv = claimScores; 
            
            System.out.println("\nIteration: "+iter);
            
            System.out.println("Ordered Sources: "+GeneralUtils.showOrderedSources(sourceScores));
            System.out.println("Ordered Claims:"+GeneralUtils.showOrderedClaims(Cv));
            System.out.println("Source Scores: "+sourceScores);
            System.out.println("Claim Scores: "+Cv);
            GeneralUtils.showClaimsPerDataItems(Cv);
            
           
            
            iter++;
                
        }
        
    }
    
}
