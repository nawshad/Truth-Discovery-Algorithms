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
public class ThreeEstimates {
    public static void ThreeEstimatesFunction(double Ts_0_value, double Tv_0_value, int totalIter){
        //initialize To_S
        double init_To_value = Ts_0_value;
        ArrayList<Double> Ts_0 = new ArrayList<Double>();
        for(int i=0; i<scores.length; i++){
            Ts_0.add(init_To_value);
        }
        
        //init Tv_S
        double init_Tv_value = Tv_0_value;
        ArrayList<Double> Tv_0 = new ArrayList<Double>();
        for(int i=0; i<scores.length; i++){
            Tv_0.add(init_To_value);
        }
        
        int iter = 0;
        //int totalIter = 50;     
        while(iter < totalIter){     
            //Calculate Cv
            double positive = 0;
            double negative = 0;
            double norm = 0;
            double claim_score = 0;
            ArrayList<Double>  Cv = new ArrayList<Double>();
            //for each claim
            for(int i=0; i<scores[0].length;i++){
                //find out source list for that claim ID
                for(int j=0; j<GeneralUtils.sourceListforClaims(i).size(); j++){
                    positive += ((Ts_0.get(GeneralUtils.sourceListforClaims(i).get(j)))*(Ts_0.get(GeneralUtils.sourceListforClaims(i).get(j))));
                }
                
                //System.out.println("Positive: "+positive);
                //negative lists
                ArrayList<Integer> negativeSourceList = new ArrayList<Integer>();
                for(int k=0; k<GeneralUtils.sourceListforDataItem(i).size(); k++){
                    if(!GeneralUtils.sourceListforClaims(i).contains(GeneralUtils.sourceListforDataItem(i).get(k))){
                        negativeSourceList.add(GeneralUtils.sourceListforDataItem(i).get(k));
                    }
                }

                //System.out.println("For claim("+i+"): Negative Source List: "+ negativeSourceList);               
                for(int m=0; m<negativeSourceList.size(); m++){
                    negative += (1-((Ts_0.get(negativeSourceList.get(m)))*(Tv_0.get(negativeSourceList.get(m)))));
                }
                
                //System.out.println("Negative: "+negative);
                //sum scores for all sources of data items pointed by a claim
                ArrayList<Integer> allSourceListforClaim = new ArrayList<Integer>();

                for(int p=0; p<GeneralUtils.sourceListforDataItem(i).size(); p++){
                    //find all sources from different data item list pointed by a claim 
                    allSourceListforClaim.add(GeneralUtils.sourceListforDataItem(i).get(p));
                }

                claim_score = (positive + negative) / allSourceListforClaim.size();
                Cv.add(claim_score);

            }
            
            //System.out.println("Cv Scores: "+Cv);
            
            //Tv score calculation
            positive = 0;
            negative = 0;
            norm = 0;
            double Tv_score = 0;
            ArrayList<Double>  Tv = new ArrayList<Double>();
            //for each claim
            for(int i=0; i<scores[0].length;i++){
                //find out source list for that claim ID
                for(int j=0; j<GeneralUtils.sourceListforClaims(i).size(); j++){
                    positive += (Cv.get(i)/(1-Ts_0.get(GeneralUtils.sourceListforClaims(i).get(j))));
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
                    negative += (1-Cv.get(i))/(1-((Ts_0.get(negativeSourceList.get(m)))));
                }

                //sum scores for all sources of data items pointed by a claim
                ArrayList<Integer> allSourceListforClaim = new ArrayList<Integer>();

                for(int p=0; p<GeneralUtils.sourceListforDataItem(i).size(); p++){
                    //find all sources from different data item list pointed by a claim 
                    allSourceListforClaim.add(GeneralUtils.sourceListforDataItem(i).get(p));
                }

                claim_score = (positive + negative) / allSourceListforClaim.size();
                Tv.add(claim_score);

            }
            
            //System.out.println("Tv Scores: "+Tv);
            
            //Ts calculation
            ArrayList<Double> Ts = new ArrayList<Double>();
            positive = 0;
            negative = 0;
            norm = 0;
            double length_V_Ds = 0;
            double source_score = 0;
            for(int i=0;i<scores.length; i++){
                ArrayList<Integer> positiveClaimListforSource = new ArrayList<Integer>();
                ArrayList<Integer> negativeClaimListforSource = new ArrayList<Integer>();

                for(int j=0; j<scores[i].length; j++){      
                    if(scores[i][j] == 1){
                        positive += Cv.get(j)/(1-Tv.get(i));
                        positiveClaimListforSource.add(j);
                    }
                }

                //calculating negative claim List. First finds out all the data items pointed by the sources. Then find out all the claims list belonging to those DataItems
                for(int k=0; k<GeneralUtils.claimsListforDataItemGivenSourceID(i).size(); k++){
                    if(!positiveClaimListforSource.contains(GeneralUtils.claimsListforDataItemGivenSourceID(i).get(k))){
                        negativeClaimListforSource.add(GeneralUtils.claimsListforDataItemGivenSourceID(i).get(k));
                    }
                }
                //negative score calculation
                for(int l=0; l<negativeClaimListforSource.size(); l++){
                    negative += (1 - Cv.get(negativeClaimListforSource.get(l)))/(1 - Tv.get(i));
                }

                length_V_Ds = GeneralUtils.claimsListforDataItemGivenSourceID(i).size();
                source_score = (positive + negative) / length_V_Ds;
                Ts.add(source_score);

            }
            
            //System.out.println("Ts Scores: "+Ts);
            Ts_0 = Ts;
            
            System.out.println("\nIteration: "+iter);
            
            GeneralUtils.showOrderedSources(Ts);
            GeneralUtils.showOrderedClaims(Cv);
            GeneralUtils.showClaimsPerDataItems(Cv);
            
            iter++;
        }
    }
      
}
