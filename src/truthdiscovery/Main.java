/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthdiscovery;

import java.util.ArrayList;
import java.util.Arrays;
import utils.GeneralUtils;

/**
 *
 * @author nawshad
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static int totalIter = 20;
    public static double gamma = 0.3;
    
    public static ArrayList<String> sourceList = new ArrayList<String>();
    public static ArrayList<String> claimList = new ArrayList<String>(); 

    //Create and store a list of list for Data Items
    public static ArrayList<ArrayList<Integer>> listDataItems = new ArrayList<ArrayList<Integer>>(); 
    public static ArrayList<Integer> Russia = new ArrayList<Integer>();
    public static ArrayList<Integer> USA = new ArrayList<Integer>();
    public static ArrayList<Integer> France = new ArrayList<Integer>();
    
    /* A 2D matrix stores the relationship between sources and claims.
         Rows are sources and columns are claims. 1 means there is a connection between source and claims.
        */  
      public static double scores[][]={
          // C0 C1 C2 C3 C4 C5 C6
      /*S0*/{1, 0, 0, 0, 0, 0, 0},
      /*S1*/{1, 0, 0, 1, 0, 0, 0},
      /*S2*/{0, 1, 0, 0, 0, 0, 0},
      /*S3*/{0, 0, 1, 0, 1, 0, 0},
      /*S4*/{0, 0, 0, 0, 1, 1, 0},
      /*S5*/{0, 0, 0, 0, 1, 0, 0},
      /*S6*/{0, 0, 0, 0, 0, 0, 1},
        };
      
    public static ArrayList<Integer> Vs_count_List = new ArrayList<Integer>(); 
    //Calculating the number of sources for particular claims and store them in a list named numberSourcesforClaimsList
    public static ArrayList<Integer> Sv_count_List = new ArrayList<Integer>();
    //public static ArrayList<Double> To_S = new ArrayList<Double>();
       
    
    public static void main(String[] args) {
          
        Russia.add(0);// Each Data Items store a reference to the claim index.
        Russia.add(1);
        Russia.add(2);
        
        listDataItems.add(Russia);
        
        USA.add(3);
        USA.add(4);
        
        listDataItems.add(USA);

        France.add(5);
        France.add(6);
        
        listDataItems.add(France);
        
        System.out.println("List of DataItems Reference to claims: "+listDataItems);

        sourceList.addAll(Arrays.asList("S1", "S2", "S3", "S4", "S5", "S6", "S7"));
        claimList.addAll(Arrays.asList("Medvedev", "Putin", "Yeltsin", "Clinton", "Obama", "Hollande", "Sarcozy"));

        //Calculating Vs score for each sources and saving that in a list
        
        for(int i=0; i<scores.length; i++){
            int Vs = 0;
            for(int j=0; j<scores[i].length; j++){
                //System.out.print(scores[i][j]+" ");
                if(scores[i][j]==1){
                    Vs++;
                }
            }
            Vs_count_List.add(Vs);
            //System.out.println();
        }
        System.out.println("Vs count list:"+Vs_count_List);
       
        for(int i=0; i<scores[0].length; i++){
            int sourceCount = 0;
            for(int j=0; j<scores.length; j++){
                if(scores[j][i]==1){
                    sourceCount++;
                }
            }
            Sv_count_List.add(sourceCount);
        }
        
        System.out.println("Sv count List :"+Sv_count_List);
        double factor = 0.5; //initial claim values
        
        //Lets initialize all the source witn 0.8 scores.
        /*double source_init_value = 0.8;
        for(int i=0; i<scores.length; i++){
            To_S.add(source_init_value);
        }*/
        
        //Sum and AvgLog
        
        boolean isAvgLog = false;
        factor = 0.5;
        //Sum.SumFactFinder(factor, isAvgLog);
        isAvgLog = true;// Enable Avg Log
        //Sum.SumFactFinder(factor, isAvgLog);
        
        //Investment
        //Investment.investmentCalculation();
        //GeneralUtils.showMatrix(scores);
        //Investment.pooledInvestmentCalculation();
       //System.out.println("********************************************To_S:"+To_S);
        
        //Truth Finder
        //TruthFinder.truthFinderFunction();
        
        //System.out.println("Sources List for Claim:"+GeneralUtils.sourceListforDataItem(6));
        
        //System.out.println("All claims List for Data Items pointed by Source:"+GeneralUtils.claimsListforDataItemGivenSourceID(1));
        
        //Cosine
        //Cosine.cosineFunctionCalculation();
        
        //TwoEstimates 
        //TwoEstimates.twoEstimatesFunction();
    }
}
