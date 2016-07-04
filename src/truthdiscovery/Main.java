/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truthdiscovery;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import utils.FileLoader;
import utils.GeneralUtils;
import utils.StaticDataLoader;

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
    
    //Contains all the data items / symbol names of stock and saved on DataItemListFromFile list
    public static ArrayList<String> GroundTruthDataItemList = new ArrayList<String>();

    /* A 2D matrix stores the relationship between sources and claims.
     Rows are sources and columns are claims. 1 means there is a connection between source and claims.
     */
    //public static double [][] scores = new double[7][7];
    
    public static ArrayList<ArrayList<Double>> scores = new ArrayList<ArrayList<Double>>();
    
    public static int row;
    public static int col;

    public static ArrayList<Integer> Vs_count_List = new ArrayList<Integer>();
    //Calculating the number of sources for particular claims and store them in a list named numberSourcesforClaimsList
    public static ArrayList<Integer> Sv_count_List = new ArrayList<Integer>();
    //public static ArrayList<Double> To_S = new ArrayList<Double>();
    
    public static void callAllFunctions(){
        //All the algorithms provided below, expcet the ones having notes, converges
        //within fixed iteration.
        
        double Cv_0 = 0; //initial claim values
        double Ts_0 = 0;
        double Tv_0 = 0;
        
        System.out.println("\n\n**********************SUM*********************************************************************\n\n");
        
        Cv_0 = 0.5;
        boolean isAvgLog = false;
        Sum.SumFactFinder(Cv_0, isAvgLog);
        
        System.out.println("\n\n*******************AVERAGE LOG*****************************************************************\n\n");
        
        Cv_0 = 0.5;
        isAvgLog = true;// Enable Avg Log
        Sum.SumFactFinder(Cv_0, isAvgLog);

        System.out.println("\n\n********************INVESTMENT*****************************************************************\n\n");
        //Investment
        Ts_0 = 0.8;
        Investment.investmentCalculation(Ts_0);
      
        System.out.println("\n\n*******************POOLED INVESTMENT***********************************************************\n\n");
        //Pooled Investment
        int iter = 5;
        Ts_0 = 0.8;
        Investment.pooledInvestmentCalculation(Ts_0, iter);
        
        System.out.println("\n\n**********************TRUTH FINDER**************************************************************\n\n");
        //Truth Finder
        Ts_0 = 0.8;
        TruthFinder.truthFinderFunction(Ts_0);
        
        System.out.println("\n\n**************************COSINE****************************************************************\n\n");
        //Cosine
        //Converges after 5000 iteration
        iter = 2500;
        Ts_0 = 0.8;
        Cv_0 = 1;
        Cosine.cosineFunctionCalculation(Ts_0, Cv_0, iter);
       
        System.out.println("\n\n**************************TWO ESTIMATES*********************************************************\n\n");
        //TwoEstimates
        //Doesn't converge. Source and Claims both oscilates in two sets of results
        iter = 600;
        Ts_0 = 0.8;
        TwoEstimates.twoEstimatesFunction(Ts_0, iter);

        System.out.println("\n\n**************************THREE ESTIMATES*******************************************************\n\n");
        //Three Estimates
        //Calculation breaks if Ts_0 = 1 is used.
        //Claims converges, though Sources don't converge rather oscilates between two sets of results.
        iter = 25;
        Ts_0 = 0.8;
        Tv_0 = 0.9;
        ThreeEstimates.ThreeEstimatesFunction(Ts_0, Tv_0, iter);
        
    }
    
    public static void main(String[] args) throws InterruptedException, IOException {
        if(args[0].equals("-d")){
            FileLoader.loadCleanFile("Data/clean_stock/ground_truth_nasdaq.txt","Data/clean_stock/stock-2011-07-01.csv");       
        }else if(args[0].equals("-sd")){
            StaticDataLoader.loadStaticData();
        }
        
        
        System.out.println("Scores Length: "+scores.size());
        /*double Cv_0 = 0.5;
        boolean isAvgLog = false;
        Sum.SumFactFinder(Cv_0, isAvgLog);*/
        
        double Cv_0 = 0; //initial claim values
        double Ts_0 = 0;
        double Tv_0 = 0;
        int iter = 0; 

        switch(args[1]){
            case "1":   System.out.println("\n\n**************************************************SUM*********************************************************************\n\n");
                        Thread.sleep(4000);
                        Cv_0 = 0.5;
                        boolean isAvgLog = false;
                        Sum.SumFactFinder(Cv_0, isAvgLog);
                        break;
            
            case "2":   System.out.println("\n\n***********************************************AVERAGE LOG*****************************************************************\n\n");
                        Thread.sleep(4000);
                        Cv_0 = 0.5;
                        isAvgLog = true;// Enable Avg Log
                        Sum.SumFactFinder(Cv_0, isAvgLog);
                        break;
            
            case "3":   System.out.println("\n\n************************************************INVESTMENT*****************************************************************\n\n");
                        Thread.sleep(4000);
                        //Investment
                        Ts_0 = 0.8;
                        Investment.investmentCalculation(Ts_0);
                        break;
                
            case "4":   System.out.println("\n\n**********************************************POOLED INVESTMENT***********************************************************\n\n");
                        Thread.sleep(4000);
                        //Pooled Investment
                        iter = 5;
                        Ts_0 = 0.8;
                        Investment.pooledInvestmentCalculation(Ts_0, iter);
                        break;
                
            case "5":   System.out.println("\n\n************************************************TRUTH FINDER**************************************************************\n\n");
                        Thread.sleep(4000);
                        //Truth Finder
                        Ts_0 = 0.8;
                        TruthFinder.truthFinderFunction(Ts_0); 
                        break;
                
            case "6" :  System.out.println("\n\n***************************************************COSINE****************************************************************\n\n");
                        Thread.sleep(4000);
                        //Cosine
                        //Converges after ~2500 iteration
                        iter = 2500;
                        Ts_0 = 0.8;
                        Cv_0 = 1;
                        Cosine.cosineFunctionCalculation(Ts_0, Cv_0, iter);
                        break;
                             
            case "7":   System.out.println("\n\n************************************************TWO ESTIMATES*********************************************************\n\n");
                        Thread.sleep(4000);
                        //TwoEstimates
                        //Doesn't converge. Source and Claims both oscilates in two sets of results
                        iter = 600;
                        Ts_0 = 0.8;
                        TwoEstimates.twoEstimatesFunction(Ts_0, iter);
                        break;
            
            case "8" :  System.out.println("\n\n***********************************************THREE ESTIMATES*******************************************************\n\n");
                        Thread.sleep(4000);
                        //Three Estimates
                        //Calculation breaks if Ts_0 = 1 is used.
                        //Claims converges, though Sources don't converge rather oscilates between two sets of results.
                        iter = 15;
                        Ts_0 = 0.8;
                        Tv_0 = 0.9;
                        ThreeEstimates.ThreeEstimatesFunction(Ts_0, Tv_0, iter);
                        break; 
              
            case "9":   System.out.println("\n\n*************************************************Running All*******************************************************\n\n");
                        Thread.sleep(4000);
                        Main.callAllFunctions();
                        break;
                
            default:  System.out.println("Wrong Choice");
        }
    }
}
